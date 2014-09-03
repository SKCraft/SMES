package com.skcraft.smes.block;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.block.IDismantleable;

import com.skcraft.smes.SMES;
import com.skcraft.smes.client.gui.GuiHandler;
import com.skcraft.smes.tileentity.TileEntityBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockMachineBase extends BlockContainer implements IDismantleable {
    private IIcon[] iconsIdle = new IIcon[6];
    private IIcon[] iconsActive = new IIcon[6];

    public BlockMachineBase(Material material) {
        super(material);
        setCreativeTab(SMES.tabSMES);
        setHardness(2.5F);
        setHarvestLevel("pickaxe", 2);
        setStepSound(soundTypeMetal);
    }

    /* Temporary! May need to make some better, cleaner way */
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.iconsIdle[0] = register.registerIcon(SMES.RSRC_PREFIX + "machine." + this.getUnlocalizedName().replace("tile." + SMES.PREFIX, "") + ".idle.bottom");
        this.iconsIdle[1] = register.registerIcon(SMES.RSRC_PREFIX + "machine." + this.getUnlocalizedName().replace("tile." + SMES.PREFIX, "") + ".idle.top");
        this.iconsIdle[2] = register.registerIcon(SMES.RSRC_PREFIX + "machine." + this.getUnlocalizedName().replace("tile." + SMES.PREFIX, "") + ".idle.front");
        this.iconsIdle[3] = register.registerIcon(SMES.RSRC_PREFIX + "machine." + this.getUnlocalizedName().replace("tile." + SMES.PREFIX, "") + ".idle.back");
        this.iconsIdle[4] = register.registerIcon(SMES.RSRC_PREFIX + "machine." + this.getUnlocalizedName().replace("tile." + SMES.PREFIX, "") + ".idle.left");
        this.iconsIdle[5] = register.registerIcon(SMES.RSRC_PREFIX + "machine." + this.getUnlocalizedName().replace("tile." + SMES.PREFIX, "") + ".idle.right");
        this.iconsActive[0] = register.registerIcon(SMES.RSRC_PREFIX + "machine." + this.getUnlocalizedName().replace("tile." + SMES.PREFIX, "") + ".active.bottom");
        this.iconsActive[1] = register.registerIcon(SMES.RSRC_PREFIX + "machine." + this.getUnlocalizedName().replace("tile." + SMES.PREFIX, "") + ".active.top");
        this.iconsActive[2] = register.registerIcon(SMES.RSRC_PREFIX + "machine." + this.getUnlocalizedName().replace("tile." + SMES.PREFIX, "") + ".active.front");
        this.iconsActive[3] = register.registerIcon(SMES.RSRC_PREFIX + "machine." + this.getUnlocalizedName().replace("tile." + SMES.PREFIX, "") + ".active.back");
        this.iconsActive[4] = register.registerIcon(SMES.RSRC_PREFIX + "machine." + this.getUnlocalizedName().replace("tile." + SMES.PREFIX, "") + ".active.left");
        this.iconsActive[5] = register.registerIcon(SMES.RSRC_PREFIX + "machine." + this.getUnlocalizedName().replace("tile." + SMES.PREFIX, "") + ".active.right");
    }

    @Override
    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int blockSide) {
        TileEntity tile = blockAccess.getTileEntity(x, y, z);
        boolean isActive = false;
        if (tile instanceof TileEntityBase) {
            blockSide = ((TileEntityBase) tile).getRotatedSide(blockSide);
            isActive = ((TileEntityBase) tile).isActive();
        }
        return isActive ? this.iconsActive[blockSide] : this.iconsIdle[blockSide];
    }

    @Override
    public int damageDropped(int metadata) {
        return metadata;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        if (entity == null) {
            return;
        }

        TileEntity tile = world.getTileEntity(x, y, z);
        if (stack.getTagCompound() != null) {
            stack.getTagCompound().setInteger("x", x);
            stack.getTagCompound().setInteger("y", y);
            stack.getTagCompound().setInteger("z", z);
            tile.readFromNBT(stack.getTagCompound());
        }

        if (tile instanceof TileEntityBase) {
            TileEntityBase tileBase = (TileEntityBase) tile;
            switch (MathHelper.floor_double(entity.rotationYaw / 360F * 4F + 0.5D) & 3) {
            case 0:
                tileBase.rotateTo(2);
                break;
            case 1:
                tileBase.rotateTo(5);
                break;
            case 2:
                tileBase.rotateTo(3);
                break;
            case 3:
                tileBase.rotateTo(4);
                break;
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile == null) {
            return false;
        } else if (tile instanceof TileEntityBase && ((TileEntityBase) tile).getContainer(player.inventory) != null) {
            if (!world.isRemote) {
                player.openGui(SMES.instance, GuiHandler.MACHINE, world, x, y, z);
            }
            return true;
        }
        return false;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        this.dropItems(world, x, y, z);
        super.breakBlock(world, x, y, z, block, meta);
    }

    private void dropItems(World world, int x, int y, int z) {
        Random rand = new Random();

        TileEntity tile = world.getTileEntity(x, y, z);
        if (!(tile instanceof IInventory)) {
            return;
        }
        IInventory inv = (IInventory) tile;

        if (!world.isRemote) {
            for (int i = 0; i < inv.getSizeInventory(); i++) {
                ItemStack item = inv.getStackInSlot(i);

                if (item != null && item.stackSize > 0) {
                    float rx = rand.nextFloat() * 0.8F + 0.1F;
                    float ry = rand.nextFloat() * 0.8F + 0.1F;
                    float rz = rand.nextFloat() * 0.8F + 0.1F;

                    EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, item.copy());

                    float factor = 0.05F;
                    entityItem.motionX = rand.nextGaussian() * factor;
                    entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                    entityItem.motionZ = rand.nextGaussian() * factor;
                    world.spawnEntityInWorld(entityItem);
                    item.stackSize = 0;
                }
            }
        }
    }

    @Override
    public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection axis) {
        if (!world.isRemote) {
            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile instanceof TileEntityBase) {
                ((TileEntityBase) tile).rotate();
                return true;
            }
        }
        return false;
    }

    @Override
    public Block setBlockName(String name) {
        super.setBlockName(SMES.PREFIX + name);
        return super.setBlockTextureName(SMES.RSRC_PREFIX + name);
    }

    /* IDismanteable */

    @Override
    public ArrayList<ItemStack> dismantleBlock(EntityPlayer player, World world, int x, int y, int z, boolean returnDrops) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileEntityBase) {
            ArrayList<ItemStack> drops = new ArrayList<ItemStack>();

            ItemStack machine = new ItemStack(this);
            drops.add(machine);
            drops.addAll(((TileEntityBase) tile).getDropItems());
            world.setBlockToAir(x, y, z);
            if (!returnDrops) {
                this.dropBlockAsItem(world, x, y, z, machine);
            }
            return drops;
        }
        return null;
    }

    @Override
    public boolean canDismantle(EntityPlayer player, World world, int x, int y, int z) {
        return world.getTileEntity(x, y, z) instanceof TileEntityBase;
    }

    /* Block Container */

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return null;
    }
}
