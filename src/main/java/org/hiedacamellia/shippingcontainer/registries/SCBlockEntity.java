package org.hiedacamellia.shippingcontainer.registries;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.hiedacamellia.shippingcontainer.ShippingContainer;
import org.hiedacamellia.shippingcontainer.common.blockentity.ContainerBlockEntity;

import java.util.function.Supplier;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class SCBlockEntity {

	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ShippingContainer.MODID);

	public static final Supplier<BlockEntityType<ContainerBlockEntity>> CONTAINER = BLOCK_ENTITY_TYPES.register("container", () -> BlockEntityType.Builder.of(ContainerBlockEntity::new, SCBlock.CONTAINER.get()).build(null));

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, CONTAINER.get(), (blockEntity, side) -> blockEntity.getItemHandler());
	}
}
