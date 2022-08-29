package com.stbg.pcmtld;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class GameMap {

	protected class DoorPair{
		public Vector2 firstDoor;
		public Vector2 secondDoor;
		
		public DoorPair(Vector2 firstDoor, Vector2 secondDoor) {
			this.firstDoor = firstDoor;
			this.secondDoor = secondDoor;
		}
	}
	
	protected ArrayList<Entity> entities;
	protected ArrayList<Bullet> bullets;
	protected ArrayList<DoorPair> doors;
	protected ArrayList<EntitySnapshot> checkP;
	public int xCamOffset;
	public int yCamOffset;
	private boolean camRight;
	private boolean camUp;
	public static boolean checkPLoad = false;
	private boolean finish = false;
	private boolean dead = false;
	// public boolean firstUsed;
	int playerIndex;
	static int playerHealth;
	int score;
	int time;
	float playerX = 0;
	float playerY = 0;
	boolean playerGrounded;
	boolean playerToLadder;
	boolean playerCanBeLaddered;
	boolean playerDir;
	/**
	 * Acts as a move left button
	 */
	boolean moveLeft = false;
	/**
	 * Acts as a move right button
	 */
	boolean moveRight = false;
	/**
	 * Acts as a jump button
	 */
	boolean jump = false;
	/**
	 * Acts as a move up button
	 */
	boolean up = false;
	/**
	 * Acts as a move down button
	 */
	boolean down = false;
	/**
	 * Acts as a ladder attach button
	 */
	boolean ladder = false;
	// Entity entity;

	public GameMap() {
		entities = new ArrayList<Entity>(100);
		bullets = new ArrayList<Bullet>(100);
		doors = new ArrayList<DoorPair>();
		checkP = new ArrayList<EntitySnapshot>();
		
		playerHealth = (int) EntityType.PLAYER.getHealth();
		playerToLadder = false;
		// if(!SettingReader.noLevels)
		// entities.addAll(EntityLoader.loadEntitiesFromMap("" + Screen3.lvl,
		// this, entities));
		checkPLoad = false;
		// System.out.println(String.valueOf(entities.get(3)));
		// System.out.println(entities.size() - 1);
		// entity = new Entity();
		
		xCamOffset = 300;
		yCamOffset = -100;
		
		camRight = true;
		camUp = true;
	}
	
	protected abstract void loadDoors();

	private void checkPoint() {
		checkP.clear();
		for(Entity e : entities) {
			EntitySnapshot snapshot = e.getSaveSnapshot();
			checkP.add(snapshot);
		}

		Screen3.setCheck();
	}

	public void loadCheck() {
		entities.clear();
		for(EntitySnapshot snapshot : checkP) {
			entities.add(EntityType.createEntityUsingSnapshot(snapshot, this));
		}
	}

	public void update(float delta) {
		if(delta > 1 / 10f)
			return;
		
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i) != null) {
				entities.get(i).update(delta, -9.8f);

				if (!entities.get(i).isDead() && (entities.get(i).getX() < 0 || entities.get(i).getY() < 0 || entities.get(i).getX() + entities.get(i).getWidth() > getPixelWidth() || entities.get(i).getY() + entities.get(i).getHeight() > getPixelHeight())) {
					// if(entities.get(i).getClass() != Player.class)
					entities.get(i).setDead(true);
					// else
					// Player.setPlayerDead(true);
				}

				if (entities.get(i).getType().equals(EntityType.PLAYER)) {
					playerHealth = entities.get(i).getHealth();
					playerGrounded = entities.get(i).isGrounded();
					playerToLadder = entities.get(i).isToLadder();
					playerCanBeLaddered = entities.get(i).canBeLaddered();
					playerDir = entities.get(i).isRight();
					playerX = entities.get(i).getX();
					playerY = entities.get(i).getY();
					entities.get(i).setMoveLeft(moveLeft);
					entities.get(i).setMoveRight(moveRight);
					entities.get(i).setJump(jump);
					entities.get(i).setUp(up);
					entities.get(i).setDown(down);
					entities.get(i).setLadder(ladder);
				}

				if (entities.get(i).isDead() && !entities.get(i).getType().equals(EntityType.PLAYER)) {
					entities.get(i).dispose();
					entities.remove(i);
					
					getPlayerIndex();
					// System.out.println("An entity just died.");
				} else if (entities.get(i).isDead() && entities.get(i).getType().equals(EntityType.PLAYER))
					if(isCheckpointed()) {
						loadCheck();
						return;
					}else setDead(true);

			}

		}
		
		for (int i = 0; i < bullets.size(); i++) {
			Bullet bullet = bullets.get(i);
			bullet.update(delta);
			
			if(doesRectCollideWithTile(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight(), TileType.BLOCK) || doesRectCollideWithTile(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight(), TileType.DESTBLOCKTILE) ||
					doesRectCollideWithTile(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight(), TileType.FINISH))
				removeBullet(bullet);
		}

		// if(entities.indexOf(Player.class) == -1 && ready)
		// Player.setPlayerDead(true);

	}

	public void render(OrthographicCamera camera, SpriteBatch batch) {
		if(playerIndex >= 0) {
			camera.position.x = entities.get(playerIndex).pos.x + xCamOffset;
			camera.position.y = entities.get(playerIndex).pos.y + yCamOffset;
		}
		else {
			if(camRight && camera.position.x >= (getWidth() - 10) * TileType.TILE_SIZE) {
				camRight = false;
			}
			else if(!camRight && camera.position.x <= 12 * TileType.TILE_SIZE) {
				camRight = true;
			}
			
			if(camUp && camera.position.y >= (getHeight() - 12) * TileType.TILE_SIZE) {
				camUp = false;
			}
			else if(!camUp && camera.position.y <= 5 * TileType.TILE_SIZE) {
				camUp = true;
			}
			
			
			int speed = 96;
			float delta = 1 / 60f;
			float delta2 = 1 / 90f;
			camera.translate((camRight ? speed : -speed) * delta, (camUp ? speed : -speed) * delta2);
		}

		camera.update();
		
		batch.begin();
		batch.setProjectionMatrix(camera.combined);

		for (Entity entity : entities) {
			entity.render(batch, camera);
			// if(entity.getClass() == Player.class){
			// camera.setToOrtho(false, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
			// camera.translate(entity.pos.x - 200, entity.pos.y - 300);
			// camera.position.x = entity.pos.x;
			// camera.position.y = entity.pos.y;
			// }
		}
		
		float delay = 250;
		for (int i = 0; i < bullets.size(); i++) {
			Bullet bullet = bullets.get(i);
			if(bullet.getX() + delay > camera.position.x + camera.viewportWidth / 2 + xCamOffset || bullet.getX() + bullet.getWidth() - delay < camera.position.x - camera.viewportWidth / 2 - xCamOffset) {
				removeBullet(bullet);
				i--;
				
				continue;
			}
			
			bullet.render(batch, camera);
		}

		batch.end();
	}

	public void dispose() {
		// EntityLoader.saveEntities("", entities);
		for (Entity entity : entities) {
			if (entity != null)
				entity.dispose();
		}
	}

	/**
	 * Gets a tile by pixel position within the game world at a specified layer.
	 * 
	 * @param layer
	 * @param x
	 * @param y
	 * @return
	 */

	public TileType getTileTypeByLocation(int layer, float x, float y) {
		return this.getTileTypeByCoordinate(layer, (int) (x / TileType.TILE_SIZE), (int) (y / TileType.TILE_SIZE));
	}

	/**
	 * Gets a tile at its coordinate within the map at a specified layer.
	 * 
	 * @param layer
	 * @param col
	 * @param row
	 * @return The tile type
	 */

	public abstract TileType getTileTypeByCoordinate(int layer, int col, int row);

	public boolean doesRectCollideWithMap(float x, float y, int width, int height, Entity entity) {
		for (Entity entity2 : entities) {
			if (x < entity2.getX() + entity2.getWidth() && x + width > entity2.getX()
					&& y < entity2.getY() + entity2.getHeight() && y + height > entity2.getY() && !entity2.isDead()) {
				if (entity2.getType().equals(EntityType.DESTROYABLEBLOCK) && !entity.isToLadder()) {
					if (entity.getType().equals(EntityType.PLAYER)
							&& entity2.getY() + entity2.getHeight() <= entity.getY() && !entity2.isDead()) {
						entity2.setTouched(true);
						// System.out.println(entity.isTouched());
					}
					return true;
				}else if (entity2.getType().equals(EntityType.REDDORR)) {
					if(!getPlayerInstance().rkey || !entity.getType().equals(EntityType.PLAYER)) {
						return true;
					}
				} else if (entity2.getType().equals(EntityType.GREENDOOR)) {
					if(!getPlayerInstance().gkey || !entity.getType().equals(EntityType.PLAYER)) {
						return true;
					}
				} else if (entity2.getType().equals(EntityType.BLUEDOOR)) {
					if(!getPlayerInstance().bkey || !entity.getType().equals(EntityType.PLAYER)) {
						return true;
					}
				}
			}
		}

		for (int row = (int) (y / TileType.TILE_SIZE); row < Math.ceil(y + height) / TileType.TILE_SIZE; row++) {
			for (int col = (int) (x / TileType.TILE_SIZE); col < Math.ceil(x + width) / TileType.TILE_SIZE; col++) {
				for (int layer = 0; layer < getLayers(); layer++) {
					TileType type = getTileTypeByCoordinate(layer, col, row);
					if (type != null) {
						if (type.isCollidable() && !entity.isToLadder()) {
							if (entity.getClass() == Player.class) {
								if (type.getId() == TileType.FINISH.getId()) {
									finish = true;
								} /*else if (type.getId() == TileType.CHECKPOINT.getId())
									checkPoint();*/
							}

							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public Vector2 getCorrespondingDoor(float x, float y, int width, int height) {
		for(DoorPair doorPair : doors) {
			Vector2 firstDoor = doorPair.firstDoor;
			Vector2 secondDoor = doorPair.secondDoor;
			if(firstDoor.x < x + width && firstDoor.x + TileType.TILE_SIZE > x && firstDoor.y < y + height && firstDoor.y + TileType.TILE_SIZE > y)
				return secondDoor;
			
			if(secondDoor.x < x + width && secondDoor.x + TileType.TILE_SIZE > x && secondDoor.y < y + height && secondDoor.y + TileType.TILE_SIZE > y)
				return firstDoor;
		}
		
		return null;
	}

	public boolean doesRectCollideWithTile(float x, float y, float width, float height, TileType type) {
		for (int layer = 0; layer < getLayers(); layer++)
			if (getTileTypeByCoordinate(layer, (int) x / TileType.TILE_SIZE, (int) y / TileType.TILE_SIZE) == type)
				return true;

		return false;
	}
	
	public boolean doesRectCollideWithAnyTile(float x, float y, float width, float height, TileType... types) {
		for(TileType type : types) {
			if(doesRectCollideWithTile(x, y, width, height, type))
				return true;
		}
		
		return false;
	}

	private Checkpoint currentCheck;
	public boolean doesEntityCollideWithEntity(float x1, float y1, int width1, int height1, float startTime) {

		// boolean ladderRetrigger = true;
		// else //return false;
		for (Entity entity : entities) {
			// Return true if you want to hurt the player for some reason!
			if (entity != null && entity.getClass() != Player.class) {
				if (entity.getX() < x1 + width1 && entity.getX() + entity.getWidth() > x1 && entity.getY() < y1 + height1 && entity.getY() + entity.getHeight() > y1 - (entity.getClass() == DestroyableBlock.class ? 3 : 0) && (startTime <= 0 || entity.getType().equals(EntityType.CHECKPOINT))) {
					if (entity.getClass() == DestroyableBlock.class && entity.getY() < y1) {
						if (!entity.isDead()) {
							entity.setTouched(true);
							//System.out.println(entity.isTouched());
						}

					} else if (entity.getType().isCollectable()) {
						if (!entity.isDead()) {
							entity.setDead(true);
							((Score) entity).onCollected(/* entities.get(getPlayerIndex()) */);
						}
					} else if (entity.getClass() == Checkpoint.class) {
						if (!((Checkpoint) entity).isCheck()) {
							if (currentCheck != null)
								currentCheck.setCheck(false);

							checkPoint();// FIXME Swap this and the bottom line to remove the self-resetting checkpoint bug (I just left it for fun, for the 'speedrunners' or sth)
							((Checkpoint) entity).setCheck(true);
							currentCheck = (Checkpoint) entity;
						}
					} else if (entity.getClass() == RedKey.class) {
						getPlayerInstance().rkey = true;
						entity.die();
					} else if (entity.getClass() == GreenKey.class) {
						getPlayerInstance().gkey = true;
						entity.die();
					} else if (entity.getClass() == BlueKey.class) {
						getPlayerInstance().bkey = true;
						entity.die();
					} else if (entity.getClass() == RedDoor.class) {
						if(getPlayerInstance().rkey) {
							entity.die();
						}
					} else if (entity.getClass() == GreenDoor.class) {
						if(getPlayerInstance().gkey) {
							entity.die();
						}
					} else if (entity.getClass() == BlueDoor.class) {
						if(getPlayerInstance().bkey) {
							entity.die();
						}
					} else
						return true;
				}
			}
		}

		return false;
	}
	
	public boolean doesEntityCollideWithBullet(Entity entity) {
		for(Bullet bullet : bullets) {
			if(bullet.getShooterEntity().equals(entity))
				continue;
			
			if (entity.getX() < bullet.getX() + bullet.getWidth() && entity.getX() + entity.getWidth() > bullet.getX() && entity.getY() < bullet.getY() + bullet.getHeight() && entity.getY() + entity.getHeight() > bullet.getY() && entity.startTime <= 0) {
				removeBullet(bullet);
				
				return true;
			}
		}
		
		return false;
	}

	public abstract int getWidth();

	public abstract int getHeight();

	public abstract int getLayers();

	public abstract boolean isReady();

	public void shootBullet(float spawnX, float spawnY, float dirX, float dirY, Entity shooterEntity) {
		bullets.add(new Bullet(spawnX, spawnY, dirX, dirY, shooterEntity));
	}
	
	private void removeBullet(Bullet bullet) {
		bullet.dispose();
		bullets.remove(bullet);
	}
	
	public Player getPlayerInstance() {
		return ((Player) entities.get(getPlayerIndex()));
	}
	
	public int getPixelWidth() {
		return this.getWidth() * TileType.TILE_SIZE;
	}

	public int getPixelHeight() {
		return this.getHeight() * TileType.TILE_SIZE;
	}

	public int getPlayerIndex() {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).getClass() == Player.class)
				return playerIndex = i;
		}

		return -1;
	}
	
	public boolean isCheckpointed() {
		return checkP.size() > 0;
	}

	public boolean isFinish() {
		return finish;
	}

	public void setFinish(boolean finish) {
		this.finish = finish;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public boolean isDead() {
		return dead;
	}

	public int getPlayerHealth() {
		return playerHealth;
	}

	public void setPlayerHealth(int playerHealth) {
		GameMap.playerHealth = playerHealth;
	}

	public boolean isPlayerGrounded() {
		return playerGrounded;
	}

	public void setMoveLeft(boolean moveLeft) {
		this.moveLeft = moveLeft;
	}

	public void setMoveRight(boolean moveRight) {
		this.moveRight = moveRight;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void setLadder(boolean ladder) {
		this.ladder = ladder;
	}

	public boolean isMoveLeft() {
		return moveLeft;
	}

	public boolean isMoveRight() {
		return moveRight;
	}

	public boolean isJump() {
		return jump;
	}

	public boolean isUp() {
		return up;
	}

	public boolean isDown() {
		return down;
	}

	public boolean isLadder() {
		return ladder;
	}

	public boolean isPlayerToLadder() {
		return playerToLadder;
	}

	public void setPlayerToLadder(boolean playerToLadder) {
		this.playerToLadder = playerToLadder;
	}

	public abstract void reRead(int lvl);

}
