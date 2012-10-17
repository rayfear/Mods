package w577.mods.mobmannequin.client;

import java.lang.reflect.InvocationTargetException;

import w577.mods.mobmannequin.BlockMobMannequin;
import w577.mods.mobmannequin.MobMannequin;
import w577.mods.mobmannequin.TileEntityMobMannequin;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityList;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ModLoader;
import net.minecraft.src.ModelBase;
import net.minecraft.src.Render;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderLiving;
import net.minecraft.src.RenderManager;
import net.minecraft.src.World;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;

public class MannequinBlockRendererHandler implements
		ISimpleBlockRenderingHandler {
	

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		if (block instanceof BlockMobMannequin) {
			renderer.renderStandardBlock(block, x, y, z);
		}
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public int getRenderId() {
		return MobMannequin.mobManneRender;
	}

}
