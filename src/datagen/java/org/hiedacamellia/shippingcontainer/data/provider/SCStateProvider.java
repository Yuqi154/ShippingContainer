package org.hiedacamellia.shippingcontainer.data.provider;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.hiedacamellia.shippingcontainer.ShippingContainer;
import org.hiedacamellia.shippingcontainer.registries.SCBlock;

public class SCStateProvider extends BlockStateProvider {
    public SCStateProvider(PackOutput gen, ExistingFileHelper helper) {
        super(gen, ShippingContainer.MODID, helper);
    }

    @Override
    protected void registerStatesAndModels() {

        horizontalBlock(SCBlock.CONTAINER.get(),models().getExistingFile(modLoc("block/container")));
    }
}