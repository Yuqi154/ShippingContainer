package org.hiedacamellia.shippingcontainer.client.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.item.ItemStack;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.shippingcontainer.core.util.ItemExchangeUtil;

public class ClientItemPriceTooltip implements ClientTooltipComponent {

    public ItemStack want;
    public ItemStack give;


    public ClientItemPriceTooltip(ItemPriceTooltip priceTooltip){
        ItemExchangeUtil.Data data = priceTooltip.getDataRaw().build();
        want = new ItemStack(data.want(),data.wantCount());
        give = new ItemStack(data.give(),data.giveCount());
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics guiGraphics) {
        RenderSystem.enableBlend();
        IUIGuiUtils.fillRoundRect(guiGraphics,x,y,44,20,2,-267386864);
        guiGraphics.renderItem(want,x+2,y+2);
        guiGraphics.renderItemDecorations(font,want,x+2,y+2);
        IUIGuiUtils.drawCenteredString(guiGraphics,font,"=",x+22,y+10,0xFFFFFF,false);
        guiGraphics.renderItem(give,x+26,y+2);
        guiGraphics.renderItemDecorations(font,give,x+26,y+2);
        RenderSystem.disableBlend();
    }

    @Override
    public int getHeight() {
        return 20;
    }

    @Override
    public int getWidth(Font font) {
        return 44;
    }
}
