package w577.mods.utilitychest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.item.ItemStack;

import w577.mods.base.CustomFile;

public class EnchantingRecipes {
	
	public static EnchantingRecipes recipes = new EnchantingRecipes();
	
	private HashMap<Integer, Double> recipeMap = new HashMap<Integer, Double>();
	
	public static EnchantingRecipes instance() {
		return recipes;
	}
	
	private EnchantingRecipes() {
		CustomFile recipeFile = new CustomFile("chestModData/disenchanting.dat");
		for (int i = 0; i < recipeFile.size(); i++) {
			int[] line = recipeFile.getLine(i);
			addRecipe(line[0], line[1]/100);
		}
	}

	public void addRecipe(int id, double multiplier) {
		recipeMap.put(id, multiplier);
	}
	
	public double getResult(int id) {
		if (recipeMap.get(id) == null) {
			return 1;
		}
		return recipeMap.get(id);
	}

}
