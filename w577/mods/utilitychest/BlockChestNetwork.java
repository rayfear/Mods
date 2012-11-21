package w577.mods.utilitychest;

import java.util.Random;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.network.PacketDispatcher;
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
	public int tickRate() {
		return 20;
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
		world.scheduleBlockUpdate(i, j, k, this.blockID, this.tickRate());
	}
	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k,
			EntityPlayer entityplayer, int par6, float par7, float par8,
			float par9) {
		TileEntityChestNetwork tecn = (TileEntityChestNetwork) UtilityChest.proxy.getServer().worldServerForDimension(entityplayer.dimension).getBlockTileEntity(i, j, k);
		//((TileEntityChestNetwork)world.getBlockTileEntity(i, j, k)).network = tecn.network;
		world.setBlockTileEntity(i, j, k, tecn);
		
		UtilityChest.proxy.showPlayerGuiChest(
				entityplayer,
				tecn,
				world, i, j, k);
		System.out.format("Activated block with network: %s %s \n", FMLCommonHandler.instance().getEffectiveSide(), tecn.network);
		return true;
	}
	
	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
			//System.out.println("Tickfuck");
			TileEntityChestNetwork tecn = (TileEntityChestNetwork) world.getBlockTileEntity(i, j, k);
			PacketDispatcher.sendPacketToAllInDimension(tecn.getDescriptionPacket(), tecn.dimension);
			world.scheduleBlockUpdate(i, j, k, this.blockID, this.tickRate());
		}
	}

}
