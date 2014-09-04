package com.skcraft.smes.block;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.skcraft.smes.SMES;
import com.skcraft.smes.tileentity.TileEntityExtractor;
import com.skcraft.smes.tileentity.TileEntityExtractor.ExtractorType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMachineExtractor extends BlockMachineBase {
    private IIcon[][] iconsIdle = new IIcon[TileEntityExtractor.ExtractorType.values().length][6];
    private IIcon[][] iconsActive = new IIcon[TileEntityExtractor.ExtractorType.values().length][6];
    
    public BlockMachineExtractor() {
        super(Material.iron);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List items) {
        ExtractorType[] types = TileEntityExtractor.ExtractorType.values();
        ItemStack itemStack = new ItemStack(item);
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        
        for (ExtractorType type : types) {
            nbttagcompound.setInteger("Type", type.ordinal());
            itemStack.setTagCompound(nbttagcompound);
            items.add(itemStack.copy());
        }   
    }
    
    /* Temporary! May need to make some better, cleaner way */
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        for (ExtractorType type : ExtractorType.values()) {
            this.iconsIdle[type.ordinal()][0] = register.registerIcon(SMES.RSRC_PREFIX + "machine." + this.getUnlocalizedName().replace("tile." + SMES.PREFIX, "") + type.toString() + ".idle.bottom");
            this.iconsIdle[type.ordinal()][1] = register.registerIcon(SMES.RSRC_PREFIX + "machine." + this.getUnlocalizedName().replace("tile." + SMES.PREFIX, "") + type.toString() + ".idle.top");
            this.iconsIdle[type.ordinal()][2] = register.registerIcon(SMES.RSRC_PREFIX + "machine." + this.getUnlocalizedName().replace("tile." + SMES.PREFIX, "") + type.toString() + ".idle.front");
            this.iconsIdle[type.ordinal()][3] = register.registerIcon(SMES.RSRC_PREFIX + "machine." + this.getUnlocalizedName().replace("tile." + SMES.PREFIX, "") + type.toString() + ".idle.back");
            this.iconsIdle[type.ordinal()][4] = register.registerIcon(SMES.RSRC_PREFIX + "machine." + this.getUnlocalizedName().replace("tile." + SMES.PREFIX, "") + type.toString() + ".idle.left");
            this.iconsIdle[type.ordinal()][5] = register.registerIcon(SMES.RSRC_PREFIX + "machine." + this.getUnlocalizedName().replace("tile." + SMES.PREFIX, "") + type.toString() + ".idle.right");
            this.iconsActive[type.ordinal()][0] = register.registerIcon(SMES.RSRC_PREFIX + "machine." + this.getUnlocalizedName().replace("tile." + SMES.PREFIX, "") + type.toString() + ".active.bottom");
            this.iconsActive[type.ordinal()][1] = register.registerIcon(SMES.RSRC_PREFIX + "machine." + this.getUnlocalizedName().replace("tile." + SMES.PREFIX, "") + type.toString() + ".active.top");
            this.iconsActive[type.ordinal()][2] = register.registerIcon(SMES.RSRC_PREFIX + "machine." + this.getUnlocalizedName().replace("tile." + SMES.PREFIX, "") + type.toString() + ".active.front");
            this.iconsActive[type.ordinal()][3] = register.registerIcon(SMES.RSRC_PREFIX + "machine." + this.getUnlocalizedName().replace("tile." + SMES.PREFIX, "") + type.toString() + ".active.back");
            this.iconsActive[type.ordinal()][4] = register.registerIcon(SMES.RSRC_PREFIX + "machine." + this.getUnlocalizedName().replace("tile." + SMES.PREFIX, "") + type.toString() + ".active.left");
            this.iconsActive[type.ordinal()][5] = register.registerIcon(SMES.RSRC_PREFIX + "machine." + this.getUnlocalizedName().replace("tile." + SMES.PREFIX, "") + type.toString() + ".active.right");
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityExtractor();
    }
}
