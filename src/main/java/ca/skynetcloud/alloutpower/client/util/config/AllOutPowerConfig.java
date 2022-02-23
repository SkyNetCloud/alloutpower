package ca.skynetcloud.alloutpower.client.util.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.skynetcloud.alloutpower.AllOutPowerMain;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import static net.minecraft.world.item.enchantment.Enchantment.Rarity.RARE;

import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

import java.util.List;

public class AllOutPowerConfig {


    public static final ForgeConfigSpec CONFIG_SPEC;
    public static final Config CONFIG;
    private static final String CONFIG_PREFIX = "gui." + AllOutPowerMain.MODID + ".config.";

    static {
        final Pair<Config, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config::new);
        CONFIG_SPEC = specPair.getRight();
        CONFIG = specPair.getLeft();
    }

    public static class Enchantment {
        public static Rarity rarity = RARE;

        public static int maxLevels = 3;
        public static boolean isTreasure = false;
        public static boolean isVillagerTrade = true;
        public static boolean isLootable = true;
        public static boolean canApplyAtEnchantingTable = true;
        public static boolean canApplyOnBooks = true;
        public static int minEnchantabilityBase = 15;
        public static int minEnchantabilityPerLevel = 5;
        public static Set<String> incompatibleEnchantments = new HashSet<>();

        public static void bake() {
            rarity = Config.rarity.get();
            maxLevels = Config.maxLevels.get();
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


        public static ForgeConfigSpec.BooleanValue enabled;


        //#region enchantmentConfigInfo
        public static DoubleValue additiveDurabilityDrop;
        public static DoubleValue maximumDurabilityDrop;
        public static DoubleValue additiveDropChance;
        public static DoubleValue minimumDurabilityDrop;
        public static DoubleValue modeDurabilityDrop;
        public static DoubleValue dropLevel;
        public static DoubleValue additiveSaveChance;
        public static DoubleValue saveChance;
        public static BooleanValue durabilityDrop;
        public static BooleanValue breakItemOnZeroDurability;
        public static EnumValue<Rarity> rarity;
        public static IntValue maxLevels;
        public static BooleanValue isTreasure;
        public static BooleanValue isVillagerTrade;
        public static BooleanValue isLootable;
        public static BooleanValue canApplyAtEnchantingTable;
        public static BooleanValue canApplyOnBooks;
        public static IntValue minEnchantabilityBase;
        public static IntValue minEnchantabilityPerLevel;
        public static ForgeConfigSpec.ConfigValue<List<? extends String>> incompatibleEnchantments;
        //#endregion

        public Config(ForgeConfigSpec.Builder builder)  {

            builder.comment("All Out Power Config");

            enabled = builder
                    .comment("If the mod is enabled.")
                    .translation("all_out_power.config.enabled")
                    .define("enableMod", true);

            builder.push("Basic Enchantment Values");

            canApplyAtEnchantingTable = builder
                    .comment("If the enchantment can be obtained on items through an enchanting table.")
                    .translation("all_out_power.config.enchantable")
                    .define("enchantable", true);


            maxLevels = builder
                    .comment("Maximum level the enchant can be.")
                    .translation("all_out_power.config.maximum_level")
                    .defineInRange("maximum_level", 3, 1, 32767);

            rarity = builder
                    .comment(("How rare the enchantment is at the enchantment table (some loot spawns included).\n" +
                            "Acceptable values are listed below.\n" +
                            "COMMON\n" +
                            "UNCOMMON\n" +
                            "RARE\n" +
                            "VERY RARE"))
                    .translation("all_out_power.config.rarity")
                    .defineEnum("rarity", Rarity.VERY_RARE);

            builder.pop();
            builder.push("Level Drop Values");

            dropLevel = builder
                    .comment("Chance for the enchant to drop down 1 level on death from 0.00 to 1.00.")
                    .translation("all_out_power.config.drop_level")
                    .defineInRange("drop_level", 1.0, 0.0, 1.0);

            additiveDropChance = builder
                    .comment("Chance for enchant to drop down 1 level with every added level\n" +
                            "So if someone with Soulbound 3 dies the chance of the enchant being downgraded would be:\n" +
                            "(dropLevel) - ((level - 1) * additiveDropChance)\n" +
                            "Remember if you've set a lot of levels this could lead to the higher levels never dropping down")
                    .translation("all_out_power.config.additive_drop_chance")
                    .defineInRange("additive_drop_chance", 0.0, 0.0, 1.0);

            builder.pop();
            builder.push("Save Chance Values");

            additiveSaveChance = builder
                    .comment("Chance for item to be kept from 0.00 to 1.00 with every added level.\n" +
                            "So if someone with Soulbound 3 dies their chances of keeping the item would be:\n" +
                            ("(saveChance) + ((level - 1) * additiveSaveChance)\n" +
                                    "Remember if you've set a lot of levels this could lead to the higher levels always being saved."))
                    .translation("all_out_power.config.additive_save_chance")
                    .defineInRange("additive_save_chance", 0.0, 0.0, 1.0);

            saveChance = builder // done
                    .comment("Chance for item with enchant to be kept from 0.00 to 1.00.")
                    .translation("all_out_power.config.save_chance")
                    .defineInRange("save_chance", 1.0, 0.0, 1.0);

            builder.pop();
            builder.push("Durability Drop Values");

            durabilityDrop = builder
                    .comment("Set whether durability drop is enabled or not. Durability drop is calculated with the min and max \n"
                            + "variables. The values chosen will most likely be in the middle and get rarer towards the ends. (triangular distribution)")
                    .translation("soulbound.config.durability_drop")
                    .define("durability_drop", false);

            breakItemOnZeroDurability = builder
                    .comment("If set to true, the item will be broken if the durability reaches 0 on it's durabilityDrop")
                    .translation("soulbound.config.break_item_on_zero_durability")
                    .define("break_item_on_zero_durability", false);

            additiveDurabilityDrop = builder
                    .comment("Subtracts this number from the max, min, and mode each level effectively making the durability \n" +
                            "drop go down the higher the level")
                    .translation("soulbound.config.additive_durability_drop")
                    .defineInRange("additive_durability_drop", 0.05, 0.0, 1.0);

            maximumDurabilityDrop = builder
                    .comment("Defines the minimum percentage that the durability goes down when returned (this percentage is of\n" +
                            "the items max durability NOT the actual durability)")
                    .translation("soulbound.config.maximum_durability_drop")
                    .defineInRange("maximum_durability_drop", 0.35, 0.0, 1.0);

            minimumDurabilityDrop = builder
                    .comment("Defines the maximum percentage that the durability goes down when returned (this percentage is of\n" +
                            "the items max durability NOT the actual durability)")
                    .translation("soulbound.config.minimum_durability_drop")
                    .defineInRange("minimum_durability_drop", 0.20, 0.0, 1.0);

            modeDurabilityDrop = builder
                    .comment("Defines the mode (average value) percentage that the durability goes down when returned. This value\n" +
                            "cannot be more than the maximum or less than the minimum. (this percentage is of\n" +
                            "the items max durability NOT the actual durability)")
                    .translation("soulbound.config.mode_durability_drop")
                    .defineInRange("mode_durability_drop", 0.25, 0.0, 1.0);
        }

    }

}

