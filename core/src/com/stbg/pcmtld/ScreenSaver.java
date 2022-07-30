package com.stbg.pcmtld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScreenSaver implements Screen {
	
	MyGdxGame game;
	
	SpriteBatch batch;
	OrthographicCamera cam;
	Texture title;
	
	float x, y, width, height;
	
	boolean right, up;
	
	public ScreenSaver(MyGdxGame mg) {
		game = mg;
		
		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		title = new Texture(Gdx.files.internal("pacman/pacmanassets/pacman-title-png.png"));
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		width = game.calculateX(title.getWidth());
		height = game.calculateY(title.getHeight());
		
		x = game.calculateX((1280 - title.getWidth()) / 2);
		y = game.calculateY(600);
		
		up = true;
		right = false;
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		batch.draw(title, x, y, width, height);
		batch.end();
		
		float speed;
		if(Gdx.graphics.getFramesPerSecond() != 0) speed = (float) 80 / Gdx.graphics.getFramesPerSecond();
		else speed = 1.2f;
		
		if(up) y+= speed;
		else y-= speed;
		
		if(right) x+= speed;
		else x-= speed;
		
		if(x <= 0) {
			right = true;
		}
		else if(x + width >= cam.viewportWidth) {
			right = false;
		}
		
		if(y <= 0) {
			up = true;
		}
		else if(y + height >= cam.viewportHeight) {
			up = false;
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		cam.setToOrtho(false, width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
