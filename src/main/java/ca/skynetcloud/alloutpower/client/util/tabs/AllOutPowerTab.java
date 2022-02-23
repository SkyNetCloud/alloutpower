package ca.skynetcloud.alloutpower.client.util.tabs;

import ca.skynetcloud.alloutpower.common.items.ItemInit;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class AllOutPowerTab {

    public static final CreativeModeTab MAIN = new CreativeModeTab("all_out_power_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack((ItemLike) ItemInit.Soul_Keeper.get());
        }
    };
}
