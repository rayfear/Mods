package w577.mods.mobmannequin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import w577.mods.mobmannequin.CommonProxy;
import w577.mods.mobmannequin.MobMannequin;
import w577.mods.mobmannequin.TileEntityMobMannequin;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRendererHandler() {
		MobMannequin.mobManneRender = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new MannequinBlockRendererHandler());
		//TileEntityMobMannequinRenderer.registerEntities();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMobMannequin.class, new TileEntityMobMannequinRenderer());
	}
	
	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}

	@Override
	public EntityPlayer getClientPlayer() {
		return FMLClientHandler.instance().getClient().thePlayer;
	}

	@Override
	public MinecraftServer getServer() {
		return Minecraft.getMinecraft().getIntegratedServer();
	}

	@Override
	public String getType() {
		return "Client";
	}
	
	@Override
	public void deleteEntityAt(int x, int y, int z) {
		TileEntityMobMannequinRenderer.deleteEntityInPosition(x, y, z);
	}
}