package com.stbg.pcmtld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DestroyableBlock extends Entity {
	
	public static Texture walkSheet;

    private static final int    FRAME_COLS = 2;     
    private static final int    FRAME_ROWS = 1;
    
    //Animation of the player

    
    
    public static Texture walkSheet2;   

    static Animation           dieAnimation;      
    static TextureRegion[]         walkFrames2;     
    static TextureRegion           currentFrame2;
    
    float stateTime;
    
    float deathTime;
    
    static boolean isTouched;
    static boolean isDead;
    
	
	@Override
	public void create(EntitySnapshot snapshot, EntityType type, GameMap map){
		super.create(snapshot, type, map);
		
		deathTime = 0.75f;
		
		isTouched = false;
		isDead = false;
		
		walkSheet = new Texture(Gdx.files.internal("pacman/pacmanassets/pacman-block.png"));
		
        //}
        
        //else if(left){
			walkSheet2 = new Texture(Gdx.files.internal("pacman/pacmanassets/pacman-block-dying.png"));
			
			
			TextureRegion[][] tmp2 = TextureRegion.split(walkSheet2, walkSheet2.getWidth()/FRAME_COLS, walkSheet2.getHeight()/FRAME_ROWS);              // #10
	        walkFrames2 = new TextureRegion[FRAME_COLS * FRAME_ROWS];
	        int index2 = 0;
	        for (int i = 0; i < FRAME_ROWS; i++) {
	            for (int j = 0; j < FRAME_COLS; j++) {
	                walkFrames2[index2++] = tmp2[i][j];
	            }
	        }
	        dieAnimation = new Animation(0.1f, walkFrames2);
		
	}
	
	public void update(float deltaTime , float gravity){
		super.update(deltaTime, gravity);
		
		if(isTouched()) 
			deathTime -= 0.015;
		
		//System.out.println(deathTime);
		
		if(deathTime <= 0){ 
			setDead(true);
			//System.out.println("Dead Block");
		}
		
		stateTime += deltaTime;
	}

	@Override
	public void render(SpriteBatch batch, OrthographicCamera camera) {
		
		//batch.setProjectionMatrix(camera.combined);
		if(isTouched()) batch.draw(dieAnimation.getKeyFrame(stateTime, true), pos.x, pos.y, getWidth(), getHeight());
		else batch.draw(walkSheet, pos.x, pos.y, getWidth(), getHeight());
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		walkSheet.dispose();
		walkSheet2.dispose();
	}
	
	@Override
	public EntitySnapshot getSaveSnapshot() {
		EntitySnapshot snapshot = super.getSaveSnapshot();
		//snapshot.putFloat("spawnRadius", spawnRadius);
		return snapshot;
	}

	//public static boolean isBlockDead() {
		//return isDead;
	//}

	//public static void setDead(boolean isDead) {
		//DestroyableBlock.isDead = isDead;
	//}

	
	
	

}
