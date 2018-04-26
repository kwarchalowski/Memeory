package com.zomiren.memeory.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.zomiren.memeory.MainMenuScreen;
import com.zomiren.memeory.Memeory;
import com.zomiren.memeory.MemeoryGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        /* initial config of the window */
        config.width = 1280;
        config.height = 720;
        config.title = "Memeory | v.04";
        config.resizable = false;


		new LwjglApplication(new MemeoryGame(), config);
	}
}
