package com.stbg.pcmtld;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

@SuppressWarnings("rawtypes")
public enum EntityType {

	PLAYER("player", Player.class, 32, 32, 40, 3, false),
	ORANGEENEMY("orange", OrangeEnemy.class, 32, 32, 40, 50, false),
	CYANENEMY("cyan", CyanMonster.class, 32, 32, 40, 50, false),
	PINKENENMY("pink", PinkMonster.class, 32, 32, 40, 50, false),
	REDENEMY("red", RedEnemy.class, 32, 32, 40, 20, false),
	DESTROYABLEBLOCK("destblock", DestroyableBlock.class, 32, 32, 0, 1, false),
	CHECKPOINT("check", Checkpoint.class, 32, 32, 0, 1, false),
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
	
	private EntityType(String id, Class loaderClass, int width, int height, float weight, double health, boolean collectable){
		this.id = id;
		this.loaderClass = loaderClass;
		this.width = width;
		this.height = height;
		this.weight = weight;
		this.health = health;
		this.collectable = collectable;
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
