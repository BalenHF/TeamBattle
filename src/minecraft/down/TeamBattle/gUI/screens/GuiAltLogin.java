package down.TeamBattle.gUI.screens;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import org.lwjgl.input.Keyboard;

import down.TeamBattle.MinecraftLoginUtils.AltLoginThread;
import down.TeamBattle.gUI.GuiPasswordField;

public final class GuiAltLogin extends GuiScreen {
	private GuiPasswordField password;
	private final GuiScreen previousScreen;
	private AltLoginThread thread;
	private GuiTextField username;

	public GuiAltLogin(GuiScreen previousScreen) {
		this.previousScreen = previousScreen;
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
		case 1:
			mc.displayGuiScreen(previousScreen);
			break;
		case 0:
			thread = new AltLoginThread(username.getText(), password.getText());
			thread.start();
			break;
		}
	}

	@Override
	public void drawScreen(int x, int y, float z) {
		drawDefaultBackground();
		username.drawTextBox();
		password.drawTextBox();
		drawCenteredString(mc.fontRendererObj, "Alt Login", width / 2, 20,
				0xFFFFFFFF);
		drawCenteredString(mc.fontRendererObj, thread == null ? "\2477Waiting..."
				: thread.getStatus(), width / 2, 29, 0xFFFFFFFF);
		if (username.getText().isEmpty()) {
			drawString(mc.fontRendererObj, "Username / E-Mail", width / 2 - 96,
					66, 0xFF888888);
		}
		if (password.getText().isEmpty()) {
			drawString(mc.fontRendererObj, "Password", width / 2 - 96, 106,
					0xFF888888);
		}
		super.drawScreen(x, y, z);
	}

	@Override
	public void initGui() {
		final int var3 = height / 4 + 24;
		buttonList.add(new GuiButton(0, width / 2 - 100, var3 + 72 + 12, "Login"));
		buttonList.add(new GuiButton(1, width / 2 - 100, var3 + 72 + 12 + 24,
				"Back"));
		username = new GuiTextField(2,mc.fontRendererObj, width / 2 - 100, 60, 200,
				20);
		password = new GuiPasswordField(mc.fontRendererObj, width / 2 - 100, 100,
				200, 20);
		username.setFocused(true);
		Keyboard.enableRepeatEvents(true);
	}

	@Override
	protected void keyTyped(char character, int key) throws IOException {
		super.keyTyped(character, key);
		if (character == '\t') {
			if (!username.isFocused() && !password.isFocused()) {
				username.setFocused(true);
			} else {
				username.setFocused(password.isFocused());
				password.setFocused(!username.isFocused());
			}
		}

		if (character == '\r') {
			actionPerformed((GuiButton) buttonList.get(0));
		}
		username.textboxKeyTyped(character, key);
		password.textboxKeyTyped(character, key);
	}

	@Override
	protected void mouseClicked(int x, int y, int button) throws IOException {
		super.mouseClicked(x, y, button);
		username.mouseClicked(x, y, button);
		password.mouseClicked(x, y, button);
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	public void updateScreen() {
		username.updateCursorCounter();
		password.updateCursorCounter();
	}
}
