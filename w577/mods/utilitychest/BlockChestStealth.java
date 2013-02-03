package w577.mods.utilitychest;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

public class BlockChestStealth extends BlockChestAdvancedUtility {

	protected BlockChestStealth(int id) {
		super(id, TileEntityChestStealth.class);
		setBlockName("StealthChest");
	}
	
	@Override
	public int getRenderType() {
		return UtilityChest.renderId;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		TileEntity te = par1IBlockAccess.getBlockTileEntity(par2, par3, par4);
		if (te != null && te instanceof TileEntityChestStealth) {
			TileEntityChestStealth tecs = (TileEntityChestStealth) te;
			if (tecs.getStackInSlot(0) != null && tecs.getStackInSlot(0).getItem() instanceof ItemBlock && Block.blocksList[tecs.getStackInSlot(0).itemID].isOpaqueCube()) {
				this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			} else {
				this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
			}
		}
	}

}
