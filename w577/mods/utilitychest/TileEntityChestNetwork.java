package w577.mods.utilitychest;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;

public class TileEntityChestNetwork extends TileEntityChestUtility {

	public String network;
	public int dimension;

	public TileEntityChestNetwork() {
		dimension = 0;
		network = "";
		altSaving = true;
	}

	@Override
	public String getInvName() {
		//System.out.println(this.hashCode() + " - Network is " + this.network);
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
		System.out.println(this.hashCode() + " - saving network: " + this.network);
		nbttc.setString("Network", network);
		nbttc.setInteger("Dim", dimension);
		System.out.println(this.hashCode() + " - saving done: " + network + "--");
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttc) {
		super.readFromNBT(nbttc);
		System.out.println(nbttc.getInteger("x") + " - " + nbttc.getInteger("y") + " - " + nbttc.getInteger("z") + ":- " + nbttc.getString("Network"));
		network = nbttc.getString("Network");
		dimension = nbttc.getInteger("Dim");
		PacketDispatcher.sendPacketToAllInDimension(this.getDescriptionPacket(), dimension);
		System.out.println(this.hashCode() + " - loading done: " + network + "--");
	}

	@Override
	public Packet getDescriptionPacket() {
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
		/*if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			UtilityChest.proxy.sortShitOut(this);
		}*/
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

	public void handlePacketData(String network, int dim) {
		System.out.println(this.hashCode() + " " + UtilityChest.proxy.getType() + " network is: " + network);
		this.network = network;
		this.dimension = dim;
		onInventoryChanged();
		UtilityChest.cnhInstance.handleBlockPlaced(this);
	}

}
