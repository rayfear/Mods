package w577.mods.utilitychest;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileEntityChestSmelter extends TileEntityAdvancedChestUtility {

	@Override
	public String getInvName() {
		return "tile.SmelterChest.name";
	}
	
	@Override
	public void updateEntity()
    {
		boolean hasFuel = burnTime > 0;
		boolean changed = false;
		if (burnTime > 0) {
			burnTime --;
		}
		
		if (!this.worldObj.isRemote) {
			if (burnTime == 0 && canSmelt() != -1) {
				curItemBurnTime = burnTime = getFuelBurnTime(getStackInSlot(0));
				if (burnTime > 0) {
					changed = true;
					if (getStackInSlot(0) != null) {
						decrStackSize(0, 1);
						if (getStackInSlot(0) != null && getStackInSlot(0).stackSize == 0) {
							setInventorySlotContents(0, getStackInSlot(0).getItem().getContainerItemStack(getStackInSlot(0)));
						}
					}
				}
			}
			
			if (canSmelt() != -1) {
				if (burnTime == 0) {
					cookTime ++;
				} else if (burnTime > 0) {
					cookTime += 5;
				}
				if (cookTime >= timeToSmelt) {
					cookTime = 0;
					smeltItem();
					changed = true;
				}
			} else {
				cookTime = 0;
			}
			
			if (hasFuel != burnTime > 0) {
				changed = true;
			}
		}
		
		if (changed) {
			onInventoryChanged();
		}
    }
	
	public int canSmelt() {
		int ret = -1;
		for (int i = 1; i < getSizeInventory(); i++) {
			if (getSmeltingResult(getStackInSlot(i)) != null) {
				ret = i;
				break;
			}
		}
		return ret;
	}

	public void smeltItem() {
		int slotToSmelt = canSmelt();
		if (slotToSmelt == -1) {
			return;
		}
		ItemStack smeltResult = getSmeltingResult(getStackInSlot(slotToSmelt)).copy();
		decrStackSize(slotToSmelt, 1);
		putStackInSlotOrDrop(smeltResult);
	}

	@Override
	public int getGuiXPos() {
		return 1;
	}
	
	@Override
	public boolean isFuel(ItemStack is) {
		return TileEntityFurnace.isItemFuel(is);
	}
	
	@Override
	public int getFuelBurnTime(ItemStack is) {
		return TileEntityFurnace.getItemBurnTime(is);
	}
	
	public ItemStack getSmeltingResult(ItemStack is) {
		return FurnaceRecipes.smelting().getSmeltingResult(is);
	}
	
}
