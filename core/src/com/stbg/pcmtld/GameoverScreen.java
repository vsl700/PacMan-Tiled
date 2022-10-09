package com.stbg.pcmtld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Input.Keys;

public class GameoverScreen implements Screen {

	MyGdxGame game;

	ShapeRenderer shapes;
	Texture texture;
	SpriteBatch batch;
	BitmapFont font;
	BitmapFont font2;
	OrthographicCamera cam;
	/*
	 * Rectangle playAgain; Rectangle quit; Vector3 touchPos;
	 */

	Button playAgain, resetLev, quit;

	static boolean dead;

	// static int playerhealth;
	// static int score;
	// static float playerX;
	// static float playerY;
	// static float redX[];
	// static float redY[];

	static float data[];
	
	/*float tX, tY, tW, tH;
	float gX, gY, gW, gH;
	float tmX, tmY, tmW, tmH;*/

	public GameoverScreen(MyGdxGame mg) {
		// TODO Auto-generated constructor stub
		game = mg;

		shapes = new ShapeRenderer();
		batch = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("pacman/pacmanassets/pacman-title-png.png"));
		font = new BitmapFont();
		//font.getData().setScale(4);

		font2 = new BitmapFont();
		//font2.getData().setScale(3);

		cam = new OrthographicCamera();
		cam.setToOrtho(false, 1280, 720);
		cam.update();

		playAgain = new Button("Try Again", font2, batch, shapes, cam, true, 0, Keys.NUM_1);
		resetLev = new Button("Reset Level", font2, batch, shapes, cam, true, 0, Keys.NUM_2);
		quit = new Button("Quit", font2, batch, shapes, cam, true, 0, Keys.NUM_3);

		/*
		 * playAgain = new Rectangle(); playAgain.x = 223.8f; playAgain.y = 200
		 * - 39; playAgain.width = 438.7f - 223.8f; playAgain.height =
		 * font2.getLineHeight();
		 * 
		 * quit = new Rectangle(); quit.x = 865.2f; quit.y = 200 - 39;
		 * quit.width = 955.2f - 865.2f; quit.height = font2.getLineHeight();
		 * 
		 * touchPos = new Vector3();
		 */

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		cam.update();

		shapes.setProjectionMatrix(cam.combined);
		shapes.begin(ShapeRenderer.ShapeType.Filled);
		shapes.rect(0, 0, cam.viewportWidth, cam.viewportHeight, Color.BLUE, Color.BLUE, Color.RED, Color.RED);
		shapes.end();

		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		batch.draw(texture, game.calculateX((1280 - texture.getWidth()) / 2), game.calculateY(600), game.calculateX(texture.getWidth()), game.calculateY(texture.getHeight()));
		/*
		 * if(dead == false){ font.draw(batch, "You've Been Hurt!", 425, 500);
		 * font2.draw(batch,
		 * "Fortunately, The Game Is Not Over! You Can Continue From The", 20,
		 * 400); font2.draw(batch, "Hurt Place With " + (int) data[0] +
		 * " Lives And " + (int) data[1] + " Points!", 250, 350);
		 * font2.draw(batch, "1. Retry", 250, 200); font2.draw(batch,
		 * "2. Reset", 550, 200); font2.draw(batch, "3. Quit", 870, 200); }else
		 * {
		 */
		font.draw(batch, "Game Over!", game.calculateX(480), game.calculateY(500));
		if (Player.time <= 1)
			font2.draw(batch, "Time's Up!", game.calculateX(533), game.calculateY(420));

		playAgain.render();
		if(GameMap.checkPLoad) resetLev.render();
		quit.render();
		/*
		 * font2.draw(batch, "Try Again", 240, 200); font2.draw(batch, "Quit",
		 * 870, 200);
		 */

		// }
		batch.end();
		/*
		 * if(dead == false){ if(Gdx.input.isKeyJustPressed(Keys.NUM_1)){
		 * Screen3.Continue(data); game.setScreen(game.scn3); }else
		 * if(Gdx.input.isKeyJustPressed(Keys.NUM_2)){
		 * game.setScreen(game.scn3); }else
		 * if(Gdx.input.isKeyJustPressed(Keys.NUM_3)){
		 * game.setScreen(game.scn1); } }else {
		 */
		if (playAgain.justTouched()) {
			game.setScreen(game.scn3);
		} else if (quit.justTouched()) {
			game.setScreen(game.scn1);
			// }
		}else if (resetLev.justTouched()) {
			Screen3.cont = false;
			game.setScreen(game.scn3);
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		cam.setToOrtho(false, width, height);

		font.getData().setScale(width / 430 + 1);
		font2.getData().setScale(width / 430);
		
		playAgain.setLocation(game.calculateX(240), game.calculateY(200));
		quit.setLocation(game.calculateX(870), game.calculateY(200));
		
		
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

	public static void setData(int playerhealth, int score, float playerX, float playerY, float redX2[], float redY2[]) {
		// GameoverScreen.playerhealth = playerhealth - 1;
		// GameoverScreen.score = score;
		// GameoverScreen.playerX = playerX;
		// GameoverScreen.playerY = playerY;
		// GameoverScreen.redX = redX2;
		// GameoverScreen.redY = redY2;

		/*
		 * if(playerhealth > 1){ dead = false;
		 * 
		 * GameoverScreen.data = new float[4 + redX2.length * 2];
		 * 
		 * GameoverScreen.data[0] = playerhealth - 1; GameoverScreen.data[1] =
		 * score; GameoverScreen.data[2] = playerX; GameoverScreen.data[3] =
		 * playerY;
		 * 
		 * for(int i = 4; i < 4 + redX2.length; i+= 2){ GameoverScreen.data[i] =
		 * redX2[i - 4]; GameoverScreen.data[i + 1] = redY2[i - 4]; } }else{
		 * dead = true; }
		 */
	}

	public static float[] getData() {
		return data;
	}

}
