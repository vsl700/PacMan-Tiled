package com.stbg.pcmtld;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Screen1 implements Screen {
	MyGdxGame game;

	ShapeRenderer shapes;
	SpriteBatch batch;
	Texture texture;
	BitmapFont font;
	OrthographicCamera cam;
	// Rectangle play;
	// Rectangle quit;

	Button play, quit;

	//float titleX, titleY, titleW, titleH;

	public static boolean endless = false;

	public Screen1(MyGdxGame mg) {
		game = mg;

		shapes = new ShapeRenderer();
		batch = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("pacman/pacmanassets/pacman-title-png.png"));
		font = new BitmapFont();

		cam = new OrthographicCamera();
		// cam.setToOrtho(false, Gdx.graphics.getWidth(),
		// Gdx.graphics.getHeight());
		// cam.update();

		/*
		 * play = new Rectangle(); play.x = (cam.viewportWidth -
		 * font.getSpaceWidth()) / 2 - 100; play.y = 400 - 39; play.width =
		 * font.getSpaceWidth() * 11; play.height = font.getLineHeight();
		 * 
		 * quit = new Rectangle(); quit.x = (cam.viewportWidth -
		 * font.getSpaceWidth()) / 2 - 50; quit.y = 300 - 39; quit.width =
		 * font.getSpaceWidth() * 4; quit.height = font.getLineHeight();
		 * 
		 * touchPos = new Vector3();
		 */

		play = new Button("Play Levels", font, batch, shapes, cam, true, 0, Keys.NUM_1);
		quit = new Button("Quit", font, batch, shapes, cam, true, 0, Keys.NUM_3);

	}

	@Override
	public void render(float delta) {
		// System.out.println("using game class variable var1:"+game.var1);
		// game.setScreen(game.scn2); //calling screen 1
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		cam.update();

		// System.out.println(Gdx.input.getX() + "; " + Gdx.input.getY());

		shapes.setProjectionMatrix(cam.combined);
		shapes.begin(ShapeRenderer.ShapeType.Filled);
		shapes.rect(0, 0, cam.viewportWidth, cam.viewportHeight, Color.BLUE, Color.BLUE, Color.MAGENTA, Color.MAGENTA);
		shapes.end();

		game.mainMenuMap.update(delta);
		game.mainMenuMap.render(game.customMapCam, game.batch);
		
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		batch.draw(texture, game.calculateX((1280 - texture.getWidth()) / 2), game.calculateY(600), game.calculateX(texture.getWidth()), game.calculateY(texture.getHeight()));
		play.render();
		quit.render();
		batch.end();

		if (play.justTouched()) {
			game.setScreen(game.scn2);
		} else if (quit.justTouched()) {
			if(Gdx.app.getType() == ApplicationType.Android)
				System.exit(0);
			else Gdx.app.exit();
		}
		/*
		 * else if(Gdx.input.isKeyJustPressed(Keys.NUM_2)){ endless = true;
		 * game.setScreen(game.scn3); }
		 */

	}

	@Override
	public void resize(int width, int height) {
		cam.setToOrtho(false, width, height);

		// System.out.println(width + ", " + height + "; " + cam.viewportWidth +
		// ", " + cam.viewportHeight);

		font.getData().setScale(width / 430);

		play.setLocation(game.calculateX((1280 - font.getSpaceWidth()) / 2 - 100), game.calculateY(361));

		quit.setLocation(game.calculateX((1280 - font.getSpaceWidth()) / 2 - 50), game.calculateY(261));

		/*titleX = game.calculateX((1280 - texture.getWidth()) / 2);
		titleY = game.calculateY(600);
		titleW = game.calculateX(texture.getWidth());
		titleH = game.calculateY(texture.getHeight());*/
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		batch.dispose();
		shapes.dispose();
		font.dispose();
		texture.dispose();
	}
}
