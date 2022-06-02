package com.stbg.pcmtld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class PauseScreen implements Screen {

	MyGdxGame game;
	
	SpriteBatch batch;
	ShapeRenderer shapes;
	OrthographicCamera camera;
	BitmapFont font;
	Rectangle cont;
	Rectangle retry;
	Rectangle quit;
	
	Vector3 touchPos;
	//Screen3 scn3;
	
	public PauseScreen(MyGdxGame mg){
		game=mg;
		
		batch = new SpriteBatch();
		shapes = new ShapeRenderer();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(3);
		
		touchPos = new Vector3();
		
		cont = new Rectangle();
		cont.x = (1280 - 13*font.getSpaceWidth() - 885) / 2;
		cont.y = 720 - 220;
		cont.width = font.getSpaceWidth() * 8;
		cont.height = font.getLineHeight();
		
		retry = new Rectangle();
		retry.x = 1280 - 8*font.getSpaceWidth() - 656;
		retry.y = cont.y;
		retry.width = font.getSpaceWidth() * 5;
		retry.height = font.getLineHeight();
		
		quit = new Rectangle();
		quit.x = 1280 - 7*font.getSpaceWidth() - 305;
		quit.y = retry.y;
		quit.width = font.getSpaceWidth() * 4;
		quit.height = font.getLineHeight();
		
		//scn3 = new Screen3(game);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		//Gdx.gl.glClearColor(0, 0, 0, 0.5f);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		

		shapes.begin(ShapeRenderer.ShapeType.Filled);
		shapes.setProjectionMatrix(camera.combined);
		shapes.setColor(0, 0, 0, 1);
		shapes.rect(0, 720 - 50 - 37, 1280, 42);
		shapes.rect(0, 720 - 245, 1280, 28);
		shapes.end();
		
		
		batch.begin();
		font.draw(batch, "Paused", (1280 - font.getScaleX() - 150)/2, 720 - 50);
		//if(Gdx.graphics.getWidth() == 1280)
		font.getData().setScale(2);
		font.draw(batch, "Continue", (1280 - 13*font.getSpaceWidth() - 885) / 2, 720 - 220);
		font.draw(batch, "Quit", 1280 - 7*font.getSpaceWidth() - 305, 720 - 220);
		font.draw(batch, "Retry", 1280 - 8*font.getSpaceWidth() - 656, 720 - 220);
		
		font.getData().setScale(3);	
		//else{
			//font.draw(batch, "Esc. Continue", 370, 90);
		//}
		//font.draw(batch, "Q. Quit", MyGdxGame.WIDTH - 250, 90);
		batch.end();
		
		//System.out.println(Gdx.graphics.getWidth());
		
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE) || (Gdx.input.justTouched() && cont.contains(touchPos.x, touchPos.y))){
			
			game.setScreen(game.scn3);
		}else if(Gdx.input.isKeyJustPressed(Keys.R) || (Gdx.input.justTouched() && retry.contains(touchPos.x, touchPos.y))){
			//Screen3.crnt = false;
			Screen3.cont = false;
			game.setScreen(game.scn3);
		}else if(Gdx.input.isKeyJustPressed(Keys.Q) || (Gdx.input.justTouched() && quit.contains(touchPos.x, touchPos.y))){
			//Screen3.crnt = false;
			Screen3.cont = false;
			game.setScreen(game.scn1);
		}
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		camera.setToOrtho(false, 1280, 720);
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
