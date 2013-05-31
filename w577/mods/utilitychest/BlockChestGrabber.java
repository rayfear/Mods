package w577.mods.utilitychest;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockChestGrabber extends BlockChestUtility {

	protected BlockChestGrabber(int id) {
		super(id, TileEntityChestGrabber.class);
		this.setUnlocalizedName("GrabberChest");
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		if (world.isRemote) {
			return;
		}
		TileEntity te = world.getBlockTileEntity(i, j, k);
		if (entity instanceof EntityItem && te instanceof TileEntityChestGrabber) {
			TileEntityChestGrabber tecg = (TileEntityChestGrabber) te;
			EntityItem item = (EntityItem) entity;
			ItemStack is = item.getEntityItem();
			tecg.putStackInSlotOrDrop(is);
			entity.setDead();
			tecg.onInventoryChanged();
		}
	}

}
