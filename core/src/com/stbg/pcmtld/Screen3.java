package com.stbg.pcmtld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Screen3 implements Screen {

	MyGdxGame game;

	public static int lvl = 0;
	boolean endless;
	boolean endless2;
	public static boolean cont = false;
	// public static boolean firstCreated;
	boolean firstopened = false;
	//public static boolean crnt = false;
	boolean debugmode = false;
	boolean paused;

	BitmapFont font, font2;
	// BitmapFont font2;
	SpriteBatch batch;
	Texture pause;
	Texture reset;
	Texture quit;

	OrthographicCamera cam;
	OrthographicCamera cam2;

	ShapeRenderer shapes;

	Rectangle leftArrow;
	Rectangle rightArrow;
	Rectangle jump;
	Rectangle ladder;
	Rectangle upArrow;
	Rectangle downArrow;

	Rectangle pauseBtn;
	Rectangle resetBtn;
	Rectangle quitBtn;

	GameMap gameMap;

	// static Player player;

	SettingReader set;

	Button contBtn, retryBtn, resetBtn1, quitBtn1;

	static float playerX;
	static float playerY;

	float deltaX, deltaY;

	int btnAmount = 5;

	boolean[] justTouched = new boolean[btnAmount];

	public Screen3(MyGdxGame mg) {
		game = mg;

		//gameMap = new TiledGameMap(lvl);
		// firstCreated = true;

		cam = new OrthographicCamera();
		cam2 = new OrthographicCamera();

		endless = Screen2.endless;
		endless2 = Screen1.endless;
		// cont = false;

		font = new BitmapFont();
		font2 = new BitmapFont();
		// font.setColor(Color.WHITE);
		if (Gdx.app.getType() == ApplicationType.Android)
			font.getData().setScale(2);
		else
			font.getData().setScale(1);
		// font2 = new BitmapFont();
		// font2.setColor(Color.WHITE);
		// font2.getData().setScale(1);

		batch = new SpriteBatch();

		pause = new Texture(Gdx.files.internal("pacman/pacmanassets/pause.png"));
		reset = new Texture(Gdx.files.internal("pacman/pacmanassets/retry.png"));
		quit = new Texture(Gdx.files.internal("pacman/pacmanassets/quit.png"));

		shapes = new ShapeRenderer();

		contBtn = new Button("Continue", font2, batch, shapes, cam, true, 0, Keys.ESCAPE);
		contBtn.setMarkColor(Color.DARK_GRAY);
		retryBtn = new Button("Restart Level", font2, batch, shapes, cam, true, 0, Keys.R);
		retryBtn.setMarkColor(Color.DARK_GRAY);
		
		quitBtn1 = new Button("Quit", font2, batch, shapes, cam, true, 0, Keys.Q);
		quitBtn1.setMarkColor(Color.DARK_GRAY);

		leftArrow = new Rectangle();
		leftArrow.width = 75;
		leftArrow.height = leftArrow.width;
		leftArrow.x = 90 - leftArrow.width / 2;
		leftArrow.y = 210 - leftArrow.width / 2;

		rightArrow = new Rectangle();
		rightArrow.width = 75;
		rightArrow.height = rightArrow.width;
		rightArrow.x = 210 - rightArrow.width / 2;
		rightArrow.y = 210 - rightArrow.width / 2;

		jump = new Rectangle();
		jump.width = 95;
		jump.height = jump.width;
		jump.x = 750 - jump.width / 2;
		jump.y = 210 - jump.width / 2;

		ladder = new Rectangle();
		ladder.width = 75;
		ladder.height = ladder.width;
		ladder.x = jump.x - 125 / 2 - ladder.width / 2;
		ladder.y = jump.y + jump.height / 2 + 20;

		downArrow = new Rectangle();
		downArrow.width = 95;
		downArrow.height = downArrow.width;
		downArrow.x = jump.x;
		downArrow.y = jump.y - downArrow.height - 20;

		pauseBtn = new Rectangle();
		pauseBtn.width = 50;
		pauseBtn.height = pauseBtn.width;
		pauseBtn.x = 860 - 50;
		pauseBtn.y = 480 - 50;

		resetBtn = new Rectangle();
		resetBtn.width = 27 * 2;
		resetBtn.height = resetBtn.width;
		resetBtn.x = 860 - 150;
		resetBtn.y = 480 - 50;

		quitBtn = new Rectangle();
		quitBtn.width = 50;
		quitBtn.height = quitBtn.width;
		quitBtn.x = 860 - 100;
		quitBtn.y = 480 - 50;
		// if(endless2)

		// else if(endless2 == false) gameMap = new TiledGameMap();
		// player = new Player();

		// System.out.println("Monitor width: " +
		// Gdx.graphics.getMonitor().virtualX + ", Monitor height: " +
		// Gdx.graphics.getMonitor().virtualY);

	}

	@Override
	public void show() {

		/*if(cont) gameMap.loadCheck();
		else {*/
			lvl = Screen2.toplay;
			gameMap = new TiledGameMap(SettingReader.stage, lvl);
			gameMap.xCamOffset = 200;
			gameMap.yCamOffset = -0;

			// cam.setToOrtho(false, 860, 480);

			// cam2.setToOrtho(false, 860, 480);
		//}

		paused = false;
		gameMap.setDead(false);
		//crnt = true;
		//cont = false;
		firstopened = true;
	}

	@Override
	public void render(float delta) {
		// System.out.println("Updating..");
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		cam.update();
		cam2.update();

		// System.out.println("Monitor width: " +
		// Gdx.graphics.getMonitor().virtualX + ", Monitor height: " +
		// Gdx.graphics.getMonitor().virtualY);

		// cam2.translate(0, 0, 1.5f);

		// System.out.println(delta);
		// System.out.println(player.playerX);
		shapes.begin(ShapeRenderer.ShapeType.Filled);
		shapes.setProjectionMatrix(cam.combined);
		shapes.rect(0, 0, cam.viewportWidth, cam.viewportHeight, SettingReader.stage.getDownColor(), SettingReader.stage.getDownColor(), SettingReader.stage.getUpColor(), SettingReader.stage.getUpColor());
		shapes.end();

		if (!paused)
			gameMap.update(delta);

		gameMap.render(cam2, batch);

		if (Gdx.app.getType() == ApplicationType.Android) {
			shapes.begin(ShapeRenderer.ShapeType.Filled);
			shapes.setProjectionMatrix(cam.combined);
			shapes.setColor(Color.GRAY);
			shapes.circle(rightArrow.x + rightArrow.width / 2, rightArrow.y + rightArrow.width / 2, rightArrow.width / 2);
			shapes.circle(leftArrow.x + leftArrow.width / 2, leftArrow.y + leftArrow.width / 2, leftArrow.width / 2);
			shapes.circle(jump.x + jump.width / 2, jump.y + jump.width / 2, jump.width / 2);

			if (gameMap.playerCanBeLaddered || gameMap.playerToLadder) {
				shapes.circle(ladder.x + ladder.width / 2, ladder.y + ladder.height / 2, ladder.width / 2);
				for (int i = 0; i < btnAmount; i++) {
					Vector3 touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
					cam.unproject(touchPos);

					boolean t = !justTouched[i] && Gdx.input.isTouched(i) && ladder.contains(touchPos.x, touchPos.y);
					gameMap.setLadder(t);
					justTouched[i] = Gdx.input.isTouched(i);

					if (t)
						break;
				}
			}

			if (gameMap.playerToLadder) {
				shapes.circle(downArrow.x + downArrow.width / 2, downArrow.y + downArrow.height / 2, downArrow.width / 2);
				for (int i = 0; i < btnAmount; i++) {
					Vector3 touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
					cam.unproject(touchPos);

					gameMap.setDown(Gdx.input.isTouched(i) && downArrow.contains(touchPos.x, touchPos.y));
					if (gameMap.isDown())
						break;
				}

			}
			for (int i = 0; i < btnAmount; i++) {
				Vector3 touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
				cam.unproject(touchPos);

				gameMap.setMoveLeft(Gdx.input.isTouched(i) && leftArrow.contains(touchPos.x, touchPos.y));
				if (gameMap.isMoveLeft())
					break;
			}

			for (int i = 0; i < btnAmount; i++) {
				Vector3 touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
				cam.unproject(touchPos);

				gameMap.setMoveRight(Gdx.input.isTouched(i) && rightArrow.contains(touchPos.x, touchPos.y));
				if (gameMap.isMoveRight())
					break;
			}

			for (int i = 0; i < btnAmount; i++) {
				Vector3 touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
				cam.unproject(touchPos);

				gameMap.setJump(Gdx.input.isTouched(i) && jump.contains(touchPos.x, touchPos.y) && !gameMap.playerToLadder);
				gameMap.setUp(Gdx.input.isTouched(i) && jump.contains(touchPos.x, touchPos.y) && gameMap.playerToLadder);
				if (gameMap.isJump() || gameMap.isUp())
					break;
			}

			shapes.end();
			// }

			// cam.lookAt(playerX, playerY, 0);

			// if(Gdx.app.getType() == ApplicationType.Android)

			// if(Player.score > 10 && Entity.playerhealth > 1)

			// System.out.println(Gdx.input.getX() + ", " + Gdx.input.getY());

			// System.out.println(cam.viewportWidth + ":" + cam.viewportHeight);

			if (Gdx.app.getType() == ApplicationType.Android) {
				batch.setProjectionMatrix(cam.combined);
				batch.begin();
				batch.draw(pause, cam.viewportWidth - 50, cam.viewportHeight - 50, 25 * 2, 25 * 2);
				batch.draw(quit, cam.viewportWidth - 50 * 2, cam.viewportHeight - 50, 25 * 2, 25 * 2);
				batch.draw(reset, cam.viewportWidth - 50 * 3, cam.viewportHeight - 50, 27 * 2, 27 * 2);

				Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
				cam.unproject(touchPos);
				// System.out.println(touchPos.x + ", " + touchPos.y + "; " +
				// Gdx.input.getX() + ", " + Gdx.input.getY());

				if (Gdx.input.justTouched() && pauseBtn.contains(touchPos.x, touchPos.y)) {
					cont = true;
					paused = true;
					//game.setScreen(game.pause);
				} else if (Gdx.input.justTouched() && quitBtn.contains(touchPos.x, touchPos.y)) {
					//crnt = false;
					if (Screen1.endless == true)
						game.setScreen(game.scn1);
					else if (endless == true || lvl <= 10) {
						game.setScreen(game.scn2);
					}
				} else if (Gdx.input.justTouched() && resetBtn.contains(touchPos.x, touchPos.y)) {
					//crnt = false;
					show();
				}

			}
			batch.end();
		}

		// The Debugging mode must be only on screen resolution 16:9!
		// Finish it!!
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		if (debugmode) {
			// int i = 15;
			font.draw(batch, "Debug Mode (BETA):", 0, cam.viewportHeight);
			font.draw(batch, "Game Info", 0, cam.viewportHeight - font.getLineHeight());
			font.draw(batch, "Lives: " + String.valueOf((int) GameMap.playerHealth), 5, cam.viewportHeight - font.getLineHeight() * 2);
			font.draw(batch, "Score: " + String.valueOf((int) Player.getScore()), 5, cam.viewportHeight - font.getLineHeight() * 3);
			font.draw(batch, "Time: " + String.valueOf((int) Player.getTime()), 5, cam.viewportHeight - font.getLineHeight() * 4);
			font.draw(batch, "Level: " + lvl, 5, cam.viewportHeight - font.getLineHeight() * 5);
			font.draw(batch, "Player Info:", 0, cam.viewportHeight - font.getLineHeight() * 6);
			font.draw(batch, "X: " + gameMap.playerX, 5, cam.viewportHeight - font.getLineHeight() * 7);
			font.draw(batch, "Y: " + gameMap.playerY, 5, cam.viewportHeight - font.getLineHeight() * 8);
			if (gameMap.playerDir)
				font.draw(batch, "Direction: Right", 5, cam.viewportHeight - font.getLineHeight() * 9);
			else
				font.draw(batch, "Direction: Left", 5, cam.viewportHeight - font.getLineHeight() * 9);
			font.draw(batch, "Grounded: " + gameMap.isPlayerGrounded(), 5, cam.viewportHeight - font.getLineHeight() * 10);
			// font.draw(batch, "X: " + Player.playerX2, 5, cam.viewportHeight -
			// 45);
			// font.draw(batch, "Y: ", 5, cam.viewportHeight - 50);

		} else
			font.draw(batch, "Lives: " + String.valueOf((int) GameMap.playerHealth) + ", Score: " + String.valueOf((int) Player.score) + ", Time: " + (int) Player.time, 0, cam.viewportHeight);

		//if (lvl == 0) {
			//font.draw(batch, "Test Mode", cam.viewportWidth - font.getSpaceWidth() - 63, cam.viewportHeight);
		//}

		// else if(Player.score <= 10){
		// font.draw(batch, "Lives: " + String.valueOf((int) Entity.playerhealth
		// + 1) + ", Score: " + String.valueOf((int) Player.score), 0,
		// Gdx.graphics.getHeight());
		// font2.draw(batch, ", Score: " + String.valueOf((int) Player.score),
		// 0, Gdx.graphics.getHeight());
		// }
		batch.end();

		if (paused) {
			shapes.begin(ShapeRenderer.ShapeType.Filled);
			shapes.setProjectionMatrix(cam.combined);
			shapes.setColor(0, 0, 0, 1);
			shapes.rect(0, game.calculateY(623), cam.viewportWidth, game.calculateY(58));
			shapes.rect(0, game.calculateY(465), cam.viewportWidth, game.calculateY(40));
			shapes.end();

			batch.begin();
			batch.setProjectionMatrix(cam.combined);
			
			font2.draw(batch, "Paused", game.calculateX((1280 - font.getScaleX() - 150)/2), game.calculateY(670));
			
			contBtn.render();
			retryBtn.render();
			quitBtn1.render();
			batch.end();
		}

		// System.out.println(set.getLevelsAmount());

		// Activate it
		/*if (Player.time <= 1) {
			GameMap.playerHealth = 0;
		}*/

		if (gameMap.isFinish()) {
			set = new SettingReader();
			set.writer(SettingReader.stage.getDir(), lvl + 1, SettingReader.score + (int) (Player.score + Player.getTime()) * (int) (GameMap.playerHealth));
			Screen2.setToplay(lvl + 1);
			game.setScreen(game.finish);
		}

		if (gameMap.isDead()) {
			if (!GameMap.checkPLoad)
				game.setScreen(game.gameover);
			else
				show();
			/*
			 * else{ Player.setStartTime(1.15f);
			 * Player.setPlayerhealth(Player.playerhealth - 1);
			 * Player.setPlayerDead(false); }
			 */
		}

		if (!paused) {
			if (Gdx.input.isKeyJustPressed(Keys.D))
				debugmode = !debugmode;

			if (Gdx.input.isKeyJustPressed(Keys.R)) {
				// game.setScreen(game.scn3);
				//crnt = false;
				if(gameMap.isCheckpointed()) {
					gameMap.loadCheck();
				}else {
					cont = false;
					gameMap.dispose();
					show();
				}
				// gameMap = new TiledGameMap(lvl);
			}
			/*
			 * if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){ //crnt = false;
			 * if(Screen1.endless == true) game.setScreen(game.scn1); else
			 * if(endless == true || lvl <= set.getLevelsAmount()){
			 * game.setScreen(game.scn2); } }
			 */
			if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
				// cont = true;
				// game.setScreen(game.pause);
				contBtn.holdOn();
				paused = true;
			}
		}

		if (contBtn.justTouched())
			paused = false;
		else if (retryBtn.justTouched()) {
			//crnt = false;
			cont = false;
			show();
		}
			
		else if (quitBtn1.justTouched()) {
			cont = false;
			game.setScreen(game.scn1);
		}

	}

	@Override
	public void resize(int width, int height) {
		cam.setToOrtho(false, width, height);

		font.getData().setScale(Math.max(width / 430 - 1, 1));
		font2.getData().setScale(width / 430);
		// font2.getData().setScale(width / 430 - 1);
		
		contBtn.setLocation(game.calculateX((1280 - 13*font.getSpaceWidth() - 885) / 2), game.calculateY(465));
		retryBtn.setLocation(game.calculateX(1280 - 8*font.getSpaceWidth() - 715), game.calculateY(465));
		quitBtn1.setLocation(game.calculateX(1280 - 7*font.getSpaceWidth() - 305), game.calculateY(465));

		// Vector3 temp = cam2.position.cpy();
		cam2.setToOrtho(false, width, height);
		if(width >= 1000 && height >= 640)
			cam2.zoom = 0.75f;
		else cam2.zoom = 1f;
		// cam2.position.x = temp.x;
		// cam2.position.y = temp.y;
		// cam2.position.z = temp.z;
	}

	@Override
	public void pause() {
		// cont = true;
		// game.setScreen(game.pause);
		paused = true;
	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {
		// if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))
		// player.reset();
		// if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))
		//crnt = false;
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		shapes.dispose();
		if (gameMap != null)
			gameMap.dispose();
	}
	
	public static void setCheck() {
		cont = true;
	}

	public static void setPlayerX(float playerX) {
		Screen3.playerX = playerX;
	}

	public static void setPlayerY(float playerY) {
		Screen3.playerY = playerY;
	}

}
