package w577.mods.utilitychest;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAdvancedChestUtility extends Container {

	private TileEntityAdvancedChestUtility upperInv;
    private int lastCookTime = 0;
    private int lastBurnTime = 0;
    private int lastItemBurnTime = 0;

	public ContainerAdvancedChestUtility(IInventory playerInventory,
			TileEntityAdvancedChestUtility chestInventory) {
		upperInv = chestInventory;
		
		chestInventory.openChest();
		
		int yPos;
		int xPos;
		
		this.addSlotToContainer(new Slot(chestInventory, 0, 152, 8));
		
		for (yPos = 0; yPos < 3; yPos ++) {
			for (xPos = 0; xPos < 9; xPos ++) {
				this.addSlotToContainer(new Slot(chestInventory, xPos+(yPos*9)+1, 8+(xPos*18), 30+(yPos*18)));
			}
		}
		
		for (yPos = 0; yPos < 3; yPos ++) {
			for (xPos = 0; xPos < 9; xPos ++) {
				this.addSlotToContainer(new Slot(playerInventory, xPos+(yPos*9)+9, 8+(xPos*18), 98+(yPos*18)));
			}
		}
		
		for (xPos = 0; xPos < 9; xPos ++) {
			this.addSlotToContainer(new Slot(playerInventory, xPos, 8+xPos*18, 156));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return upperInv.isUseableByPlayer(var1);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int i) {
		ItemStack retStack = null;
		Slot curSlot = (Slot) this.inventorySlots.get(i);
		
		if (curSlot != null && curSlot.getHasStack()) {
			ItemStack curStack = curSlot.getStack();
			retStack = curStack.copy();
			
			if (i < 28) {
				if (!this.mergeItemStack(curStack, 28, this.inventoryItemStacks.size(), true)) {
					return null;
				}
			} else {
				if (upperInv.isFuel(curStack)) {
					if (!this.mergeItemStack(curStack, 0, 1, false)) {
						if (!this.mergeItemStack(curStack, 1, 27, false)) {
							return null;
						}
					}
				} else if (!this.mergeItemStack(curStack, 1, 27, false)) {
					return null;
				}
			}
			
			if (curStack.stackSize == 0) {
				curSlot.putStack(null);
			} else {
				curSlot.onSlotChanged();
			}
		}
		
		return retStack;
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.upperInv.cookTime);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.upperInv.burnTime);
        par1ICrafting.sendProgressBarUpdate(this, 2, this.upperInv.curItemBurnTime);
    }
	
	@Override
	public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        for (int i = 0; i < this.crafters.size(); i++) {
        	ICrafting craft = (ICrafting) this.crafters.get(i);
        	if (lastCookTime != upperInv.cookTime) {
        		craft.sendProgressBarUpdate(this, 0, this.upperInv.cookTime);
        	}
        	if (lastBurnTime != upperInv.burnTime) {
        		craft.sendProgressBarUpdate(this, 1, this.upperInv.burnTime);
        	}
        	if (lastItemBurnTime != upperInv.curItemBurnTime) {
        		craft.sendProgressBarUpdate(this, 2, this.upperInv.curItemBurnTime);
        	}
        }
        lastCookTime = upperInv.cookTime;
        lastBurnTime = upperInv.burnTime;
        lastItemBurnTime = upperInv.curItemBurnTime;
    }
	
	@Override
	public void updateProgressBar(int par1, int par2)
    {
		if (par1 == 0) {
			upperInv.cookTime = par2;
		}
		if (par1 == 1) {
			upperInv.burnTime = par2;
		}
		if (par1 == 2) {
			upperInv.curItemBurnTime = par2;
		}
    }

}
