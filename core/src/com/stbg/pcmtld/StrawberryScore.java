package com.stbg.pcmtld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class StrawberryScore extends Score {

	@Override
	protected Texture getScoreTexture() {
		return new Texture(Gdx.files.internal("pacman/pacmanassets/pacman-strawberry.png"));
	}
	
	@Override
	public void onCollected() {
		map.getPlayerInstance().applyEffect(Effects.invisibilityEffect(10));
	}
	
}
