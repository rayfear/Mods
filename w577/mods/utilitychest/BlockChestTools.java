package w577.mods.utilitychest;

import java.util.Random;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockChestTools extends BlockChestUtility {

	private Random rand;
	private int base = 20;

	public BlockChestTools(int id) {
		super(id);
		setBlockName("ToolsChest");
		rand = new Random();

	}

	@Override
	public int tickRate() {
		return rand.nextInt(base) + base;
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityChestTools();
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		if (UtilityChest.proxy.getServer() == null) {
			return;
		}

		TileEntity te = world.getBlockTileEntity(i, j, k);
		if (te instanceof TileEntityChestTools) {
			TileEntityChestTools tect = (TileEntityChestTools) te;

			for (int i1 = 0; i1 < tect.getSizeInventory(); i1++) {
				ItemStack is = tect.getStackInSlot(i1);
				if (is == null) {
					continue;
				}

				if (is.getItem().isItemTool(is)) {
					if (is.getItemDamage() > 0) {
						is.setItemDamage(is.getItemDamage() - 1);
					}
				}
			}
			tect.onInventoryChanged();
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
