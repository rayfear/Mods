package w577.mods.utilitychest;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraftforge.common.ForgeDirection;

public abstract class TileEntityAdvancedChestUtility extends TileEntityChestUtility implements ISidedInventory, net.minecraftforge.common.ISidedInventory {
	
	public int burnTime = 0;
	
	public int curItemBurnTime = 0;
	
	public int cookTime = 0;
	
	public int timeToSmelt = 1500;
	
	private int[] fuelSlot = {0};
	
	private int[] chestSlots = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27};
	
	public TileEntityAdvancedChestUtility() {
		this.contents = new ItemStack[28];
		this.startSlot = 1;
	}
	
	@Override
	public int getSizeInventory() {
		return 28;
	}

	@Override
	public int getStartInventorySide(ForgeDirection side) {
		if (side == ForgeDirection.UP) {
			return 0;
		}
		return 1;
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side) {
		if (side == ForgeDirection.UP) {
			return 1;
		}
		return 27;
	}
	
	public abstract int getGuiXPos();

	public boolean isFuel(ItemStack curStack) {
		return false;
	}
	
	public int getFuelBurnTime(ItemStack curStack) {
		return 0;
	}
	
	public int getScaledPercentBurned(int scaler) {
		if (curItemBurnTime == 0) {
			curItemBurnTime = 200;
		}
		return (burnTime*scaler/curItemBurnTime);
	}
	

	
	@Override
	public void readFromNBT(NBTTagCompound tagComp) {
		super.readFromNBT(tagComp);
		burnTime = tagComp.getInteger("burnTime");
		cookTime = tagComp.getInteger("cookTime");
		curItemBurnTime = tagComp.getInteger("curItem");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagComp) {
		super.writeToNBT(tagComp);
		tagComp.setInteger("burnTime", burnTime);
		tagComp.setInteger("cookTime", cookTime);
		tagComp.setInteger("curItem", curItemBurnTime);
	}
	
	public boolean burnFromTop() {
		return true;
	}

	public boolean doesBurn() {
		return true;
	}

	public int getScaledPercentCooked(int i) {
		return cookTime*i/timeToSmelt;
	}

	public boolean doesCook() {
		return true;
	}
	
	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		return i == 0 ? isFuel(itemstack) : true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return var1 == 1 ? fuelSlot : chestSlots;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return i == 0 ? isFuel(itemstack) : true;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return j != 0 || i != 0;
	}
}
