package w577.mods.utilitychest;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockChestUtility extends BlockContainer {
	
	public Class<? extends TileEntity> te;

	protected BlockChestUtility(int id, Class<? extends TileEntity> te) {
		super(id, Material.wood);
		this.te = te;
		this.setCreativeTab(CreativeTabs.tabMisc);
		if (id >= 256) {
			this.disableStats();
		}
		this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
		this.setHardness(2.5F);
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return UtilityChest.renderId;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entity, ItemStack is) {
		int face = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		int meta = 2;
		
		if (face == 0)
        {
            meta = 2;
        }

        if (face == 1)
        {
            meta = 5;
        }

        if (face == 2)
        {
            meta = 3;
        }

        if (face == 3)
        {
            meta = 4;
        }
        world.setBlockMetadataWithNotify(i, j, k, meta, 3);
	}
	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityPlayer, int i1, float f, float f1, float f2) {
		if (entityPlayer.isSneaking()) {
			return false;
		}
		if (world.isRemote) {
			return true;
		}
		entityPlayer.displayGUIChest((IInventory) world.getBlockTileEntity(i, j, k));
		return true;
		
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1) {
		try {
			return te.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void breakBlock(World world, int i, int j, int k, int par5, int par6) {
		TileEntityChestUtility te = (TileEntityChestUtility) world.getBlockTileEntity(i, j, k);
		Random random = new Random();
		
		if (te != null) {
			for (int var8 = 0; var8 < te.getSizeInventory(); ++var8)
            {
                ItemStack var9 = te.getStackInSlot(var8);

                if (var9 != null)
                {
                    float var10 = random.nextFloat() * 0.8F + 0.1F;
                    float var11 = random.nextFloat() * 0.8F + 0.1F;
                    EntityItem var14;

                    for (float var12 = random.nextFloat() * 0.8F + 0.1F; var9.stackSize > 0; world.spawnEntityInWorld(var14))
                    {
                        int var13 = random.nextInt(21) + 10;

                        if (var13 > var9.stackSize)
                        {
                            var13 = var9.stackSize;
                        }

                        var9.stackSize -= var13;
                        var14 = new EntityItem(world, (double)((float)i + var10), (double)((float)j + var11), (double)((float)k + var12), new ItemStack(var9.itemID, var13, var9.getItemDamage()));
                        float var15 = 0.05F;
                        var14.motionX = (double)((float)random.nextGaussian() * var15);
                        var14.motionY = (double)((float)random.nextGaussian() * var15 + 0.2F);
                        var14.motionZ = (double)((float)random.nextGaussian() * var15);

                        if (var9.hasTagCompound())
                        {
                            var14.getEntityItem().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
                        }
                    }
                }
            }
        }

        super.breakBlock(world, i, j, k, par5, par6);
	}
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("wood");
    }

}
