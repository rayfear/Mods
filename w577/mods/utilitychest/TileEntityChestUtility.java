package w577.mods.utilitychest;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityChestUtility extends TileEntity implements IInventory {
	
	public ItemStack[] contents = new ItemStack[27];
	public int startSlot = 0;

	@Override
	public int getSizeInventory() {
		return 27;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return contents[var1];
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		ItemStack curStack = getStackInSlot(var1);
		if (curStack != null) {
			ItemStack is;
			if (curStack.stackSize <= var2) {
				is = curStack.copy();
				setInventorySlotContents(var1, null);
			} else {
				is = curStack.splitStack(var2);
				if (curStack.stackSize <= 0) {
					curStack = null;
					setInventorySlotContents(var1, null);
				}
			}
			this.onInventoryChanged();
			return is;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		if (getStackInSlot(var1) != null) {
			ItemStack is = getStackInSlot(var1);
			setInventorySlotContents(var1, null);
			return is;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
		contents[var1] = var2;
		if (var2 != null && var2.stackSize > getInventoryStackLimit()) {
			var2.stackSize = getInventoryStackLimit();
		}
		this.onInventoryChanged();
	}

	@Override
	public String getInvName() {
		return "Utility Chest";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : var1.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}
	
	@Override
	public void readFromNBT(NBTTagCompound tagcomp) {
		super.readFromNBT(tagcomp);
		NBTTagList items = tagcomp.getTagList("Items");
		
		for (int i = 0; i < items.tagCount(); i++) {
			NBTTagCompound curItem = (NBTTagCompound) items.tagAt(i);
			byte slot = curItem.getByte("Slot");
			ItemStack curIS = ItemStack.loadItemStackFromNBT(curItem);
			if (slot < 0 || slot >= getSizeInventory()) {
				continue;
			}
			setInventorySlotContents(slot, curIS);
		}
		
		onInventoryChanged();
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagcomp) {
		super.writeToNBT(tagcomp);
		NBTTagList items = new NBTTagList();
		
		for (int i = 0; i < contents.length; i++) {
			if (getStackInSlot(i) != null) {
				NBTTagCompound curItem = new NBTTagCompound();
				curItem.setByte("Slot", (byte) i);
				getStackInSlot(i).writeToNBT(curItem);
				items.appendTag(curItem);
			}
		}
		
		tagcomp.setTag("Items", items);
	}
	
	public void putStackInSlotOrDrop(ItemStack smeltResult) {
		if (smeltResult == null) {
			return;
		}
		int i = startSlot ;
		ItemStack itemStack;
		if (smeltResult.isStackable()) {
			while (smeltResult.stackSize > 0 && i < getSizeInventory()) {
				itemStack = getStackInSlot(i++);
				if (itemStack == null) {
					continue;
				}
				
				if (itemStack.isItemEqual(smeltResult)) {
					int j = smeltResult.stackSize + itemStack.stackSize;
					if (j <= itemStack.getMaxStackSize()) {
						smeltResult.stackSize = 0;
						itemStack.stackSize = j;
					} else {
						smeltResult.stackSize -= itemStack.getMaxStackSize() - itemStack.stackSize;
						itemStack.stackSize = itemStack.getMaxStackSize();
					}
				}
			}
		}
			
		if (smeltResult.stackSize > 0) {
			i = startSlot;
			while (i < getSizeInventory()) {
				itemStack = getStackInSlot(i);
				if (itemStack == null) {
					setInventorySlotContents(i, smeltResult.copy());
					smeltResult.stackSize = 0;
					return;
				}
				i++;
			}
		} else {
			return;
		}
		dropItem(smeltResult);
	}

	public void dropItem(ItemStack smeltResult) {
		EntityItem item = new EntityItem(this.worldObj, this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, smeltResult);
		item.motionX = 0D;
		item.motionY = 0.4D;
		item.motionZ = 0D;
		if (smeltResult.hasTagCompound()) {
			item.getEntityItem().setTagCompound((NBTTagCompound) smeltResult.getTagCompound().copy());
		}
		this.worldObj.spawnEntityInWorld(item);
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

}
