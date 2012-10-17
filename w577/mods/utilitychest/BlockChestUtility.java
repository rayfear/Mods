package w577.mods.utilitychest;

import java.util.Random;

import cpw.mods.fml.common.registry.BlockProxy;

import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public abstract class BlockChestUtility extends BlockContainer {

	private Random random;
	public boolean altSaving = false;

	public BlockChestUtility(int id) {
		super(id, Material.wood);
		if (id >= 256) {
			disableStats();
		}
		random = new Random();
		blockIndexInTexture = 0;
		setHardness(2.5F);
		setStepSound(Block.soundWoodFootstep);
		setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
		this.setCreativeTab(CreativeTabs.tabDeco);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return UtilityChest.utilityChestRender;
	}

	@Override
	public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k,
			int l) {
		return blockIndexInTexture;
	}

	@Override
	public int getBlockTextureFromSide(int i) {
		return blockIndexInTexture;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k,
			EntityLiving entityliving) {

		byte meta = 0;
		int face = MathHelper
				.floor_double((double) ((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;

		if (face == 0) {
			meta = 2;
		}
		if (face == 1) {
			meta = 5;
		}
		if (face == 2) {
			meta = 3;
		}
		if (face == 3) {
			meta = 4;
		}

		world.setBlockMetadataWithNotify(i, j, k, meta);
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k,
			EntityPlayer entityplayer, int par6, float par7, float par8,
			float par9) {
		TileEntityChestUtility chestu = (TileEntityChestUtility) world
				.getBlockTileEntity(i, j, k);
		
		UtilityChest.proxy.showPlayerGuiChest(
				entityplayer,
				chestu,
				world, i, j, k);

		//entityplayer.displayGUIChest((IInventory) chestu);
		return true;
	}

	public boolean getNextFreeSlot(World world, ItemStack is,
			TileEntityChestUtility uchest) {
		if (is == null) {
			return false;
		}

		int i = 0;
		ItemStack itemstack;

		if (is.isStackable()) {
			while (is.stackSize > 0 && i < uchest.getSizeInventory()) {
				itemstack = uchest.getStackInSlot(i++);
				if (itemstack == null) {
					continue;
				}

				if (itemstack.itemID == is.itemID
						&& (!is.getHasSubtypes() || itemstack.getItemDamage() == is
								.getItemDamage())
						&& ItemStack.func_77970_a(is, itemstack)) {
					int j = is.stackSize + itemstack.stackSize;
					if (j <= is.getMaxStackSize()) {
						is.stackSize = 0;
						itemstack.stackSize = j;
					} else {
						is.stackSize -= is.getMaxStackSize()
								- itemstack.stackSize;
						itemstack.stackSize = itemstack.getMaxStackSize();
					}
				}
			}
		}

		if (is.stackSize > 0) {
			i = 0;
			while (i < uchest.getSizeInventory()) {
				itemstack = uchest.getStackInSlot(i);
				if (itemstack == null) {
					uchest.setInventorySlotContents(i, is.copy());
					is.stackSize = 0;
					break;
				}
				i++;
			}

			if (is.stackSize > 0) {
				dropItem(world, is.copy(), uchest);
				is.stackSize = 0;
			}
		}
		return true;
	}

	public void dropItem(World world, ItemStack is,
			TileEntityChestUtility uchest) {
		EntityItem item = new EntityItem(world, ((float) uchest.xCoord) + 0.5F,
				((float) uchest.yCoord) + 0.5F, ((float) uchest.zCoord) + 0.5F,
				is);
		item.motionX = 0.0F;
		item.motionY = 0.4F;
		item.motionZ = 0.0F;
		if (is.hasTagCompound()) {
			item.item.setTagCompound((NBTTagCompound) is.getTagCompound()
					.copy());
		}
		world.spawnEntityInWorld(item);

	}

	@Override
	public void breakBlock(World world, int par2, int par3, int par4, int par5,
			int par6) {
		if (!altSaving) {
			TileEntityChestUtility tecu = (TileEntityChestUtility) world
					.getBlockTileEntity(par2, par3, par4);
			if (tecu != null) {
				for (int i = 0; i < tecu.getSizeInventory(); i++) {
					ItemStack itemstack = tecu.getStackInSlot(i);

					if (itemstack == null) {
						continue;
					}

					float f = random.nextFloat() * 0.8F + 0.1F;
					float f1 = random.nextFloat() * 0.8F + 0.1F;
					float f2 = random.nextFloat() * 0.8F + 0.1F;

					while (itemstack.stackSize > 0) {
						int j = random.nextInt(21) + 10;

						if (j > itemstack.stackSize) {
							j = itemstack.stackSize;
						}

						itemstack.stackSize -= j;
						EntityItem entityitem = new EntityItem(world,
								(float) par2 + f, (float) par3 + f1,
								(float) par4 + f2, new ItemStack(
										itemstack.itemID, j,
										itemstack.getItemDamage()));
						float f3 = 0.05F;
						entityitem.motionX = (float) random.nextGaussian() * f3;
						entityitem.motionY = (float) random.nextGaussian() * f3
								+ 0.2F;
						entityitem.motionZ = (float) random.nextGaussian() * f3;

						if (itemstack.hasTagCompound()) {
							entityitem.item
									.setTagCompound((NBTTagCompound) itemstack
											.getTagCompound().copy());
						}

						world.spawnEntityInWorld(entityitem);
					}
				}
			}
		}
	}

}
