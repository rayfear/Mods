package w577.mods.utilitychest;

import java.util.Random;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockChestSmelter extends BlockChestUtility {
	
	Random rand;
	private int base = 600;

	public BlockChestSmelter(int id) {
		super(id);
		setBlockName("SmelterChest");
		rand = new Random();
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityChestSmelter();
	}

	@Override
	public int tickRate() {
		return rand.nextInt(base) + base;
	}

	public ItemStack getResult(ItemStack is) {
		if (is == null) {
			return null;
		}

		return FurnaceRecipes.smelting().getSmeltingResult(is);
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		if (UtilityChest.proxy.getServer() == null) {
			return;
		}

		TileEntity te = world.getBlockTileEntity(i, j, k);
		if (te instanceof TileEntityChestSmelter) {
			TileEntityChestSmelter tecs = (TileEntityChestSmelter) te;

			for (int i1 = 0; i1 < tecs.getSizeInventory(); i1++) {
				ItemStack is = tecs.getStackInSlot(i1);

				if (getResult(is) != null) {
					if (getNextFreeSlot(world, getResult(is).copy(), tecs)) {
						tecs.decrStackSize(i1, 1);
					}
				}
			}
			tecs.onInventoryChanged();
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
