package org.hiedacamellia.shippingcontainer.registries;

import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.hiedacamellia.shippingcontainer.ShippingContainer;

public class SCItem {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ShippingContainer.MODID);

    public static final DeferredItem<BlockItem> CONTAINER = ITEMS.registerSimpleBlockItem(SCBlock.CONTAINER);
}
