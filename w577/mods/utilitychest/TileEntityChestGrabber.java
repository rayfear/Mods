package w577.mods.utilitychest;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityChestGrabber extends TileEntityChestUtility {

	@Override
	public String getInvName() {
		return "tile.GrabberChest.name";
	}
	
	@Override
	public void dropItem(ItemStack is) {
		EntityItem item = new EntityItem(this.worldObj, this.xCoord + 0.5D, this.yCoord - 0.5D, this.zCoord + 0.5D, is);
		item.motionX = 0D;
		item.motionY = 0D;
		item.motionZ = 0D;
		if (is.hasTagCompound()) {
			item.getEntityItem().setTagCompound((NBTTagCompound) is.getTagCompound().copy());
		}
		this.worldObj.spawnEntityInWorld(item);
	}
}
