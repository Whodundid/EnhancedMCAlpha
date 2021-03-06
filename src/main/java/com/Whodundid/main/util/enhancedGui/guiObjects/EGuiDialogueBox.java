package com.Whodundid.main.util.enhancedGui.guiObjects;

import com.Whodundid.main.util.enhancedGui.interfaces.IEnhancedGuiObject;
import com.Whodundid.main.util.miscUtil.EUtil;
import com.Whodundid.main.util.storageUtil.EArrayList;

//Last edited: Jan 2, 2019
//First Added: Oct 22, 2018
//Author: Hunter Bragg

public class EGuiDialogueBox extends InnerEnhancedGui {
	
	public int messageColor = 0xffffff;
	public String message = "";
	protected EArrayList<String> wordWrappedLines;
	protected IEnhancedGuiObject primaryObject;
	protected EGuiButton yes, no, okButton;
	
	public enum DialogueBoxTypes { yesNo, ok, custom; }
	
	protected EGuiDialogueBox() {}
	
	public EGuiDialogueBox(IEnhancedGuiObject parentIn, int xPos, int yPos, int width, int height) {
		this(parentIn, xPos, yPos, width, height, DialogueBoxTypes.yesNo);
	}
	
	public EGuiDialogueBox(IEnhancedGuiObject parentIn, int xPos, int yPos, int width, int height, DialogueBoxTypes typeIn) {
		init(parentIn, xPos, yPos, width, height);
		addObject(header = new EGuiHeader(this));
		//bringToFront();
		requestFocus();
		getTopParent().setFocusLockObject(this);
		
		switch (typeIn) {
		case yesNo:
			yes = new EGuiButton(this, midX - 100, midY + 10, 65, 20, "Yes");
			no = new EGuiButton(this, midX + 25, midY + 10, 65, 20, "No");
			addObject(yes.setZLevel(1), no.setZLevel(1));
			break;
		case ok:
			okButton = new EGuiButton(this, midX - 25, midY + 10, 50, 20, "Ok") {
				{ setRunActionOnPress(true); }
				@Override
				public void performAction() {
					playPressSound();
					parent.close();
				}
			};
			addObject(okButton.setZLevel(1));
			break;
		default: break;
		}
	}
	
	@Override
	public void drawObject(int mXIn, int mYIn, float ticks) {
		drawRect(startX, startY, endX, endY, -0x00ffffff); //black
		drawRect(startX + 1, startY + 1, endX - 1, endY - 1, 0xff4c4c4c); //grey
		if (wordWrappedLines != null) {
			int lnWidth = wordWrappedLines.size() * 10;
			int totalWidth = midY + 5 - startY;
			int lnStartY = startY + (totalWidth - lnWidth) / 2;
			int i = 0;
			for (String s : wordWrappedLines) {
				drawCenteredStringWithShadow(s, midX, lnStartY + (i * 10), messageColor);
				i++;
			}
		}
		super.drawObject(mXIn, mYIn, ticks);
	}
	
	@Override
	public void keyPressed(char typedKey, int keyCode) {
		if (keyCode == 28) { //enter
			if (primaryObject != null && primaryObject instanceof EGuiButton) {
				((EGuiButton) primaryObject).performAction();
			}
		}
	}
	
	public IEnhancedGuiObject getPrimaryObject() { return primaryObject; }
	public EGuiDialogueBox setPrimaryObject(IEnhancedGuiObject objIn) { primaryObject = objIn; return this; }
	public EGuiDialogueBox addButtonOption(EGuiButton buttonIn) { addObject(buttonIn); return this; }
	public EGuiDialogueBox setDisplayString(String stringIn) { header.setDisplayString(stringIn); return this; }
	public EGuiDialogueBox setDisplayStringColor(int colorIn) { header.setDisplayStringColor(colorIn); return this; }
	public EGuiDialogueBox setMessage(String stringIn) { message = stringIn; wordWrappedLines = EUtil.createWordWrapString(message, width - 20); return this; }
	public EGuiDialogueBox setMessageColor(int colorIn) { messageColor = colorIn; return this; }
	public EGuiDialogueBox setMoveable(boolean val) { if (header.moveButton != null) { header.moveButton.setPersistent(false).setVisible(false); } return this; }
}
