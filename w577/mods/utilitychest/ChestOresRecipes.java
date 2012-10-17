package w577.mods.utilitychest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ChestOresRecipes {

	public static ChestOresRecipes baserecipes = new ChestOresRecipes();
	private Map smeltingList = new HashMap();

	public static ChestOresRecipes getBase() {
		return baserecipes;
	}

	private ChestOresRecipes() {
		addRecipe(Block.oreCoal.blockID, 4, 2, Item.coal.shiftedIndex);
		addRecipe(Block.oreDiamond.blockID, 5, 2, Item.diamond.shiftedIndex);
		addRecipe(Block.oreLapis.blockID, 0, 21, 4,
				Item.dyePowder.shiftedIndex, 4);
		addRecipe(Block.oreRedstone.blockID, 11, 4, Item.redstone.shiftedIndex);
		addRecipe(Block.oreIron.blockID, Item.ingotIron.shiftedIndex);
		addRecipe(Block.oreGold.blockID, Item.ingotGold.shiftedIndex);
		addRecipe(Block.oreEmerald.blockID, 2, 3, Item.emerald.shiftedIndex);
	}

	public void addRecipe(int itemID, int returnID) {
		addRecipe(itemID, 0, 2, 1, returnID, 0);
	}

	public void addRecipe(int itemID, int rand, int base, int returnID) {
		addRecipe(itemID, 0, rand, base, returnID, 0);
	}

	public void addRecipe(ItemStack is, ItemStack returnIS) {
		addRecipe(is.itemID, is.getItemDamage(), 2, 1, returnIS.itemID,
				returnIS.getItemDamage());
	}

	public void addRecipe(ItemStack is, int rand, int base, ItemStack returnIS) {
		addRecipe(is.itemID, is.getItemDamage(), rand, base, returnIS.itemID,
				returnIS.getItemDamage());
	}

	public void addRecipe(int itemID, int itemDamage, int rand, int base,
			int returnID, int returnDam) {
		smeltingList.put(Arrays.asList(itemID, itemDamage),
				Arrays.asList(rand, base, returnID, returnDam));
	}

	public ItemStack getResult(int itemID, int itemDamage) {
		List<Integer> list = (List) smeltingList.get(Arrays.asList(itemID,
				itemDamage));

		if (list == null) {
			return null;
		}

		Random rand = new Random();
		int size = rand.nextInt(list.get(0)) + list.get(1);

		return new ItemStack(list.get(2), size, list.get(3));
	}

	public Map getRecipes() {
		return smeltingList;
	}

}
