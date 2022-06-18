package com.stbg.pcmtld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;

public class SettingReader {
	
	//File file = new File("pcm.txt");
	public static LevelGroups stage;
	public int setting = 1;
	public static boolean endless = false;
	public static boolean noLevels = false;
	public static int score = 0;
	String text = "";
	Preferences prefs;
	int a;
	
	public SettingReader(){
		prefs = Gdx.app.getPreferences("pacman");
		
		getStage();
		getLevel(stage);
		getScore(stage);
		endless = prefs.getBoolean("endless");
		
		FileHandle file = Gdx.files.local("levels/" + stage.getDir() + "/" + setting + ".tmx");
		//FileHandle file4 = Gdx.files.local("entities/" + setting + ".json");
		FileHandle file2 = Gdx.files.local("levels/" + stage.getDir() + "/" + 1 + ".tmx");
		//FileHandle file3 = Gdx.files.local("entities/" + 1 + ".json");
		
		if(!file2.exists()){
			noLevels = true;
		}
		
		if(!file.exists())
			setting = 1;
	}
	
	public void writer(String func, int write, int score){
		if(write <= Screen2.levels){
		prefs.putInteger(func, write);
		prefs.putBoolean("endless", false);
		prefs.putInteger(func + "Scr", score);
		prefs.flush();
		}else if(write > Screen2.levels){
			LevelGroups next = stage.getNext();
			if(next != null && prefs.getInteger(next.getDir()) == 0)
				prefs.putInteger(next.getDir(), 1);
			
			prefs.putInteger(func, 1);
			prefs.putBoolean("endless", true);
			prefs.putInteger(func + "Scr", score);
			prefs.flush();
		}
	}
	
	public LevelGroups getStage() {
		LevelGroups temp = LevelGroups.getLast();
		
		while(temp != null) {
			if(getLevel(temp) > 0)
				return stage = temp;
			
			temp = temp.getPrev();
		}
		
		return stage = LevelGroups.NORMAL;
	}
	
	public int getLevel(LevelGroups group) {
		setting = prefs.getInteger(group.getDir());
		return setting;
	}
	
	public int getScore(LevelGroups group) {
		score = prefs.getInteger(group.getDir() + "Scr");
		return score;
	}
	
	public int getLevelsAmount(LevelGroups group){
		//int a = 1;
		//FileHandle file;
		a = -1;
		while(true){
			FileHandle file = Gdx.files.local("levels/" + group.getDir() + "/" + (a + 1) + ".tmx");
			if(file.exists()) a++;
			else break;
			//file = Gdx.files.internal(a + ".lvl");
		}
		//File file = new File(a + ".lvl");
		//if(file.exists()){
			//a++;
			//file = new File(a + ".lvl");
		//}
		//System.out.println(a);
		if(a == -1)
			noLevels = true;
		
		return a;
	}

}
