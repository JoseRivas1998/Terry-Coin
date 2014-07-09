package com.tcg.terry.gamestates;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.tcg.terry.entities.Cloud;
import com.tcg.terry.entities.Cursor;
import com.tcg.terry.main.Game;
import com.tcg.terry.managers.GameStateManager;

public class MenuState extends GameState {

	private SpriteBatch sb;
	private ShapeRenderer sr;
	private BitmapFont title;
	private BitmapFont options;
	private String titleText;
	
	private String play;
	private String controls;
	private String quit;
	private String fullscreen;
	
	private float tX;
	private float tY;
	private float pX;
	private float pY;
	private float cX;
	private float cY;
	private float qX;
	private float qY;
	private float tW;
	private float tH;
	private float pW;
	private float pH;
	private float cW;
	private float cH;
	private float qW;
	private float qH;
	private float fX, fY, fW, fH;
	
	private Array<Cloud> clouds;
	
	Cursor c;
	Rectangle p;
	Rectangle co;
	Rectangle q;
	Rectangle t;
	Rectangle f;
	Rectangle d;

	
	private int dc;
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		sb = new SpriteBatch();
		sr = new ShapeRenderer();
		title = new BitmapFont(Game.fontFile, Game.fontImage, false);
		title.setScale(2);
		options = new BitmapFont(Game.fontFile, Game.fontImage, false);
		options.setScale(1.5f);
		c = new Cursor();
		
		titleText = "Terry's Coin Collector";
		play = "Play";
		controls = "How to Play";
		quit = "Quit";
		fullscreen = "Toggle Fullscreen";
		
		tW = title.getBounds(titleText).width;
		tH = title.getBounds(titleText).height;
		tX = (Game.WIDTH * .5f) - (tW * .5f);
		tY = (Game.HEIGHT * .75f) - (tH * .5f);
		
		pW = options.getBounds(play).width;
		pH = options.getBounds(play).height;
		pX = (Game.WIDTH - pW) * .5f;
		pY = (Game.HEIGHT * .5f) - pH;
		cW = options.getBounds(controls).width;
		cH = options.getBounds(controls).height;
		cX = (Game.WIDTH - cW) * .5f;
		cY = (Game.HEIGHT * .5f) - pH * 3;
		qW = options.getBounds(quit).width;
		qH = options.getBounds(quit).height;
		qX = (Game.WIDTH - qW) * .5f;
		qY = (Game.HEIGHT * .5f) - pH * 5f;
		
		fW = options.getBounds(fullscreen).width;
		fH = options.getBounds(fullscreen).height;
		fX = Game.WIDTH - fW;
		fY = fH;
		
		p = new Rectangle(pX, pY - pH, pW, pH);
		co = new Rectangle(cX, cY - cH, cW, cH);
		q = new Rectangle(qX, qY - qH, qW, qH);
		t = new Rectangle(tX, tY - tH, tW, tH);
		f = new Rectangle(fX, fY - fH, fW, fH);
		d = new Rectangle(0, Game.HEIGHT - 10, 10, 10);
		
		clouds = new Array<Cloud>();
		for(int i = 0; i < 30; i++) {
			clouds.add(new Cloud(MathUtils.random(Game.WIDTH), MathUtils.random(Game.HEIGHT)));
			while(((t.overlaps(clouds.get(i).bounds())) || (p.overlaps(clouds.get(i).bounds())) || (co.overlaps(clouds.get(i).bounds())) || (q.overlaps(clouds.get(i).bounds())) || f.overlaps(clouds.get(i).bounds())) ) {
				clouds.get(i).setPosition(MathUtils.random(Game.WIDTH), MathUtils.random(Game.HEIGHT));
			}
		}
		
		Game.res.getMusic("title").play();
		Game.res.getMusic("title").setLooping(true);
		
		dc = 0;
	}

	@Override
	public void update(float dt) {
		handleInput();
		c.update();
	}

	@Override
	public void draw() {
		sb.begin();
		for(int i = 0; i < clouds.size; i++) {
			clouds.get(i).draw(sb);
		}
		title.draw(sb, titleText, tX, tY);
		options.draw(sb, play, pX, pY);
		options.draw(sb, controls, cX, cY);
		if(Gdx.app.getType() == ApplicationType.Desktop) { 
			options.draw(sb, quit, qX, qY);
			options.draw(sb, fullscreen, fX, fY);
		}
		sb.end();
		if(Game.debug) debugDraw();
	}
	
	public void debugDraw() {
		sr.begin(ShapeType.Line);
		sr.setColor(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1);
		c.draw(sr);
		for(int i = 0; i < clouds.size; i++) {
			sr.rect(clouds.get(i).bounds().x, clouds.get(i).bounds().y, clouds.get(i).bounds().width, clouds.get(i).bounds().height);
		}
		sr.rect(t.x, t.y, t.width, t.height);
		sr.rect(p.x, p.y, p.width, p.height);
		sr.rect(co.x, co.y, co.width, co.height);
		if(Gdx.app.getType() == ApplicationType.Desktop) {
			sr.rect(q.x, q.y, q.width, q.height);
			sr.rect(f.x, f.y, f.width, f.height);
		}
		sr.rect(d.x, d.y, d.width, d.height);
		sr.end();
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void handleInput() {
		switch(Gdx.app.getType()) {
		case Desktop: desktop(); break;
		case Android: android(); break;
		}
	}
	
	public void desktop() {
		if(c.getBounds().overlaps(p)) {
			gsm.setState(gsm.PLAY);
		}
		if(c.getBounds().overlaps(co)) {
			gsm.setState(gsm.CONTROLS);
		}
		if(c.getBounds().overlaps(d)) {
			dc++;
			if(dc == 10) {
				dc = 0;
				Game.setDebug(!Game.debug);
			}
		}
		if(c.getBounds().overlaps(q)) {
			Gdx.app.exit();
		}
		if(c.getBounds().overlaps(f)) {
			Gdx.graphics.setDisplayMode(Game.WIDTH, Game.HEIGHT, !Gdx.graphics.isFullscreen());
		}
	}
	
	public void android() {
		if(c.getBounds().overlaps(p)) {
			Game.res.getMusic("title").stop();
			gsm.setState(gsm.PLAY);
		}
		if(c.getBounds().overlaps(co)) {
			Game.res.getMusic("title").stop();
			gsm.setState(gsm.CONTROLS);
		}
		if(c.getBounds().overlaps(d)) {
			dc++;
			if(dc == 10) {
				dc = 0;
				Game.setDebug(!Game.debug);
			}
		}
	}

	@Override
	public void dispose() {
		sb.dispose();
		title.dispose();
		options.dispose();
		sr.dispose();
	}

}
