package com.stbg.pcmtld;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stbg.pcmtld.Effects.Effect;

public class Player extends Entity {

	LinkedList<Effect> effects;
	 
	boolean fall = false;
	boolean movingRight;
	boolean movingLeft;
	
	//static boolean hurt = false;
	static boolean deadPlayer = false;
	static boolean playerGrounded = false;
	//boolean right = true;
	//boolean left = false;
	
	private static final int JUMP_VELOCITY = 5;
	
	public static float score = 0;
	public static float time = 101;
	
	public boolean invisible = false;
	
	float spawnTime;
	//public float health;
	//public float playerX = x;
	//public float playerY = y;
	
	Texture walkSheet;

    private static final int    FRAME_COLS = 2;     
    private static final int    FRAME_ROWS = 1;   
    
    //Animation of the player

    Animation           walkAnimation;      
    TextureRegion[]         walkFrames;     
    TextureRegion           currentFrame;
    
    Texture walkSheet2;   

    Animation           walkAnimation2;      
    TextureRegion[]         walkFrames2;     
    TextureRegion           currentFrame2;
    
    private static final int    FRAME_COLS2 = 11;     
    private static final int    FRAME_ROWS2 = 1;
    
    Texture walkSheet3;   

    Animation           dieAnimation;      
    TextureRegion[]         walkFrames3;     
    TextureRegion           currentFrame3;
    
    Texture walkSheet4;   

    Animation           hurtAnimation;      
    TextureRegion[]         walkFrames4;     
    TextureRegion           currentFrame4;
    
    Texture walkSheet5;   

    Animation           hurtAnimation2;      
    TextureRegion[]         walkFrames5;     
    TextureRegion           currentFrame5;
	
    float stateTime;
    
	
	@Override
	public void create(EntitySnapshot snapshot, EntityType type, GameMap map) {
		super.create(snapshot, type, map);
		spawnTime = 7;
		deadPlayer = false;
		movingRight = false;
		movingLeft = false;
		score = 0;
		time = 101;
		SPEED = 125;
		
		startTime = 1.15f;
		//health = 50;
		
		effects = new LinkedList<Effects.Effect>();
		
		//if(right){
		walkSheet = new Texture(Gdx.files.internal("pacman/pacmanassets/pacman-right.png"));
		
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
			walkSheet2 = new Texture(Gdx.files.internal("pacman/pacmanassets/pacman-left.png"));
			
			
			TextureRegion[][] tmp2 = TextureRegion.split(walkSheet2, walkSheet2.getWidth()/FRAME_COLS, walkSheet2.getHeight()/FRAME_ROWS);              // #10
	        walkFrames2 = new TextureRegion[FRAME_COLS * FRAME_ROWS];
	        int index2 = 0;
	        for (int i = 0; i < FRAME_ROWS; i++) {
	            for (int j = 0; j < FRAME_COLS; j++) {
	                walkFrames2[index2++] = tmp2[i][j];
	            }
	        }
	        walkAnimation2 = new Animation(0.1f, walkFrames2);
	        
	        walkSheet3 = new Texture(Gdx.files.internal("pacman/pacmanassets/pacman-die.png"));
			
			
			TextureRegion[][] tmp3 = TextureRegion.split(walkSheet3, walkSheet3.getWidth()/FRAME_COLS2, walkSheet3.getHeight()/FRAME_ROWS2);              // #10
	        walkFrames3 = new TextureRegion[FRAME_COLS2 * FRAME_ROWS2];
	        int index3 = 0;
	        for (int i = 0; i < FRAME_ROWS2; i++) {
	            for (int j = 0; j < FRAME_COLS2; j++) {
	                walkFrames3[index3++] = tmp3[i][j];
	            }
	        }
	        dieAnimation = new Animation(0.1f, walkFrames3);
	        
	        walkSheet4 = new Texture(Gdx.files.internal("pacman/pacmanassets/pacman-hurt-right.png"));
			
			
			TextureRegion[][] tmp4 = TextureRegion.split(walkSheet4, walkSheet4.getWidth()/FRAME_COLS, walkSheet4.getHeight()/FRAME_ROWS);              // #10
	        walkFrames4 = new TextureRegion[FRAME_COLS * FRAME_ROWS];
	        int index4 = 0;
	        for (int i = 0; i < FRAME_ROWS; i++) {
	            for (int j = 0; j < FRAME_COLS; j++) {
	                walkFrames4[index4++] = tmp4[i][j];
	            }
	        }
	        hurtAnimation = new Animation(0.1f, walkFrames4);
	        
	        walkSheet5 = new Texture(Gdx.files.internal("pacman/pacmanassets/pacman-hurt-left.png"));
			
			
			TextureRegion[][] tmp5 = TextureRegion.split(walkSheet5, walkSheet5.getWidth()/FRAME_COLS, walkSheet5.getHeight()/FRAME_ROWS);              // #10
	        walkFrames5 = new TextureRegion[FRAME_COLS * FRAME_ROWS];
	        int index5 = 0;
	        for (int i = 0; i < FRAME_ROWS; i++) {
	            for (int j = 0; j < FRAME_COLS; j++) {
	                walkFrames5[index5++] = tmp5[i][j];
	            }
	        }
	        hurtAnimation2 = new Animation(0.1f, walkFrames5);
        //}
	}
	
