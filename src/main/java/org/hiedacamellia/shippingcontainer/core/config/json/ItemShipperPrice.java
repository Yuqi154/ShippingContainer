package org.hiedacamellia.shippingcontainer.core.config.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import org.hiedacamellia.shippingcontainer.ShippingContainer;
import org.hiedacamellia.shippingcontainer.core.network.ItemShipperPriceSyncBiMessage;
import org.hiedacamellia.shippingcontainer.core.util.ItemExchangeUtil;
import org.hiedacamellia.shippingcontainer.core.util.SCItemStackUtil;
import org.hiedacamellia.shippingcontainer.core.util.SCItemUtil;

import java.util.HashMap;
import java.util.Map;

public class ItemShipperPrice {

    private static Map<String, ItemExchangeUtil.DataRaw> itemPriceMap = new HashMap<>();


    public static Codec<Map<String, ItemExchangeUtil.DataRaw>> CODEC = Codec.unboundedMap(Codec.STRING, ItemExchangeUtil.DataRaw.CODEC);
    public static StreamCodec<ByteBuf, Map<String, ItemExchangeUtil.DataRaw>> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(CODEC),
            (e) -> e,
            HashMap::new
    );

    public static void setItemPriceMap(Map<String, ItemExchangeUtil.DataRaw> map) {
        itemPriceMap = map;
    }

    public static Map<String, ItemExchangeUtil.DataRaw> getItemPriceMap() {
        return itemPriceMap;
    }

    public static void load() {
        JsonObject itemShipperPrice = SCJsonHelper.get("item_shipper_price");
        try {
            JsonArray asJsonArray = itemShipperPrice.get("item_price").getAsJsonArray();
            for (int i = 0; i < asJsonArray.size(); i++) {
                JsonObject jsonObject = asJsonArray.get(i).getAsJsonObject();
                String item_want = jsonObject.get("item_want").getAsString();
                int item_want_count = jsonObject.get("item_want_count").getAsInt();
                String item_give = jsonObject.get("item_give").getAsString();
                int item_give_count = jsonObject.get("item_give_count").getAsInt();
                ItemExchangeUtil.DataRaw dataRaw = new ItemExchangeUtil.DataRaw(
                        item_want, item_want_count, item_give, item_give_count
                );
                itemPriceMap.put(item_want, dataRaw);
            }
            ShippingContainer.LOGGER.info("ItemShipperPrice loaded with " + itemPriceMap.size() + " items");
        } catch (Exception e) {
            ShippingContainer.LOGGER.debug("ItemShipperPrice load error: " + e.getMessage());
        }
    }

    public static void reload() {
        itemPriceMap.clear();
        load();
    }

    public static ItemExchangeUtil.DataRaw getPrice(ItemStack stack) {
        return getPrice(SCItemStackUtil.toString(stack));
    }

    public static ItemExchangeUtil.DataRaw getPrice(Item item) {
        return getPrice(SCItemUtil.toString(item));
    }

    public static ItemExchangeUtil.DataRaw getPrice(ResourceLocation resourceLocation) {
        return getPrice(resourceLocation.toString());
    }

    public static ItemExchangeUtil.DataRaw getPrice(String itemId) {
        return itemPriceMap.getOrDefault(itemId, null);
    }

    public static boolean hasPrice(ItemStack stack) {
        return hasPrice(SCItemStackUtil.toString(stack));
    }
    public static boolean hasPrice(Item item) {
        return hasPrice(SCItemUtil.toString(item));
    }

    public static boolean hasPrice(ResourceLocation resourceLocation) {
        return hasPrice(resourceLocation.toString());
    }

    public static boolean hasPrice(String itemId) {
        return itemPriceMap.containsKey(itemId);
    }

    public static void save() {
        JsonArray jsonElements = new JsonArray();
        for (Map.Entry<String, ItemExchangeUtil.DataRaw> entry : itemPriceMap.entrySet()) {
            JsonObject jsonObject = new JsonObject();
            ItemExchangeUtil.DataRaw dataRaw = entry.getValue();
            jsonObject.addProperty("item_want", dataRaw.want());
            jsonObject.addProperty("item_want_count", dataRaw.wantCount());
            jsonObject.addProperty("item_give", dataRaw.give());
            jsonObject.addProperty("item_give_count", dataRaw.giveCount());
            jsonElements.add(jsonObject);
        }
        JsonObject itemShipperPrice = new JsonObject();
        itemShipperPrice.add("item_price", jsonElements);
        SCJsonHelper.save("item_shipper_price", itemShipperPrice);
    }

    public static void sync2Client() {
        PacketDistributor.sendToAllPlayers(new ItemShipperPriceSyncBiMessage(itemPriceMap));
    }

    public static void sync(ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, new ItemShipperPriceSyncBiMessage(itemPriceMap));
    }

    public static void send2Server() {
        PacketDistributor.sendToServer(new ItemShipperPriceSyncBiMessage(itemPriceMap));
    }

}
