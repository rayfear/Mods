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
	
	public void handlePacketData(String network) {
		this.network = network;
	}
	
}
