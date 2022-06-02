package com.stbg.pcmtld.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.stbg.pcmtld.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MyGdxGame(), config);
		
		//config.useGL30 = true;
		config.vSyncEnabled = false;
		config.title = "PAC-MAN";
		config.addIcon("pacman/icon/pacman_ico.png", FileType.Internal);
		config.width = MyGdxGame.WIDTH;
		config.height = MyGdxGame.HEIGHT;
		//config.foregroundFPS = 0;
		//config.resizable = false;
		//config.fullscreen = true;
	}
}