	public void update(float deltaTime , float gravity){
		for(int i = 0; i < effects.size(); i++) {
			Effect e = effects.get(i);
			if(!e.update(this, deltaTime))
				effects.remove(e);
		}
		
		if((Gdx.input.isKeyPressed(Keys.UP) || jump) && grounded && !isToLadder())
			this.velocityY += JUMP_VELOCITY * getWeight();
		else if ((Gdx.input.isKeyPressed(Keys.UP) || jump) && !grounded && this.velocityY > 0 && !isToLadder())
			this.velocityY += JUMP_VELOCITY * getWeight() * deltaTime;
		else if(isToLadder() && canBeLaddered()){
			float ladderVelocity = JUMP_VELOCITY * getWeight();
			if((Gdx.input.isKeyPressed(Keys.UP) || up) && (map.doesRectCollideWithTile(getX(), getY() + ladderVelocity * deltaTime, getWidth(), getHeight(), TileType.LADDER) || map.doesRectCollideWithTile(getX() + getWidth(), getY() + ladderVelocity * deltaTime, getWidth(), getHeight(), TileType.LADDER))) this.velocityY = ladderVelocity;
			else if((Gdx.input.isKeyPressed(Keys.DOWN) || down) && (map.doesRectCollideWithTile(getX(), getY() - ladderVelocity * deltaTime, getWidth(), getHeight(), TileType.LADDER) || map.doesRectCollideWithTile(getX() + getWidth(), getY() - ladderVelocity * deltaTime, getWidth(), getHeight(), TileType.LADDER))) this.velocityY = -ladderVelocity;
			else this.velocityY = 0;
		}
		
		
		ladder = canBeLaddered() && Gdx.input.isKeyJustPressed(Keys.SPACE);
		
		door = Gdx.input.isKeyJustPressed(Keys.E) || Gdx.input.isKeyJustPressed(Keys.CONTROL_LEFT);

		if(Gdx.input.isKeyPressed(Keys.LEFT)) moveLeft = true;
		else if(Gdx.input.isKeyPressed(Keys.RIGHT)) moveRight = true;
		
		super.update(deltaTime, gravity);
		
		if (map.doesEntityCollideWithEntity(getX(), getY(), getWidth(), getHeight(), getStartTime()) && !invisible) {
			if (getHealth() - 1 < 1)
				die();
			else {
				setHealth(getHealth() - 1);
				setStartTime(1.15f);
			}

		}
		
		//movingRight = false;
		//movingLeft = false;
		
		
		/*if(Gdx.app.getType() == ApplicationType.Desktop){
		if(Gdx.input.isKeyPressed(Keys.LEFT) || moveLeft){
			//left = true;
			//right = false;
			if(isToLadder()){
				if(map.doesRectCollideWithTile(getX() + getWidth() - 2, getY(), getWidth(), getHeight(), TileType.LADDER) || map.doesRectCollideWithTile(getX() - 2, getY(), getWidth(), getHeight(), TileType.LADDER)){
					//movingLeft = true;
					right = false;
					moveX(-SPEED * deltaTime);
				}else setToLadder(false);
			}else{
				//movingLeft = true;
				right = false;
				moveX(-SPEED * deltaTime);
			}
		}
		
		if(Gdx.input.isKeyPressed(Keys.RIGHT) || moveRight){
			//right = true;
			//left = false;
			if(isToLadder()){
				if(map.doesRectCollideWithTile(getX() + 1, getY(), getWidth(), getHeight(), TileType.LADDER) || map.doesRectCollideWithTile(getX() + getWidth() + 1, getY(), getWidth(), getHeight(), TileType.LADDER)){
					//movingRight = true;
					right = true;
					moveX(SPEED * deltaTime);
				}else setToLadder(false);
			}else{
				//movingRight = true;
				right = true;
				moveX(SPEED * deltaTime);
			}
		}
		
		}else if (Gdx.app.getType() == ApplicationType.Android){
			
		}*/
		stateTime += deltaTime;;
		
		if(time > 0)
			time-= deltaTime;
		else if(time <= 0) {
			time = 0;
			die();
		}
		
		playerGrounded = grounded;
		
		//Screen3.setPlayerX(x);
		//Screen3.setPlayerY(y);
		
		//setPlayerX(pos.x);
		//setPlayerY(pos.y);
		
		
		if(getStartTime() > 0){
			setStartTime(getStartTime() - deltaTime);
			
			if(invisible)
				stateTime+= deltaTime * 2; // Increase animation speed
		}
		//else spawnTime = 7;
		//if(playerhealth <= 1)
		//setDead(true);
		
		setPlayerDead(this.isDead());
		
		if(getHealth() < 0) setPlayerDead(true);
		
		//if(entityCollision(x, y, 0, 0)){
			//setDead(true);
		//}
		
		//System.out.println(stateTime);
		
		//System.out.println(x);
		
		//System.out.println("Right:" + right);
		//System.out.println("Left:" + left);
		
		//spawnRadius = snapshot.getFloat("spawnRadius", 50);
		
	}

