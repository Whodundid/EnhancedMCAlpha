package com.Whodundid.main.global.subMod;

import com.Whodundid.main.global.GlobalSettings;
import com.Whodundid.main.global.SettingsGui;
import com.Whodundid.main.util.enhancedGui.guiObjects.EGuiButton;
import com.Whodundid.main.util.enhancedGui.guiObjects.EGuiDialogueBox;
import com.Whodundid.main.util.enhancedGui.guiObjects.EGuiHeader;
import com.Whodundid.main.util.enhancedGui.interfaces.IEnhancedGuiObject;
import com.Whodundid.main.util.storageUtil.EArrayList;

//Last edited: Dec 28, 2018
//First Added: Dec 28, 2018
//Author: Hunter Bragg

public class SubModErrorDialogueBox extends EGuiDialogueBox {
	
	protected SubModErrorType type;
	protected SubMod mod;
	protected EArrayList<SubMod> mods = new EArrayList();
	
	public SubModErrorDialogueBox(IEnhancedGuiObject parentIn, int xPos, int yPos, int width, int height, SubModErrorType typeIn, SubMod modIn) {
		init(parentIn, xPos, yPos, width, height);
		type = typeIn;
		mod = modIn;
		addObject(header = new EGuiHeader(this));
		bringToFront();
		requestFocus();
		getTopParent().setFocusLockObject(this);
		setDisplayStringColor(0xffbb00);
		setMessageColor(0xff5555);
		setZLevel(1);
		EGuiButton disableAll, enableAll, cancel, okButton;
		switch (type) {
		case ENABLE:
			setDisplayString("Mod Enable Error");
			enableAll = new EGuiButton(this, midX - 90, midY + 7, 65, 20, "Enable All") {
				{ setRunActionOnPress(true); }
				@Override public void performAction() {
					playPressSound();
					reloadSettings(true);
					parent.close();
				}
			};
			cancel = new EGuiButton(this, midX + 30, midY + 7, 65, 20, "Cancel") {
				{ setRunActionOnPress(true); }
				@Override public void performAction() {
					playPressSound();
					parent.close();
				}
			};
			addObject(enableAll.setZLevel(1), cancel.setZLevel(1));
			break;
		case DISABLE:
			setDisplayString("Mod Disable Error");
			disableAll = new EGuiButton(this, midX - 90, midY + 7, 65, 20, "Disable All") {
				{ setRunActionOnPress(true); }
				@Override public void performAction() {
					playPressSound();
					reloadSettings(false);
					parent.close();
				}
			};
			cancel = new EGuiButton(this, midX + 30, midY + 7, 65, 20, "Cancel") {
				{ setRunActionOnPress(true); }
				@Override public void performAction() {
					playPressSound();
					parent.close();
				}
			};
			addObject(disableAll.setZLevel(1), cancel.setZLevel(1));
			break;
		case NOGUI:
			setDisplayString("No Gui Found");
			setMessage(SubModType.getModName(mod.getModType()) + " does not have a gui.");
			okButton = new EGuiButton(this, midX - 25, midY + 7, 50, 20, "Ok") {
				{ setRunActionOnPress(true); }
				@Override
				public void performAction() {
					playPressSound();
					parent.close();
				}
			};
			addObject(okButton.setZLevel(1));
			break;
		case NOTFOUND:
			setDisplayString("Mod Not Found");
			okButton = new EGuiButton(this, midX - 25, midY + 7, 50, 20, "Ok") {
				{ setRunActionOnPress(true); }
				@Override
				public void performAction() {
					playPressSound();
					parent.close();
				}
			};
			addObject(okButton.setZLevel(1));
			break;
		}
	}
	
	public SubModErrorDialogueBox createErrorMessage(EArrayList<SubMod> modsIn) {
		if (modsIn != null && !modsIn.isEmpty()) {
			mods.addAll(modsIn);
			message += "Mods: (";
			modsIn.forEach((m) -> { message += (SubModType.getModName(m.getModType()) + ", "); } );
			message = message.substring(0, message.length() - 2);
			message += ")";
			switch (type) {
			case ENABLE: message += " are required to enable " + SubModType.getModName(mod.getModType()) + "."; break;
			case DISABLE: message += " require " + SubModType.getModName(mod.getModType()) + " to properly function."; break;
			default: break;
			}
			setMessage(message);
		}
		return this;
	}
	
	private void reloadSettings(boolean val) {
		mods.forEach(m -> { GlobalSettings.updateSetting(m, val); });
		GlobalSettings.updateSetting(mod, val);
		if (mc.currentScreen instanceof SettingsGui) { ((SettingsGui) mc.currentScreen).reloadCurrentPage(); }
	}
}
