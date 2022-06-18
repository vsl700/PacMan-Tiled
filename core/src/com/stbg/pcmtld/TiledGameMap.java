package com.stbg.pcmtld;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;;

public class TiledGameMap extends GameMap {
	
	TiledMap tiledMap;
	OrthogonalTiledMapRenderer tiledMapRenderer;
	public boolean ready = false;
	
	public TiledGameMap(LevelGroups stage, int lvl) {
		// TODO Auto-generated constructor stub
		ready = false;
		tiledMap = new TmxMapLoader().load("levels/" + stage.getDir() + "/" + lvl + ".tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		entities.addAll(EntityLoader.loadEntitiesFromMap(this));
		entities.trimToSize();
		ready = true;
		playerIndex = getPlayerIndex();
	}

	@Override
	public void render(OrthographicCamera camera, SpriteBatch batch){
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
