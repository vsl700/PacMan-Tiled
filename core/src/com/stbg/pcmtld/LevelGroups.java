package com.stbg.pcmtld;

public enum LevelGroups {

	NORMAL("pri", "Primary levels"),
	JUNGLE("jun", "Jungle levels"),
	ICEAGE("ice", "Ice-age levels"),
	DESERT("des", "Desert levels");
	
	private String dir;
	private String name;
	
	private LevelGroups(String dir, String name) {
		this.dir = dir;
		this.name = name;
	}
	
	public String getDir() {
		return dir;
	}
	
	public String getName() {
		return name;
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
