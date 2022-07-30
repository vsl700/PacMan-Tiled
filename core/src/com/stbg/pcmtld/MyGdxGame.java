package com.stbg.pcmtld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game {

	public static final int WIDTH = 860;
	public static final int HEIGHT = 480;
	public static final String version = "1.4";

	// public String var1="i will use it in screen1";
	// public String var2="i will use it in screen2";

	OrthographicCamera cam, customMapCam;

	public Screen1 scn1; // instantiating screen 1 (menu)
	public Screen2 scn2; // instantiating screen 2 (level select)
	public Screen3 scn3;
	// public PauseScreen pause;
	public FinishScreen finish;
	public GameoverScreen gameover;
	public ScreenSaver scnS;

	long timeout = 0;
	int mouseX, mouseY;
	
	Screen temp;

	//BitmapFont font;
	SpriteBatch batch;
	
	CustomGameMap mainMenuMap;
	

	public void create() {
		cam = new OrthographicCamera();		
		customMapCam = new OrthographicCamera();
		
		scn1 = new Screen1(this);
		scnS = new ScreenSaver(this);
		setScreen(scn1);// scn1 object will be associated with same instance of
						// this class
		scn2 = new Screen2(this); // scn2 object will be associated with same
									// instance of this class

		scn3 = new Screen3(this);
		// pause = new PauseScreen(this);
		finish = new FinishScreen(this);
		gameover = new GameoverScreen(this);

		//font = new BitmapFont();
		//font.setColor(Color.WHITE);
		//font.getData().setScale(1);

		batch = new SpriteBatch();
		
		timeout = System.currentTimeMillis();
		
		mouseX = Gdx.input.getX();
		mouseY = Gdx.input.getY();
		
		mainMenuMap = new CustomGameMap();
		mainMenuMap.xCamOffset = 100;
		mainMenuMap.yCamOffset = 100;
	}

	public void render() {
		if(getScreen() != scn3 && getScreen() != scnS && System.currentTimeMillis() - timeout >= 300000) {
			temp = getScreen();
			setScreen(scnS);
		}
		
		super.render(); // important!
		
		cam.update();
		customMapCam.update();
		
		/*batch.begin();
		// batch.setProjectionMatrix(cam.combined);
		if (Gdx.app.getType() == ApplicationType.Desktop)
			font.draw(batch, "PAC-MAN v" + version, 0, 13);
		else if (Gdx.app.getType() == ApplicationType.Android)
			font.draw(batch, "PAC-MAN Mobile Version", 0, 13);

		font.draw(batch, Gdx.graphics.getFramesPerSecond() + " fps", 0, 26);
		batch.end();*/
		
		if (Gdx.input.isKeyJustPressed(Keys.F11)) {
			if (Gdx.graphics.isFullscreen())
				Gdx.graphics.setWindowedMode(860, 480);
			else
				Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

			getScreen().resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.ANY_KEY) || mouseX != Gdx.input.getX() || mouseY != Gdx.input.getY()) {
			timeout = System.currentTimeMillis();
			if(getScreen().equals(scnS))
				setScreen(temp);
		}
		
		mouseX = Gdx.input.getX();
		mouseY = Gdx.input.getY();
	}

	public void dispose() {
		// dispose
		scn1.dispose();
		scn2.dispose();
		scn3.dispose();
		// pause.dispose();
		finish.dispose();
		gameover.dispose();
		
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {
		cam.setToOrtho(false, width, height);
		customMapCam.setToOrtho(false, width, height);
		if(width >= 1000 && height >= 640)
			customMapCam.zoom = 0.75f;
		else customMapCam.zoom = 1f;
		
		getScreen().resize(width, height);
	}

	public float calculateX(float oldX) {
		return oldX * cam.viewportWidth / 1280;
	}

	public float calculateY(float oldY) {
		return oldY * cam.viewportHeight / 720;
	}

	public float calculateX(float oldX, float oldW) {
		return oldX * cam.viewportWidth / oldW;
	}

	public float calculateY(float oldY, float oldH) {
		return oldY * cam.viewportHeight / oldH;
	}

}
