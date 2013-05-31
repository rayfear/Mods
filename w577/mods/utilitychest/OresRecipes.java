package w577.mods.utilitychest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import w577.mods.base.CustomFile;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class OresRecipes {

	public static OresRecipes recipes = new OresRecipes();
	
	private HashMap recipeMap = new HashMap();
	
	public static OresRecipes instance() {
		return recipes;
	}
	
	private OresRecipes() {
		CustomFile recipeFile = new CustomFile("chestModData/ores.dat");
		for (int i = 0; i < recipeFile.size(); i++) {
			addRecipe(recipeFile.getLine(i));
		}
	}
	
	public void addRecipe(int[] line) {
		switch(line.length) {
		case 2: addRecipe(line[0], line[1]); break;
		case 4: addRecipe(line[0], line[1], line[2], line[3]); break;
		case 6: addRecipe(line[0], line[1], line[2], line[3], line[4], line[5]); break;
		}
	}

	public void addRecipe(int itemID, int returnID) {
		addRecipe(itemID, 0, 2, 1, returnID, 0);
	}
	
	public void addRecipe(int itemID, int rand, int base, int returnID) {
		addRecipe(itemID, 0, rand, base, returnID, 0);
	}
	
	public void addRecipe(ItemStack is, ItemStack returnIS) {
		addRecipe(is.itemID, is.getItemDamage(), 2, 1, returnIS.itemID, returnIS.getItemDamage());
	}
	
	public void addRecipe(ItemStack is, int rand, int base, ItemStack returnIS) {
		addRecipe(is.itemID, is.getItemDamage(), rand, base, returnIS.itemID, returnIS.getItemDamage());
	}
	
	public void addRecipe(int itemID, int itemDamage, int rand, int base, int returnID, int returnDamage) {
		recipeMap.put(Arrays.asList(itemID, itemDamage), Arrays.asList(rand, base, returnID, returnDamage));
	}
	
	public ItemStack getResult(int itemID, int itemDamage) {
		List<Integer> retList = (List<Integer>) recipeMap.get(Arrays.asList(itemID, itemDamage));
		
		if (retList == null) {
			return null;
		}
		
		Random rand = new Random();
		int stacksize = rand.nextInt(retList.get(0)) + retList.get(1);
		return new ItemStack(retList.get(2), stacksize, retList.get(3));
	}
}
