package org.hiedacamellia.shippingcontainer.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;
import org.hiedacamellia.shippingcontainer.common.menu.ContainerMenu;
import org.hiedacamellia.shippingcontainer.core.config.SCCommonConfig;
import org.hiedacamellia.shippingcontainer.core.config.json.ItemShipperPrice;
import org.hiedacamellia.shippingcontainer.core.util.ItemExchangeUtil;
import org.hiedacamellia.shippingcontainer.registries.SCBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class ContainerBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {

    private NonNullList<ItemStack> stacks = NonNullList.withSize(54, ItemStack.EMPTY);
    private final SidedInvWrapper handler = new SidedInvWrapper(this, null);
    private final ContainerData data = new SimpleContainerData(2);

    private int readyToShip = 0;
    private boolean shipped = false;



    public ContainerBlockEntity(BlockPos pos, BlockState blockState) {
        super(SCBlockEntity.CONTAINER.get(), pos, blockState);
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag compound, HolderLookup.@NotNull Provider lookupProvider) {
        super.loadAdditional(compound, lookupProvider);
        if (!this.tryLoadLootTable(compound))
            this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compound, this.stacks, lookupProvider);
        readyToShip = compound.getInt("readyToShip");
        shipped = compound.getBoolean("shipped");
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag compound, HolderLookup.@NotNull Provider lookupProvider) {
        super.saveAdditional(compound, lookupProvider);
        if (!this.trySaveLootTable(compound)) {
            ContainerHelper.saveAllItems(compound, this.stacks, lookupProvider);
        }
        compound.putInt("readyToShip", readyToShip);
        compound.putBoolean("shipped", shipped);
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        if(direction==Direction.DOWN){
            return new int[]{};
        }
        return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26
        , 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @Nullable Direction direction) {
        return true;
    }

    @Override
    public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {
        if(direction==Direction.DOWN){
            return false;
        }
        return true;
    }

    @Override
    protected Component getDefaultName() {
        return Component.empty();
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return stacks;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.stacks = nonNullList;
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new ContainerMenu(i,inventory, ContainerLevelAccess.create(level,worldPosition),handler,data,worldPosition);
    }

    @Override
    public int getContainerSize() {
        return stacks.size();
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider lookupProvider) {
        return this.saveWithFullMetadata(lookupProvider);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ContainerBlockEntity blockEntity){
        blockEntity.serverTick();
    }

    protected void serverTick(){
        data.set(0, readyToShip);
        data.set(1, SCCommonConfig.SHIPPING_TIME.get());
        if(shipped)return;

        boolean hasItem = false;
        for (ItemStack stack : stacks) {
            if (!stack.isEmpty()) {
                hasItem = true;
                break;
            }
        }

        if(hasItem){
            readyToShip++;
        }else {
            readyToShip=0;
        }

        if(readyToShip >= SCCommonConfig.SHIPPING_TIME.get()){
            ship();
            shipped = true;
            readyToShip = 0;
        }
    }

    private void ship(){
        HashMap<Item, Integer> map = new HashMap<>();
        for (int i = 0; i < stacks.size(); i++) {
            ItemStack stack = stacks.get(i);
            if(!stack.isEmpty()){
                if(ItemShipperPrice.hasPrice(stack)) {
                    Item item = stack.getItem();
                    if (map.containsKey(item)) {
                        map.put(item, map.get(item) + stack.getCount());
                    } else {
                        map.put(item, stack.getCount());
                    }
                    stacks.set(i, ItemStack.EMPTY);
                }
            }
        }

        HashMap<Item, Integer> out = new HashMap<>();
        map.forEach((a, b) -> {
            ItemExchangeUtil.Data data = ItemShipperPrice.getPrice(a).build();
            int i = b / data.wantCount();
            int o = i * data.giveCount();
            if (o > 0) {
                if (out.containsKey(data.give())) {
                    out.put(data.give(), o + out.get(data.give()));
                } else {
                    out.put(data.give(), o);
                }
            }
            if (b - i * data.wantCount() > 0) {
                if (out.containsKey(a)) {
                    out.put(a, b - i * data.wantCount() + out.get(a));
                } else {
                    out.put(a, b - i * data.wantCount());
                }
            }
        });

        if(!out.isEmpty()) {
            out.forEach((item, count) -> {
                int remaining = count;
                while(remaining > 0){
                    if(remaining>64){
                        ItemStack stack = new ItemStack(item, 64);
                        remaining -= 64;
                        ItemHandlerHelper.insertItemStacked(handler, stack, false);
                    }else {
                        ItemStack stack = new ItemStack(item, remaining);
                        remaining = 0;
                        ItemHandlerHelper.insertItemStacked(handler, stack, false);
                    }
                }
            });
        }

    }

    public void resetShipped() {
        this.shipped = false;
    }

    public @Nullable IItemHandler getItemHandler() {
        return handler;
    }
}
