package com.stbg.pcmtld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DestroyableBlock extends Entity {
	
	private TextureRegion aliveTexture;

    private static final int    FRAME_COLS = 2;     
    private static final int    FRAME_ROWS = 1;
    
    //Animation of the player

    
    
    private Texture dyingTexture;   

    private Animation           dieAnimation;      
    private TextureRegion[]         walkFrames2; 
    
    float stateTime;
    
    float deathTime;
    
	
	@Override
	public void create(EntitySnapshot snapshot, EntityType type, GameMap map){
		super.create(snapshot, type, map);
		
		deathTime = snapshot.getFloat("deathTime", 0.75f);
		
		Texture temp = new Texture(Gdx.files.local("levels/" + SettingReader.stage.getDir() + "/res/tiles.png"));
		aliveTexture = TextureRegion.split(temp, 32, 32)[0][0];
		
        //}
        
        //else if(left){
			dyingTexture = temp;
			
			
			TextureRegion[][] tmp2 = TextureRegion.split(dyingTexture, TileType.TILE_SIZE, TileType.TILE_SIZE);              // #10
	        walkFrames2 = new TextureRegion[FRAME_COLS * FRAME_ROWS];
	        int index2 = 0;
	        for (int i = 0; i < FRAME_ROWS; i++) {
	            for (int j = 0; j < FRAME_COLS; j++) {
	                walkFrames2[index2++] = tmp2[i][j];
	            }
	        }
	        dieAnimation = new Animation(0.1f, walkFrames2);
		
	}
	
	@Override
	protected void saveEntityData(EntitySnapshot snapshot) {
		super.saveEntityData(snapshot);
		
		snapshot.putFloat("deathTime", deathTime);
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
		if(isTouched()) batch.draw(dieAnimation.getKeyFrame(stateTime, true), pos.x, pos.y);
		else batch.draw(aliveTexture, pos.x, pos.y);
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		aliveTexture.getTexture().dispose();
		//walkSheet2.dispose();
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
