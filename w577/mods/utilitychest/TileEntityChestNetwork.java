package w577.mods.utilitychest;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;

public class TileEntityChestNetwork extends TileEntity {

	public String network;
	
	public TileEntityChestNetwork() {
		network = "";
	}

	/*@Override
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
		if (var1 != 1) {
			return;
		}
		System.out.println("Side: " + FMLCommonHandler.instance().getEffectiveSide()); 
		if (var2 != null) {
			System.out.println("Stacksize: " + var2.stackSize);
		} else {
			System.out.println("Stacksize: null");
		}
		/*if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			//System.out.println("Side is client, so returning");
			return;
		}
		/*if (var2 != null) {
			System.out.println("Stacksize: " + var2.stackSize);
		}
		UtilityChest.getCNH().handleSetInvSlotContents(var1, var2, network);
		//System.out.println("Sending packet to server, set");
		//PacketDispatcher.sendPacketToServer(PacketHandler.getNetworkPacketSet(this, var1, var2));
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
		
	}*/
	
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        network = par1NBTTagCompound.getString("network");
    }
	
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setString("network", network);
    }
	
	@Override
	public Packet getDescriptionPacket() {
		return PacketHandler.getNetworkPacket(this);	
	}
	
	/*@Override
	public void onInventoryChanged() {
		super.onInventoryChanged();
	}*/
	
	public void handlePacketData(String network) {
		this.network = network;
	}
	
}
