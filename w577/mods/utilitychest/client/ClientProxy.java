package w577.mods.utilitychest.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import w577.mods.utilitychest.ChestNetworkSaveHandler;
import w577.mods.utilitychest.CommonProxy;
import w577.mods.utilitychest.TileEntityChestNetwork;
import w577.mods.utilitychest.UtilityChest;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChestNetwork.class, new TileEntityChestUtilityRenderer(this.getImageDir() + "chestnetwork.png"));
		
		UtilityChest.renderId = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new ChestUtilityRenderHelper());
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (!(te instanceof TileEntityChestNetwork)) {
			return null;
		}
		TileEntityChestNetwork tecn = (TileEntityChestNetwork) te;
		if (ID == 0) {
			return new GuiNetworkChest(tecn, world);
		}
		if (ID == 1) {
			ChestNetworkSaveHandler ces = ChestNetworkSaveHandler.instance(false);
			return new GuiChest(player.inventory, (IInventory) ces.getInventory(tecn.network));
		}
		return null;
	}
	
	@Override
	public World getClientWorld() {
		return Minecraft.getMinecraft().theWorld;
	}
}
