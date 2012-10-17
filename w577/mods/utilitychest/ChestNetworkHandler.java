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
	private Random random;

	public ChestNetworkHandler() {
		curWorld = "";
		random = new Random();
	}

	public void saveContents() {
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
		networks = new ArrayList<String>();
		contents = new HashMap<String, ItemStack[]>();
		File file;
		if (UtilityChest.proxy.getType().equals("Client")) {
			file = new File(UtilityChest.proxy.getServer().getFile("")
					.getAbsolutePath(), "\\saves\\" + curWorld + "\\ntwch.dat");
		} else if (UtilityChest.proxy.getType().equals("Common")) {
			file = new File(UtilityChest.proxy.getServer().getFile("")
					.getAbsolutePath(), "\\" + curWorld + "\\ntwch.dat");
		} else {
			return;
		}

		if (file.exists()) {
			try {
				FileInputStream fis = new FileInputStream(file);
				NBTTagList tag = CompressedStreamTools.readCompressed(fis)
						.getTagList("Ntwch");
				NBTTagCompound comp = (NBTTagCompound) tag.tagAt(0);
				NBTTagList nets = comp.getTagList("Networks");
				for (int i = 0; i < nets.tagCount(); i++) {
					networks.add(((NBTTagString) nets.tagAt(i)).toString());
				}
				for (String s : networks) {
					NBTTagList conts = comp.getTagList(s + "Contents");
					ItemStack chest[] = new ItemStack[27];
					for (int i = 0; i < conts.tagCount(); i++) {
						NBTTagCompound is = (NBTTagCompound) conts.tagAt(i);
						int j = is.getByte("Slot") & 0xFF;
						ItemStack item = ItemStack.loadItemStackFromNBT(is);
						if (item == null) {
							continue;
						}
						if (j >= 0 && j < chest.length) {
							chest[j] = item;
						}
					}

					contents.put(s, chest);
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
		String net = tecn.network;
		if (is == null) {
			return;
		}
		if (is.stackSize > tecn.getInventoryStackLimit()) {
			is.stackSize = tecn.getInventoryStackLimit();
		}
		if (!networks.contains(tecn.network)) {
			handleBlockPlaced(tecn);
		}
		if (contents.get(tecn.network) == null) {
			contents.put(tecn.network, new ItemStack[27]);
		}
		ItemStack[] iss = contents.get(net);
		iss[i] = is;
		contents.put(net, iss);
		tecn.onInventoryChanged();
	}

}
