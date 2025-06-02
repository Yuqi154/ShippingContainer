package org.hiedacamellia.shippingcontainer.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.hiedacamellia.shippingcontainer.ShippingContainer;
import org.hiedacamellia.shippingcontainer.common.menu.ContainerMenu;
import org.hiedacamellia.shippingcontainer.common.menu.ItemShipperPriceMenu;

public class SCMenu {
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, ShippingContainer.MODID);
	public static final DeferredHolder<MenuType<?>,MenuType<ContainerMenu>> CONTAINER_UI = MENU_TYPES.register("container_ui", () -> IMenuTypeExtension.create(ContainerMenu::new));
	public static final DeferredHolder<MenuType<?>,MenuType<ItemShipperPriceMenu>> SHIPPER_PRICE_UI = MENU_TYPES.register("shipper_price_ui", () -> IMenuTypeExtension.create(ItemShipperPriceMenu::new));

}
