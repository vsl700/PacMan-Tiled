package com.stbg.pcmtld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class CherryScore extends Score {

	@Override
	protected Texture getScoreTexture() {
		return new Texture(Gdx.files.internal("pacman/pacmanassets/pacman-cherry.png"));
	}
	
	@Override
	public void onCollected() {
		
	}
	
}
