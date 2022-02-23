package ca.skynetcloud.alloutpower;


import ca.skynetcloud.alloutpower.client.util.config.AllOutPowerConfig;
import ca.skynetcloud.alloutpower.common.items.ItemInit;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod("alloutpower")
public class AllOutPowerMain {

    // Directly reference a log4j logger.
    public static final String MODID = "alloutpower";

    public static AllOutPowerMain instance;

    private static final Logger LOGGER = LogManager.getLogger();

    public AllOutPowerMain() {
        instance = this;

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AllOutPowerConfig.CONFIG_SPEC);

        if(!AllOutPowerConfig.Config.enabled.get())
            return;


        MinecraftForge.EVENT_BUS.register(this);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemInit.ITEMS.register(modEventBus);
        ItemInit.ENCHANTMENT.register(modEventBus);

    }

    private void setup(final FMLCommonSetupEvent event) {

    }


}
