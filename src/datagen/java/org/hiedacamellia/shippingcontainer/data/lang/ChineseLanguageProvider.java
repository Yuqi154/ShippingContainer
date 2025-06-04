package org.hiedacamellia.shippingcontainer.data.lang;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.hiedacamellia.shippingcontainer.ShippingContainer;
import org.hiedacamellia.shippingcontainer.registries.SCBlock;

public class ChineseLanguageProvider extends LanguageProvider {

    public ChineseLanguageProvider(PackOutput output) {
        super(output, ShippingContainer.MODID, "zh_cn");
    }

    @Override
    protected void addTranslations() {

        add(SCBlock.CONTAINER.get(),"出货箱");

        add("item_group.shippingcontainer.main", "出货箱");

        add("gui.shipping_container.title", "出货箱");
        add("gui.shipping_container.ship_time", "回收于: %d 秒");
        add("gui.shipping_container.ready", "准备就绪");

        add("gui.shippingcontainer.item_shipper_price.title", "出货箱价格设置");

        add("gui.shippingcontainer.shift","按住Shift查看价格");

        add("shippingcontainer.configuration.shipping_time", "出货箱回收时间");
    }
}
