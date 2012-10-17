package w577.mods.utilitychest;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockChestNetwork extends BlockChestUtility {

	public BlockChestNetwork(int id) {
		super(id);
		setBlockName("NetworkedChest");
		altSaving = true;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityChestNetwork();
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k,
			EntityLiving entityliving) {
		super.onBlockPlacedBy(world, i, j, k, entityliving);

		TileEntity te = world.getBlockTileEntity(i, j, k);
		if (te == null) {
			return;
		}

		if (!(te instanceof TileEntityChestNetwork)) {
			return;
		}

		UtilityChest.proxy.showPlayerGuiChestNetwork(
				(EntityPlayer) entityliving,
				(TileEntityChestNetwork) world.getBlockTileEntity(i, j, k),
				world, i, j, k);
	}

}
