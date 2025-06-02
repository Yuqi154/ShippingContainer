package org.hiedacamellia.shippingcontainer.registries;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.hiedacamellia.shippingcontainer.ShippingContainer;
import org.hiedacamellia.shippingcontainer.common.block.ContainerBlock;

public class SCBlock {
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ShippingContainer.MODID);

	public static final DeferredBlock<ContainerBlock> CONTAINER = BLOCKS.register("container", ()-> new ContainerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).pushReaction(PushReaction.IGNORE).strength(4,100).noOcclusion()));



}
