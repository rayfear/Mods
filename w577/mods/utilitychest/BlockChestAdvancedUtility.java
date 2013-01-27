package w577.mods.utilitychest;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class BlockChestAdvancedUtility extends BlockChestUtility {

	protected BlockChestAdvancedUtility(int id, Class<? extends TileEntity> te) {
		super(id, te);
	}
	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityPlayer, int i1, float f, float f1, float f2) {
		if (world.isRemote) {
			return true;
		}
		if (entityPlayer.isSneaking()) {
			return false;
		}
		entityPlayer.openGui(UtilityChest.instance, 2, world, i, j, k);
		return true;
	}

}
