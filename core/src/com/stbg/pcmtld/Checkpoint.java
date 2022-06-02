package com.stbg.pcmtld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Checkpoint extends Entity {
	
	Texture notChecked, checked;
	
	boolean check;
	
	@Override
	public void create(EntitySnapshot snapshot, EntityType type, GameMap map) {
		super.create(snapshot, type, map);
		
		notChecked = new Texture(Gdx.files.internal("pacman/pacmanassets/pacman-checkp-notreached.png"));
		checked = new Texture(Gdx.files.internal("pacman/pacmanassets/pacman-checkp-reached.png"));
		
		check = false;
	}
	
	@Override
	public void render(SpriteBatch batch, OrthographicCamera camera) {
		if(check)
			batch.draw(checked, getX(), getY());
		else batch.draw(notChecked, getX(), getY());
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		notChecked.dispose();
		checked.dispose();
	}

}
