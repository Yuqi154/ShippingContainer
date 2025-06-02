package org.hiedacamellia.shippingcontainer.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.Slot;
import net.neoforged.neoforge.network.PacketDistributor;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.shippingcontainer.common.menu.ContainerMenu;
import org.hiedacamellia.shippingcontainer.core.network.ResetShipC2SMessage;

public class ContainerScreen extends AbstractContainerScreen<ContainerMenu> {

    private ContainerData data;
    private BlockPos pos;

    public ContainerScreen(ContainerMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.data = container.data;
        this.pos = container.pos;
        this.imageWidth = 180;
        this.imageHeight = 166;
    }

    @Override
    public void onClose() {
        super.onClose();
        PacketDistributor.sendToServer(new ResetShipC2SMessage(pos));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
        RenderSystem.enableBlend();
        IUIGuiUtils.fillRoundRect(guiGraphics, this.leftPos-1, this.topPos-1, this.imageWidth, this.imageHeight, 0.05f, 0xFFffffff);
        IUIGuiUtils.fillRoundRect(guiGraphics, this.leftPos+1, this.topPos+1, this.imageWidth, this.imageHeight, 0.05f, 0xFF555555);
        IUIGuiUtils.fillRoundRect(guiGraphics, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, 0.05f, 0xFFc6c6c6);
        RenderSystem.disableBlend();
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        IUIGuiUtils.drawCenteredString(guiGraphics,IUIGuiUtils.getFont(),Component.translatable("gui.shipping_container.title"), this.imageWidth / 2, 10, 0x404040,false);
        if(data != null) {
            int count = data.get(0);
            int need = data.get(1);
            int left = need - count;
            if(count!=0)
                IUIGuiUtils.drawCenteredString(guiGraphics,IUIGuiUtils.getFont(),Component.translatable("gui.shipping_container.ship_time", left/20), this.imageWidth / 2, 19, 0x404040,false);
            else
                IUIGuiUtils.drawCenteredString(guiGraphics,IUIGuiUtils.getFont(),Component.translatable("gui.shipping_container.ready"), this.imageWidth / 2, 19, 0x404040,false);
        }
    }

    @Override
    public void renderSlot(GuiGraphics guiGraphics, Slot slot) {
        IUIGuiUtils.renderSlotBackground(guiGraphics,slot.x,slot.y);
        super.renderSlot(guiGraphics,slot);
    }




    @Override
    public void init() {
        super.init();


    }

}
