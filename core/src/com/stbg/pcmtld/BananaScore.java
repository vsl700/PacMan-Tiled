package com.stbg.pcmtld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class BananaScore extends Score {

	@Override
	protected Texture getScoreTexture() {
		return new Texture(Gdx.files.internal("pacman/pacmanassets/pacman-banana.png"));
	}
	
	@Override
	public void onCollected() {
		map.getPlayerInstance().applyEffect(Effects.speedEffect(10));
	}
	
}
