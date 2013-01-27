package w577.mods.utilitychest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ToolsFuels {

	public static ToolsFuels fuels = new ToolsFuels();
	
	private HashMap<List<Integer>, Integer> fuelMap = new HashMap();
	
	public static ToolsFuels instance() {
		return fuels;
	}
	
	private ToolsFuels() {
		addFuel(Item.stick.itemID, 0, 100);
		addFuel(Block.planks.blockID, Arrays.asList(0, 1, 2, 3), 400);
		addFuel(Block.cobblestone.blockID, 0, 800);
		addFuel(Item.ingotIron.itemID, 0, 1600);
		addFuel(Item.ingotGold.itemID, 0, 5200);
		addFuel(Item.diamond.itemID, 0, 10400);
	}
	
	public void addFuel(int itemID, List<Integer> itemDamages, int fuelTime) {
		for (int i = 0; i < itemDamages.size(); i++) {
			addFuel(itemID, itemDamages.get(i), fuelTime);
		}
	}

	public void addFuel(int itemID, int itemDamage, int fuelTime) {
		fuelMap.put(Arrays.asList(itemID, itemDamage), fuelTime);
	}
	
	public int getFuelTime(ItemStack is) {
		if (is == null) {
			return 0;
		}
		if (fuelMap.get(Arrays.asList(is.itemID, is.getItemDamage())) == null) {
			return 0;
		}
		return fuelMap.get(Arrays.asList(is.itemID, is.getItemDamage()));
	}
}
