package w577.mods.utilitychest.client;

import org.lwjgl.opengl.GL11;

import w577.mods.utilitychest.ContainerAdvancedChestUtility;
import w577.mods.utilitychest.TileEntityAdvancedChestUtility;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.StatCollector;

public class GuiAdvancedChestUtility extends GuiContainer {

	private TileEntityAdvancedChestUtility upperInv;
	private IInventory lowerInv;

	public GuiAdvancedChestUtility(IInventory playerInventory,
			TileEntityAdvancedChestUtility chestInventory) {
		super(
				new ContainerAdvancedChestUtility(playerInventory,
						chestInventory));
		upperInv = chestInventory;
		lowerInv = playerInventory;
		this.allowUserInput = false;
		this.ySize = 180;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		this.fontRenderer.drawString(
				StatCollector.translateToLocal(this.upperInv.getInvName()), 8,
				6, 0x404040);
		this.fontRenderer.drawString(
				StatCollector.translateToLocal(lowerInv.getInvName()), 8,
				this.ySize - 94, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,
			int var3) {
		int textureId = this.mc.renderEngine
				.getTexture("/chestModImages/gui/utilitygui.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(textureId);
		int xPos = (this.width - this.xSize) / 2;
		int yPos = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(xPos, yPos, 0, 0, this.xSize, this.ySize);
		if (upperInv.getStackInSlot(0) == null) {
			this.drawTexturedModalRect(xPos + 152, yPos + 8,
					176 + (upperInv.getGuiXPos() * 16), 0, 16, 16);
		}

		if (upperInv.doesBurn()) {
			this.drawTexturedModalRect(xPos + 132, yPos + 8,
					176 + (upperInv.getGuiXPos() * 16), 16, 16, 16);

			if (upperInv.burnTime > 0) {
				int pctBurnLeft = upperInv.getScaledPercentBurned(16);
				if (upperInv.burnFromTop()) {
					this.drawTexturedModalRect(xPos + 132, yPos + 24
							- pctBurnLeft, 176 + (upperInv.getGuiXPos() * 16),
							48 - pctBurnLeft, 16, pctBurnLeft);
				} else {
					this.drawTexturedModalRect(xPos + 132, yPos + 8
							+ pctBurnLeft, 176 + (upperInv.getGuiXPos() * 16),
							32 + pctBurnLeft, 16, pctBurnLeft);
				}
			}
			if (upperInv.doesCook()) {
				int pctCookLeft = upperInv.getScaledPercentCooked(20);
				this.drawTexturedModalRect(xPos+121, yPos+6, 176, 16, 8, 20);
				this.drawTexturedModalRect(xPos+121, yPos+6, 184, 16, 8, pctCookLeft);
			}
		}
	}

}
