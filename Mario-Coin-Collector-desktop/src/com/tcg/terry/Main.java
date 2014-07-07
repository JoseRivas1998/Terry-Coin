package com.tcg.terry;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tcg.terry.main.Game;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Terry-Coin-Collector";
		cfg.useGL20 = false;
		cfg.width = 1280;
		cfg.height = 720;
		if(!Game.debug) cfg.fullscreen = true;
		
		new LwjglApplication(new Game(), cfg);
	}
}
