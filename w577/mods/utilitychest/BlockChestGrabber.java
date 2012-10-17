package w577.mods.utilitychest;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockChestGrabber extends BlockChestUtility {

	public BlockChestGrabber(int id) {
		super(id);
		setBlockName("GrabberChest");
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityChestGrabber();
	}
	
	@Override
	public void dropItem(World world, ItemStack is,
			TileEntityChestUtility uchest) {
		EntityItem item = new EntityItem(world, ((float) uchest.xCoord) + 0.5F,
				((float) uchest.yCoord) - 0.5F, ((float) uchest.zCoord) + 0.5F,
				is);
		item.motionX = 0.0F;
		item.motionY = 0.0F;
		item.motionZ = 0.0F;
		if (is.hasTagCompound()) {
			item.item.setTagCompound((NBTTagCompound) is.getTagCompound()
					.copy());
		}
		world.spawnEntityInWorld(item);
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		TileEntity te = world.getBlockTileEntity(i, j, k);
		if (entity instanceof EntityItem && te instanceof TileEntityChestGrabber) {
			TileEntityChestGrabber tecg = (TileEntityChestGrabber) te;
			EntityItem item = (EntityItem) entity;
			ItemStack is = item.item;
			getNextFreeSlot(world, is, tecg);
			entity.setDead();
			tecg.onInventoryChanged();
		}
	}

}
