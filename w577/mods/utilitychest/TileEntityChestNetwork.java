package w577.mods.utilitychest;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileEntityChestNetwork extends TileEntity implements IInventory {

	public String network;
	private ChestNetworkHandler cnh;
	
	public TileEntityChestNetwork() {
		network = "";
		cnh = UtilityChest.getCNH();
	}

	@Override
	public int getSizeInventory() {
		return 27;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return UtilityChest.getCNH().handleGetStackInSlot(var1, this);
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		return UtilityChest.getCNH().handleDecrStackInSlot(var1, var2, this);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return UtilityChest.getCNH().handleGetStackInSlotOnClosing(var1, this);
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
		UtilityChest.getCNH().handleSetInvSlotContents(var1, var2, this);
	}

	@Override
	public String getInvName() {
		StringBuilder name = new StringBuilder().append("Networked Chest: \"");
		if (network.contains("+")) {
			name.append(network.substring(0,network.indexOf("+"))).append("\"*");
		} else {
			name.append(network).append("\"");
		}
		return name.toString();
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
	public void openChest() {
		
	}

	@Override
	public void closeChest() {
		
	}
	
	@Override
	public void onInventoryChanged() {
		super.onInventoryChanged();
	}
	
	public void handlePacketData(String network) {
		this.network = network;
		UtilityChest.getCNH().handleBlockPlaced(this);
	}
	
}
