
package org.hiedacamellia.shippingcontainer.common.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.hiedacamellia.shippingcontainer.registries.SCMenu;

import java.util.HashMap;
import java.util.Map;

public class ContainerMenu extends BaseMenu {
	public final Level world;
	public final Player entity;
	public BlockPos pos;
	public ContainerData data;
	private ContainerLevelAccess access = ContainerLevelAccess.NULL;
	private final Map<Integer, Slot> customSlots = new HashMap<>();

	public ContainerMenu(int containerId, Inventory inventory, RegistryFriendlyByteBuf buf) {
		this(containerId, inventory, ContainerLevelAccess.NULL,buf.readBlockPos());
	}

	public ContainerMenu(int containerId, Inventory inventory, BlockPos pos) {
		this(containerId, inventory, ContainerLevelAccess.NULL,pos);
	}

	public ContainerMenu(int containerId, Inventory inventory, ContainerLevelAccess access, BlockPos pos) {
		this(containerId, inventory, access, new ItemStackHandler(60), new SimpleContainerData(9),pos);
	}

	public ContainerMenu(int id, Inventory inv, ContainerLevelAccess access, IItemHandler itemHandler, ContainerData containerData, BlockPos pos) {
		super(SCMenu.CONTAINER_UI.get(), id);
		this.entity = inv.player;
		this.world = inv.player.level();
		this.pos = pos;
		this.access = access;
		this.data = containerData;



		this.addDataSlots(data);


		for (int si = 0; si < 6; ++si)
			for (int sj = 0; sj < 9; ++sj)
				this.customSlots.put(sj + (si) * 9, this.addSlot(new SlotItemHandler(itemHandler, sj + (si) * 9, 10 + sj * 18, 2 + 24 + si * 18)));


		for (int si = 0; si < 3; ++si)
			for (int sj = 0; sj < 9; ++sj)
				this.addSlot(new Slot(inv, sj + (si + 1) * 9, 10 + sj * 18, 2 + 84 + 60 + si * 18));
		for (int si = 0; si < 9; ++si)
			this.addSlot(new Slot(inv, si, 10 + si * 18, 2 + 142 + 60));
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	@Override
	protected int getSize() {
		return 27;
	}

}
