package w577.mods.utilitychest.client;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiChest;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import w577.mods.utilitychest.CommonProxy;
import w577.mods.utilitychest.TileEntityChestAdvancedUtility;
//import w577.mods.utilitychest.TileEntityChestDeenchant;
import w577.mods.utilitychest.TileEntityChestGrabber;
import w577.mods.utilitychest.TileEntityChestNetwork;
import w577.mods.utilitychest.TileEntityChestOres;
import w577.mods.utilitychest.TileEntityChestSmelter;
import w577.mods.utilitychest.TileEntityChestTools;
import w577.mods.utilitychest.TileEntityChestUtility;
import w577.mods.utilitychest.UtilityChest;

public class ClientProxy extends CommonProxy {

	@Override
	public void showPlayerGuiChestNetwork(EntityPlayer player,
			TileEntityChestNetwork te, World world, int x, int y, int z) {
		player.openGui(UtilityChest.instance, 0, world, x, y, z);
	}
	
	@Override
	public void showPlayerGuiChest(EntityPlayer player, TileEntityChestUtility te, World world, int x, int y, int z) {
		player.openGui(UtilityChest.instance, te.getGuiId(), world, x, y, z);
	}
	
	@Override
	public Object getNewAdvancedChestGui(EntityPlayer player, TileEntityChestUtility te) {
		return new GuiAdvancedChest(player.inventory, (TileEntityChestAdvancedUtility) te);
	}

	@Override
	public Object getNewSimpleChestGui(EntityPlayer player, TileEntityChestUtility te) {
		return new GuiChest(player.inventory, te);
	}

	@Override
	public Object getNewNetworkGui(TileEntityChestNetwork te) {
		return new GuiChestNetwork(te);
	}

	@Override
	public void registerTileEntitySpecialRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(
				TileEntityChestNetwork.class,
				new TileEntityChestUtilityRenderer(
						"/chestModImages/chestnetwork.png"));
		ClientRegistry.bindTileEntitySpecialRenderer(
				TileEntityChestTools.class, new TileEntityChestUtilityRenderer(
						"/chestModImages/chesttools.png"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChestOres.class,
				new TileEntityChestUtilityRenderer(
						"/chestModImages/chestores.png"));
		ClientRegistry.bindTileEntitySpecialRenderer(
				TileEntityChestGrabber.class,
				new TileEntityChestUtilityRenderer(
						"/chestModImages/chestgrabber.png"));
		ClientRegistry.bindTileEntitySpecialRenderer(
				TileEntityChestSmelter.class,
				new TileEntityChestUtilityRenderer(
						"/chestModImages/chestsmelter.png"));
		//ClientRegistry.bindTileEntitySpecialRenderer(
		//		TileEntityChestDeenchant.class,
		//		new TileEntityChestUtilityRenderer(
		//				"/chestModImages/chestdeenchant.png"));

		UtilityChest.instance.utilityChestRender = RenderingRegistry
				.getNextAvailableRenderId();
		RenderingRegistry
				.registerBlockHandler(new ChestUtilityRendererHandler());
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
	public void sortShitOut(TileEntityChestNetwork tecn) {
		World world = getServer().worldServerForDimension(getClientPlayer().dimension);
		world.setBlockTileEntity(tecn.xCoord, tecn.yCoord, tecn.zCoord, tecn);
	}
}
