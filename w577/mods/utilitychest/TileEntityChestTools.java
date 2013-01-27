package w577.mods.utilitychest;

import net.minecraft.item.ItemStack;

public class TileEntityChestTools extends TileEntityAdvancedChestUtility {
	
	@Override
	public String getInvName() {
		return "tile.ToolsChest.name";
	}

	@Override
	public int getGuiXPos() {
		return 2;
	}
	
	@Override
	public boolean doesCook() {
		return false;
	}
	
	@Override
	public boolean isFuel(ItemStack is) {
		return ToolsFuels.instance().getFuelTime(is) != 0;
	}
	
	@Override
	public int getFuelBurnTime(ItemStack is) {
		return ToolsFuels.instance().getFuelTime(is);
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
			if (burnTime == 0 && canFix() != -1) {
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
			
			if (canFix() != -1) {
				if (burnTime == 0) {
					cookTime++;
				} else if (burnTime > 0) {
					cookTime += 5;
				}
				if (cookTime >= 200) {
					cookTime = 0;
					fixItem();
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

	public void fixItem() {
		int slotToFix = canFix();
		if (slotToFix == -1) {
			return;
		}
		ItemStack curStack = getStackInSlot(slotToFix);
		curStack.setItemDamage(curStack.getItemDamage() - 1);
	}

	public int canFix() {
		int ret = -1;
		for (int i = 1; i < getSizeInventory(); i++) {
			ItemStack curStack = getStackInSlot(i);
			if (curStack != null && (curStack.getItem().isItemTool(curStack)) && curStack.getItemDamage() > 0) {
				ret = i;
				break;
			}
		}
		return ret;
	}

}
