package com.stbg.pcmtld;

import java.util.HashMap;

public enum TileType {

	BLOCK(true, "Block"),
	AIR(false, "Air"),
	FINISH(true, "Finish"),
	SCORE(false, "Score", 5),
	REDKEY(false, "RKey"),
	REDDOOR(false, "RDoor"),
	GREENKEY(false, "GKey"),
	GREENDOOR(false, "GDoor"),
	BLUEKEY(false, "BKey"),
	BLUEDOOR(false, "BDoor"),
	DESTBLOCKTILE(false, "destblock"),
	REDSTILE(false, "red"),
	ORANGESTILE(false, "yellow"),
	CYANSTILE(false, "cyan"),
	PINKSTILE(false, "pink"),
	PLAYERSTILE(false, "player"),
	LADDER(false, "ladder"),
	CHECKPOINT(false, "check"),
	BIGSCORE(false, "bigscore"),
	BANANASCORE(false, "banana"),
	HEARTSCORE(false, "heart"),
	STRAWBERRYSCORE(false, "berry"),
	CHERRYSCORE(false, "cherry"),
	DOOR(false, "door");
	//SCORETILE(16, false, "score"),
	
	
	private int id;
	private boolean collidable;
	private String name;
	private float damage;
	
	public static final int TILE_SIZE = 32;
	
	private static class Incrementor{ // Had to use a whole new class because you can't use an enum's static variables when creating the enum's constants
		public static int i = 1;
	}
	
	private TileType(boolean collidable, String name){
		this(collidable, name, 0);
	}
	
	private TileType(boolean collidable, String name, float damage){
		this.id = Incrementor.i++;
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
