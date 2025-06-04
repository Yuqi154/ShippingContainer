package org.hiedacamellia.shippingcontainer.client.widget;


import net.minecraft.world.inventory.tooltip.TooltipComponent;
import org.hiedacamellia.shippingcontainer.core.util.ItemExchangeUtil;

public class ItemPriceTooltip implements TooltipComponent {

    private final ItemExchangeUtil.DataRaw dataRaw;

    public ItemPriceTooltip(ItemExchangeUtil.DataRaw dataRaw) {
        this.dataRaw = dataRaw;
    }

    public ItemExchangeUtil.DataRaw getDataRaw() {
        return dataRaw;
    }

}
