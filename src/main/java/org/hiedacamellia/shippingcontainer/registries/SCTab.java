package org.hiedacamellia.shippingcontainer.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.hiedacamellia.shippingcontainer.ShippingContainer;

public class SCTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ShippingContainer.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SHIPPING_CONTAINER = CREATIVE_MODE_TABS.register(ShippingContainer.MODID,
            () -> CreativeModeTab.builder().title(Component.translatable("item_group.shippingcontainer.main"))
                    .icon(() -> new ItemStack(SCItem.CONTAINER.get())).displayItems((parameters, tabData) -> {
                        for (DeferredHolder<Item, ? extends Item> item: SCItem.ITEMS.getEntries()){
                            tabData.accept(item.get());
                        }
                    })
                    .build());
}
