package w577.mods.utilitychest.client;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import w577.mods.utilitychest.BlockChestNetwork;
import w577.mods.utilitychest.BlockChestUtility;
import w577.mods.utilitychest.UtilityChest;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class ChestUtilityRenderHelper implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		if (block instanceof BlockChestNetwork) {
			TileEntityRenderer.instance.renderTileEntityAt(block.createTileEntity(null, 0), 0.0D, 0.0D, 0.0D, 0.0F);
		}
		if (block instanceof BlockChestUtility) {
			TileEntityRenderer.instance.renderTileEntityAt(block.createTileEntity(null, 0), 0.0D, 0.0D, 0.0D, 0.0F);
		}
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return UtilityChest.renderId;
	}

}
