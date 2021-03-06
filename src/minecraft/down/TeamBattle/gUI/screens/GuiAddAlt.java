package down.TeamBattle.gUI.screens;

import java.io.IOException;
import java.net.Proxy;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import org.lwjgl.input.Keyboard;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import down.TeamBattle.TeamBattleClient;
import down.TeamBattle.Utils.Alt;
import down.TeamBattle.gUI.GuiPasswordField;

public class GuiAddAlt extends GuiScreen {
	private class AddAltThread extends Thread {
		private final String password;
		private final String username;

		public AddAltThread(final String username, final String password) {
			this.username = username;
			this.password = password;
			status = "\2477Waiting...";
		}

		private final void checkAndAddAlt(String username, String password) {
			final YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(
					Proxy.NO_PROXY, "");
			final YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service
					.createUserAuthentication(Agent.MINECRAFT);
			auth.setUsername(username);
			auth.setPassword(password);
			try {
				auth.logIn();
				TeamBattleClient.getAltManager().getContents()
						.add(new Alt(username, password));
				TeamBattleClient.getFileManager().getFileByName("alts").saveFile();
				status = "\247aAlt added. (" + username + ")";
			} catch (final AuthenticationException e) {
				status = "\247cAlt failed!";
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			if (password.equals("")) {
				TeamBattleClient.getAltManager().getContents()
						.add(new Alt(username, ""));
				TeamBattleClient.getFileManager().getFileByName("alts").saveFile();
				status = "\247aAlt added. (" + username + " - offline name)";
				return;
			}
			status = "\247eTrying alt...";
			checkAndAddAlt(username, password);
		}
	}

	private final GuiAltManager manager;
	private GuiPasswordField password;
	private String status = "\2477Waiting...";

	private GuiTextField username;

	public GuiAddAlt(GuiAltManager manager) {
		this.manager = manager;
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
		case 0:
			final AddAltThread login = new AddAltThread(username.getText(),
					password.getText());
			login.start();
			break;
		case 1:
			mc.displayGuiScreen(manager);
			break;
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		username.drawTextBox();
		password.drawTextBox();
		drawCenteredString(fontRendererObj, "Add Alt", width / 2, 20,
				0xFFFFFFFF);
		if (username.getText().isEmpty()) {
			drawString(mc.fontRendererObj, "Username / E-Mail", width / 2 - 96,
					66, 0xFF888888);
		}
		if (password.getText().isEmpty()) {
			drawString(mc.fontRendererObj, "Password", width / 2 - 96, 106,
					0xFF888888);
		}
		drawCenteredString(fontRendererObj, status, width / 2, 30, 0xFFFFFFFF);
		super.drawScreen(i, j, f);
	}

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, width / 2 - 100,
				height / 4 + 92 + 12, "Login"));
		this.buttonList.add(new GuiButton(1, width / 2 - 100,
				height / 4 + 116 + 12, "Back"));
		username = new GuiTextField(2,mc.fontRendererObj, width / 2 - 100, 60, 200,
				20);
		password = new GuiPasswordField(mc.fontRendererObj, width / 2 - 100, 100,
				200, 20);
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		username.textboxKeyTyped(par1, par2);
		password.textboxKeyTyped(par1, par2);
		if (par1 == '\t'
				&& (this.username.isFocused() || this.password.isFocused())) {
			this.username.setFocused(!this.username.isFocused());
			this.password.setFocused(!this.password.isFocused());
		}

		if (par1 == '\r') {
			actionPerformed((GuiButton) buttonList.get(0));
		}
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) throws IOException {
		super.mouseClicked(par1, par2, par3);
		username.mouseClicked(par1, par2, par3);
		password.mouseClicked(par1, par2, par3);
	}
}