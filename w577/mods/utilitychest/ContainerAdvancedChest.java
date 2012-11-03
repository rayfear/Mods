package w577.mods.utilitychest;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class ContainerAdvancedChest extends Container {
	
	private IInventory upperInv;
	private int numRows;

	public ContainerAdvancedChest(IInventory playerInventory, IInventory teInventory) {
		this.upperInv = teInventory;
		this.numRows = teInventory.getSizeInventory()/9;
		
		teInventory.openChest();
		
		int xPos;
		int yPos;
		
		this.addSlotToContainer(new Slot(teInventory, 0, 152, 8));
		
		for (yPos = 0; yPos < this.numRows; yPos++) {
			for (xPos = 0; xPos < 9; xPos ++) {
				this.addSlotToContainer(new Slot(teInventory, xPos + yPos*9 + 1, 8 + xPos*18, 30 + yPos*18));
			}
		}
		
		for (yPos = 0; yPos < this.numRows; yPos++) {
			for (xPos = 0; xPos < 9; xPos ++) {
				this.addSlotToContainer(new Slot(playerInventory, xPos + yPos*9 + 9, 8 + xPos*18, 98 + yPos*18));
			}
		}
		
		for (xPos = 0; xPos < 9; xPos ++) {
			this.addSlotToContainer(new Slot(playerInventory, xPos, 8 + xPos*18, 156));
		}
		
		
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int par1)
    {
        ItemStack var2 = null;
        Slot var3 = (Slot)this.inventorySlots.get(par1);

        if (var3 != null && var3.getHasStack())
        {
            ItemStack var4 = var3.getStack();
            var2 = var4.copy();

            if (par1 < this.numRows * 9)
            {
                if (!this.mergeItemStack(var4, this.numRows * 9, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var4, 0, this.numRows * 9, false))
            {
                return null;
            }

            if (var4.stackSize == 0)
            {
                var3.putStack((ItemStack)null);
            }
            else
            {
                var3.onSlotChanged();
            }
        }

        return var2;
    }

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return this.upperInv.isUseableByPlayer(var1);
	}

}
