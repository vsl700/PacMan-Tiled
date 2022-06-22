package com.stbg.pcmtld;

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

//Screen2 class
public class Screen2 implements Screen {
	MyGdxGame game;

	ShapeRenderer shapes;
	SpriteBatch batch;
	Texture texture;
	Texture lock;
	Texture unlock;
	Texture lvlclear;
	BitmapFont font;
	BitmapFont font2;
	// BitmapFont font3;
	SettingReader sr;
	OrthographicCamera cam;

	// Vector3 touchPos;

	// Rectangle newGame;
	// Rectangle contGame;
	// Rectangle goBack;

	LevelGroups currentStage;
	Button newGame, contGame, goBack;
	Button nextGroup, prevGroup;

	int r = 200;

	/*float titleX, titleY, titleW, titleH;
	float wX, wY, hX, hY;*/

	public static int levels;
	public static boolean endless;
	public static int toplay;
	int completed;
	int locked;

	public Screen2(MyGdxGame mg) {
		game = mg;

		shapes = new ShapeRenderer();
		batch = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("pacman/pacmanassets/pacman-title-png.png"));
		lock = new Texture(Gdx.files.internal("pacman/pacmanassets/lvllock.png"));
		unlock = new Texture(Gdx.files.internal("pacman/pacmanassets/lvlunlock.png"));
		lvlclear = new Texture(Gdx.files.internal("pacman/pacmanassets/lvlclear.png"));
		font = new BitmapFont();
		//font.getData().setScale(3);
		font2 = new BitmapFont();
		//font2.getData().setScale(2);
		// font3 = new BitmapFont();
		// font3.getData().setScale(1);
		cam = new OrthographicCamera();
		cam.update();

		sr = new SettingReader();
		levels = sr.getLevelsAmount(SettingReader.stage);
		toplay = sr.setting;
		endless = SettingReader.endless;
		completed = toplay - 1;
		locked = levels - toplay;

		// touchPos = new Vector3();

		// newGame = new Rectangle();
		// newGame.x = 552.5f;
		// newGame.y = 500 - 39;
		// newGame.width = 752.4f - 552.5f;
		// newGame.height = font.getLineHeight();
		//
		// contGame = new Rectangle();
		// contGame.x = 528.5f;
		// contGame.y = 450 - 39;
		// contGame.width = 706.4f - 528.5f;
		// contGame.height = font.getLineHeight();
		//
		// goBack = new Rectangle();
		// goBack.x = 512.5f;
		// goBack.y = 150 - 39;
		// goBack.width = 710.4f - 512.5f;
		// goBack.height = font.getLineHeight();
		// sr = new SettingReader();

		newGame = new Button("New Game", font, batch, shapes, cam, true, 0, Keys.NUM_1);
		contGame = new Button("Continue", font, batch, shapes, cam, true, 0, Keys.NUM_2);
		goBack = new Button("Go Back", font, batch, shapes, cam, true, 0, Keys.B);
		
		nextGroup = new Button("--next--", font, batch, shapes, cam, true, 0, Keys.NUM_3);
		prevGroup = new Button("--prev--", font, batch, shapes, cam, true, 0, Keys.NUM_4);

