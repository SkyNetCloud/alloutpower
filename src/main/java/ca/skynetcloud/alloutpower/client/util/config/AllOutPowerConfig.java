package ca.skynetcloud.alloutpower.client.util.config;

import ca.skynetcloud.alloutpower.AllOutPowerMain;

import static net.minecraft.world.item.Rarity.RARE;
import static net.minecraftforge.common.ForgeConfigSpec.*;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AllOutPowerConfig {

    public static final ForgeConfigSpec CONFIG_SPEC;
    public static final Config CONFIG;
    private static final String CONFIG_PREFIX = "gui." + AllOutPowerMain.MODID + ".config.";

    static {
        final Pair<Config, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config::new);
        CONFIG_SPEC = specPair.getRight();
        CONFIG = specPair.getLeft();
    }
    public static void bake(){
        Enchantment.bake();
    }

    public static class Enchantment {
        public static Rarity rarity = RARE;
        public static int levels = 1;
        public static boolean isTreasure = false;
        public static boolean isVillagerTrade = true;
        public static boolean isLootable = true;
        public static boolean canApplyAtEnchantingTable = true;
        public static boolean canApplyOnBooks = true;
        public static int minEnchantabilityBase = 15;
        public static int minEnchantabilityPerLevel = 5;
        public static Set<String> incompatibleEnchantments = new HashSet<>();

        public static void bake() {
            rarity = Config.Rarity.get();
            levels = Config.levels.get();
            isTreasure = Config.isTreasure.get();
            isVillagerTrade = Config.isVillagerTrade.get();
            isLootable = Config.isLootable.get();
            canApplyAtEnchantingTable = Config.canApplyAtEnchantingTable.get();
            canApplyOnBooks = Config.canApplyOnBooks.get();
            minEnchantabilityBase = Config.minEnchantabilityBase.get();
            minEnchantabilityPerLevel = Config.minEnchantabilityPerLevel.get();
            incompatibleEnchantments.clear();

            for (String enchantment : Config.incompatibleEnchantments.get()) {

                if (ForgeRegistries.ENCHANTMENTS.containsKey(new ResourceLocation(enchantment))) {
                    incompatibleEnchantments.add(enchantment);
                }
            }
        }
    }

    public static class Config {

        public static DoubleValue additiveDurabilityDrop;
        public static DoubleValue maximumDurabilityDrop;
        public static DoubleValue minimumDurabilityDrop;
        public static DoubleValue modeDurabilityDrop;
        public static DoubleValue dropLevel;
        public static DoubleValue additiveDropChance;
        public static BooleanValue durabilityDrop;
        public static BooleanValue breakItemOnZeroDurability;
        public static EnumValue<Rarity> Rarity;
        public static IntValue levels;
        public static BooleanValue isTreasure;
        public static BooleanValue isVillagerTrade;
        public static BooleanValue isLootable;
        public static BooleanValue canApplyAtEnchantingTable;
        public static BooleanValue canApplyOnBooks;
        public static IntValue minEnchantabilityBase;
        public static IntValue minEnchantabilityPerLevel;
        public static ForgeConfigSpec.ConfigValue<List<? extends String>> incompatibleEnchantments;

        public Config(ForgeConfigSpec.Builder builder){

        }

    }
}
