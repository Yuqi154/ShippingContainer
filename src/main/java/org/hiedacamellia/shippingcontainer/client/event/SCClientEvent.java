package org.hiedacamellia.shippingcontainer.client.event;


import com.mojang.datafixers.util.Either;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import org.hiedacamellia.shippingcontainer.client.widget.ClientItemPriceTooltip;
import org.hiedacamellia.shippingcontainer.client.widget.ItemPriceTooltip;
import org.hiedacamellia.shippingcontainer.core.config.json.ItemShipperPrice;

import java.util.List;

@EventBusSubscriber(value = Dist.CLIENT)
public class SCClientEvent {

    public static void regTooltipComponents(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(ItemPriceTooltip.class, ClientItemPriceTooltip::new);
    }

    @SubscribeEvent
    public static void onTooltip(RenderTooltipEvent.GatherComponents event){

        if(Screen.hasShiftDown()) {
            ItemStack stack = event.getItemStack();
            if (ItemShipperPrice.hasPrice(stack)) {
                List<Either<FormattedText, TooltipComponent>> components = event.getTooltipElements();
                components.add(Either.right(new ItemPriceTooltip(ItemShipperPrice.getPrice(stack))));
            }
        }else {
            ItemStack stack = event.getItemStack();
            if (ItemShipperPrice.hasPrice(stack)) {
                List<Either<FormattedText, TooltipComponent>> components = event.getTooltipElements();
                components.add(Either.left(Component.translatable("gui.shippingcontainer.shift")));
            }
        }

    }

}
