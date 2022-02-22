package ca.skynetcloud.alloutpower.client.event;

import ca.skynetcloud.alloutpower.client.util.SoulKeeperHandler;
import ca.skynetcloud.alloutpower.common.items.books.SoulKeeperBook;
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
            SoulKeeperHandler.getOrCreateSoulKeeperHandler((Player) event.getEntityLiving()).retainPlayerDrops(event.getDrops());
        }
    }
    @SubscribeEvent
    public static void ItenMoveEvent(PlayerEvent.Clone event){
            if(event.isWasDeath()){
                Player deadplayer = event.getOriginal();
                if(SoulKeeperHandler.hasStoredDrops(deadplayer)){
                    SoulKeeperHandler.getOrCreateSoulKeeperHandler(deadplayer).transferPlayerItems(event.getPlayer());
                } else if (SoulKeeperHandler.hasStoredDrops(event.getPlayer())){
                    SoulKeeperHandler.getOrCreateSoulKeeperHandler(event.getPlayer()).transferPlayerItems(event.getPlayer());
                }
            }
    }
}
