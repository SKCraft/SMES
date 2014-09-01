package com.skcraft.smes.client.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementEnergyStored;

import com.skcraft.smes.SMES;
import com.skcraft.smes.inventory.container.ContainerRareMetalExtractor;
import com.skcraft.smes.tileentity.TileEntityRareMetalExtractor;

public class GuiRareMetalExtractor extends GuiBase {
    public GuiRareMetalExtractor(TileEntityRareMetalExtractor tileEntity, InventoryPlayer inventory) {
        super(new ContainerRareMetalExtractor(tileEntity, inventory), 
              new ResourceLocation(SMES.MOD_ID, "textures/gui/machine/rareMetalExtractor.png"));
        this.addElement(new ElementEnergyStored(this, 50, 50, tileEntity.getBattery()));
    }
}
