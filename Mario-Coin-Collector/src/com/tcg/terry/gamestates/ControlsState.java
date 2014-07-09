package com.tcg.terry.gamestates;

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

public class ControlsState extends GameState {

	private SpriteBatch sb;
	private ShapeRenderer sr;
	private BitmapFont title;
	private BitmapFont content;
	
	private String titleText;
	private String objective;
	private String objectiveText;
	private String controls;
	private String controls1, controls2, controls3;
	private String menu;
	
	private float tX, tY, tW, tH;
	private float oX, oY, oW, oH;
	private float otX, otY, otW, otH;
	private float cX, cY, cW, cH;
	private float c1X, c1Y, c1W, c1H, c2X, c2Y, c2W, c2H, c3X, c3Y, c3W, c3H;
	private float mX, mY, mW, mH;
	
	private Array<Cloud> clouds;
	
	Cursor c;
	
	private Rectangle t;
	private Rectangle o, ot;
	private Rectangle co, c1, c2, c3;
	private Rectangle m;
	

	public ControlsState(GameStateManager gsm) {
		super(gsm);
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void init() {
		
		sb = new SpriteBatch();
		sr = new ShapeRenderer();
		c = new Cursor();
		content = new BitmapFont(Game.fontFile, Game.fontImage, false); 
		title = new BitmapFont(Game.fontFile, Game.fontImage, false); 
		title.setScale(3);
		
		titleText = "How to Play";
		objective = "Objective:";
		objectiveText = "Collect as many coins as you can in 2 minutes!";
		controls = "Controls:";
		switch(Gdx.app.getType()) {
		case Android:
			controls1 = "Touch the screen to move left and right";
			controls3 = "Tap the Jump button to jump";
			controls2 = "Hold the Sprint button to move faster";
			break;
		case Desktop:
			controls1 = "Use the arrow keys or WASD to move left and right";
			controls3 = "Press space to jump";
			controls2 = "Hold either Shift key to move faster";
			break;
		}
		menu = "Return to Menu";

		tW = title.getBounds(titleText).width;
		tH = title.getBounds(titleText).height;
		tX = (Game.WIDTH - tW) * .5f;
		tY = Game.HEIGHT * .9f;
		
		oW = content.getBounds(objective).width;
		oH = content.getBounds(objective).height;
		oX = (Game.WIDTH - oW) * .5f;
		oY = tY - tH * 2;
		otW = content.getBounds(objectiveText).width;
		otH = content.getBounds(objectiveText).height;
		otX = (Game.WIDTH - otW) * .5f;
		otY = oY - oH - otH;
		
		cW = content.getBounds(controls).width;
		cH = content.getBounds(controls).height;
		cX = (Game.WIDTH - cW) * .5f;
		cY = otY - otH - cH * 2;
		c1W = content.getBounds(controls1).width;
		c1H = content.getBounds(controls1).height;
		c1X = (Game.WIDTH - c1W) * .5f;
		c1Y = cY - cH - c1H;
		c2W = content.getBounds(controls2).width;
		c2H = content.getBounds(controls2).height;
		c2X = (Game.WIDTH - c2W) * .5f;
		c2Y = c1Y - c1H - 5;
		c3W = content.getBounds(controls3).width;
		c3H = content.getBounds(controls3).height;
		c3X = (Game.WIDTH - c3W) * .5f;
		c3Y = c2Y - c2H - 5;
		
		mW = content.getBounds(menu).width;
		mH = content.getBounds(menu).height;
		mX = (Game.WIDTH - mW) * .5f;
		mY = c3Y - c3H - mH * 3;
		
		t = new Rectangle(tX, tY - tH, tW, tH);
		o = new Rectangle(oX, oY - oH, oW, oH);
		ot = new Rectangle(otX, otY - otH, otW, otH);
		co = new Rectangle(cX, cY - cH, cW, cH);
		c1 = new Rectangle(c1X, c1Y - c1H, c1W, c1H);
		c2 = new Rectangle(c2X, c2Y - c2H, c2W, c2H);
		c3 = new Rectangle(c3X, c3Y - c3H, c3W, c3H);
		m = new Rectangle(mX, mY - mH, mW, mH);
		
		clouds = new Array<Cloud>();
		for(int i = 0; i < 30; i++) {
			clouds.add(new Cloud(MathUtils.random(Game.WIDTH), MathUtils.random(Game.HEIGHT)));
			while(((t.overlaps(clouds.get(i).bounds())) || (o.overlaps(clouds.get(i).bounds())) || (ot.overlaps(clouds.get(i).bounds())) || (co.overlaps(clouds.get(i).bounds())) || (c1.overlaps(clouds.get(i).bounds())) || (c2.overlaps(clouds.get(i).bounds())) || (c3.overlaps(clouds.get(i).bounds())) || (m.overlaps(clouds.get(i).bounds())))) {
				clouds.get(i).setPosition(MathUtils.random(Game.WIDTH), MathUtils.random(Game.HEIGHT));
			}
		}
		
		Game.res.getMusic("controls").play();
		Game.res.getMusic("controls").setLooping(true);
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
		content.draw(sb, objective, oX, oY);
		content.draw(sb, objectiveText, otX, otY);
		content.draw(sb, controls, cX, cY);
		content.draw(sb, controls1, c1X, c1Y);
		content.draw(sb, controls2, c2X, c2Y);
		content.draw(sb, controls3, c3X, c3Y);
		content.draw(sb, menu, mX, mY);
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
		sr.rect(o.x, o.y, o.width, o.height);
		sr.rect(ot.x, ot.y, ot.width, ot.height);
		sr.rect(co.x, co.y, co.width, co.height);
		sr.rect(c1.x, c1.y, c1.width, c1.height);
		sr.rect(c2.x, c2.y, c2.width, c2.height);
		sr.rect(c3.x, c3.y, c3.width, c3.height);
		sr.rect(m.x, m.y, m.width, m.height);
		sr.end();
	}

	@Override
	public void handleInput() {
		if(m.overlaps(c.getBounds())) {
			gsm.setState(gsm.MENU);
		}

	}

	@Override
	public void dispose() {
		sb.dispose();
		title.dispose();
		sr.dispose();

	}

}
