package org.hiedacamellia.shippingcontainer;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.hiedacamellia.shippingcontainer.client.event.SCClientEvent;


@Mod(value = ShippingContainer.MODID,dist = Dist.CLIENT)
public class ShippingContainerClient
{

    public ShippingContainerClient(IEventBus modEventBus, ModContainer modContainer)
    {
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        modEventBus.addListener(SCClientEvent::regTooltipComponents);
    }


}
