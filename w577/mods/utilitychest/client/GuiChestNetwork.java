package w577.mods.utilitychest.client;

import org.lwjgl.input.Keyboard;

import w577.mods.utilitychest.PacketHandler;
import w577.mods.utilitychest.TileEntityChestNetwork;
import w577.mods.utilitychest.UtilityChest;
import net.minecraft.src.ChatAllowedCharacters;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.World;

public class GuiChestNetwork extends GuiScreen {

	private TileEntityChestNetwork chestn;
	private GuiTextField textBoxName;
	private GuiTextField textBoxPass;
	private final String enterName = "Enter Network Name";
	private final String enterPass = "Enter Password (blank for none)";
	private static String allowedCharacters = ChatAllowedCharacters.allowedCharacters;
	private boolean closed = false;

	public GuiChestNetwork(TileEntityChestNetwork chestn) {
		this.chestn = chestn;
	}

	@Override
	public void initGui() {
		controlList.clear();
		Keyboard.enableRepeatEvents(true);
		controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 120,
				"Done"));
		textBoxName = new GuiTextField(fontRenderer, width / 2 - 100, 60, 200,
				20);
		textBoxName.setFocused(true);
		textBoxName.setMaxStringLength(15);
		textBoxPass = new GuiTextField(fontRenderer, width / 2 - 100, 110, 200,
				20);
		textBoxPass.setMaxStringLength(15);
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		if (UtilityChest.proxy.getClientWorld().getBlockTileEntity(
				chestn.xCoord, chestn.yCoord, chestn.zCoord) == null) {
			return;
		}
		if (textBoxPass.getText().equals("")) {
			chestn.network = textBoxName.getText();
		} else {
			chestn.network = textBoxName.getText() + '+' + textBoxPass.getText();
		}
		mc.getSendQueue().addToSendQueue(
				PacketHandler.getPacketNetworkServer(chestn));
	}

	@Override
	public void updateScreen() {
		textBoxName.updateCursorCounter();
		textBoxPass.updateCursorCounter();
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
		drawCenteredString(fontRenderer, enterName, width / 2, 40, 0xffffff);
		drawCenteredString(fontRenderer, enterPass, width / 2, 90, 0xffffff);
		textBoxName.drawTextBox();
		textBoxPass.drawTextBox();
		super.drawScreen(i, j, f);
	}

	@Override
	protected void keyTyped(char c, int i) {
		if (c == '\r') {
			actionPerformed((GuiButton) controlList.get(0));
		}
		if (c == 9) {
			if (textBoxName.isFocused()) {
				textBoxName.setFocused(false);
				textBoxPass.setFocused(true);
			} else if (textBoxPass.isFocused()) {
				textBoxPass.setFocused(false);
				textBoxName.setFocused(true);
			}
		}
		if (c == '+' || c == '*') { return; }
		if (allowedCharacters.indexOf(c) >= 0 || i == 14) {
			textBoxName.textboxKeyTyped(c, i);
			textBoxPass.textboxKeyTyped(c, i);
		}
	}
	
	@Override
	protected void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		textBoxName.mouseClicked(i, j, k);
		textBoxPass.mouseClicked(i, j, k);
	}

}
