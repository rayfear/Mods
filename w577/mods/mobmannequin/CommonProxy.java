package w577.mods.mobmannequin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.server.FMLServerHandler;

public class CommonProxy implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return null;
	}

	public void registerRendererHandler() {
		
	}
	
	public World getClientWorld() {
		return null;
	}

	public MinecraftServer getServer() {
		return FMLServerHandler.instance().getServer();
	}

	public String getType() {
		return "Common";
	}

	public EntityPlayer getClientPlayer() {
		return null;
	}

	public void deleteEntityAt(int x, int y, int z) {
		
	}

}
