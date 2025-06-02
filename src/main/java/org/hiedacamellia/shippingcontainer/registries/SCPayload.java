package org.hiedacamellia.shippingcontainer.registries;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.hiedacamellia.shippingcontainer.core.network.ItemShipperPriceSyncBiMessage;
import org.hiedacamellia.shippingcontainer.core.network.ResetShipC2SMessage;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class SCPayload {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1.0.0");

        registrar.playBidirectional(
                ItemShipperPriceSyncBiMessage.TYPE,
                ItemShipperPriceSyncBiMessage.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        ItemShipperPriceSyncBiMessage::handleClient,
                        ItemShipperPriceSyncBiMessage::handleServer
                )
        );
        registrar.playToServer(
                ResetShipC2SMessage.TYPE,
                ResetShipC2SMessage.STREAM_CODEC,
                ResetShipC2SMessage::handleServer
        );



    }
}
