package w577.mods.utilitychest;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {
	
	private final String imageDir = "/chestModImages/";

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (ID == 0) {
		}
		if (ID == 1) {
			return new ContainerChest(player.inventory, (IInventory) te);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return null;
	}
	
	public void registerRenderers() {
		
	}
	
	public String getImageDir() {
		return imageDir;
	}
	
	public World getClientWorld() {
		return null;
	}

}