	@Override
	public void render(SpriteBatch batch, OrthographicCamera camera) {
		
		//batch.setProjectionMatrix(camera.combined);
		//if(right)
		
		
		if(getStartTime() > 0){
			//invisible = true;
			if(right)
				batch.draw(hurtAnimation.getKeyFrame(stateTime, true), pos.x, pos.y, getWidth(), getHeight());
			else batch.draw(hurtAnimation2.getKeyFrame(stateTime, true), pos.x, pos.y, getWidth(), getHeight());
			//setStartTime(-0.005f);
			//if(spawnTime <= 0){
				//invisible = false;
				//hurt(false);
			//}
		}//else if(Gdx.input.isKeyPressed(Keys.RIGHT) || right2){
			//right2 = true;
		else{
			Color c = batch.getColor();
			if(invisible)
				batch.setColor(c.r, c.g, c.b, 0.4f);
			
			if(right)
				batch.draw(walkAnimation.getKeyFrame(stateTime, true), pos.x, pos.y, getWidth(), getHeight());
			else batch.draw(walkAnimation2.getKeyFrame(stateTime, true), pos.x, pos.y, getWidth(), getHeight());
			

			batch.setColor(c);
		}
		//}else if(Gdx.input.isKeyPressed(Keys.LEFT) || right2 == false){
			//right2 = false;
			//batch.draw(walkAnimation2.getKeyFrame(stateTime, true), pos.x, pos.y, getWidth(), getHeight());
		//}
		//System.out.println(spawnTime);
		//System.out.println(isHurt());
		//System.out.println(this.velocityY);
		
			//camera.translate(0, this.velocityY /60);
		//if(Gdx.input.isKeyPressed(Keys.UP) && grounded)
		//else if (Gdx.input.isKeyPressed(Keys.UP) && !grounded && this.velocityY > 0)
			
		
	}
	
	public void shoot() {
		if(startTime > 0)
			return;
		
		if(right)
			map.shootBullet(getX() + getWidth() - 7, getY() + getHeight() / 2 - 5, 1, 0, this);
		else
			map.shootBullet(getX(), getY() + getHeight() / 2 - 5, -1, 0, this);
	}
	
	public void applyEffect(Effect effect) {
		effect.onStart(this);
		
		effects.add(effect);
	}
	
	@Override
	public void dispose(){
		walkSheet.dispose();
		walkSheet2.dispose();
	}
	
	@Override
	public EntitySnapshot getSaveSnapshot() {
		EntitySnapshot snapshot = super.getSaveSnapshot();
		//snapshot.putFloat("spawnRadius", spawnRadius);
		return snapshot;
	}
	
	public static float getScore() {
		return score;
	}

	public static void setScore(float score) {
		Player.score = score;
	}

	public static float getTime() {
		return time;
	}

	public static void setTime(float time) {
		Player.time = time;
	}
	
	public static boolean isPlayerDead(){
		return deadPlayer;
	}
	
	public static void setPlayerDead(boolean dead){
		deadPlayer = dead;
	}
	
	public static boolean isPlayerGrounded(){
		return playerGrounded;
	}
	
	//public void reset(){
		//pos.x = 0;
		//pos.y = 0;
	//}

}
