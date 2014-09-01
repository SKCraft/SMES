package com.skcraft.smes.client.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementEnergyStored;
import cofh.lib.gui.element.TabBase;

import com.skcraft.smes.SMES;
import com.skcraft.smes.inventory.container.ContainerRareMetalExtractor;
import com.skcraft.smes.tileentity.TileEntityRareMetalExtractor;

public class GuiRareMetalExtractor extends GuiBase {
    private TileEntityRareMetalExtractor tileEntity;
    
    public GuiRareMetalExtractor(TileEntityRareMetalExtractor tileEntity, InventoryPlayer inventory) {
        super(new ContainerRareMetalExtractor(tileEntity, inventory), 
              new ResourceLocation(SMES.MOD_ID, "textures/gui/machine/rareMetalExtractor.png"));
        this.tileEntity = tileEntity;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.addElement(new ElementEnergyStored(this, 8, 8, tileEntity.getBattery()));
    }
}
