package w577.mods.utilitychest;

import java.util.Random;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockChestOres extends BlockChestUtility {

	private Random rand;
	private int base = 600;

	public BlockChestOres(int id) {
		super(id);
		setBlockName("OresChest");
		rand = new Random();
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityChestOres();
	}

	@Override
	public int tickRate() {
		return rand.nextInt(base) + base;
	}

	public ItemStack getResult(ItemStack is) {
		if (is == null) {
			return null;
		}

		return ChestOresRecipes.getBase()
				.getResult(is.itemID, is.getItemDamage());
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		if (UtilityChest.proxy.getServer() == null) {
			return;
		}

		TileEntity te = world.getBlockTileEntity(i, j, k);
		if (te instanceof TileEntityChestOres) {
			TileEntityChestOres teco = (TileEntityChestOres) te;

			for (int i1 = 0; i1 < teco.getSizeInventory(); i1++) {
				ItemStack is = teco.getStackInSlot(i1);

				if (getResult(is) != null) {
					if (getNextFreeSlot(world, getResult(is).copy(), teco)) {
						teco.decrStackSize(i1, 1);
					}
				}
			}
			teco.onInventoryChanged();
		}
		world.scheduleBlockUpdate(i, j, k, this.blockID, this.tickRate());
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k,
			EntityLiving entity) {
		super.onBlockPlacedBy(world, i, j, k, entity);

		world.scheduleBlockUpdate(i, j, k, this.blockID, this.tickRate());
	}

}
