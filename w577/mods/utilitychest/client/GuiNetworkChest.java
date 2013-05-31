package w577.mods.utilitychest.client;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.network.PacketDispatcher;

import w577.mods.utilitychest.PacketHandler;
import w577.mods.utilitychest.TileEntityChestNetwork;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.world.World;

public class GuiNetworkChest extends GuiScreen {

	private TileEntityChestNetwork te;
	private World world;

	private GuiTextField nameBox;
	private GuiTextField passBox;

	private final String nameString = "Enter Network Name";
	private final String passString = "Enter Network Password (Blank for none)";

	private final String allowedCharacters = ChatAllowedCharacters.allowedCharacters;

	public GuiNetworkChest(TileEntityChestNetwork te, World world) {
		this.te = te;
		this.world = world;
	}

	@Override
	public void initGui() {
		buttonList.clear();
		Keyboard.enableRepeatEvents(true);
		buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 120,
				"Done"));
		((GuiButton) buttonList.get(0)).enabled = false;
		nameBox = new GuiTextField(fontRenderer, width / 2 - 100, 60, 200, 20);
		nameBox.setFocused(true);
		nameBox.setMaxStringLength(34);
		passBox = new GuiTextField(fontRenderer, width / 2 - 100, 110, 200, 20);
		passBox.setMaxStringLength(15);
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		if (world.getBlockTileEntity(te.xCoord, te.yCoord, te.zCoord) != te) {
			return;
		}
		String network = nameBox.getText();
		if (passBox.getText() != "") {
			network = network + '+' + passBox.getText();
		}
		te.network = network;
		NetClientHandler nch = this.mc.getNetHandler();
		if (nch != null) {
			nch.addToSendQueue(PacketHandler.getNetworkPacketServer(te));
		}

	}

	@Override
	public void updateScreen() {
		nameBox.updateCursorCounter();
		passBox.updateCursorCounter();
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		if (!guibutton.enabled) {
			return;
		}
		if (guibutton.id == 0) {
			mc.displayGuiScreen(null);
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		drawCenteredString(fontRenderer, nameString, width / 2, 40, 0xffffff);
		drawCenteredString(fontRenderer, passString, width / 2, 90, 0xffffff);
		nameBox.drawTextBox();
		passBox.drawTextBox();
		super.drawScreen(i, j, f);
	}

	@Override
	protected void keyTyped(char c, int i) {
		if (c == '\r') {
			actionPerformed((GuiButton) buttonList.get(0));
		}
		if (c == 9) {
			if (nameBox.isFocused()) {
				nameBox.setFocused(false);
				passBox.setFocused(true);
			} else if (passBox.isFocused()) {
				passBox.setFocused(false);
				nameBox.setFocused(true);
			}
		}
		if ((allowedCharacters.indexOf(c) >= 0 || i == 14)
				&& !(c == '+' || c == '*' || c == ' ' || c == '"')) {
			nameBox.textboxKeyTyped(c, i);
			passBox.textboxKeyTyped(c, i);
		}
		if (nameBox.getText().equals("")) {
			((GuiButton) buttonList.get(0)).enabled = false;
		} else {
			((GuiButton) buttonList.get(0)).enabled = true;
		}
	}

	@Override
	protected void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		nameBox.mouseClicked(i, j, k);
		passBox.mouseClicked(i, j, k);
	}

}
