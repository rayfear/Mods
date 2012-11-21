package w577.mods.utilitychest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.CompressedStreamTools;
import net.minecraft.src.EntityItem;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.NBTTagString;
import net.minecraft.src.World;

public class ChestNetworkHandler {

	public static List<String> networks;
	public static Map<String, ItemStack[]> contents;

	public static String curWorld;
	private int tick;

	public ChestNetworkHandler() {
		curWorld = "";
		ChestNetworkHandler.networks = new ArrayList<String>();
		ChestNetworkHandler.contents = new HashMap<String, ItemStack[]>();
	}

	public void saveContents() {
		if (FMLCommonHandler.instance().getEffectiveSide() != Side.SERVER) {
			return;
		}
		File file;
		File tmpFile;
		if (UtilityChest.proxy.getType().equals("Client")) {
			if (UtilityChest.proxy.getServer() == null) {
				return;
			}
			file = new File(UtilityChest.proxy.getServer().getFile("")
					.getAbsolutePath(), "\\saves\\" + curWorld + "\\ntwch.dat");
			tmpFile = new File(UtilityChest.proxy.getServer().getFile("")
					.getAbsolutePath(), "\\saves\\" + curWorld
					+ "\\tmpntwch.dat");
		} else if (UtilityChest.proxy.getType().equals("Common")) {
			file = new File(UtilityChest.proxy.getServer().getFile("")
					.getAbsolutePath(), "\\" + curWorld + "\\ntwch.dat");
			tmpFile = new File(UtilityChest.proxy.getServer().getFile("")
					.getAbsolutePath(), "\\" + curWorld + "\\tmpntwch.dat");
		} else {
			return;
		}
		try {
			FileOutputStream fos = new FileOutputStream(tmpFile);
			NBTTagList mainTag = new NBTTagList();
			NBTTagCompound comp = new NBTTagCompound();
			NBTTagList nets = new NBTTagList();
			for (String s : networks) {
				nets.appendTag(new NBTTagString(s, s));
			}
			comp.setTag("Networks", nets);
			for (String s : networks) {
				NBTTagList conts = new NBTTagList();
				for (int i = 0; i < contents.get(s).length; i++) {
					if (contents.get(s)[i] != null) {
						NBTTagCompound is = new NBTTagCompound();
						is.setByte("Slot", (byte) i);
						contents.get(s)[i].writeToNBT(is);
						conts.appendTag(is);
					}
				}
				comp.setTag(s + "Contents", conts);
			}
			mainTag.appendTag(comp);

			NBTTagCompound mainComp = new NBTTagCompound();
			mainComp.setTag("Ntwch", mainTag);
			CompressedStreamTools.writeCompressed(mainComp, fos);
			if (file.exists()) {
				file.delete();
			}
			tmpFile.renameTo(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadContents() {		
		ChestNetworkHandler.networks = new ArrayList<String>();
		ChestNetworkHandler.contents = new HashMap<String, ItemStack[]>();
		
		String proxyType = UtilityChest.proxy.getType();
		
		File file;
		String parentPath = UtilityChest.proxy.getServer().getFile("").getAbsolutePath();
		
		if (proxyType.equals("Client")) {
			file = new File(parentPath, "\\saves\\" + curWorld + "\\ntwch.dat");
		}
		else if (proxyType.equals("Common")) {
			file = new File(parentPath, "\\" + curWorld + "\\ntwch.dat");
		}
		else {
			throw new IllegalStateException("Unable to load Networked Chest networks and contents; unknown proxy type: " + proxyType);
		}

		if (file.exists()) {
			try {
				FileInputStream inStream = new FileInputStream(file);
				NBTTagList ntwchTagList = CompressedStreamTools.readCompressed(inStream).getTagList("Ntwch");
				NBTTagCompound ntwchTagMap = (NBTTagCompound) ntwchTagList.tagAt(0);
				NBTTagList savedNetworks = ntwchTagMap.getTagList("Networks");
				
				for (int i = 0; i < savedNetworks.tagCount(); i++) {
					String networkName = ((NBTTagString) savedNetworks.tagAt(i)).toString();
					ChestNetworkHandler.networks.add(networkName);
					
					System.out.println("Network `" + networkName + "`:\n"); // dbg
					
					NBTTagList savedNetworkContents = ntwchTagMap.getTagList(networkName + "Contents");
					ItemStack[] networkInventory = new ItemStack[27];
					
					for (int j = 0; j < savedNetworkContents.tagCount(); j++) {
						NBTTagCompound networkContentsSlot = (NBTTagCompound) savedNetworkContents.tagAt(j);
						int slotIndex = networkContentsSlot.getByte("Slot") & 0xFF;
						ItemStack stack = ItemStack.loadItemStackFromNBT(networkContentsSlot);
						if (stack == null) {
							continue;
						}
						System.out.println("Stack of " + stack.stackSize + " " + stack.getItemName());
						if (slotIndex >= 0 && slotIndex < networkInventory.length) {
							networkInventory[slotIndex] = stack;
						}
					}

					contents.put(networkName, networkInventory);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (!networks.contains("")) {
			networks.add("");
			contents.put("", new ItemStack[27]);
		}
		
	}

	public void tick() {
		if (UtilityChest.proxy.getServer() != null) {
			String savePlace = UtilityChest.proxy.getServer()
					.worldServerForDimension(0).getSaveHandler()
					.getSaveDirectoryName();
			if (!curWorld.equals(savePlace)) {
				tick = 0;
				curWorld = savePlace;
				loadContents();
				System.out.println("Loaded");
			}
			tick++;
			if (tick >= 20) {
				saveContents();
				tick = 0;
			}
		}

	}

	public void handleBlockPlaced(TileEntityChestNetwork tecn) {
		if (UtilityChest.proxy.getServer() == null) {
			return;
		}
		if (!networks.contains(tecn.network)) {
			networks.add(tecn.network);
			contents.put(tecn.network, new ItemStack[27]);
		}
	}

	public ItemStack handleDecrStackSize(TileEntityChestNetwork tecn, int i,
			int j) {
		String net = tecn.network;
		if (contents.get(net)[i] != null) {
			if (j > contents.get(net)[i].stackSize) {
				j = contents.get(net)[i].stackSize;
			}
			ItemStack is = contents.get(net)[i].splitStack(j);
			if (contents.get(net)[i].stackSize <= 0) {
				contents.get(net)[i] = null;
			}
			tecn.onInventoryChanged();
			return is;
		}
		return null;
	}

	public ItemStack handleGetStackInSlot(TileEntityChestNetwork tecn, int par1) {
		if (contents == null) {
			return null;
		}
		if (!networks.contains(tecn.network)) {
			handleBlockPlaced(tecn);
		}
		if (contents.get(tecn.network) == null) {
			contents.put(tecn.network, new ItemStack[27]);
		}
		return contents.get(tecn.network)[par1];
	}

	public void handleSetInvSlot(TileEntityChestNetwork tecn, int i,
			ItemStack is) {
		if (UtilityChest.proxy.getServer() == null) { return; }
		String net = tecn.network;
		if (is == null) {
			return;
		}
		if (is.stackSize == 0) {
			is = null;
			return;
		}
		if (is.stackSize > tecn.getInventoryStackLimit()) {
			is.stackSize = tecn.getInventoryStackLimit();
		}
		System.out.println("network is: " + net);
		System.out.println("networks is null: " + (networks == null ? "T" : "F"));
		System.out.println("tecn is null: " + (tecn == null ? "T" : "F"));
		System.out.println("Stacksize is: " + is.stackSize);
		if (!networks.contains(tecn.network)) {
			handleBlockPlaced(tecn);
		}
		if (contents.get(tecn.network) == null) {
			contents.put(tecn.network, new ItemStack[27]);
		}
		contents.get(net)[i] = is;
		tecn.onInventoryChanged();
	}

}
