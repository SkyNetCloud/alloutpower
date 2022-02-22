package ca.skynetcloud.alloutpower.common.items;

import ca.skynetcloud.alloutpower.AllOutPowerMain;
import ca.skynetcloud.alloutpower.common.items.books.SoulKeeperBook;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
   public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AllOutPowerMain.MODID);
   public static final DeferredRegister<Enchantment> ENCHANTMENT = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, AllOutPowerMain.MODID);

   public static RegistryObject<Enchantment> Soul_Keeper = ENCHANTMENT.register("soul_keeper", () -> new SoulKeeperBook());

}
