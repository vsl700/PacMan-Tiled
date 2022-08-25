package com.stbg.pcmtld;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {

	protected Vector2 pos;
	protected EntityType type;
	protected float velocityY;
	protected int SPEED;
	// protected int health = 50;
	protected GameMap map;
	protected boolean grounded = false;
	protected boolean right;
	protected boolean touched;
	protected boolean dead;
	protected boolean toLadder;
	protected boolean canBeLaddered;
	protected boolean moveLeft = false;
	protected boolean moveRight = false;
	protected boolean jump = false;
	protected boolean up = false;
	protected boolean down = false;
	protected boolean ladder = false;
	protected boolean door = false;
	protected int health;

	// private boolean dead;

	public static boolean hurt;

	float startTime;

	protected EntitySnapshot snapshot;

	public void create(EntitySnapshot snapshot, EntityType type, GameMap map) {
		this.snapshot = snapshot;
		this.pos = new Vector2(snapshot.getX(), snapshot.getY());
		this.type = type;
		this.map = map;

		health = (int) getType().getHealth();

		// dead = false;

		hurt = false;

		right = true;
		touched = false;
		dead = false;
		toLadder = false;

		

	}

	public void update(float deltaTime, float gravity) {
		float newY = pos.y;

		if (!isToLadder())
			this.velocityY += gravity * deltaTime * getWeight();
		newY += this.velocityY * deltaTime;

		if (map.doesRectCollideWithMap(pos.x, newY, getWidth(), getHeight(), this)) {
			if (velocityY < 0) {
				pos.y = (float) Math.floor(pos.y);
				grounded = true;
			}
			this.velocityY = 0;
		} else {
			pos.y = newY;
			grounded = false;
		}

		if (moveLeft) {
			if (isToLadder() && !(map.doesRectCollideWithTile(getX() + getWidth() - 2, getY(), getWidth(), getHeight(), TileType.LADDER) || map.doesRectCollideWithTile(getX() - 2, getY(), getWidth(), getHeight(), TileType.LADDER))) 
				// movingLeft = true;
				ladder = true;

			right = false;
			moveX(-SPEED * deltaTime);
		} else if (moveRight) {
			if (isToLadder() && !(map.doesRectCollideWithTile(getX() + 1, getY(), getWidth(), getHeight(), TileType.LADDER) || map.doesRectCollideWithTile(getX() + getWidth() + 1, getY(), getWidth(), getHeight(), TileType.LADDER)))
				// movingRight = true;
				ladder = true;
			
			right = true;
			moveX(SPEED * deltaTime);

		}

		// if(!isToLadder()) {
		// if(map.doesRectCollideWithMap(getX(), getY(), getWidth(),
		// getHeight(), this))
		// }

		if (canBeLaddered() && ladder) {
			setToLadder(!isToLadder());
			
			while (map.doesRectCollideWithMap(getX(), getY(), getWidth(), getHeight(), this)) {
				if (!map.doesRectCollideWithMap(getX(), getY() - 32, getWidth(), getHeight(), this))
					pos.y--;
				else
					pos.y++;
			}
		}else if(!canBeLaddered() && isToLadder()) {
			setToLadder(false);
			
			while (map.doesRectCollideWithMap(getX(), getY(), getWidth(), getHeight(), this)) {
				if (!map.doesRectCollideWithMap(getX(), getY() - 32, getWidth(), getHeight(), this))
					pos.y--;
				else
					pos.y++;
			}
		}
		
		if(door) { // TODO BETA!!!!
			Vector2 temp = map.getCorrespondingDoor(getX(), getY(), getWidth(), getHeight());
			
			if(temp != null)
				pos.set(temp);
		}
		
		if (!type.isCollectable() && type.isKillable() && !type.equals(EntityType.DESTROYABLEBLOCK) && map.doesEntityCollideWithBullet(this)) {
			setHealth(getHealth() - 1);
			//setStartTime(1.15f);
		}
		
		if(getHealth() < 1)
			die();

		// System.out.println(getX() + ", " + getY());
		

		moveLeft = false;
		moveRight = false;
		ladder = false;
	}

	public abstract void render(SpriteBatch batch, OrthographicCamera camera);

	/*
	 * public void reset() {
	 * 
	 * }
	 */

	public abstract void dispose();

	protected void moveX(float amount) {
		float newX = pos.x + amount;
		if (!map.doesRectCollideWithMap(newX, pos.y, getWidth(), getHeight(), this) && (getClass() == Player.class || map.doesRectCollideWithAnyTile(newX, pos.y - 1, getWidth(), getHeight(), TileType.BLOCK, TileType.DESTBLOCKTILE) && map.doesRectCollideWithAnyTile(newX + getWidth(), pos.y - 1, getWidth(), getHeight(), TileType.BLOCK, TileType.DESTBLOCKTILE)))
			pos.x = newX;
		else {
			if (getClass() != Player.class)
				right = !right;
		}

	}

	public EntitySnapshot getSaveSnapshot() {
		return new EntitySnapshot(type.getId(), pos.x, pos.y);
	}

	public Vector2 getPos() {
		return pos;
	}

	public float getX() {
		return pos.x;
	}

	public float getY() {
		return pos.y;
	}

	public EntityType getType() {
		return type;
	}

	public boolean isGrounded() {
		return grounded;
	}

	public int getWidth() {
		return type.getWidth();
	}

	public int getHeight() {
		return type.getHeight();
	}

	public float getWeight() {
		return type.getWeight();
	}

	public boolean isTouched() {
		return touched;
	}

	public void setTouched(boolean touched) {
		this.touched = touched;
	}

	public void setDead(boolean set) {
		dead = set;
	}

	public boolean isDead() {
		return dead;
	}

	// public static void setPlayerhealth(double playerhealth) {
	// Entity.playerhealth = playerhealth;
	// }

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

	public boolean isToLadder() {
		return toLadder;
	}

	public void setToLadder(boolean toLadder) {
		this.toLadder = toLadder;
	}

	public boolean canBeLaddered() {
		return (map.doesRectCollideWithTile(getX(), getY(), getWidth(), getHeight(), TileType.LADDER) || map.doesRectCollideWithTile(getX() + getWidth(), getY(), getWidth(), getHeight(), TileType.LADDER) || map.doesRectCollideWithTile(getX(), getY() - 1, getWidth(), getHeight(), TileType.LADDER) || map.doesRectCollideWithTile(getX() + getWidth(), getY() - 1, getWidth(), getHeight(), TileType.LADDER));
	}

	public boolean isRight() {
		return right;
	}

	public void die() {
		if (startTime <= 0) {
			setDead(true);
		}
	}

	public void setStartTime(float startTime) {
		this.startTime = startTime;
	}

	protected float getStartTime() {
		return startTime;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
}
