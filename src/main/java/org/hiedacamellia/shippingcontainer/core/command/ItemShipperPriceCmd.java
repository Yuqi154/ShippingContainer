package org.hiedacamellia.shippingcontainer.core.command;

import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.hiedacamellia.shippingcontainer.common.menu.ItemShipperPriceMenu;
import org.hiedacamellia.shippingcontainer.core.config.json.ItemShipperPrice;

@EventBusSubscriber
public class ItemShipperPriceCmd {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("mystiasizakaya").then(Commands.literal("price_addon")
                .then(Commands.literal("open").executes(
                        context -> {
                            ServerPlayer serverPlayer = context.getSource().getPlayer();
                            if (serverPlayer != null) {
                                serverPlayer.openMenu(new SimpleMenuProvider(ItemShipperPriceMenu::new,Component.empty()));
                            }
                            return 1;
                        }
                        ))
                .then(Commands.literal("reload").executes(
                        context -> {
                            ServerPlayer serverPlayer = context.getSource().getPlayer();
                            if (serverPlayer != null) {
                                if(serverPlayer.hasPermissions(4)){
                                    ItemShipperPrice.reload();
                                }
                            }
                            return 1;
                        }
                ))
        ));
    }
}
