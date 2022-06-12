package com.stbg.pcmtld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class BigScore extends Score {
	
	@Override
	protected Texture getScoreTexture() {
		return new Texture(Gdx.files.internal("pacman/pacmanassets/pacman-bigscore.png"));
	}
	
	@Override
	public void onCollected() {
		Player.setScore(Player.getScore() + 50);
		//map.getPlayerInstance().applyEffect(Effects.speedEffect(10));
	}
	
}
