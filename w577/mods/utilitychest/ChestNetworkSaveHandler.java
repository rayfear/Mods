package w577.mods.utilitychest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.event.world.WorldEvent.Save;
import net.minecraftforge.event.world.WorldEvent.Unload;

public class ChestNetworkSaveHandler {
	
	public static class ChestNetworkWorldEventHandler {
		@ForgeSubscribe
		public void onWorldLoad(Load evt) {
			ChestNetworkSaveHandler handler = instance(evt.world.isRemote);
			if (evt.world.isRemote) {
				reloadHandler(true, null);
			} else if (handler == null) {
				reloadHandler(false, evt.world);
			}
		}

		@ForgeSubscribe
		public void onWorldSave(Save evt) {
			if (!evt.world.isRemote) {
				instance(false).save();
			}
		}
		
		@ForgeSubscribe
		public void onWorldUnload(Unload evt) {
			if (!evt.world.isRemote && evt.world.provider.dimensionId == 0) {
				serverSide = null;
			}
		}
	}
	
	private String curWorld;
	
	public ArrayList<String> networks;
	public HashMap<String, InventoryChestNetwork> contents;
	private boolean side;
	
	private static ChestNetworkSaveHandler clientSide;
	private static ChestNetworkSaveHandler serverSide;
	
	public ChestNetworkSaveHandler(boolean side, World world) {
		curWorld = "";
		
		networks = new ArrayList<String>();
		contents = new HashMap<String, InventoryChestNetwork>();
		this.side = side;
		if (!side) {
			load(world);
		}
	}
	
	private static void reloadHandler(boolean b, World world) {
		ChestNetworkSaveHandler newHand = new ChestNetworkSaveHandler(b, world);
		if (b) {
			clientSide = newHand;
		} else {
			serverSide = newHand;
		}
	}

	public void load(World world) {
		String worldName = world.getSaveHandler().getWorldDirectoryName();
		
		if (curWorld.equals(worldName)) {
			return;
			//Already loaded - do not load
		}
		curWorld = worldName;
		networks = new ArrayList<String>();
		contents = new HashMap<String, InventoryChestNetwork>();
		
		File file = MinecraftServer.getServer().getFile("");
		if (!(MinecraftServer.getServer() instanceof DedicatedServer)) {
			file = new File(file, "/saves/");
		}
		file = new File(file, curWorld + "/ntwch.dat");
		
		if (file.exists()) {
			try {
				FileInputStream fis = new FileInputStream(file);
				NBTTagList ntwchTagList = CompressedStreamTools.readCompressed(fis).getTagList("ntwch");
				NBTTagCompound ntwchTagComp = (NBTTagCompound) ntwchTagList.tagAt(0);
				NBTTagList ntwchNetworks = ntwchTagComp.getTagList("networks");
				for (int i = 0; i < ntwchNetworks.tagCount(); i++) {
					String curNet = ((NBTTagString)ntwchNetworks.tagAt(i)).toString();
					networks.add(curNet);
					
					NBTTagList curConts = ntwchTagComp.getTagList(curNet+"contents");
					
					InventoryChestNetwork inv = new InventoryChestNetwork(curNet, curNet);
					
					for (int j = 0; j < curConts.tagCount(); j ++) {
						NBTTagCompound curSlot = (NBTTagCompound) curConts.tagAt(j);
						byte curSlotNum = curSlot.getByte("Slot");
						ItemStack curStack = ItemStack.loadItemStackFromNBT(curSlot);
						if (curStack == null) {
							continue;
						}
						if (curSlotNum >= 0 && curSlotNum < inv.getSizeInventory()) {
							inv.setInventorySlotContents(curSlotNum, curStack);
						}
					}
					
					contents.put(curNet, inv);
				}
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

	public void save() {
		NBTTagList ntwchTagList = new NBTTagList();
		NBTTagCompound ntwchTagComp = new NBTTagCompound();
		NBTTagList ntwchNetworks = new NBTTagList();
		for (String s : networks) {
			ntwchNetworks.appendTag(new NBTTagString(s, s));
			NBTTagList curConts = new NBTTagList();
			for (int i = 0; i < contents.get(s).getSizeInventory(); i++) {
				if (contents.get(s).getStackInSlot(i) != null) {
					NBTTagCompound curStack = new NBTTagCompound();
					curStack.setByte("Slot", (byte) i);
					contents.get(s).getStackInSlot(i).writeToNBT(curStack);
					curConts.appendTag(curStack);
				}
				ntwchTagComp.setTag(s + "contents", curConts);
			}
		}
		ntwchTagComp.setTag("networks", ntwchNetworks);
		ntwchTagList.appendTag(ntwchTagComp);
		
		NBTTagCompound ntwchCompMain = new NBTTagCompound();
		ntwchCompMain.setTag("ntwch", ntwchTagList);
		
		File file = MinecraftServer.getServer().getFile("");
		if (!(MinecraftServer.getServer() instanceof DedicatedServer)) {
			file = new File(file, "/saves/");
		}
		File tmpFile = new File(file, curWorld + "/tmpntwch.dat");
		file = new File(file, curWorld + "/ntwch.dat");
		
		
		try {
			FileOutputStream fos = new FileOutputStream(tmpFile);
			CompressedStreamTools.writeCompressed(ntwchCompMain, fos);
			if (file.exists()) {
				file.delete();
			}
			tmpFile.renameTo(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public InventoryChestNetwork getInventory(String network, String name) {
		if (!networks.contains(network)) {
			networks.add(network);
			contents.put(network, new InventoryChestNetwork(network, name));
		}
		contents.get(network).setName(name);
		return contents.get(network);
	}

	public static ChestNetworkSaveHandler instance(boolean b) {
		if (b) {
			return clientSide;
		} else {
			return serverSide;
		}
	}

}
