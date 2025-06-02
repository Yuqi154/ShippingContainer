package org.hiedacamellia.shippingcontainer.data;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.hiedacamellia.shippingcontainer.ShippingContainer;
import org.hiedacamellia.shippingcontainer.data.lang.ChineseLanguageProvider;
import org.hiedacamellia.shippingcontainer.data.lang.EnglishLanguageProvider;
import org.hiedacamellia.shippingcontainer.data.provider.SCRecipeProvider;
import org.hiedacamellia.shippingcontainer.data.provider.SCStateProvider;

@EventBusSubscriber(modid = ShippingContainer.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Data {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        var gen = event.getGenerator();
        var packOutput = gen.getPackOutput();
        var helper = event.getExistingFileHelper();
        var registries = event.getLookupProvider();
        gen.addProvider(event.includeClient(), new EnglishLanguageProvider(packOutput));
        gen.addProvider(event.includeClient(), new ChineseLanguageProvider(packOutput));
        gen.addProvider(event.includeClient(), new SCStateProvider(packOutput, helper));
        gen.addProvider(event.includeServer(), new SCRecipeProvider(packOutput,registries));

    }
}