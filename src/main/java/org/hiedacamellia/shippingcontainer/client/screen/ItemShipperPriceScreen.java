package org.hiedacamellia.shippingcontainer.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.hiedacamellia.immersiveui.client.graphic.util.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.gui.component.widget.component.UnderLineComponentWidget;
import org.hiedacamellia.immersiveui.client.gui.component.widget.price.ItemPriceWidget;
import org.hiedacamellia.shippingcontainer.client.widget.SCCustomButton;
import org.hiedacamellia.shippingcontainer.common.menu.ItemShipperPriceMenu;
import org.hiedacamellia.shippingcontainer.core.config.json.ItemShipperPrice;
import org.hiedacamellia.shippingcontainer.core.util.ItemExchangeUtil;
import org.hiedacamellia.shippingcontainer.core.util.SCItemStackUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemShipperPriceScreen extends AbstractContainerScreen<ItemShipperPriceMenu> {

    private ItemShipperPriceMenu menu;

    private int leftPos;
    private int topPos;
    private int imageWidth;
    private int imageHeight;

    private UnderLineComponentWidget title;

    private SCCustomButton left;
    private SCCustomButton right;

    private List<ItemPriceWidget> list = new ArrayList<>();
    private SCCustomButton add;

    private List<Pair<String,ItemExchangeUtil.Data>> map = new ArrayList<>();

    private int page = 0;

    private final int sPage = 6;

    public ItemShipperPriceScreen(ItemShipperPriceMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.menu = menu;
        this.imageWidth = 260;
        this.imageHeight = 166;

        Map<String, ItemExchangeUtil.DataRaw> itemPriceMap = ItemShipperPrice.getItemPriceMap();
        itemPriceMap.forEach(((s, dataRaw) -> {
            Pair<String, ItemExchangeUtil.Data> pair = Pair.of(s, dataRaw.build());
            map.add(pair);
        }));
    }

    @Override
    public void onClose() {
        setChanged();
        super.onClose();
    }

    public void tryAccept(ItemStack itemStack){
        for (ItemPriceWidget itemPriceWidget : list) {
            if (itemPriceWidget.tryAccept(itemStack)) return;
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.enableBlend();
        IUIGuiUtils.fillRoundRect(guiGraphics, this.leftPos-1, this.topPos-1, this.imageWidth, this.imageHeight, 0.05f, 0xFFffffff);
        IUIGuiUtils.fillRoundRect(guiGraphics, this.leftPos+1, this.topPos+1, this.imageWidth, this.imageHeight, 0.05f, 0xFF555555);
        IUIGuiUtils.fillRoundRect(guiGraphics, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, 0.05f, 0xFFc6c6c6);
        RenderSystem.disableBlend();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics,mouseX,mouseY,partialTick);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    }

    @Override
    protected void renderSlot(GuiGraphics guiGraphics, Slot slot) {
        IUIGuiUtils.renderSlotBackground(guiGraphics,slot.x,slot.y);
        super.renderSlot(guiGraphics, slot);
    }

    @Override
    public void init() {
        super.init();

        int centerX = this.width / 2;
        int centerY = this.height / 2;
        this.leftPos = centerX - this.imageWidth / 2;
        this.topPos = centerY - this.imageHeight / 2;

        left = new SCCustomButton.builder(Component.literal("<"), button -> {
            setPageChanged();
            if(page>0) {
                page = Math.max(0, page - sPage);
                resetItemPriceWidget();
            }
        }).pos(leftPos + 10, topPos - 20).size(20, 16).build();
        right = new SCCustomButton.builder(Component.literal(">"), button -> {
            setPageChanged();
            if(page + sPage <= map.size()){
                page += sPage;
                resetItemPriceWidget();
            }
        }).pos(leftPos + 230, topPos - 20).size(20, 16).build();

        resetItemPriceWidget();

        addRenderableWidget(left);
        addRenderableWidget(right);


        Component titlec = Component.translatable("gui.shippingcontainer.item_shipper_price.title");
        title = new UnderLineComponentWidget(this.leftPos + imageWidth / 2 - font.width(titlec) / 2, this.topPos - 14, titlec);

        addRenderableWidget(title);
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        ItemStack draggingItem = menu.getCarried();
        tryAccept(draggingItem.copy());

        return super.mouseClicked(mouseX, mouseY, button);
    }

    public void setPageChanged(){
        list.forEach(e->{
            int index = list.indexOf(e)+page;
            if(index!=-1) {
                if(index<map.size()){
                    map.remove(index);
                    map.add(index, Pair.of(SCItemStackUtil.toString(e.getItemStackWant()), new ItemExchangeUtil.Data(e.getItemStackWant(), e.getCountWant(), e.getItemStackSell(), e.getCountSell())));
                }else {
                    map.add(index, Pair.of(SCItemStackUtil.toString(e.getItemStackWant()), new ItemExchangeUtil.Data(e.getItemStackWant(), e.getCountWant(), e.getItemStackSell(), e.getCountSell())));
                }
            }else {
                map.add(index, Pair.of(SCItemStackUtil.toString(e.getItemStackWant()), new ItemExchangeUtil.Data(e.getItemStackWant(), e.getCountWant(), e.getItemStackSell(), e.getCountSell())));
            }
        });
    }

    public void setChanged(){
        setPageChanged();
        Map<String, ItemExchangeUtil.DataRaw> hashMap = new HashMap<>();
        map.forEach(stringIntegerPair -> hashMap.put(stringIntegerPair.getFirst(), stringIntegerPair.getSecond().toRaw()));
        hashMap.remove(SCItemStackUtil.toString(ItemStack.EMPTY));
        ItemShipperPrice.setItemPriceMap(hashMap);
        ItemShipperPrice.save();
        ItemShipperPrice.send2Server();
    }

    public void resetItemPriceWidget(){
        list.forEach(this::removeWidget);
        list.clear();
        for (int i = page; i < map.size(); i++) {
            int pos = i-page;
            if(pos>=sPage) break;
            int x = leftPos+5;
            if(pos>=3){
                x += 130;
            }
            int y = topPos+5;
            y+= (pos%3)*25;

            String string = map.get(i).getFirst();
            ItemExchangeUtil.Data data = map.get(i).getSecond();

            ItemPriceWidget SimplePriceWidget = new ItemPriceWidget(x, y,
                    data.want().getDefaultInstance(),data.wantCount(),
                    data.give().getDefaultInstance(),data.giveCount()
                    );
            list.add(SimplePriceWidget);
        }
        resetAddButton(list.size());
        list.forEach(this::addRenderableWidget);
    }

    public void resetAddButton(int pos){
        int x = leftPos+5;
        if(pos>=3){
            x += 90;
        }
        int y = topPos+5;
        y+= (pos%3)*25;

        removeWidget(add);
        if(pos>=6){
            return;
        }
        add = new SCCustomButton.builder(Component.literal("+"), button -> {
            ItemPriceWidget SimplePriceWidget = new ItemPriceWidget(button.getX(), button.getY());
            list.add(SimplePriceWidget);
            addRenderableWidget(SimplePriceWidget);
            resetAddButton(list.size());
        }).pos(x, y).size(120, 20).build();
        addRenderableWidget(add);
    }
}
