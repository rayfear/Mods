package w577.mods.utilitychest;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class UtilityChestRecipeHandler {

	public static void addRecipes() {
		GameRegistry.addRecipe(new ItemStack(UtilityChest.blockChestNetwork),
				new Object[] { "aba", "c#c", "ddd", 'a',
						Block.torchRedstoneActive, 'b', Item.sign, 'd',
						Item.redstoneRepeater, 'c', Item.redstone, '#',
						Block.chest });

		GameRegistry.addRecipe(new ItemStack(UtilityChest.blockChestTools),
				new Object[] { " a ", "a#a", "bab", 'a', Block.stone, 'b',
						Block.blockDiamond, '#', Block.chest });

		GameRegistry.addRecipe(new ItemStack(UtilityChest.blockChestOres),
				new Object[] { "aba", "c#d", "aea", 'a', Block.blockIron, 'b',
						Block.oreCoal, 'c', Block.oreLapis, 'd',
						Block.oreRedstone, 'e', Block.oreDiamond, '#',
						Block.chest });

		GameRegistry.addRecipe(new ItemStack(UtilityChest.blockChestOres),
				new Object[] { "aea", "c#d", "aba", 'a', Block.blockIron, 'b',
						Block.oreCoal, 'c', Block.oreLapis, 'd',
						Block.oreRedstone, 'e', Block.oreDiamond, '#',
						Block.chest });

		GameRegistry.addRecipe(new ItemStack(UtilityChest.blockChestOres),
				new Object[] { "aca", "e#b", "ada", 'a', Block.blockIron, 'b',
						Block.oreCoal, 'c', Block.oreLapis, 'd',
						Block.oreRedstone, 'e', Block.oreDiamond, '#',
						Block.chest });

		GameRegistry.addRecipe(new ItemStack(UtilityChest.blockChestOres),
				new Object[] { "ada", "e#b", "aca", 'a', Block.blockIron, 'b',
						Block.oreCoal, 'c', Block.oreLapis, 'd',
						Block.oreRedstone, 'e', Block.oreDiamond, '#',
						Block.chest });

		GameRegistry.addRecipe(new ItemStack(UtilityChest.blockChestGrabber),
				new Object[] { "aaa", "b#b", "cbc", 'a', Item.stick, 'b',
						Item.slimeBall, 'c', Block.pistonStickyBase, '#',
						Block.chest });

		GameRegistry.addRecipe(new ItemStack(UtilityChest.blockChestSmelter),
				new Object[] { "aaa", "b#b", "cdc", 'a', Item.fireballCharge,
						'b', Item.blazeRod, 'c', Block.furnaceIdle, 'd',
						Item.bucketLava, '#', Block.chest });

		GameRegistry.addRecipe(
				new ItemStack(UtilityChest.blockChestDeenchanter),
				new Object[] { "aba", "b#b", "aba", 'a',
						new ItemStack(Item.dyePowder, 1, 4), 'b',
						Block.obsidian, '#', Block.chest });
	}

}