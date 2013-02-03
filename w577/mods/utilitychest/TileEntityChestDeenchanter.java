package w577.mods.utilitychest;

import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileEntityChestDeenchanter extends TileEntityChestSmelter {
	
	@Override
	public String getInvName() {
		return "tile.DeenchanterChest.name";
	}

	@Override
	public int getGuiXPos() {
		return 3;
	}
	
	@Override
	public boolean burnFromTop() {
		return false;
	}
	
	@Override
	public int getScaledPercentBurned(int scaler) {
		return cookTime*scaler/timeToSmelt;
	}
	
	@Override
	public ItemStack getSmeltingResult(ItemStack is) {
		if (is == null) {
			return null;
		}
		if (is.hasTagCompound() && is.getTagCompound().hasKey("ench")) {
			
			NBTTagList tags = is.getTagCompound().getTagList("ench");
			int totallevels = 0;
			Random rand = new Random();
			for (int i = 0; i < tags.tagCount(); i++) {
				NBTTagCompound curTag = (NBTTagCompound) tags.tagAt(i);
				int curLvl = curTag.getShort("lvl");
				totallevels += (rand.nextInt(3)+1)*(curLvl-1) + 1;
			}
			if (totallevels > 64) { totallevels = 64; }
			ItemStack newStack = new ItemStack(Item.expBottle.itemID, totallevels, 0);
			return newStack;
		}
		return null;
	}
	
	@Override
	public void smeltItem() {
		int slotToSmelt = canSmelt();
		if (slotToSmelt == -1) {
			return;
		}
		ItemStack smeltResult = getSmeltingResult(getStackInSlot(slotToSmelt)).copy();
		getStackInSlot(slotToSmelt).getTagCompound().removeTag("ench");
		putStackInSlotOrDrop(smeltResult);
	}
	
	@Override
	public boolean doesCook() {
		return false;
	}
	
	@Override
	public boolean requiresFuel() {
		return true;
	}
	
	@Override
	public boolean isFuel(ItemStack is) {
		if (is == null) {
			return false;
		}
		return is.itemID == Item.glassBottle.itemID;
	}
	
	@Override
	public int getFuelBurnTime(ItemStack is) {
		if (isFuel(is)) {
			return 1500;
		}
		return 0;
	}

}
