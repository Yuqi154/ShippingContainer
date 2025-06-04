package org.hiedacamellia.shippingcontainer.data.lang;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.hiedacamellia.shippingcontainer.ShippingContainer;
import org.hiedacamellia.shippingcontainer.registries.SCBlock;

public class EnglishLanguageProvider extends LanguageProvider {

    public EnglishLanguageProvider(PackOutput output) {
        super(output, ShippingContainer.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {

        add(SCBlock.CONTAINER.get(),"Shipping Container");

        add("item_group.shippingcontainer.main", "Shipping Container");

        add("gui.shipping_container.title", "Shipping Container");
        add("gui.shipping_container.ship_time", "Will Ship In %d Seconds");
        add("gui.shipping_container.ready", "Ready To Ship");

        add("gui.shippingcontainer.item_shipper_price.title", "Item Shipper Price Modifier");

        add("gui.shippingcontainer.shift","Press Shift to view price");

        add("shippingcontainer.configuration.shipping_time", "Shipping Time");
    }
}
