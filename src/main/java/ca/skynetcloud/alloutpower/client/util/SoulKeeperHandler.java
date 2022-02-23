package ca.skynetcloud.alloutpower.client.util;



import ca.skynetcloud.alloutpower.client.util.config.AllOutPowerConfig;
import ca.skynetcloud.alloutpower.common.items.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ItemLike;
import org.apache.commons.compress.utils.Lists;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoulKeeperHandler {

    private static final HashMap<Player, SoulKeeperHandler> handlerMap = new HashMap<>();
    public static final String soulboundTag = "SoulboundItems";
    public static final String storedStacksTag = "StoredStacks";
    public static final String soundTag = "playBrokeSound";
    public static final String stackTag = "Stack";
    public final Player player;
    public static boolean warn = true;

    public static SoulKeeperHandler getOrCreateSoulboundHandler(Player player) {
        if (getSoulboundHandler(player) != null)
            return getSoulboundHandler(player);
        else
            return createSoulboundHandler(player);
    }

    @Nullable
    public static SoulKeeperHandler getSoulboundHandler(Player player) {
        return handlerMap.get(player);
    }

    public static SoulKeeperHandler createSoulboundHandler(Player player) {
        SoulKeeperHandler newHandler = new SoulKeeperHandler(player);
        handlerMap.put(player, newHandler);
        return newHandler;
    }

    public static boolean hasStoredDrops(Player player) {
        return hasSerializedDrops(player);
    }

    private SoulKeeperHandler(Player playerIn) {
        this.player = playerIn;
    }

    public void retainDrops(Collection<ItemEntity> eventDrops) {
        List<ItemEntity> retainedDrops = Lists.newArrayList();
        for (ItemEntity eventDrop : eventDrops) {
            ItemStack item = eventDrop.getItem();
            if (item.isEnchanted() && EnchantmentHelper.getEnchantments(item).containsKey(ItemInit.Soul_Keeper.get())) {
                int level = EnchantmentHelper.getItemEnchantmentLevel(ItemInit.Soul_Keeper.get(), item);
                double chance = AllOutPowerConfig.CONFIG.saveChance.get() + (AllOutPowerConfig.CONFIG.additiveSaveChance.get() * (level - 1));
                double rng = Math.random();
                if (rng < chance) {
                    retainedDrops.add(eventDrop);
                }
            }
        }

        retainedDrops.forEach(dropItem -> {
            eventDrops.remove(dropItem);
        });

        this.serializeDrops(retainedDrops);
    }

    private void serializeDrops(Collection<ItemEntity> drops) {
        CompoundTag soulData = new CompoundTag();
        soulData.putInt(storedStacksTag, drops.size());
        int counter = 0;

        for (ItemEntity drop : drops) {
            ItemStack stack = this.itemEditor(drop.getItem()).copy();
            if (stack != null) {
                CompoundTag serializedStack = stack.serializeNBT();
                soulData.put(stackTag + counter, serializedStack);
                counter++;
            }
        }

        this.player.getPersistentData().put(soulboundTag, soulData);
    }

    private static boolean hasSerializedDrops(Player player) {
        return player.getPersistentData().contains(soulboundTag);
    }


    private List<ItemStack> deserializeDrops() {
        List<ItemStack> deserialized = Lists.newArrayList();
        CompoundTag soulData = this.player.getPersistentData().getCompound(soulboundTag);
        int counter = soulData.getInt(storedStacksTag) - 1;

        for (int c = counter; c >= 0; c--) {
            CompoundTag nbt = soulData.getCompound(stackTag + c);
            ItemStack stack = ItemStack.of(nbt);

            if (!stack.isEmpty()) {
                deserialized.add(stack);
            }

            soulData.remove(stackTag + c);
        }

        this.player.getPersistentData().remove(soulboundTag);
        return deserialized;
    }

    private ItemStack itemEditor(ItemStack item) {
        int level = EnchantmentHelper.getItemEnchantmentLevel(ItemInit.Soul_Keeper.get(), item);
        if (AllOutPowerConfig.CONFIG.durabilityDrop.get()) {
            double minimum = AllOutPowerConfig.CONFIG.minimumDurabilityDrop.get() - (AllOutPowerConfig.CONFIG.additiveDurabilityDrop.get() * (level - 1));
            if (minimum < 0) {
                minimum = 0;
            }
            double maximum = AllOutPowerConfig.CONFIG.maximumDurabilityDrop.get() - (AllOutPowerConfig.CONFIG.additiveDurabilityDrop.get() * (level - 1));
            if (maximum < 0) {
                maximum = 0;
            }
            double mode = AllOutPowerConfig.CONFIG.modeDurabilityDrop.get() - (AllOutPowerConfig.CONFIG.additiveDurabilityDrop.get() * (level - 1));
            if (mode < 0) {
                mode = 0;
            }

            int newDurability = (int) (item.getMaxDamage() * this.triangularDistribution(minimum, maximum, mode));
            if (item.hurt(newDurability, this.player.getRandom(), (ServerPlayer) this.player)) {
                if (this.player instanceof Player) {
                    this.player.awardStat(Stats.ITEM_BROKEN.get(item.getItem()));
                }

                if (AllOutPowerConfig.CONFIG.breakItemOnZeroDurability.get()) {
                    item.setDamageValue(item.getMaxDamage());
                    return item;
                }
                item.setDamageValue(item.getMaxDamage() - 1);
            }

        }
        double chance = AllOutPowerConfig.CONFIG.dropLevel.get() - (AllOutPowerConfig.CONFIG.additiveDropChance.get() * (level - 1));
        if (!(Math.random() < chance))
            return item;
        if (level > 1) {
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(item);
            enchantments.remove(ItemInit.Soul_Keeper.get());
            enchantments.put(ItemInit.Soul_Keeper.get(), level - 1);
            EnchantmentHelper.setEnchantments(enchantments, item);
        } else {
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(item);
            enchantments.remove(ItemInit.Soul_Keeper.get());

            EnchantmentHelper.setEnchantments(enchantments, item);
        }
        return item;
    }

    public double triangularDistribution(double a, double b, double c) {
        double F = (c - a) / (b - a);
        double rand = Math.random();
        if (rand < F)
            return a + Math.sqrt(rand * (b - a) * (c - a));
        else
            return b - Math.sqrt((1 - rand) * (b - a) * (b - c));
    }

    public void transferItems(Player rebornPlayer) {
        List<ItemStack> retainedDrops = this.deserializeDrops();

        if (retainedDrops.isEmpty())
            return;
        for (ItemStack item : retainedDrops) {
            rebornPlayer.addItem(item);
        }

        handlerMap.remove(this.player);
    }
}
