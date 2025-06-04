package org.hiedacamellia.shippingcontainer.core.event;


import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.hiedacamellia.shippingcontainer.core.config.json.ItemShipperPrice;

@EventBusSubscriber
public class SCPlayerEvent {


    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if(player instanceof ServerPlayer serverPlayer){
            ItemShipperPrice.sync(serverPlayer);
        }
    }
}
