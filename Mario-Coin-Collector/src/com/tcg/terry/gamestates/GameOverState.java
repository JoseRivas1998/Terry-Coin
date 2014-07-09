package com.tcg.terry.gamestates;

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

public class GameOverState extends GameState {

	private SpriteBatch sb;
	private ShapeRenderer sr;
	private BitmapFont title;
	private BitmapFont options;
	private String titleText;
	
	private String play;
	private String menu;
	private String coins;
	
	private float tX;
	private float tY;
	private float cX, cY;
	private float pX;
	private float pY;
	private float mX;
	private float mY;
	private float tW;
	private float tH;
	private float cW, cH;
	private float pW;
	private float pH;
	private float mW;
	private float mH;
	
	private Array<Cloud> clouds;
	
	Cursor cu;
	Rectangle p;
	Rectangle m;
	Rectangle t;
	Rectangle co;
	
	public GameOverState(GameStateManager gsm, long c) {
		super(gsm);
		this.c = c;
		System.out.print(c);
		System.out.println(this.c);
		init();
	}

	@Override
	public void init() {
		sb = new SpriteBatch();
		sr = new ShapeRenderer();
		title = new BitmapFont(Game.fontFile, Game.fontImage, false);
		title.setScale(4);
		options = new BitmapFont(Game.fontFile, Game.fontImage, false);
		options.setScale(1.5f);
		cu = new Cursor();
		
		titleText = "Game Over";
		play = "Play Again";
		menu = "Menu";
		coins = "Total Coins: " + Long.toString(this.c);
		
		tW = title.getBounds(titleText).width;
		tH = title.getBounds(titleText).height;
		tX = (Game.WIDTH * .5f) - (tW * .5f);
		tY = (Game.HEIGHT * .75f) - (tH * .5f);
		
		cW = options.getBounds(coins).width;
		cH = options.getBounds(coins).height;
		pW = options.getBounds(play).width;
		pH = options.getBounds(play).height;
		cX = (Game.WIDTH - cW) * .5f;
		cY = (Game.HEIGHT * .5f);
		pX = (Game.WIDTH - pW) * .5f;
		pY = (Game.HEIGHT * .5f) - cH - pH;
		mW = options.getBounds(menu).width;
		mH = options.getBounds(menu).height;
		mX = (Game.WIDTH - mW) * .5f;
		mY = (Game.HEIGHT * .5f) - cH * 4;
		
		p = new Rectangle(pX, pY - pH, pW, pH);
		m = new Rectangle(mX, mY - mH, mW, mH);
		t = new Rectangle(tX, tY - tH, tW, tH);
		co = new Rectangle(cX, cY - cH, cW, cH);
		
		clouds = new Array<Cloud>();
		for(int i = 0; i < 40; i++) {
			clouds.add(new Cloud(MathUtils.random(Game.WIDTH), MathUtils.random(Game.HEIGHT)));
			while(( (t.overlaps(clouds.get(i).bounds())) || (p.overlaps(clouds.get(i).bounds())) || (m.overlaps(clouds.get(i).bounds())) || (co.overlaps(clouds.get(i).bounds()))) ) {
				clouds.get(i).setPosition(MathUtils.random(Game.WIDTH), MathUtils.random(Game.HEIGHT));
			}
		}

		Game.res.getMusic("gos").play();
		Game.res.getMusic("gos").setLooping(false);
		
	}

	@Override
	public void update(float dt) {
		handleInput();
		cu.update();
	}

	@Override
	public void draw() {
		sb.begin();
		for(int i = 0; i < clouds.size; i++) {
			clouds.get(i).draw(sb);
		}
		title.draw(sb, titleText, tX, tY);
		options.draw(sb, play, pX, pY);
		options.draw(sb, menu, mX, mY);
		options.draw(sb, coins, cX, cY);
		sb.end();
		if(Game.debug) debugDraw();

	}

	
	public void debugDraw() {
		sr.begin(ShapeType.Line);
		sr.setColor(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1);
		cu.draw(sr);
		for(int i = 0; i < clouds.size; i++) {
			sr.rect(clouds.get(i).bounds().x, clouds.get(i).bounds().y, clouds.get(i).bounds().width, clouds.get(i).bounds().height);
		}
		sr.rect(t.x, t.y, t.width, t.height);
		sr.rect(p.x, p.y, p.width, p.height);
		sr.rect(co.x, co.y, co.width, co.height);
		sr.rect(m.x, m.y, m.width, m.height);
		sr.end();
	}
	
	@Override
	public void handleInput() {
		if(m.overlaps(cu.getBounds())) {
			gsm.setState(gsm.MENU);
		}
		if(p.overlaps(cu.getBounds())) {
			gsm.setState(gsm.PLAY);
		}
	}

	@Override
	public void dispose() {
		sr.dispose();
		sb.dispose();
		title.dispose();
		options.dispose();
	}

}
