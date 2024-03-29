package com.stbg.pcmtld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Score extends Entity {

	Texture score;
	
	@Override
	public void create(EntitySnapshot snapshot, EntityType type, GameMap map){
		super.create(snapshot, type, map);
		
		score = getScoreTexture();
	}
	
	protected Texture getScoreTexture() {
		return new Texture(Gdx.files.internal("pacman/pacmanassets/pacman-score.png"));
	}
	
	@Override
	public void render(SpriteBatch batch, OrthographicCamera camera) {
		// TODO Auto-generated method stub
		
		//batch.setProjectionMatrix(camera.combined);
		batch.draw(score, getX(), getY(), getWidth(), getHeight());
	}
	
	public void onCollected(/* Entity entity */) {
		Player.setScore(Player.getScore() + 10);
	}

	@Override
	public EntitySnapshot getSaveSnapshot() {
		EntitySnapshot snapshot = super.getSaveSnapshot();
		//snapshot.putFloat("spawnRadius", spawnRadius);
		return snapshot;
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		score.dispose();
	}
	
	

}
