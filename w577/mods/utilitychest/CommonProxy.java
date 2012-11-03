package w577.mods.utilitychest;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ContainerChest;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.server.FMLServerHandler;

public class CommonProxy implements IGuiHandler {

	public void showPlayerGuiChestNetwork(EntityPlayer player,
			TileEntityChestNetwork te, World world, int x, int y, int z) {

	}

	public void registerTileEntitySpecialRenderers() {

	}

	public Object getNewNetworkGui(TileEntityChestNetwork te) {
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityChestUtility) {
			if (ID == 1) {
				return new ContainerChest(player.inventory, (IInventory) te);
			} else if (ID == 2) {
				return new ContainerAdvancedChest(player.inventory, (IInventory) te);
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityChestNetwork && ID == 0) {
			return getNewNetworkGui((TileEntityChestNetwork) te);
		}
		if (te != null && te instanceof TileEntityChestUtility) {
			if (ID == 1) {
				return getNewSimpleChestGui(player, (TileEntityChestUtility) te);
			} else if (ID == 2) {
				return getNewAdvancedChestGui(player, (TileEntityChestUtility) te);
			}
		}
		return null;
	}

	public Object getNewAdvancedChestGui(EntityPlayer player, TileEntityChestUtility te) {
		return null;
	}

	public Object getNewSimpleChestGui(EntityPlayer player, TileEntityChestUtility te) {
		return null;
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

	public void showPlayerGuiChest(EntityPlayer entityplayer,
			TileEntityChestUtility chestu, World world, int i, int j, int k) {
		entityplayer.displayGUIChest((IInventory) chestu);		
	}

}
