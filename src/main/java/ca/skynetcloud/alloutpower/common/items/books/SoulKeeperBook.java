package ca.skynetcloud.alloutpower.common.items.books;



import ca.skynetcloud.alloutpower.client.util.config.AllOutPowerConfig;
import ca.skynetcloud.alloutpower.common.items.ItemInit;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nonnull;


public class SoulKeeperBook extends Enchantment{

    public SoulKeeperBook() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR,
                new EquipmentSlot[] { EquipmentSlot.MAINHAND, EquipmentSlot.CHEST });
    }

    @Nonnull
    @Override
    public Rarity getRarity() {
        return AllOutPowerConfig.Enchantment.rarity;
    }

    @Override
    public int getMaxLevel() {
        return AllOutPowerConfig.Enchantment.maxLevels;
    }

    @Override
    public int getMinCost(int enchantmentLevel) {
        return AllOutPowerConfig.Enchantment.minEnchantabilityBase
                + AllOutPowerConfig.Enchantment.minEnchantabilityPerLevel * (enchantmentLevel - 1);
    }

    @Override
    public int getMaxCost(int enchantmentLevel) {
        return 50;
    }


    @Override
    public boolean isTreasureOnly() {
        return AllOutPowerConfig.Enchantment.isTreasure;
    }

    @Override
    public boolean isTradeable() {
        return AllOutPowerConfig.Enchantment.isVillagerTrade;
    }

    @Override
    public boolean isDiscoverable() {
        return AllOutPowerConfig.Enchantment.isLootable;
    }

    @Override
    protected boolean checkCompatibility(Enchantment ench) {
        ResourceLocation rl = ench.getRegistryName();

        if (rl != null && AllOutPowerConfig.Enchantment.incompatibleEnchantments.contains(rl.toString())) {
            return false;
        }
        return super.checkCompatibility(ench);
    }

    @Override
    public boolean canApplyAtEnchantingTable(@Nonnull ItemStack stack) {
        return super.canApplyAtEnchantingTable(stack) && AllOutPowerConfig.Enchantment.canApplyAtEnchantingTable;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return AllOutPowerConfig.Enchantment.canApplyOnBooks;
    }
}
