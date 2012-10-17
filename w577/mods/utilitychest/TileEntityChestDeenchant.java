/*package w577.mods.utilitychest;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;

public class TileEntityChestDeenchant extends TileEntityChestAdvancedUtility {
	
	public int curTickTime = 60;

	@Override
	public String getInvName() {
		return "De-enchanting Chest";
	}
	
	@Override
	public int getGuiXPos() {
		return 3;
	}
	
	@Override
	public void updateEntity() {
		if (curTickTime < 60) {
			curTickTime++;
		}
		
		if (curTickTime == 60) {
			if (canSmelt()) {
				
			}
		}
	}

	private boolean canSmelt() {
		if (this.getStackInSlot(0) == null || this.getStackInSlot(0).itemID != Item.glassBottle.shiftedIndex) {
			return false;
		}
		for (int i = 1; i < this.getSizeInventory(); i++) {
			if (this.getStackInSlot(i) == null || !this.getStackInSlot(i).hasTagCompound()) {
				continue;
			}
			if (!this.getStackInSlot(i).getTagCompound().hasKey("ench")) {
				continue;
			}
			if (!putItemInChest(new ItemStack(Item.expBottle, 1, 1))) {
				return false;
			}
			short level = ((NBTTagCompound)this.getStackInSlot(i).getTagCompound().getTagList("ench").tagAt(0)).getShort("lvl");
			level--;
			((NBTTagCompound)this.getStackInSlot(i).getTagCompound().getTagList("ench").tagAt(0)).setShort("lvl", level);
			
			System.out.println(((NBTTagCompound)this.getStackInSlot(i).getTagCompound().getTagList("ench").tagAt(0)).getShort("lvl"));
		}
		return false;
	}

	@Override
	public boolean putItemInChest(ItemStack itemStack) {
		return false;
	}

}*/
