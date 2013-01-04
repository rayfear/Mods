package w577.mods.utilitychest;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class ChestNetworkHandler {
	
	public ArrayList<String> networks;
	public HashMap<String, NetworkItems> contents;
	
	public String curWorld;
	
	public int tickCount;
	
	public ChestNetworkHandler() {
		curWorld = "";
		networks = new ArrayList<String>();
		contents = new HashMap<String, NetworkItems>();
		tickCount = 1;
	}

	public void load(World world) {
		String worldName = world.getSaveHandler().getSaveDirectoryName();
		if (curWorld.equals(worldName)) {
			//Already loaded, no need to load.
			return;
		}
		System.out.println(worldName);
	}
	
	public void save() {
		File saveFile;
		File tempSaveFile;
		
		String savePath = (saveFile = new File("")).getAbsolutePath();
		
		//System.out.println(savePath);
	}

	public void tick() {
		
		tickCount++;
		
		if (tickCount >= 20) {
			tickCount = 0;
		}
		save();
	}

	//TODO: Maybe implement client loading from server
	public void loadFromServer() {
		
	}

	public ItemStack handleGetStackInSlot(int var1,
			TileEntityChestNetwork tecn) {
		NetworkItems items = contents.get(tecn.network);
		
		if (items == null) {
			return null;
		}
		if (items.getItemAt(var1) == null) {
			return null;
		}
		if (items.getItemAt(var1).stackSize <= 0) {
			return null;
		}
		return items.getItemAt(var1);
	}

	public ItemStack handleDecrStackInSlot(int var1, int var2,
			TileEntityChestNetwork tecn) {
		NetworkItems items = contents.get(tecn.network);
		if (items == null) {
			return null;
		}
		return items.decrStackAt(var1, var2, tecn.getInventoryStackLimit());
	}

	public ItemStack handleGetStackInSlotOnClosing(int var1,
			TileEntityChestNetwork tecn) {
		NetworkItems items = contents.get(tecn.network);
		if (items == null) {
			return null;
		}
		return items.getItemAt(var1);
	}

	public void handleSetInvSlotContents(int var1, ItemStack var2,
			TileEntityChestNetwork tecn) {
		NetworkItems items = contents.get(tecn.network);
		if (items == null) {
			return;
		}
		items.setItemAt(var1, var2, tecn.getInventoryStackLimit());
	}
	
	public void handleSetInvSlotContents(int var1, ItemStack var2,
			String network) {
		NetworkItems items = contents.get(network);
		if (items == null) {
			return;
		}
		items.setItemAt(var1, var2, 64);
	}

	public void handleBlockPlaced(TileEntityChestNetwork tecn) {
		if (!networks.contains(tecn.network)) {
			networks.add(tecn.network);
			contents.put(tecn.network, new NetworkItems());
		}
	}

}
