package com.Whodundid.main.util.enhancedGui.guiUtil;

import com.Whodundid.main.util.enhancedGui.guiObjects.EGuiHeader;

//Last edited: Jan 1, 2019
//First Added: Jan 1, 2019
//Author: Hunter Bragg

public class HeaderAlreadyExistsException extends Exception {
	
	public HeaderAlreadyExistsException(EGuiHeader existingHeader) {
		super("Only one Header object can be attached to an object. Header: " + existingHeader + " already is attached to " + existingHeader.getParent() + ".");
	}
}
