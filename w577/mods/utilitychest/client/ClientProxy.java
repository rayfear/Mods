package w577.mods.utilitychest.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import w577.mods.utilitychest.ChestNetworkSaveHandler;
import w577.mods.utilitychest.CommonProxy;
import w577.mods.utilitychest.InventoryChestNetwork;
import w577.mods.utilitychest.TileEntityAdvancedChestUtility;
import w577.mods.utilitychest.TileEntityChestDeenchanter;
import w577.mods.utilitychest.TileEntityChestGrabber;
import w577.mods.utilitychest.TileEntityChestNetwork;
import w577.mods.utilitychest.TileEntityChestOres;
import w577.mods.utilitychest.TileEntityChestSmelter;
import w577.mods.utilitychest.TileEntityChestStealth;
import w577.mods.utilitychest.TileEntityChestTools;
import w577.mods.utilitychest.UtilityChest;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChestNetwork.class, new TileEntityChestUtilityRenderer(this.getImageDir() + "chestnetwork.png"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChestTools.class, new TileEntityChestUtilityRenderer(this.getImageDir() + "chesttools.png"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChestOres.class, new TileEntityChestUtilityRenderer(this.getImageDir() + "chestores.png"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChestGrabber.class, new TileEntityChestUtilityRenderer(this.getImageDir() + "chestgrabber.png"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChestSmelter.class, new TileEntityChestUtilityRenderer(this.getImageDir() + "chestsmelter.png"));
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChestDeenchanter.class, new TileEntityChestUtilityRenderer(this.getImageDir() + "chestdeenchant.png"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChestStealth.class, new TileEntityChestUtilityRenderer(this.getImageDir() + "cheststealth.png"));
		
		UtilityChest.renderId = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new ChestUtilityRenderHelper());
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (ID == 0) {
			TileEntityChestNetwork tecn = (TileEntityChestNetwork) te;
			return new GuiNetworkChest(tecn, world);
		}
		if (ID == 1) {
			TileEntityChestNetwork tecn = (TileEntityChestNetwork) te;
			if (MinecraftServer.getServer() == null || ChestNetworkSaveHandler.instance(false) == null) {
				return new GuiChest(player.inventory, new InventoryChestNetwork("", tecn.network));
			}
			System.out.println(MinecraftServer.getServer());
			ChestNetworkSaveHandler ces = ChestNetworkSaveHandler.instance(false);
			String name = ((TileEntityChestNetwork) MinecraftServer.getServer().worldServerForDimension(player.dimension).getBlockTileEntity(x, y, z)).network;
			return new GuiChest(player.inventory, (IInventory) ces.getInventory("", name));
		}
		if (ID == 2) {
			return new GuiAdvancedChestUtility(player.inventory, (TileEntityAdvancedChestUtility) te);
		}
		return null;
	}
	
	@Override
	public World getClientWorld() {
		return Minecraft.getMinecraft().theWorld;
	}
}
