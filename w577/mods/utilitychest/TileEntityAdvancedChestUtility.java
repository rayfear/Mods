package w577.mods.utilitychest;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public abstract class TileEntityAdvancedChestUtility extends TileEntityChestUtility implements ISidedInventory {
	
	public int burnTime = 0;
	
	public int curItemBurnTime = 0;
	
	public int cookTime = 0;
	
	public int timeToSmelt = 1500;
	
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
}