		// toplay = SettingReader.setting;
	}

	@SuppressWarnings("static-access")
	@Override
	public void render(float delta) {
		// System.out.println("using game class variable var2:"+game.var2);
		// game.setScreen(game.scn1); //calling back screen 1
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		cam.update();

		shapes.setProjectionMatrix(cam.combined);
		shapes.begin(ShapeRenderer.ShapeType.Filled);
		shapes.rect(0, 0, cam.viewportWidth, cam.viewportHeight, Color.BLUE, Color.BLUE, Color.MAGENTA, Color.MAGENTA);
		shapes.end();
		/*
		 * shapes.begin(ShapeRenderer.ShapeType.Line);
		 * shapes.setColor(Color.WHITE); shapes.rect(180, 180, 930, 300);
		 * shapes.end();
		 */

		game.mainMenuMap.update(delta);
		game.mainMenuMap.render(game.customMapCam, game.batch);
		
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		batch.draw(texture, game.calculateX((1280 - texture.getWidth()) / 2), game.calculateY(600), game.calculateX(texture.getWidth()), game.calculateY(texture.getHeight()));
		if (sr.noLevels)
			// font.draw(batch, "New Game", (cam.viewportWidth -
			// font.getSpaceWidth()) / 2 - 100, 500);
			font.draw(batch, "Opps! We Forgot The Levels!", game.calculateX((1280 - font.getSpaceWidth()) / 2 - 100), game.calculateY(480));

		else {
			newGame.render();

			if (toplay > 1 || sr.score > 0) {
				// font.draw(batch, "Continue", (cam.viewportWidth -
				// font.getSpaceWidth()) / 2 - 100, 450);
				contGame.render();
				// font.getData().setScale(2);
				font2.draw(batch, "(Level " + toplay + ", Score: " + (int) sr.score + ")", game.calculateX((1280 - font.getSpaceWidth()) / 2 + 85), contGame.y + font2.getLineHeight());
			} // else if(endless == true){
				// font.draw(batch, "2. Continue", (1280 - font.getSpaceWidth())
				// / 2 - 100, 450);
				// font.getData().setScale(2);
				// font2.draw(batch, "(Endless Level)", (1280 -
				// font.getSpaceWidth()) / 2 + 125, 440);
				// }
			
			if(currentStage.getNext() != null && sr.getLevel(currentStage.getNext()) > 0)
				nextGroup.render();
			
			if(currentStage.getPrev() != null)
				prevGroup.render();
		}
		// font.draw(batch, "Go Back", (cam.viewportWidth -
		// font.getSpaceWidth()) / 2 - 100, 150);
		goBack.render();
		// font3.draw(batch, "if the game doesn't show the cont. button, restart
		// it!", (1280 - font.getSpaceWidth()) / 2 + 320, 15);
		batch.end();

		if (goBack.justTouched()) {
			game.setScreen(game.scn1);
		} else if (newGame.justTouched() && !sr.noLevels) {
			SettingReader.stage = currentStage;
			
			sr.writer(currentStage.getDir(), 1, 0);
			toplay = 1;
			// System.out.println(toplay);
			endless = false;
			Screen1.endless = false;
			game.setScreen(game.scn3);
		} else if (contGame.justTouched() && !sr.noLevels) {
			SettingReader.stage = currentStage;
			if (toplay > 1 || sr.score > 0) {
				Screen1.endless = false;
				game.setScreen(game.scn3);
			} // else if(toplay > 10 && toplay > 1){

			// }
		}else if(nextGroup.justTouched()) { 
			currentStage = currentStage.getNext();
			setLevelDataTexts();
		}else if(prevGroup.justTouched()) { 
			currentStage = currentStage.getPrev();
			setLevelDataTexts();
		}else if (Gdx.input.isKeyJustPressed(Keys.T)) {
			SettingReader.stage = currentStage;
			toplay = 0;
			game.setScreen(game.scn3);
		}

		// sr.writer(1);

		/*
		 * if(Gdx.input.justTouched()){ game.setScreen(game.scn1); }
		 */

	}

	public static void setToplay(int tp) {
		if (toplay > levels)
			toplay = 1;
		else
			toplay = tp;
	}

	public static int getToplay() {
		return toplay;
	}

	@Override
	public void resize(int width, int height) {
		cam.setToOrtho(false, width, height);

		font.getData().setScale(width / 430);
		font2.getData().setScale(width / 430 - 1);

		/*wX = game.calculateX((1280 - font.getSpaceWidth()) / 2 - 100);
		wY = game.calculateY(480);

		hX = game.calculateX((1280 - font.getSpaceWidth()) / 2 + 85);
		hY = game.calculateY(440);*/

		// System.out.println(hY);

		newGame.setLocation(game.calculateX((1280 - font.getSpaceWidth()) / 2 - 100), game.calculateY(480));
		//newGame.resize();

		contGame.setLocation(game.calculateX((1280 - font.getSpaceWidth()) / 2 - 100), game.calculateY(420));
		//contGame.resize();

		goBack.setLocation(game.calculateX((1280 - font.getSpaceWidth()) / 2 - 100), game.calculateY(130));
		//goBack.resize();
		
		nextGroup.setLocation(newGame.x, game.calculateY(330));
		prevGroup.setLocation(newGame.x, game.calculateY(270));

		/*titleX = game.calculateX((1280 - texture.getWidth()) / 2);
		titleY = game.calculateY(600);
		titleW = game.calculateX(texture.getWidth());
		titleH = game.calculateY(texture.getHeight());*/
	}

	@Override
	public void show() {
		// sr = new SettingReader();
		// levels = sr.getLevelsAmount();
		// toplay = sr.setting;
		// endless = SettingReader.endless;
		// completed = toplay - 1;
		// locked = levels - toplay;
		// System.out.println(sr.getLevelsAmount());
		// toplay = SettingReader.setting;
		currentStage = sr.getStage();
		toplay = sr.getLevel(currentStage);
		
		setLevelDataTexts();
	}
	
	private void setLevelDataTexts() {
		if(currentStage.getNext() != null && sr.getLevel(currentStage.getNext()) > 0)
			nextGroup.setText(currentStage.getNext().getName());
		
		if(currentStage.getPrev() != null)
			prevGroup.setText(currentStage.getPrev().getName());
		
		levels = sr.getLevelsAmount(currentStage);
		toplay = sr.getLevel(currentStage);
		sr.getScore(currentStage);
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
		toplay = sr.getLevel(sr.getStage());
	}

	@Override
	public void dispose() {
		batch.dispose();
		shapes.dispose();
		font.dispose();
		texture.dispose();
	}
}