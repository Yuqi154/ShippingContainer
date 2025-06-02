package org.hiedacamellia.shippingcontainer.registries;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import org.hiedacamellia.shippingcontainer.client.screen.ContainerScreen;
import org.hiedacamellia.shippingcontainer.client.screen.ItemShipperPriceScreen;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SCScreen {
	@SubscribeEvent
	public static void clientLoad(RegisterMenuScreensEvent event) {
		event.register(SCMenu.CONTAINER_UI.get(), ContainerScreen::new);
		event.register(SCMenu.SHIPPER_PRICE_UI.get(), ItemShipperPriceScreen::new);
	}
}
