package com.stbg.pcmtld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;

public class SettingReader {
	
	//File file = new File("pcm.txt");
	public int setting = 1;
	public static boolean endless = false;
	public static boolean noLevels = false;
	public static int score = 0;
	String text = "";
	Preferences prefs;
	int a = -1;
	
	public SettingReader(){
		prefs = Gdx.app.getPreferences("pacman");
		
		setting = prefs.getInteger("setting");
		endless = prefs.getBoolean("endless");
		score = prefs.getInteger("scr");
		
		FileHandle file = Gdx.files.local("levels/" + setting + ".tmx");
		//FileHandle file4 = Gdx.files.local("entities/" + setting + ".json");
		FileHandle file2 = Gdx.files.local("levels/" + 1 + ".tmx");
		//FileHandle file3 = Gdx.files.local("entities/" + 1 + ".json");
		
		if(!file2.exists()){
			noLevels = true;
		}
		
		if(!file.exists())
			setting = 1;
	}
	
	public void writer(String func, int write, int score){
		if(write <= Screen2.levels){
		prefs.putInteger("setting", write);
		prefs.putBoolean("endless", false);
		prefs.putInteger("scr", score);
		prefs.flush();
		}else if(write > Screen2.levels){
			prefs.putInteger("setting", 1);
			prefs.putBoolean("endless", true);
			prefs.putInteger("scr", score);
			prefs.flush();
		}
	}
	
	public int getLevel() {
		setting = prefs.getInteger("setting");
		return setting;
	}
	
	public int getLevelsAmount(){
		//int a = 1;
		//FileHandle file;
		while(true){
			FileHandle file = Gdx.files.local("levels/" + (a + 1) + ".tmx");
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
		return a;
	}

}
