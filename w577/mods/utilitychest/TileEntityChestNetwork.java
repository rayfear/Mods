package w577.mods.utilitychest;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;

public class TileEntityChestNetwork extends TileEntityChestUtility {

	public String network;

	public TileEntityChestNetwork() {
		network = "";
		altSaving = true;
	}

	@Override
	public String getInvName() {
		StringBuilder net = new StringBuilder();
		net.append("\"");
		if (network.indexOf('+') != -1) {
			net.append(network.substring(0, network.indexOf('+')));
			net.append("\"*");
		} else {
			net.append(network);
			net.append("\"");
		}
		
		return "Networked Chest: " + net.toString();
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttc) {
		super.writeToNBT(nbttc);
		nbttc.setString("Network", network);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttc) {
		super.readFromNBT(nbttc);
		network = nbttc.getString("Network");
	}

	@Override
	public Packet getAuxillaryInfoPacket() {
		Packet pkt = PacketHandler.getPacketNetwork(this);
		return pkt;
	}

	@Override
	public ItemStack getStackInSlot(int par1) {
		return UtilityChest.cnhInstance.handleGetStackInSlot(this, par1);
	}

	@Override
	public void onInventoryChanged() {
		super.onInventoryChanged();
		UtilityChest.cnhInstance.saveContents();
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		return UtilityChest.cnhInstance.handleDecrStackSize(this, i, j);
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack is) {
		UtilityChest.cnhInstance.handleSetInvSlot(this, var1, is);
	}

	public void handlePacketData(String network) {
		this.network = network;
		onInventoryChanged();
		UtilityChest.cnhInstance.handleBlockPlaced(this);
	}

}
