package com.stbg.pcmtld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;;

public class TiledGameMap extends GameMap {
	
	Texture background;
	
	TiledMap tiledMap;
	OrthogonalTiledMapRenderer tiledMapRenderer;
	public boolean ready = false;
	
	public TiledGameMap(LevelGroups stage, int lvl) {
		// TODO Auto-generated constructor stub
		if(Gdx.files.local("levels/" + stage.getDir() + "/res/" + "bg.png").exists())
			background = new Texture(Gdx.files.local("levels/" + stage.getDir() + "/res/" + "bg.png"));
		
		ready = false;
		tiledMap = new TmxMapLoader().load("levels/" + stage.getDir() + "/" + lvl + ".tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		entities.addAll(EntityLoader.loadEntitiesFromMap(this));
		entities.trimToSize();
		loadDoors();
		ready = true;
		playerIndex = getPlayerIndex();
	}
	
	@Override
	protected void loadDoors() {
		for(int layer = 0; layer < getLayers(); layer++) {
			Vector2 firstDoor = null;
			for(int row = 0; row < getHeight(); row++) {
				boolean flag = false;
				for(int col = 0; col < getWidth(); col++) {
					TileType tile = getTileTypeByCoordinate(layer, col, row);
					if(tile!= null && tile.equals(TileType.DOOR)) {
						if(firstDoor == null) {
							firstDoor = new Vector2(col * TileType.TILE_SIZE, row * TileType.TILE_SIZE);
						}
						else {
							Vector2 secondDoor = new Vector2(col * TileType.TILE_SIZE, row * TileType.TILE_SIZE);
							doors.put(firstDoor, secondDoor);
							
							flag = true;
						}
					}
				}
				
				if(flag)
					break;
			}
		}
	}

	@Override
	public void render(OrthographicCamera camera, SpriteBatch batch){
		if(background != null) {
			//batch.setProjectionMatrix(camera.combined);
			batch.begin();
			batch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
			batch.end();
		}
		
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		
		//batch.setProjectionMatrix(camera.combined);
		//batch.begin();
		super.render(camera, batch);
		//batch.end();
	}
	
	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		super.update(delta);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		tiledMap.dispose();
		tiledMapRenderer.dispose();
	}

	@Override
	public TileType getTileTypeByCoordinate(int layer, int col, int row) {
		// TODO Auto-generated method stub
		Cell cell = ((TiledMapTileLayer) tiledMap.getLayers().get(layer)).getCell(col, row);
		
		if(cell != null){
			TiledMapTile tile = cell.getTile();
			
			if(tile != null){
				int id = tile.getId();
				return TileType.getTileTypeById(id);
			}
			
		}
		
		return null;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getWidth();
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getHeight();
	}

	@Override
	public int getLayers() {
		// TODO Auto-generated method stub
		return tiledMap.getLayers().getCount();
		//return 2;
	}

	public boolean isReady() {
		return ready;
	}

	@Override
	public TileType getTileTypeByLocation(int layer, float x, float y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reRead(int lvl) {
		// TODO Auto-generated method stub
		tiledMap = new TmxMapLoader().load("levels/" + lvl + ".tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		entities.clear();
		entities.addAll(EntityLoader.loadEntitiesFromMap(this));
		entities.trimToSize();
	}

}
