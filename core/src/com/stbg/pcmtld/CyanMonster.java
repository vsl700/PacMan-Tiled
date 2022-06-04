package com.stbg.pcmtld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CyanMonster extends Entity {

	
	Texture walkSheet;

    private static final int    FRAME_COLS = 2;     
    private static final int    FRAME_ROWS = 1;     

    Animation           walkAnimation;      
    TextureRegion[]         walkFrames;     
    TextureRegion           currentFrame;
    
    Texture walkSheet2;   

    Animation           walkAnimation2;      
    TextureRegion[]         walkFrames2;     
    TextureRegion           currentFrame2;
	
    float stateTime;
	
	@Override
	public void create(EntitySnapshot snapshot, EntityType type, GameMap map){
		super.create(snapshot, type, map);
		
		//pos = new Vector2(snapshot.getX(), snapshot.getY());
		
		SPEED = 125;
		
		walkSheet = new Texture(Gdx.files.internal("pacman/pacmanassets/pacman-monster-cyan-right.png"));
		
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);              // #10
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new Animation(0.1f, walkFrames);
        //}
        
        //else if(left){
			walkSheet2 = new Texture(Gdx.files.internal("pacman/pacmanassets/pacman-monster-cyan-left.png"));
			
			
			TextureRegion[][] tmp2 = TextureRegion.split(walkSheet2, walkSheet2.getWidth()/FRAME_COLS, walkSheet2.getHeight()/FRAME_ROWS);              // #10
	        walkFrames2 = new TextureRegion[FRAME_COLS * FRAME_ROWS];
	        int index2 = 0;
	        for (int i = 0; i < FRAME_ROWS; i++) {
	            for (int j = 0; j < FRAME_COLS; j++) {
	                walkFrames2[index2++] = tmp2[i][j];
	            }
	        }
	        walkAnimation2 = new Animation(0.1f, walkFrames2);
	        
	}
	
	public void update(float deltaTime , float gravity){
		super.update(deltaTime, gravity);
		
		if(right){
			//System.out.println("Right");
			moveX(SPEED * deltaTime);
			}
		else{
			//System.out.println("Left");
			moveX(-SPEED * deltaTime);
		}
		
		stateTime += deltaTime;
	}
	
	@Override
	public void render(SpriteBatch batch, OrthographicCamera camera) {
		
		//batch.setProjectionMatrix(camera.combined);
		if(right)
		batch.draw(walkAnimation.getKeyFrame(stateTime, true), pos.x, pos.y, getWidth(), getHeight());
		else
			batch.draw(walkAnimation2.getKeyFrame(stateTime, true), pos.x, pos.y, getWidth(), getHeight());
	}
	
	@Override
	public EntitySnapshot getSaveSnapshot() {
		EntitySnapshot snapshot = super.getSaveSnapshot();
		//snapshot.putFloat("spawnRadius", spawnRadius);
		return snapshot;
	}

	public void dispose() {
		// TODO Auto-generated method stub
		walkSheet.dispose();
		walkSheet2.dispose();
	}

}
