package w577.mods.utilitychest;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockChestNetwork extends BlockContainer {

	public BlockChestNetwork(int id) {
		super(id, Material.wood);
		this.blockIndexInTexture = 0;
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setBlockName("NetworkedChest");
		if (id >= 256) {
			this.disableStats();
		}
		this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return UtilityChest.renderId;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityChestNetwork();
	}
	
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entity) {
		int face = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		int meta = 2;
		
		if (face == 0)
        {
            meta = 2;
        }

        if (face == 1)
        {
            meta = 5;
        }

        if (face == 2)
        {
            meta = 3;
        }

        if (face == 3)
        {
            meta = 4;
        }
        world.setBlockMetadataWithNotify(i, j, k, meta);
        if (entity instanceof EntityPlayer) {
        	((EntityPlayer) entity).openGui(UtilityChest.instance, 0, world, i, j, k);
        }
	}
	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityPlayer, int i1, float f, float f1, float f2) {
		if (world.isRemote) {
			return true;
		}
		if (entityPlayer.isSneaking()) {
			return false;
		}
		entityPlayer.openGui(UtilityChest.instance, 1, world, i, j, k);
		return true;
		
	}

}
