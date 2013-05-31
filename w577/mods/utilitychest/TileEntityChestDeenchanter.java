package w577.mods.utilitychest;

import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileEntityChestDeenchanter extends TileEntityAdvancedChestUtility {
	
	@Override
	public String getInvName() {
		return "tile.DeenchanterChest.name";
	}
	
	@Override
	public void updateEntity()
    {
		super.updateEntity();
		
		burnTime = 1;
		boolean changed = false;
		
		if (!this.worldObj.isRemote) {			
			if (canSmelt() != -1) {
				cookTime += 5;
				if (cookTime >= timeToSmelt) {
					//cookTime = 0;
					smeltItem();
					changed = true;
				}
			} else {
				cookTime = 0;
			}
		}
		
		if (changed) {
			onInventoryChanged();
		}
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
				int levels;
				switch (curLvl) {
				case 1: levels = 1; break;
				case 2: levels = 3; break;
				case 3: levels = 6; break;
				case 4: levels = 9; break;
				case 5: levels = 21; break;
				default: levels = 1;
				}
				levels *= EnchantingRecipes.instance().getResult(curTag.getShort("id"));
				totallevels += levels;
			}
			if (totallevels > 64) { totallevels = 64; }
			ItemStack newStack = new ItemStack(Item.expBottle.itemID, totallevels, 0);
			return newStack;
		}
		return null;
	}
	
	public void smeltItem() {
		int slotToSmelt = canSmelt();
		if (slotToSmelt == -1) {
			return;
		}
		ItemStack smeltResult = getSmeltingResult(getStackInSlot(slotToSmelt)).copy();
		if (smeltResult.stackSize > getStackInSlot(0).stackSize) {
			return;
		} else {
			decrStackSize(0, smeltResult.stackSize);
			cookTime = 0;
		}
		getStackInSlot(slotToSmelt).getTagCompound().removeTag("ench");
		putStackInSlotOrDrop(smeltResult);
	}
	
	public int canSmelt() {
		int ret = -1;
		for (int i = 1; i < getSizeInventory(); i++) {
			if (getSmeltingResult(getStackInSlot(i)) != null) {
				ret = i;
				break;
			}
		}
		if (getStackInSlot(0) == null || getStackInSlot(0).itemID != Item.glassBottle.itemID) {
			ret = -1;
		}
		return ret;
	}

	@Override
	public boolean doesCook() {
		return false;
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
