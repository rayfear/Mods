package w577.mods.utilitychest;

import net.minecraft.item.ItemStack;

public class TileEntityChestOres extends TileEntityChestSmelter {
	
	public TileEntityChestOres() {
		timeToSmelt = 3000;
	}
	
	@Override
	public String getInvName() {
		return "tile.OresChest.name";
	}
	
	@Override
	public ItemStack getSmeltingResult(ItemStack is) {
		if (is == null) {
			return null;
		}
		return OresRecipes.instance().getResult(is.itemID, is.getItemDamage());
	}
	
	@Override
	public int getGuiXPos() {
		return 1;
	}
}
