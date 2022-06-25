package com.stbg.pcmtld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class FinishScreen implements Screen {

	MyGdxGame game;
	
	SpriteBatch batch;
	ShapeRenderer shapes;
	Texture texture;
	BitmapFont font;
	SettingReader set;
	OrthographicCamera cam;
	
	Button next, quit;
	
	/*Rectangle next;
	Rectangle quit;
	Vector3 touchPos;*/
	
	int newLevel;

	@SuppressWarnings("unused")
	private GlyphLayout draw;
	
	public FinishScreen(MyGdxGame mg) {
		// TODO Auto-generated constructor stub
		game=mg;
		
		shapes = new ShapeRenderer();
		batch = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("pacman/pacmanassets/pacman-title-png.png"));
		font = new BitmapFont();
		//font.getData().setScale(3);
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 1280, 720);
		cam.update();
		
		next = new Button("Next Level", font, batch, shapes, cam, true, 0, Keys.NUM_1);
		quit = new Button("Main Menu", font, batch, shapes, cam, true, 0, Keys.NUM_2);
		
		/*next = new Rectangle();
		next.x = 318.7f;
		next.y = 200 - 39;
		next.width = 530.5f - 318.7f;
		next.height = font.getLineHeight();
		
		quit = new Rectangle();
		quit.x = 678.5f;
		quit.y = 200 - 39;
		quit.width = 899.2f - 678.5f;
		quit.height = font.getLineHeight();
		
		touchPos = new Vector3();*/
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		set = new SettingReader();
		newLevel = set.setting;
		GameMap.checkPLoad = false;
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		cam.update();
		
		shapes.setProjectionMatrix(cam.combined);
		shapes.begin(ShapeRenderer.ShapeType.Filled);
		shapes.rect(0, 0, cam.viewportWidth, cam.viewportHeight, Color.BLUE, Color.BLUE, Color.LIME, Color.LIME);
		shapes.end();
		
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		batch.draw(texture, game.calculateX((1280 - texture.getWidth()) / 2), game.calculateY(600), game.calculateX(texture.getWidth()), game.calculateY(texture.getHeight()));
		font.draw(batch, "Level Finished!", game.calculateX((1280 - font.getSpaceWidth()) / 2 - 135), game.calculateY(550));
		font.draw(batch, "Score: " + (int) Player.getScore(), game.calculateX((1280 - font.getSpaceWidth()) / 2 - 135), game.calculateY(450));
		font.draw(batch, "Time: " + (int) Player.getTime(), game.calculateX((1280 - font.getSpaceWidth()) / 2 - 135), game.calculateY(400));
		font.draw(batch, "Lives: x" + (int) (GameMap.playerHealth), game.calculateX((1280 - font.getSpaceWidth()) / 2 - 135), game.calculateY(350));
		font.draw(batch, "Total Score: " + (((int) GameMap.playerHealth) * ((int) Player.getScore() + (int) Player.getTime())), game.calculateX((1280 - font.getSpaceWidth()) / 2 - 135), game.calculateY(250));
		//font.draw(batch, "2. Random Generated Level", (MyGdxGame.WIDTH - font.getSpaceWidth()) / 2 - 130, 300);
		//font.draw(batch, "Next Level", (1280 - font.getSpaceWidth()) / 2 - 310, 200);
		//font.draw(batch, "Main Menu", (1280 - font.getSpaceWidth()) / 2 + 50, 200);
		
		next.render();
		quit.render();
		batch.end();
		
		//System.out.println(newLevel);
		
		if(next.justTouched()){
			if(newLevel == 1)
				SettingReader.stage = SettingReader.stage.getNext();
			
			Screen2.setToplay(newLevel);
			//Screen3.lvl = newLevel;
			game.setScreen(game.scn3);
		}else if(quit.justTouched()){
			Screen2.setToplay(newLevel);
			game.setScreen(game.scn1);
		}

		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		cam.setToOrtho(false, width, height);
		
		font.getData().setScale(width / 430);
		
		next.setLocation(game.calculateX((1280 - font.getSpaceWidth()) / 2 - 310), game.calculateY(100));
		quit.setLocation(game.calculateX((1280 - font.getSpaceWidth()) / 2 + 50), game.calculateY(100));
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
