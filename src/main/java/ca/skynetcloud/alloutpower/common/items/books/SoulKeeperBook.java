package ca.skynetcloud.alloutpower.common.items.books;



import ca.skynetcloud.alloutpower.client.util.config.AllOutPowerConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import javax.annotation.Nonnull;

public class SoulKeeperBook extends Enchantment {

     public SoulKeeperBook(){
     super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR, new EquipmentSlot[] {
             EquipmentSlot.MAINHAND, EquipmentSlot.CHEST
     });
    }


    @Override
    public int getMaxLevel() {
        return AllOutPowerConfig.Enchantment.levels;
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
