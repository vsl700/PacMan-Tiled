package com.stbg.pcmtld;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

@SuppressWarnings("rawtypes")
public enum EntityType {

	PLAYER("player", Player.class, 32, 32, 40, 3, false),
	ORANGEENEMY("orange", OrangeEnemy.class, 32, 32, 40, 4, false),
	CYANENEMY("cyan", CyanMonster.class, 32, 32, 40, 4, false),
	PINKENENMY("pink", PinkMonster.class, 32, 32, 40, 4, false),
	REDENEMY("red", RedEnemy.class, 32, 32, 40, 2, false),
	DESTROYABLEBLOCK("destblock", DestroyableBlock.class, 32, 32, 0, 1, false),
	CHECKPOINT("check", Checkpoint.class, 32, 32, 0, 1, false, false),
	REDKEY("rkey", RedKey.class, 32, 32, 0, 1, false, false),
	GREENKEY("gkey", GreenKey.class, 32, 32, 0, 1, false, false),
	BLUEKEY("bkey", BlueKey.class, 32, 32, 0, 1, false, false),
	REDDORR("rdoor", RedDoor.class, 32, 32, 0, 1, false, false),
	GREENDOOR("gdoor", GreenDoor.class, 32, 32, 0, 1, false, false),
	BLUEDOOR("bdoor", BlueDoor.class, 32, 32, 0, 1, false, false),
	SCORE("score", Score.class, 9, 8, 0, 1, true),
	BIGSCORE("bigscore", BigScore.class, 18, 16, 0, 1, true),
	BANANASCORE("banana", BananaScore.class, 32, 32, 0, 1, true),
	HEARTSCORE("heart", HeartScore.class, 32, 32, 0, 1, true),
	STRAWBERRYSCORE("berry", StrawberryScore.class, 32, 32, 0, 1, true),
	CHERRYSCORE("cherry", CherryScore.class, 32, 32, 0, 1, true);
	
	private String id;
	private Class loaderClass;
	private int width, height;
	private float weight;
	private double health;
	private boolean collectable;
	private boolean killable;
	
	private EntityType(String id, Class loaderClass, int width, int height, float weight, double health, boolean collectable) {
		this(id, loaderClass, width, height, weight, health, collectable, true);
	}
	
	private EntityType(String id, Class loaderClass, int width, int height, float weight, double health, boolean collectable, boolean killable){
		this.id = id;
		this.loaderClass = loaderClass;
		this.width = width;
		this.height = height;
		this.weight = weight;
		this.health = health;
		this.collectable = collectable;
		this.killable = killable;
	}

	public String getId() {
		return id;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public float getWeight() {
		return weight;
	}
	
	public boolean isCollectable() {
		return collectable;
	}
	
	public boolean isKillable() {
		return killable;
	}
	
	public static Entity createEntityUsingSnapshot(EntitySnapshot entitySnapshot, GameMap map){
		@SuppressWarnings("static-access")
		EntityType type = entityTypes.get(entitySnapshot.type); 
		try {
			@SuppressWarnings("unchecked")
			Entity entity = ClassReflection.newInstance(type.loaderClass);
			entity.create(entitySnapshot, type, map);
			return entity;
		} catch (ReflectionException e) {
			// TODO Auto-generated catch block
			Gdx.app.error("Entity Loader", "Could not load entity of type " + type.id);
			return null;
		}
	}
	
	private static HashMap<String, EntityType> entityTypes;
	
	static{
		entityTypes = new HashMap<String, EntityType>();
		for(EntityType type : EntityType.values())
			entityTypes.put(type.id, type);
		
	}

	public double getHealth() {
		// TODO Auto-generated method stub
		return health;
	}
	
}
