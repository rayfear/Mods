package w577.mods.utilitychest.client;

import org.lwjgl.opengl.GL11;

import w577.mods.utilitychest.ContainerAdvancedChest;
import w577.mods.utilitychest.TileEntityChestAdvancedUtility;
import w577.mods.utilitychest.TileEntityChestUtility;
import net.minecraft.src.Container;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.IInventory;
import net.minecraft.src.StatCollector;

public class GuiAdvancedChest extends GuiContainer {
	
	private TileEntityChestAdvancedUtility upperInv;
	private IInventory lowerInv;

	public GuiAdvancedChest(IInventory playerInventory, TileEntityChestAdvancedUtility teInventory) {
		super(new ContainerAdvancedChest(playerInventory, teInventory));
		upperInv = teInventory;
		lowerInv = playerInventory;
		this.allowUserInput = false;
		this.ySize = 180;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
    {
        this.fontRenderer.drawString(StatCollector.translateToLocal(this.upperInv.getInvName()), 8, 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal(this.lowerInv.getInvName()), 8, this.ySize - 96 + 2, 4210752);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,
			int var3) {
		int textureInt = this.mc.renderEngine.getTexture("/chestModImages/utilitygui.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(textureInt);
        int xPos = (this.width - this.xSize) / 2;
        int yPos = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(xPos, yPos, 0, 0, this.xSize, this.ySize);
        this.drawTexturedModalRect(xPos+152, yPos+8, 176+(upperInv.getGuiXPos()*16), 0, 16, 16);
        this.drawTexturedModalRect(xPos+134, yPos+8, 176+(upperInv.getGuiXPos()*16), 16, 16, 16);
	}

}
