package w577.mods.utilitychest;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import net.minecraft.src.INetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class NetworkPacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		ByteArrayDataInput byteInput = ByteStreams.newDataInput(packet.data);
		int xPos = byteInput.readInt();
		int yPos = byteInput.readInt();
		int zPos = byteInput.readInt();
		int dimPos = byteInput.readInt();
		World world;
		if (packet.channel.equals("NetChest")) {
			world = UtilityChest.proxy.getServer().worldServerForDimension(dimPos);
		} else if (packet.channel.equals("NetChestServ")) {
			world = UtilityChest.proxy.getClientWorld();
			if (world.getWorldInfo().getDimension() != dimPos) {
				return;
			}
		} else {
			throw new IllegalStateException("Unkown packet recieved");
		}
		TileEntity tileEntity = world.getBlockTileEntity(xPos, yPos, zPos);
		if (tileEntity instanceof TileEntityChestNetwork) {
			TileEntityChestNetwork tileEntityCN = (TileEntityChestNetwork) tileEntity;
			
			int netLength = byteInput.readShort();
			
			StringBuilder netStrBuil = new StringBuilder();
			
			for (int i = 0; i < netLength; ++i) {
				netStrBuil.append(byteInput.readChar());
			}
			
			tileEntityCN.handlePacketData(netStrBuil.toString(), dimPos);
		}
		
	}

}
