package org.hiedacamellia.shippingcontainer.core.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.hiedacamellia.shippingcontainer.ShippingContainer;
import org.hiedacamellia.shippingcontainer.core.config.json.ItemShipperPrice;
import org.hiedacamellia.shippingcontainer.core.util.ItemExchangeUtil;

import java.util.Map;

public record ItemShipperPriceSyncBiMessage(Map<String, ItemExchangeUtil.DataRaw> map) implements CustomPacketPayload {

    public static final Type<ItemShipperPriceSyncBiMessage> TYPE = new Type<>(ShippingContainer.rl( "item_shipper_price_sync_bi_message"));

    public static final StreamCodec<ByteBuf, ItemShipperPriceSyncBiMessage> STREAM_CODEC = StreamCodec.composite(
            ItemShipperPrice.STREAM_CODEC,
            ItemShipperPriceSyncBiMessage::map,
            ItemShipperPriceSyncBiMessage::new
    );

    @Override
    public Type<ItemShipperPriceSyncBiMessage> type() {
        return TYPE;
    }


    public static void handleServer(final ItemShipperPriceSyncBiMessage data, final IPayloadContext context) {
        context.enqueueWork(() -> {
                    if (context.player().hasPermissions(4)) {
                        ItemShipperPrice.setItemPriceMap(data.map());
                        ItemShipperPrice.save();
                        ItemShipperPrice.sync2Client();
                    }
                });
    }

    public static void handleClient(final ItemShipperPriceSyncBiMessage data, final IPayloadContext context) {
        context.enqueueWork(() -> {
                    ItemShipperPrice.setItemPriceMap(data.map());
                });
    }
}
