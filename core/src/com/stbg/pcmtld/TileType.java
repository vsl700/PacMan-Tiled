package com.stbg.pcmtld;

import java.util.HashMap;

public enum TileType {

	BLOCK(1, true, "Block"),
	AIR(2, false, "Air"),
	FINISH(3, true, "Finish"),
	SCORE(4, false, "Score", 5),
	REDKEY(5, false, "RKey"),
	REDDOOR(6, false, "RDoor"),
	GREENKEY(7, false, "GKey"),
	GREENDOOR(8, false, "GDoor"),
	BLUEKEY(9, false, "BKey"),
	BLUEDOOR(10, false, "BDoor"),
	DESTBLOCKTILE(11, false, "destblock"),
	REDSTILE(12, false, "red"),
	ORANGESTILE(13, false, "yellow"),
	CYANSTILE(14, false, "cyan"),
	PINKSTILE(15, false, "pink"),
	PLAYERSTILE(16, false, "player"),
	LADDER(17, false, "ladder"),
	CHECKPOINT(18, false, "check"),
	BIGSCORE(19, false, "bigscore");
	//SCORETILE(16, false, "score"),
	
	
	private int id;
	private boolean collidable;
	private String name;
	private float damage;
	
	public static final int TILE_SIZE = 32;
	
	private TileType(int id, boolean collidable, String name){
		this(id, collidable, name, 0);
	}
	
	private TileType(int id, boolean collidable, String name, float damage){
		this.id = id;
		this.collidable = collidable;
		this.name = name;
		this.damage = damage;
	}

	public int getId() {
		return id;
	}

	public boolean isCollidable() {
		return collidable;
	}

	public String getName() {
		return name;
	}

	public float getDamage() {
		return damage;
	}
	
	private static HashMap<Integer, TileType> tileMap;
	
	static{
		tileMap = new HashMap<Integer, TileType>();
		for(TileType tileType : TileType.values()){
			tileMap.put(tileType.getId(), tileType);
		}
	}
	
	public static TileType getTileTypeById(int id){
		return tileMap.get(id);
	}
	
	public static void removeTile(TileType tile){
		//System.out.println("Delete");
		//tileMap.remove(tile);
	}
	
}
