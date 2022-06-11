package com.stbg.pcmtld;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameMap {

	protected ArrayList<Entity> entities;
	ArrayList<Entity> checkP;
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
		playerHealth = (int) EntityType.PLAYER.getHealth();
		playerToLadder = false;
		// if(!SettingReader.noLevels)
		// entities.addAll(EntityLoader.loadEntitiesFromMap("" + Screen3.lvl,
		// this, entities));
		checkPLoad = false;
		uncheck();
		// System.out.println(String.valueOf(entities.get(3)));
		// System.out.println(entities.size() - 1);
		// entity = new Entity();
		
		xCamOffset = 300;
		yCamOffset = -100;
		
		camRight = true;
		camUp = true;
	}

	@SuppressWarnings("unchecked")
	private void checkPoint() {
		checkP = (ArrayList<Entity>) entities.clone();

		Screen3.setCheck();
	}

	@SuppressWarnings("unchecked")
	public void loadCheck() {
		entities = (ArrayList<Entity>) checkP.clone();
	}

	public void uncheck() {
		checkP = null;
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

				if (entities.get(i).getClass() == Player.class) {
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

				if (entities.get(i).isDead() && entities.get(i).getClass() != Player.class) {
					entities.remove(i);
					// System.out.println("An entity just died.");
				} else if (entities.get(i).isDead() && entities.get(i).getClass() == Player.class)
					setDead(true);

			}

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
			if (entity2.getClass() == DestroyableBlock.class && x < entity2.getX() + entity2.getWidth() && x + width > entity2.getX() && y < entity2.getY() + entity2.getHeight() && y + height > entity2.getY() && !entity2.isDead())
				return true;
		}

		for (int row = (int) (y / TileType.TILE_SIZE); row < Math.ceil(y + height) / TileType.TILE_SIZE; row++) {
			for (int col = (int) (x / TileType.TILE_SIZE); col < Math.ceil(x + width) / TileType.TILE_SIZE; col++) {
				for (int layer = 0; layer < getLayers(); layer++) {
					TileType type = getTileTypeByCoordinate(layer, col, row);
					if (type != null) {
						if (type.isCollidable() && !entity.isToLadder()) {
							if (entity.getClass() == Player.class) {
								if (type.getId() == 3) {
									finish = true;
								} else if (type.getId() == 18)
									checkPoint();
							}

							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public boolean doesRectCollideWithTile(float x, float y, float width, float height, TileType type) {
		for (int layer = 0; layer < getLayers(); layer++)
			if (getTileTypeByCoordinate(layer, (int) x / TileType.TILE_SIZE, (int) y / TileType.TILE_SIZE) == type)
				return true;

		return false;
	}

	public boolean doesEntityCollideWithEntity(float x1, float y1, int width1, int height1, float startTime) {

		// boolean ladderRetrigger = true;
		// else //return false;
		for (Entity entity : entities) {
			// Return true if you want to hurt the player for some reason!
			if (entity != null && entity.getClass() != Player.class) {
				if (entity.getX() < x1 + width1 && entity.getX() + entity.getWidth() > x1 && entity.getY() < y1 + height1 && entity.getY() + entity.getHeight() > y1 && startTime <= 0) {
					if (entity.getClass() == DestroyableBlock.class && entity.getY() < y1 - 1  + height1 && entity.getY() + 1 + entity.getHeight() > y1) {
						if (!entity.isDead()) {
							entity.setTouched(true);
							// System.out.println(entity.isTouched());
						}

					} else if (entity.getType().isCollectable() && entity.getY() < y1 - 1 + height1 && entity.getY() + 1 + entity.getHeight() > y1) {
						if (!entity.isDead()) {
							entity.setDead(true);
							((Score) entity).onCollected(/* entities.get(getPlayerIndex()) */);
						}
					} else if (entity.getClass() == Checkpoint.class) {
						checkPoint();
					} else
						return true;
				} /*
					 * else if(Gdx.input.isKeyJustPressed(Keys.SPACE) &&
					 * entity.getClass() == Ladder.class && entity.getX() < x1 +
					 * width1 && entity.getX() + entity.getWidth() > x1 &&
					 * entity.getY() < y1 + height1 && entity.getY() +
					 * entity.getHeight() > y1){ for(Entity player : entities){
					 * if(player.getClass() == Player.class && ladderRetrigger){
					 * player.setToLadder(!player.isToLadder()); ladderRetrigger
					 * = false; break; } } }
					 */
			}
		}

		return false;
	}

	public abstract int getWidth();

	public abstract int getHeight();

	public abstract int getLayers();

	public abstract boolean isReady();

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
				return i;
		}

		return -1;
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
