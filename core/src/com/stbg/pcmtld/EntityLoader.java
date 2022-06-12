package com.stbg.pcmtld;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class EntityLoader {

	private static Json json = new Json();
	
	public static int count = 0;
	//public static boolean done = false;
	//static int count2 = 0;
	//public static float xData[][];
	//public static float yData[][];
	
	public static ArrayList<Entity> loadEntities(String id, GameMap map, ArrayList<Entity> currentEntities){
		//if(Screen3.crnt){
		Gdx.files.local("entities/").file().mkdirs();
		FileHandle file = Gdx.files.local("entities/" + id + ".json");
		if(file.exists()){
			EntitySnapshot[] snapshots = json.fromJson(EntitySnapshot[].class, file.readString());
			ArrayList<Entity> entities = new ArrayList<Entity>(100);
			for(EntitySnapshot snapshot : snapshots){
				entities.add(EntityType.createEntityUsingSnapshot(snapshot, map));
				//System.out.println(String.valueOf(entities.get(count)));
				//Entity.entAmount++;
				count = snapshots.length;
				/*if(snapshot.getType() == "red"){
					RedEnemy.setPosition(count);
					RedEnemy.setValue(snapshot.getType());
				}else if(snapshot.getType() == "orange"){
					OrangeEnemy.setPosition(count);
					OrangeEnemy.setValue(snapshot.getType());
				}else if(snapshot.getType() == "cyan"){
					CyanMonster.setPosition(count);
					CyanMonster.setValue(snapshot.getType());
				}if(snapshot.getType() == "pink"){
					PinkMonster.setPosition(count);
					PinkMonster.setValue(snapshot.getType());
				}*/
				//}//else if(snapshot.getType() == "orange"){
					//OrangeEnemy.setPosition(count);
					//OrangeEnemy.setValue("red");
				//}
				//System.out.println(snapshot.getType());
				//count++;
			}
			//done = true;
			//if(Entity.ready2)
			//for(EntitySnapshot snapshot: snapshots){
				/*if(snapshot.getType() == "red"){
					RedEnemy.setPosition(count2);
					RedEnemy.setValue(snapshot.getType());
				}else if(snapshot.getType() == "orange"){
					OrangeEnemy.setPosition(count2);
					OrangeEnemy.setValue(snapshot.getType());
				}else if(snapshot.getType() == "cyan"){
					CyanMonster.setPosition(count2);
					CyanMonster.setValue(snapshot.getType());
				}if(snapshot.getType() == "pink"){
					PinkMonster.setPosition(count2);
					PinkMonster.setValue(snapshot.getType());
				}*/
				//if(snapshot.getType() != "player"){
				//Entity.redX2[count2] = snapshot.getX();
				//Entity.redY2[count2] = snapshot.getY();
				//}
				
				//count2++;
			//}
			return entities;
		} else {
			//Gdx.app.error("EntityLoader", "Could not load entities.");
			saveEntities("cont.json", currentEntities);
			return null;
		}
		//} else
		//return null;
		
	}
	
	public static ArrayList<Entity> loadEntitiesFromMap(GameMap map){
		//if(Screen3.crnt){
		//Gdx.files.local("entities/").file().mkdirs();
		//FileHandle file = Gdx.files.local("entities/" + id + ".json");
		//if(file.exists()){
			
			//EntitySnapshot[] snapshots;
		
		//TiledGameMap map2 = new TiledGameMap(Integer.parseInt(id));
			
			//ArrayList<EntitySnapshot> snapshots = new ArrayList<EntitySnapshot>(100);
			ArrayList<Entity> entities = new ArrayList<Entity>(100);
			
			//if(map.getLayers() != 0)
			//if(Screen3.firstCreated)
			for(int x = 0; x <= map.getWidth(); x++){
				for(int y = 0; y <= map.getHeight(); y++){
					for(int layers = 1; layers < map.getLayers(); layers++){
						if(map.getTileTypeByCoordinate(layers, x, y) == TileType.REDSTILE){
							EntitySnapshot snapshot = new EntitySnapshot("red", x * 32, y * 32);
							//snapshots.add(snapshot);
							entities.add(EntityType.createEntityUsingSnapshot(snapshot, map));
						}else if(map.getTileTypeByCoordinate(layers, x, y) == TileType.ORANGESTILE){
							EntitySnapshot snapshot = new EntitySnapshot("orange", x * 32, y * 32);
							//snapshots.add(snapshot);
							entities.add(EntityType.createEntityUsingSnapshot(snapshot, map));
						}else if(map.getTileTypeByCoordinate(layers, x, y) == TileType.CYANSTILE){
							EntitySnapshot snapshot = new EntitySnapshot("cyan", x * 32, y * 32);
							//snapshots.add(snapshot);
							entities.add(EntityType.createEntityUsingSnapshot(snapshot, map));
							//System.out.println("CYAN");
						}else if(map.getTileTypeByCoordinate(layers, x, y) == TileType.PINKSTILE){
							EntitySnapshot snapshot = new EntitySnapshot("pink", x * 32, y * 32);
							//snapshots.add(snapshot);
							entities.add(EntityType.createEntityUsingSnapshot(snapshot, map));
							//System.out.println("PINK");
						}else if(map.getTileTypeByCoordinate(layers, x, y) == TileType.PLAYERSTILE){
							EntitySnapshot snapshot = new EntitySnapshot("player", x * 32, y * 32);
							//snapshots.add(snapshot);
							entities.add(EntityType.createEntityUsingSnapshot(snapshot, map));
							//System.out.println("PLAYER");
						}else if(map.getTileTypeByCoordinate(layers, x, y) == TileType.SCORE){
							EntitySnapshot snapshot = new EntitySnapshot("score", x * 32 + 11, y * 32 + 11);
							//snapshots.add(snapshot);
							entities.add(EntityType.createEntityUsingSnapshot(snapshot, map));
						}else if(map.getTileTypeByCoordinate(layers, x, y) == TileType.BIGSCORE){
							EntitySnapshot snapshot = new EntitySnapshot("bigscore", x * 32 + 7, y * 32 + 8);
							//snapshots.add(snapshot);
							entities.add(EntityType.createEntityUsingSnapshot(snapshot, map));
						}else if(map.getTileTypeByCoordinate(layers, x, y) == TileType.BANANASCORE){
							EntitySnapshot snapshot = new EntitySnapshot("banana", x * 32, y * 32);
							//snapshots.add(snapshot);
							entities.add(EntityType.createEntityUsingSnapshot(snapshot, map));
						}else if(map.getTileTypeByCoordinate(layers, x, y) == TileType.HEARTSCORE){
							EntitySnapshot snapshot = new EntitySnapshot("heart", x * 32, y * 32);
							//snapshots.add(snapshot);
							entities.add(EntityType.createEntityUsingSnapshot(snapshot, map));
						}else if(map.getTileTypeByCoordinate(layers, x, y) == TileType.STRAWBERRYSCORE){
							EntitySnapshot snapshot = new EntitySnapshot("berry", x * 32, y * 32);
							//snapshots.add(snapshot);
							entities.add(EntityType.createEntityUsingSnapshot(snapshot, map));
						}else if(map.getTileTypeByCoordinate(layers, x, y) == TileType.CHERRYSCORE){
							EntitySnapshot snapshot = new EntitySnapshot("cherry", x * 32, y * 32);
							//snapshots.add(snapshot);
							entities.add(EntityType.createEntityUsingSnapshot(snapshot, map));
						}else if(map.getTileTypeByCoordinate(layers, x, y) == TileType.DESTBLOCKTILE){
							EntitySnapshot snapshot = new EntitySnapshot("destblock", x * 32, y * 32);
							//snapshots.add(snapshot);
							entities.add(EntityType.createEntityUsingSnapshot(snapshot, map));
						}
					}
				}
			}
			
			//for(EntitySnapshot snapshot : snapshots){
				//entities.add(EntityType.createEntityUsingSnapshot(snapshot, map));
			//}
			
			count = entities.size();
			/*for(EntitySnapshot snapshot : snapshots){
				entities.add(EntityType.createEntityUsingSnapshot(snapshot, map));
				//System.out.println(String.valueOf(entities.get(count)));
				//Entity.entAmount++;
				count = snapshots.length;
				/*if(snapshot.getType() == "red"){
					RedEnemy.setPosition(count);
					RedEnemy.setValue(snapshot.getType());
				}else if(snapshot.getType() == "orange"){
					OrangeEnemy.setPosition(count);
					OrangeEnemy.setValue(snapshot.getType());
				}else if(snapshot.getType() == "cyan"){
					CyanMonster.setPosition(count);
					CyanMonster.setValue(snapshot.getType());
				}if(snapshot.getType() == "pink"){
					PinkMonster.setPosition(count);
					PinkMonster.setValue(snapshot.getType());
				}
				//}//else if(snapshot.getType() == "orange"){
					//OrangeEnemy.setPosition(count);
					//OrangeEnemy.setValue("red");
				//}
				//System.out.println(snapshot.getType());
				//count++;
			}*/
			//done = true;
			//if(Entity.ready2)
			//for(EntitySnapshot snapshot: snapshots){
				/*if(snapshot.getType() == "red"){
					RedEnemy.setPosition(count2);
					RedEnemy.setValue(snapshot.getType());
				}else if(snapshot.getType() == "orange"){
					OrangeEnemy.setPosition(count2);
					OrangeEnemy.setValue(snapshot.getType());
				}else if(snapshot.getType() == "cyan"){
					CyanMonster.setPosition(count2);
					CyanMonster.setValue(snapshot.getType());
				}if(snapshot.getType() == "pink"){
					PinkMonster.setPosition(count2);
					PinkMonster.setValue(snapshot.getType());
				}*/
				//if(snapshot.getType() != "player"){
				//Entity.redX2[count2] = snapshot.getX();
				//Entity.redY2[count2] = snapshot.getY();
				//}
				
				//count2++;
			//}
			return entities;
		//} else {
			//Gdx.app.error("EntityLoader", "Could not load entities.");
			//saveEntities("cont.json", currentEntities);
			//return null;
		//}
		//} else
		//return null;
		
	}
	
	public static void saveEntities(String id, ArrayList<Entity> entities){
		ArrayList<EntitySnapshot> snapshots = new ArrayList<EntitySnapshot>();
		for(Entity entity : entities){
			snapshots.add(entity.getSaveSnapshot());
		}
		
		Gdx.files.local("saves/").file().mkdirs();
		FileHandle file = Gdx.files.local("saves'/" + id + ".sav");
		file.writeString(json.prettyPrint(snapshots), false);
	}
	
}
