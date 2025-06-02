package org.hiedacamellia.shippingcontainer.core.config.json;

import com.google.gson.JsonObject;
import net.minecraft.util.GsonHelper;
import net.neoforged.fml.loading.FMLPaths;
import org.hiedacamellia.shippingcontainer.ShippingContainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class SCJsonHelper {

    private static final Map<String, JsonObject> jsonMap = new HashMap<>();

    public static void init(){
        Path path = FMLPaths.CONFIGDIR.get();
        if (!path.resolve("shipping_container").toFile().exists()) {
            path.resolve("shipping_container").toFile().mkdirs();
        }
        loadJson("item_shipper_price");
        ItemShipperPrice.load();
    }

    private static void loadJson(String fileName) {
        Path path = FMLPaths.CONFIGDIR.get().resolve("shipping_container").resolve(fileName+".json");
        if (!path.toFile().exists()) {
            ShippingContainer.LOGGER.debug("SCJsonHelper: File {} does not exist, skipping load.", fileName+".json");
            try {
                Files.writeString(path, "{}");
            } catch (IOException e) {
                ShippingContainer.LOGGER.debug("SCJsonHelper: Failed to create empty JSON file for {}: {}", fileName, e.getMessage());
            }
            return;
        }
        try {
            String content = Files.readString(path);
            jsonMap.put(fileName, GsonHelper.parse(content));
        } catch (IOException e) {
            ShippingContainer.LOGGER.debug("SCJsonHelper: Failed to load JSON file {}: {}", fileName, e.getMessage());
        } catch (com.google.gson.JsonSyntaxException e) {
            ShippingContainer.LOGGER.debug("SCJsonHelper: Invalid JSON syntax in file {}: {}", fileName, e.getMessage());
        }
    }

    public static JsonObject get(String key) {
        return jsonMap.get(key);
    }

    public static void save(String fileName, JsonObject jsonObject) {
        Path path = FMLPaths.CONFIGDIR.get().resolve("shipping_container").resolve(fileName + ".json");
        try {
            Files.writeString(path, jsonObject.toString());
        } catch (IOException e) {
            ShippingContainer.LOGGER.debug("MIJsonHelper: Failed to save JSON file {}: {}", fileName, e.getMessage());
        } catch (Exception e) {
            ShippingContainer.LOGGER.debug("MIJsonHelper: Unexpected error while saving JSON file {}: {}", fileName, e.getMessage());
        }
    }



}
