package com.stbg.pcmtld;

import com.badlogic.gdx.graphics.Color;

public enum LevelGroups {

	NORMAL("pri", "Primary levels", Color.BLUE, Color.BLACK),
	JUNGLE("jun", "Jungle levels", Color.CYAN, Color.FOREST),
	ICEAGE("ice", "Ice-age levels", Color.BLUE, Color.BLACK),
	DESERT("des", "Desert levels", Color.BLUE, Color.BLACK);
	
	private String dir;
	private String name;
	private Color upColor, downColor;
	
	private LevelGroups(String dir, String name, Color upColor, Color downColor) {
		this.dir = dir;
		this.name = name;
		this.upColor = upColor;
		this.downColor = downColor;
	}
	
	public String getDir() {
		return dir;
	}
	
	public String getName() {
		return name;
	}
	
	public Color getUpColor() {
		return upColor;
	}
	
	public Color getDownColor() {
		return downColor;
	}
	
	public static int length() {
		return values().length;
	}
	
	public static LevelGroups getFirst() {
		return values()[0];
	}
	
	public static LevelGroups getLast() {
		if(values().length == 0)
			return null;
		
		return values()[values().length - 1];
	}
	
	public LevelGroups getNext() {
		if(ordinal() + 1 == values().length)
			return null;
		
		return values()[ordinal() + 1];
	}
	
	public LevelGroups getPrev() {
		if(ordinal() == 0)
			return null;
		
		return values()[ordinal() - 1];
	}
	
}
