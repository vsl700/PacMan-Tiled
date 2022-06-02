package com.stbg.pcmtld;

import java.util.HashMap;

public class EntitySnapshot {

	public static String type;
	public static float x;
	public static float y;
	public HashMap<String, String> data;
	
	public EntitySnapshot() {
		// TODO Auto-generated constructor stub
	}
	
	public EntitySnapshot(String type, float x, float y){
		EntitySnapshot.type = type;
		EntitySnapshot.x = x;
		EntitySnapshot.y = y;
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		EntitySnapshot.type = type;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		EntitySnapshot.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		EntitySnapshot.y = y;
	}
	
	public void putFloat(String key, float value){
		data.put(key, "" + value);
	}
	
	public void putInt(String key, int value){
		data.put(key, "" + value);
	}
	
	public void putBoolean(String key, boolean value){
		data.put(key, "" + value);
	}
	
	public void putString(String key, String value){
		data.put(key, value);
	}
	
	public float getFloat(String key, float defaultValue){
		if(data.containsKey(key)){
			try{
				return Float.parseFloat(data.get(key));
			}catch(Exception e){
				return defaultValue;
			}
		} else
			return defaultValue;
	}
	
	public int getInt(String key, int defaultValue){
		if(data.containsKey(key)){
			try{
				return Integer.parseInt(data.get(key));
			}catch(Exception e){
				return defaultValue;
			}
		} else
			return defaultValue;
	}
	
	public boolean getBoolean(String key, boolean defaultValue){
		if(data.containsKey(key)){
			try{
				return Boolean.parseBoolean(data.get(key));
			}catch(Exception e){
				return defaultValue;
			}
		} else
			return defaultValue;
	}
	
	public String getString(String key, String defaultValue){
		if(data.containsKey(key)){
			return data.get(key);
		} else
			return defaultValue;
	}
	
}
