package com.stbg.pcmtld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class HeartScore extends Score {

	@Override
	protected Texture getScoreTexture() {
		return new Texture(Gdx.files.internal("pacman/pacmanassets/pacman-heart.png"));
	}
	
	@Override
	public void onCollected() {
		map.getPlayerInstance().health++;
	}
	
}
