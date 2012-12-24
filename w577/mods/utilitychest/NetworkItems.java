package w577.mods.utilitychest;

import net.minecraft.item.ItemStack;

public class NetworkItems {

	private ItemStack[] contents;
	
	public NetworkItems() {
		contents = new ItemStack[27];
	}
	
	public ItemStack getItemAt(int i) {
		if (i < 0 || i > 26) {
			return null;
		}
		if (contents[i] == null) {
			return null;
		}
		System.out.println(contents[i].stackSize);
		return contents[i];
	}
	
	public void setItemAt(int i, ItemStack items, int j) {
		if (items == null) {
			return;
		}
		if (items.stackSize <= 0) {
			return;
		}
		if (items.stackSize > j) {
			items.stackSize = j;
		}
		contents[i] = items;
	}
	
	public ItemStack decrStackAt(int i, int j, int k) {
		ItemStack is = getItemAt(i);
		if (is == null) {
			return null;
		}
		is.stackSize -= j;
		if (is.stackSize <= 0) {
			is = null;
		}
		setItemAt(i, is, k);
		return is;
		
	}
}
