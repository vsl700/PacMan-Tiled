package com.stbg.pcmtld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Bullet {

	private Vector2 pos;
	private Vector2 dir;
	private TextureRegion texture;
	private Entity shooterEntity;
	
	private static final float SPEED = 450;
	
	
	public Bullet(float spawnX, float spawnY, float dirX, float dirY, Entity shooterEntity) {
		this.shooterEntity = shooterEntity;
		pos = new Vector2(spawnX, spawnY);
		dir = new Vector2(dirX, dirY);
		
		Texture temp = new Texture(Gdx.files.local("levels/" + SettingReader.stage.getDir() + "/res/tiles.png"));
		texture = TextureRegion.split(temp, 32, 32)[0][3];
	}
	
	public void update(float deltaTime) {
		pos.add(dir.cpy().scl(deltaTime * SPEED));
	}
	
	public void render(SpriteBatch batch, OrthographicCamera camera) {
		batch.draw(texture, getX() - 16, getY() - 12);
	}
	
	public void dispose() {
		texture.getTexture().dispose();
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
