package w577.mods.utilitychest.client;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import w577.mods.utilitychest.BlockChestUtility;
import w577.mods.utilitychest.TileEntityChestNetwork;
import w577.mods.utilitychest.UtilityChest;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.TileEntityRenderer;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class ChestUtilityRendererHandler implements
		ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		if (block instanceof BlockChestUtility) {
			BlockChestUtility blocku = (BlockChestUtility) block;
			TileEntityRenderer.instance.renderTileEntityAt(blocku
					.createNewTileEntity(UtilityChest.proxy.getClientWorld()),
					0.0D, 0.0D, 0.0D, 0.0F);
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
		return UtilityChest.instance.utilityChestRender;
	}

}
