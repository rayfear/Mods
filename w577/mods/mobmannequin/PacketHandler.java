package w577.mods.mobmannequin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(NetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		if (packet.channel.equals("MobManne")) {
			ByteArrayDataInput dat = ByteStreams.newDataInput(packet.data);
			int x = dat.readInt();
			int y = dat.readInt();
			int z = dat.readInt();
			int dim = dat.readInt();

			World world = MobMannequin.proxy.getType().equals("Common") ? MobMannequin.proxy.getServer().worldServerForDimension(dim) : MobMannequin.proxy.getClientWorld();
			
			if (world.getWorldInfo().getDimension() == dim) {
			
				TileEntity te = world.getBlockTileEntity(x, y, z);
				if (te instanceof TileEntityMobMannequin) {
					TileEntityMobMannequin temm = (TileEntityMobMannequin) te;
					temm.mobid = dat.readInt();
				}
			}
		}
		
	}
	
	public static Packet getPacket(TileEntityMobMannequin te) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		int x = te.xCoord;
		int y = te.yCoord;
		int z = te.zCoord;
		int dim = te.worldObj.getWorldInfo().getDimension();
		try {
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(z);
			dos.writeInt(dim);
			dos.writeInt(te.mobid);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Packet250CustomPayload pkt = new Packet250CustomPayload();
		pkt.channel = "MobManne";
		pkt.data = bos.toByteArray();
		pkt.length = bos.size();
		pkt.isChunkDataPacket = false;
		return pkt;
	}

}
