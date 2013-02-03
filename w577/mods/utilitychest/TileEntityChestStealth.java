package w577.mods.utilitychest;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.StatCollector;

public class TileEntityChestStealth extends TileEntityAdvancedChestUtility {
	
	@Override
	public String getInvName() {
		if (getStackInSlot(0) != null && getStackInSlot(0).getItem() instanceof ItemBlock && Block.blocksList[getStackInSlot(0).itemID].isOpaqueCube()) {
			return StatCollector.translateToLocal(Block.blocksList[getStackInSlot(0).itemID].getBlockName()+".name") + " Chest";
		}
		return "tile.StealthChest.name";
	}

	@Override
	public int getGuiXPos() {
		return 0;
	}
	
	@Override
	public boolean doesBurn() {
		return false;
	}
	
	@Override
	public boolean doesCook() {
		return false;
	}
	
	@Override
	public void onInventoryChanged() {
		super.onInventoryChanged();
		this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, blockMetadata);
	}

}
