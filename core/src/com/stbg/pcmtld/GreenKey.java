package com.stbg.pcmtld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GreenKey extends Entity {

	TextureRegion texture;

	@Override
	public void create(EntitySnapshot snapshot, EntityType type, GameMap map) {
		super.create(snapshot, type, map);
		
		Texture temp = new Texture(Gdx.files.local("levels/" + SettingReader.stage.getDir() + "/res/tiles.png"));
		texture = TextureRegion.split(temp, 32, 32)[0][6];
	}
	
	@Override
	public void render(SpriteBatch batch, OrthographicCamera camera) {
		batch.draw(texture, getX(), getY());
	}

	@Override
	public void dispose() {
		texture.getTexture().dispose();
	}

}
