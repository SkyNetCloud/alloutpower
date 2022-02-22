package ca.skynetcloud.alloutpower.client.event;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SoulKeeperEvent {

    @SubscribeEvent
    public static void retrievalSystemEvent(LivingDropsEvent event){
        if(event.getEntity() instanceof Player){

        }
    }
    @SubscribeEvent
    public static void ItenMoveEvent(PlayerEvent.Clone event){
            if(event.isWasDeath()){


            }

    }
}
