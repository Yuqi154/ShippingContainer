package org.hiedacamellia.shippingcontainer;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.hiedacamellia.shippingcontainer.core.config.SCCommonConfig;
import org.hiedacamellia.shippingcontainer.core.config.json.SCJsonHelper;
import org.hiedacamellia.shippingcontainer.registries.*;
import org.slf4j.Logger;


@Mod(ShippingContainer.MODID)
public class ShippingContainer
{

    public static final String MODID = "shippingcontainer";

    public static final Logger LOGGER = LogUtils.getLogger();

    public ShippingContainer(IEventBus modEventBus, ModContainer modContainer)
    {
        SCItem.ITEMS.register(modEventBus);
        SCBlock.BLOCKS.register(modEventBus);
        SCBlockEntity.BLOCK_ENTITY_TYPES.register(modEventBus);
        SCMenu.MENU_TYPES.register(modEventBus);
        SCTab.CREATIVE_MODE_TABS.register(modEventBus);

        SCJsonHelper.init();

        modContainer.registerConfig(ModConfig.Type.COMMON, SCCommonConfig.SPEC);

    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
