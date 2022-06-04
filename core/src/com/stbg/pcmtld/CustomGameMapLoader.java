package com.stbg.pcmtld;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class CustomGameMapLoader {

	private static Json json = new Json();
	private static final int WIDTH = 50, HEIGHT = 300;
	
	public static CustomGameMapData loadMap(String id, String name){
		Gdx.files.local("maps/").file().mkdirs();
		FileHandle file = Gdx.files.local("maps/" + id + ".map");
		if(file.exists()){
			CustomGameMapData data = json.fromJson(CustomGameMapData.class, file.readString());
			return data;
		} else {
			CustomGameMapData data = generateRandomMap(id, name);
			
			return data;
		}
	}
	
	public static CustomGameMapData generateRandomMap(String id, String name){
		
		CustomGameMapData mapData = new CustomGameMapData();
		mapData.id = id;
		mapData.name = name;
		mapData.map = new int[3][HEIGHT][WIDTH];
		
		Random random = new Random();
		
		int col = 3, row = 0, startRow = row, startCol = col;
		int[][][] tempMap = new int[mapData.map.length][HEIGHT][WIDTH];
		tempMap[0][0][0] = TileType.BLOCK.getId();
		tempMap[1][1][0] = TileType.PLAYERSTILE.getId();
		
		boolean right = true;
		while(row < HEIGHT - 12) {
			if(col + 10 >= WIDTH) {
				int end = row + random.nextInt(7) + 4;
				tempMap[0][row][col] = TileType.BLOCK.getId();
				while(row < end) {
					tempMap[2][++row][col] = TileType.LADDER.getId();
				}
				tempMap[0][row][col] = TileType.BLOCK.getId();
				tempMap[0][row][--col] = TileType.BLOCK.getId();
				tempMap[0][row][--col] = TileType.BLOCK.getId();
				
				for(int layer = 0; layer < mapData.map.length; layer++) {
					for(int r = startRow; r < HEIGHT; r++) {
						for(int c = 0; c < WIDTH; c++) {
							if(right) {
								mapData.map[layer][r][c] = tempMap[layer][r][c];
							}
							else {
								mapData.map[layer][r][c] = tempMap[layer][r][WIDTH - c - 1];
							}
						}
					}
				}
				
				startRow = ++row;
				startCol = col = WIDTH - col + 1;
				right = !right;
				tempMap = new int[mapData.map.length][HEIGHT][WIDTH];
				
				continue;
			}
			
			int choice = random.nextInt(4);
			switch (choice) {
			case 0:
				tempMap[1][row + 1][col] = TileType.SCORE.getId();

				tempMap[0][row][col] = TileType.BLOCK.getId();
				col++;

				break;
			case 1:
				int longestHole = 3;
				if (col >= longestHole && tempMap[0][row][col - longestHole] == 0) {
					tempMap[0][row][col] = TileType.BLOCK.getId();
					col++;
				} else
					col += 2;

				break;
			case 2:
				if (row < HEIGHT - 10) {
					int highestSpace = 3;
					if (row >= highestSpace && tempMap[0][row - highestSpace][col] == 0) {
						tempMap[0][row][col] = TileType.BLOCK.getId();
						row++;
						col++;
					} else
						row++;
				}
				break;
			case 3:
				int enemyId;
				int[] enemyIds = new int[] { TileType.REDSTILE.getId(), TileType.ORANGESTILE.getId(),
						TileType.CYANSTILE.getId(), TileType.PINKSTILE.getId() };
				enemyId = enemyIds[random.nextInt(4)];

				if (col < WIDTH && col > 0 && isRangeOfTiles(row, col - 1, 4, TileType.BLOCK.getId(), tempMap)) {
					tempMap[1][row + 1][col - 1] = enemyId;
					col++;
				} else if (random.nextBoolean() && col + 10 < WIDTH) {
					if (!isRangeOfTiles(row, col - 1, 3, TileType.BLOCK.getId(), tempMap))
						col++;

					// tempMap[0][row + 1][col] = TileType.BLOCK.getId();
					tempMap[0][row][col] = TileType.BLOCK.getId();
					tempMap[1][row + 1][++col] = enemyId;
					int end = col + random.nextInt(2) + 3;
					while (col < end) {
						tempMap[0][row][col++] = TileType.BLOCK.getId();
					}
					// tempMap[0][row + 1][col] = TileType.BLOCK.getId();
					tempMap[0][row][col] = TileType.BLOCK.getId();

					col += 3;
				}

				break;
			}
			
		}
		
		if(right)
			mapData.map[0][startRow][startCol - 2] = TileType.FINISH.getId();
		else
			mapData.map[0][startRow][WIDTH - startCol + 1] = TileType.FINISH.getId();
		
		/*Array<TileGroup> tileGroups = new Array<TileGroup>();
		tileGroups.add(new TileGroup(0, 0, 0));
		
		//for(int row = 0; row < 1; row++){
			for(int col = tileGroups.peek().getWidth() + 3; col < SIZE - 6; col+= tileGroups.peek().getWidth() + 3 + random.nextInt(4)){
				int row = random.nextInt(2);
				if(random.nextBoolean()) {
					tileGroups.add(new TileGroup(col, row, random.nextInt(TileGroup.getPatternsAmount() - 1) + 1));
				}else {
					int[][] pattern = new int[1][random.nextInt(9) + 3];
					for(int i = 0; i < pattern[0].length; i++) {
						pattern[0][i] = 1;
					}
					
					tileGroups.add(new TileGroup(col, row, pattern));
				}
			}
		//}
		
		
		for(TileGroup tileGroup : tileGroups) {
			for(int i = 0; i < tileGroup.getHeight(); i++) {
				for(int j = 0; j < tileGroup.getWidth(); j++) {
					int tile = tileGroup.pattern[i][j];
					if(tile == TileType.BLOCK.getId() || tile == TileType.LADDER.getId())
						mapData.map[0][i + tileGroup.y][j + tileGroup.x] = tile;
					else mapData.map[1][i + tileGroup.y][j + tileGroup.x] = tile;
				}
			}
		}*/
		
		
		/*for(int row = 0; row < SIZE; row++){
			for(int col = 0; col < SIZE; col++){
				//mapData.map[0][row][col] = TileType.BLOCK.getId();
				
				if(col == SIZE){
					mapData.map[0][row][col] = TileType.FINISH.getId();
				}
				
				int object = random.nextInt(5);
				
				for(int i = 0; i < random.nextInt(15) + 1; i++){
					if(object == 0){
						mapData.map[0][row][col] = TileType.BLOCK.getId();
					} else if(object == 1){
						
					} else if(object == 2){
						
					} else if(object == 3){
						
					} else if(object == 4){
						
					} else if(object == 5){
						
					}
				}
				//mapData.map[1][row][col] = 
				
			}
		}*/
		
		return mapData;
		
	}
	
	/*private static int changeCol(int col, int changeBy, boolean right) {
		if(right)
			return col+= changeBy;
		
		return col-= changeBy;
	}*/
	
	public static boolean isRangeOfTiles(int row, int col, int minLength, int tile, int[][][] map) {
		for(int i = col; i >= 0 && col - i < minLength; i--) {
			if(map[0][row][i] != tile)
				return false;
		}
		
		return true;
	}
	
	/*private boolean collidesWithTileGroup(int x, int y, Array<TileGroup> tileGroups, TileGroup tileGroup) {
		for(TileGroup group : tileGroups) {
			
		}
	}*/
	
	/*private static class TileGroup{
		public int x;
		public int y;
		public int[][] pattern;
		
		public static int[][][] patterns = {{{1, 1, 1, 1, 1}, {1, 16, 0, 0, 0}, {1, 0, 0, 0, 0}}, {{1, 1, 1, 1, 1}, {1, 0, 0, 0, 1}}, {{1, 1, 1, 1, 1, 1, 1}, {1, 0, 0, 0, 0, 0, 1}}, {{1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 0, 12, 0, 0, 0, 0, 0, 1}}};
		
		public TileGroup(int x, int y, int patternIndex) {
			this.x = x;
			this.y = y;
			pattern = patterns[patternIndex];
		}
		
		public TileGroup(int x, int y, int[][] pattern) {
			this.x = x;
			this.y = y;
			this.pattern = pattern;
		}
		
		public TileGroup xFlip() {
			for(int i = 0; i < pattern.length; i++) {
				for(int j = 0; j < pattern[i].length / 2; j++) {
					int temp = pattern[i][j];
					pattern[i][j] = pattern[i][pattern[i].length - j - 1];
					pattern[i][pattern[i].length - j - 1] = temp;
				}
			}
			
			return this;
		}
		
		public int getWidth() {
			int max = pattern[0].length;
			for(int i = 1; i < pattern.length; i++) {
				if(pattern[i].length > max)
					max = pattern[i].length;
			}
			
			return max;
		}
		
		public int getHeight() {
			return pattern.length;
		}
		
		public static int getPatternsAmount() {
			return patterns.length;
		}
	}*/
	
}
