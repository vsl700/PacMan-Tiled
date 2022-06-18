package com.stbg.pcmtld;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CustomGameMap extends GameMap {

	String id;
	String name;
	int[][][] map;
	
	private TextureRegion[][] tiles;
	
	public CustomGameMap(/* int lvl */) {
		// TODO Auto-generated constructor stub
		CustomGameMapData data = CustomGameMapLoader.loadMap("basic", "pcm"/* + lvl */);
		this.id = data.id;
		this.name = data.name;
		this.map = data.map;
		
		entities.addAll(EntityLoader.loadEntitiesFromMap(this));
		entities.trimToSize();
		playerIndex = getPlayerIndex();
		
		
		tiles = TextureRegion.split(new Texture("levels/pri/res/tiles.png"), TileType.TILE_SIZE, TileType.TILE_SIZE);
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
        
	}

	@Override
	public TileType getTileTypeByCoordinate(int layer, int col, int row) {
		// TODO Auto-generated method stub
		if (col < 0 || col >= getWidth() || row < 0 || row >= getHeight())
		return null;
		
		return TileType.getTileTypeById(map[layer][row][col]);
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return map[0][0].length;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return map[0].length;
	}

	@Override
	public int getLayers() {
		// TODO Auto-generated method stub
		return map.length;
	}

	@Override
	public void render(OrthographicCamera camera, SpriteBatch batch) {
		// TODO Auto-generated method stub
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for(int layer = 0; layer < getLayers(); layer++){
			if(layer == 1) continue;
			for(int row = 0; row < getHeight(); row++){
				for(int col = 0; col < getWidth(); col++){
					TileType type = this.getTileTypeByCoordinate(layer, col, row);
					if(type != null)
						batch.draw(tiles[0][type.getId() - 1], col * TileType.TILE_SIZE, row * TileType.TILE_SIZE);
				}
			}
		}
		
		batch.end();
		
		super.render(camera, batch);
	}
	
	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		super.update(delta);
	}
	

	@Override
	public TileType getTileTypeByLocation(int layer, float x, float y) {
		// TODO Auto-generated method stub
		return this.getTileTypeByCoordinate(layer, (int) (x / TileType.TILE_SIZE), getHeight() - (int) (y / TileType.TILE_SIZE - 1));
	}

	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void reRead(int lvl) {
		// TODO Auto-generated method stub
		
	}

}
