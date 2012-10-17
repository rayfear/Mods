package w577.mods.utilitychest;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;

public abstract class TileEntityChestUtility extends TileEntity implements
		IInventory {

	private ItemStack[] chestContents = new ItemStack[28];
	protected boolean altSaving = false;

	@Override
	public int getSizeInventory() {
		return 27;
	}

	@Override
	public ItemStack getStackInSlot(int par1) {
		return this.chestContents[par1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if (this.chestContents[par1] != null) {
			ItemStack var3;

			if (this.chestContents[par1].stackSize <= par2) {
				var3 = this.chestContents[par1];
				this.chestContents[par1] = null;
				this.onInventoryChanged();
				return var3;
			} else {
				var3 = this.chestContents[par1].splitStack(par2);

				if (this.chestContents[par1].stackSize == 0) {
					this.chestContents[par1] = null;
				}

				this.onInventoryChanged();
				return var3;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1) {
		if (this.chestContents[par1] != null) {
			ItemStack var2 = this.chestContents[par1];
			this.chestContents[par1] = null;
			return var2;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack is) {
		this.chestContents[var1] = is;

		if (is != null && is.stackSize > this.getInventoryStackLimit()) {
			is.stackSize = this.getInventoryStackLimit();
		}

		this.onInventoryChanged();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord,
				this.zCoord) != this ? false : player.getDistanceSq(
				(double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D,
				(double) this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openChest() {

	}

	@Override
	public void closeChest() {

	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		if (!altSaving) {
			NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
			chestContents = new ItemStack[getSizeInventory()];

			for (int i = 0; i < nbttaglist.tagCount(); i++) {
				NBTTagCompound nbttagcompound = (NBTTagCompound) nbttaglist
						.tagAt(i);
				int j = nbttagcompound.getByte("Slot") & 0xff;

				if (j >= 0 && j < chestContents.length) {
					chestContents[j] = ItemStack
							.loadItemStackFromNBT(nbttagcompound);
				}
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		if (!altSaving) {
			NBTTagList nbttaglist = new NBTTagList();

			for (int i = 0; i < chestContents.length; i++) {
				if (chestContents[i] != null) {
					NBTTagCompound nbttagcompound = new NBTTagCompound();
					nbttagcompound.setByte("Slot", (byte) i);
					chestContents[i].writeToNBT(nbttagcompound);
					nbttaglist.appendTag(nbttagcompound);
				}
			}

			par1NBTTagCompound.setTag("Items", nbttaglist);
		}
	}

	public int getGuiId() {
		return 1;
	}

}
