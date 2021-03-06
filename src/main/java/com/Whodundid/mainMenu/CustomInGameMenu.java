package com.Whodundid.mainMenu;

import com.Whodundid.main.global.SettingsGui;
import com.Whodundid.main.util.enhancedGui.EnhancedGui;
import com.Whodundid.main.util.enhancedGui.guiObjects.EGuiButton;
import com.Whodundid.main.util.enhancedGui.interfaces.IEnhancedActionObject;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;
import net.minecraftforge.fml.client.GuiModList;

//Last edited: Dec 14, 2018
//First Added: Dec 14, 2018
//Author: Hunter Bragg

public class CustomInGameMenu extends EnhancedGui {

	EGuiButton backToGame, eSettings, achievements, stats, openToLan, options, modOptions, quitToMenu;
	
	@Override
	public void initGui() {
		super.initGui();
		backwardsTraverseable = false;
		enableHeader(false);		
	}
	
	@Override
	public void initObjects() {
		int i = sWidth / 2;
		int j = sHeight / 4;
		
		backToGame = new EGuiButton(this, i - 100, j - 16, 200, 20, I18n.format("menu.returnToGame", new Object[0]));
		eSettings = new EGuiButton(this, i - 100, j + 8, 200, 20, "Enhanced MC Settings");
		achievements = new EGuiButton(this, i - 100, j + 32, 98, 20, I18n.format("gui.achievements", new Object[0]));
		stats = new EGuiButton(this, i + 2, j + 32, 98, 20, I18n.format("gui.stats", new Object[0]));
		openToLan = new EGuiButton(this, i - 100, j + 56, 200, 20, I18n.format("menu.shareToLan", new Object[0]));
		options = new EGuiButton(this, i - 100, j + 80, 98, 20, I18n.format("menu.options", new Object[0]));
		modOptions = new EGuiButton(this, i + 2, j + 80, 98, 20, I18n.format("fml.menu.modoptions"));
		quitToMenu = new EGuiButton(this, i - 100, j + 104, 200, 20, mc.isIntegratedServerRunning() ? I18n.format("menu.returnToMenu", new Object[0]) : I18n.format("menu.disconnect", new Object[0]));
		
		openToLan.setEnabled(mc.isSingleplayer() && !mc.getIntegratedServer().getPublic());
		
		addObject(backToGame, eSettings, achievements, stats, openToLan, options, modOptions, quitToMenu);
	}
	
	@Override
	public boolean doesGuiPauseGame() { return true; }
	
	@Override
	public void drawObject(int mX, int mY, float ticks) {
		drawMenuGradient();
		drawCenteredStringWithShadow(I18n.format("menu.game", new Object[0]), wPos, (height / 4 - 16) / 2, 16777215);
	}
	
	@Override
	public void actionPerformed(IEnhancedActionObject object) {
		if (object.equals(backToGame)) { closeGui(); }
		if (object.equals(achievements)) { if (mc.thePlayer != null) { mc.displayGuiScreen(new GuiAchievements(this, mc.thePlayer.getStatFileWriter())); } }
		if (object.equals(stats)) { if (mc.thePlayer != null) { mc.displayGuiScreen(new GuiStats(this, mc.thePlayer.getStatFileWriter())); } }
		if (object.equals(openToLan)) { mc.displayGuiScreen(new GuiShareToLan(guiInstance)); }
		if (object.equals(options)) { mc.displayGuiScreen(new GuiOptions(guiInstance, mc.gameSettings)); }
		if (object.equals(modOptions)) { mc.displayGuiScreen(new GuiModList(guiInstance)); }
		if (object.equals(eSettings)) { mc.displayGuiScreen(new SettingsGui()); }
		
		if (object.equals(quitToMenu)) {
			boolean flag = mc.isIntegratedServerRunning();
            boolean flag1 = mc.func_181540_al();
			mc.theWorld.sendQuittingDisconnectingPacket();
			mc.loadWorld(null);
			if (flag) { mc.displayGuiScreen(new GuiMainMenu()); }
			else if (flag1) {
				RealmsBridge bridge = new RealmsBridge();
				bridge.switchToRealms(new GuiMainMenu());
			} else { mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu())); }
		}
	}
}
