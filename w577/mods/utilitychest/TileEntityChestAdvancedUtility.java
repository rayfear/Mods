package w577.mods.utilitychest;

import net.minecraft.src.ItemStack;

public class TileEntityChestAdvancedUtility extends TileEntityChestUtility {
	
	@Override
	public int getGuiId() {
		return 2;
	}
	
	@Override
	public int getSizeInventory() {
		return 28;
	}

	@Override
	public String getInvName() {
		return "Advanced Chest";
	}
	
	public int getGuiXPos() {
		return 0;
	}
	
	public boolean doesSmelt() {
		return true;
	}
	
	public boolean putItemInChest(ItemStack itemstack) {
		
		return false;
	}
}
