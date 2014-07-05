package com.tcg.terry.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.tcg.terry.managers.Content;
import com.tcg.terry.managers.GameStateManager;

public class Game implements ApplicationListener {
	public static int WIDTH;
	public static int HEIGHT;
	public static boolean debug;
	public static void setDebug(boolean debug) {
		Game.debug = debug;
	}

	public static FileHandle fontFile;
	public static FileHandle fontImage;
	
	public static OrthographicCamera cam;
	
	private GameStateManager gsm;
	
	public static Content res;
	
	@Override
	public void create() {		
		debug = false;
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		fontFile = Gdx.files.internal("Fonts/text.fnt");
		fontImage = Gdx.files.internal("Fonts/text.png");
		
		Texture.setEnforcePotImages(false);
		
		cam = new OrthographicCamera(WIDTH, HEIGHT);
		cam.translate(WIDTH / 2, HEIGHT / 2);
		cam.update();

		res = new Content();
		res.loadMusic(Gdx.files.internal("sound/Field1.ogg"), "bgm");
		res.loadMusic(Gdx.files.internal("sound/Theme1.ogg"), "title");
		res.loadMusic(Gdx.files.internal("sound/Applause2.ogg"), "go");
		res.loadMusic(Gdx.files.internal("sound/Fanfare3.ogg"), "splash");
		res.loadMusic(Gdx.files.internal("sound/Gameover1.ogg"), "gos");
		res.loadMusic(Gdx.files.internal("sound/Scene6.ogg"), "controls");
		res.loadSound("sound/Coin.ogg", "coin");
		res.loadSound("sound/Jump1.ogg", "jump");
		
		gsm = new GameStateManager();
	}
	@Override
	public void render() {		
		
		//makes screen black
		Gdx.gl.glClearColor(0, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.draw();
		
		Gdx.graphics.setTitle("Terry's Coin Collector | " + Gdx.graphics.getFramesPerSecond() + " FPS" );
	}

	@Override
	public void dispose() {
		res.removeAll();
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}
}
