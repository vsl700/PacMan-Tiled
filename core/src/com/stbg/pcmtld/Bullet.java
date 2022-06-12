package com.stbg.pcmtld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bullet {

	private Vector2 pos;
	private Vector2 dir;
	private Texture texture;
	private Entity shooterEntity;
	
	private static final float SPEED = 450;
	
	
	public Bullet(float spawnX, float spawnY, float dirX, float dirY, Entity shooterEntity) {
		this.shooterEntity = shooterEntity;
		pos = new Vector2(spawnX, spawnY);
		dir = new Vector2(dirX, dirY);
		
		texture = new Texture(Gdx.files.internal("pacman/pacmanassets/pacman-bullet.png"));
	}
	
	public void update(float deltaTime) {
		pos.add(dir.cpy().scl(deltaTime * SPEED));
	}
	
	public void render(SpriteBatch batch, OrthographicCamera camera) {
		batch.draw(texture, getX(), getY());
	}
	
	public void dispose() {
		texture.dispose();
	}
	
	public Entity getShooterEntity() {
		return shooterEntity;
	}
	
	public float getX() {
		return pos.x;
	}

	public float getY() {
		return pos.y;
	}
	
	public int getWidth() {
		return 8;
	}
	
	public int getHeight() {
		return 8;
	}
	
}
