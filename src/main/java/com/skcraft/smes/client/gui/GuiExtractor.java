package com.skcraft.smes.client.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementEnergyStored;
import cofh.lib.render.RenderHelper;

import com.skcraft.smes.SMES;
import com.skcraft.smes.inventory.container.ContainerExtractor;
import com.skcraft.smes.tileentity.TileEntityExtractor;

public class GuiExtractor extends GuiBase {
    private TileEntityExtractor tileEntity;
    
    public GuiExtractor(TileEntityExtractor tileEntity, InventoryPlayer inventory) {
        super(new ContainerExtractor(tileEntity, inventory), 
              new ResourceLocation(SMES.MOD_ID, "textures/gui/machine/extractor" + tileEntity.getType().toString() + ".png"));
        this.tileEntity = tileEntity;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.addElement(new ElementEnergyStored(this, 8, 8, tileEntity.getBattery()));
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTick, int x, int y) {
        super.drawGuiContainerBackgroundLayer(partialTick, x, y);
        RenderHelper.bindTexture(texture);
        drawTexturedModalRect(guiLeft + 75, guiTop + 33, 176, 0, tileEntity.getProcessingEnergyScaled(22), 15);
    }
}
