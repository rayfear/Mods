package w577.mods.mobmannequin;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockMobMannequin extends BlockContainer {
	

	public BlockMobMannequin(int id) {
		super(id, Material.clay);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		this.setBlockName("MannequinBlock");
		this.setStepSound(Block.soundStoneFootstep);
	}
	
	@Override
	public boolean renderAsNormalBlock()
    {
        return false;
    }
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return MobMannequin.mannePlacerItem.shiftedIndex;
    }
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return par1 == 0 || par1 == 1 ? 6 : 5;
    }
	
	@Override
	public int damageDropped(int i) {
		return i;
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityMobMannequin();
	}
	
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return !par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) ? false : super.canPlaceBlockAt(par1World, par2, par3, par4);
    }
	
	@Override
	public int getRenderType() {
		return MobMannequin.mobManneRender;
	}
}
