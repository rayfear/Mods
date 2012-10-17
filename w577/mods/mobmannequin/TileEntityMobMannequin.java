package w577.mods.mobmannequin;

import w577.mods.mobmannequin.PacketHandler;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityList;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;

public class TileEntityMobMannequin extends TileEntity {

	public int mobid;
	public int rotation = 45;
	public Entity ent;
	
	public Entity getEntityFromTe() {
		if (MobMannequin.disallowedMobIds.contains(mobid)) {
			return null;
		}
		if (ent != null) {
			return ent;
		}
		ent = EntityList.createEntityByID(mobid, worldObj);
		ent.setPositionAndRotation(xCoord, yCoord, zCoord, rotation, 0);
		return ent;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		mobid = par1NBTTagCompound.getInteger("mobid");
		rotation = par1NBTTagCompound.getInteger("rot");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("mobid", mobid);
		par1NBTTagCompound.setInteger("rot", rotation);
	}
	
	@Override
	public Packet getAuxillaryInfoPacket() {
		Packet pkt = PacketHandler.getPacket(this);
		return pkt;
	}
}
