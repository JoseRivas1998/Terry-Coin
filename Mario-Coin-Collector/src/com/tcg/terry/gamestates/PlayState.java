package com.tcg.terry.gamestates;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tcg.terry.entities.Cloud;
import com.tcg.terry.entities.Coin;
import com.tcg.terry.entities.Cursor;
import com.tcg.terry.entities.Flower;
import com.tcg.terry.entities.Ground;
import com.tcg.terry.entities.JumpButton;
import com.tcg.terry.entities.Player;
import com.tcg.terry.entities.SprintButton;
import com.tcg.terry.main.Game;
import com.tcg.terry.managers.GameStateManager;
import com.tcg.terry.managers.Timer;

public class PlayState extends GameState {
	
	private SpriteBatch sb;
	private BitmapFont font;
	private Timer timer;
	private Ground g;
	private Player p;
	private ShapeRenderer sr;
	private Coin c;
	private Vector2 pPos, cPos, iPos;
	private JumpButton lJ, rJ;
	private SprintButton lS, rS;
	private Cursor in;
	private Array<Flower> f;
	private Array<Cloud> cls;
	private Rectangle text;
	
	private Timer ti;
	
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void init() {
		timer = new Timer(121);
		cls = new Array<Cloud>();
		font = new BitmapFont(Game.fontFile, Game.fontImage, false);
		float h1 = font.getBounds("Time Left: " + Long.toString(timer.getCurrentTime()) + " seconds").height; 
		float h2 = font.getBounds("Coins: ").height;
		float h = h1 + h2;
		float w = font.getBounds("Time Left: " + Long.toString(timer.getCurrentTime()) + " seconds").width; 
		text = new Rectangle(0, Game.HEIGHT - h, w, h);
		for(int i = 0; i < 20; i++) {
			cls.add(new Cloud(MathUtils.random(Game.WIDTH), MathUtils.random(Game.HEIGHT * .3f, Game.HEIGHT)));
			while((text.overlaps(cls.get(i).bounds()))) {
				cls.get(i).setPosition(MathUtils.random(Game.WIDTH), MathUtils.random(Game.HEIGHT * .3f, Game.HEIGHT));
			}
		}
		f = new Array<Flower>();
		for(int i = 0; i < 10; i++) {
			f.add(new Flower(MathUtils.random(Game.WIDTH), Game.HEIGHT * .24f));
		}
		for(int i = 0; i < 10; i++) {
			f.add(new Flower(MathUtils.random(Game.WIDTH), MathUtils.random(Game.HEIGHT * .25f)));
		}
		in = new Cursor();
		sb = new SpriteBatch();
		g = new Ground();
		switch(Gdx.app.getType()) {
		case Desktop: 
			p = new Player(g, null, null, in, null, null);
			font.setScale(1); 
			break;
		case Android: 
			font.setScale(1.5f);
			lS = new SprintButton(Game.WIDTH * .1f, Game.HEIGHT * .2f);
			rS = new SprintButton(Game.WIDTH * .8f, Game.HEIGHT * .2f);
			lJ = new JumpButton(lS.getBounds().x, lS.getBounds().y - lS.getBounds().height * 2);
			rJ = new JumpButton(rS.getBounds().x, rS.getBounds().y - rS.getBounds().height * 2);
			p = new Player(g, lJ, rJ, in, lS, rS);
			break;
		}
		c = new Coin(p, MathUtils.random(Game.WIDTH), timer);
		sr = new ShapeRenderer();
		Game.res.getMusic("bgm").play();
		Game.res.getMusic("bgm").setLooping(true);
		pPos = new Vector2();
		cPos = new Vector2();
		iPos = new Vector2();
	}

	
	private long totalCoins;
	@Override
	public void update(float dt) {
		timer.update(dt);
		in.update();
		p.update(dt);
		c.update(dt);
		if(timer.isCounting()) totalCoins = c.getCoins();
		System.out.println(totalCoins);
		if(timer.getCurrentTime() <= 0) {
			if(timer.isCounting()) {
				ti = new Timer(5);
				if(Game.res.getMusic("bgm").isPlaying()) {
					Game.res.getMusic("bgm").stop();
				}
				Game.res.getMusic("go").play();
			}
			gameOver(dt);
			timer.stop();
		}
	}
	
	public void gameOver(float dt) {
		ti.update(dt);
		if(ti.getCurrentTime() <= 0) {
			System.out.println(totalCoins);
			gsm.GameOver(totalCoins);
		}
		
	}
	

	@SuppressWarnings("incomplete-switch")
	@Override
	public void draw() {
		g.draw();
		sb.begin();
		for(int i = 0; i < cls.size; i++) {
			cls.get(i).draw(sb);
		}
		for(int i = 0; i < f.size; i++) {
			f.get(i).draw(sb);
		}
		p.draw(sb, Gdx.graphics.getDeltaTime());
		c.draw(sb);
		String timeS = "Time Left: " + Long.toString(timer.getCurrentTime()) + " seconds";
		float height = font.getBounds(timeS).height;
		font.draw(sb, timeS, 0, Game.HEIGHT);
		font.draw(sb, "Coins: " + c.coinString(), 0, Game.HEIGHT - height - 5);
		sb.end();
		switch(Gdx.app.getType()) {
		case Android:
			lJ.draw();rJ.draw(); rS.draw(); lS.draw();
			break;
		case Desktop:
			break;
		}
		if(Game.debug) debugDraw();
	}
	
	public void debugDraw() {
		pPos.set(p.getBounds().x, p.getBounds().y);
		cPos.set(c.getBounds().x, c.getBounds().y);
		iPos.set(Gdx.input.getX(), Game.HEIGHT - Gdx.input.getY());
		sr.begin(ShapeType.Line);
		in.draw(sr);
		sr.setColor(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1);
		sr.rect(p.getBounds().x, p.getBounds().y, p.getBounds().width, p.getBounds().height);
		sr.setColor(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1);
		sr.rect(c.getBounds().x, c.getBounds().y, c.getBounds().width, c.getBounds().height);
		sr.setColor(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1);
		sr.line(Game.WIDTH * .25f, 0, Game.WIDTH * .25f, Game.HEIGHT);
		sr.line(Game.WIDTH * .5f, 0, Game.WIDTH * .5f, Game.HEIGHT);
		sr.line(Game.WIDTH * .75f, 0, Game.WIDTH * .75f, Game.HEIGHT);
		sr.line(0, Game.HEIGHT * .25f, Game.WIDTH, Game.HEIGHT * .25f);
		sr.line(0, Game.HEIGHT * .5f, Game.WIDTH, Game.HEIGHT * .5f);
		sr.line(0, Game.HEIGHT * .75f, Game.WIDTH, Game.HEIGHT * .75f);
		for(int i = 0; i < f.size; i++) {
			if(i - 1 > 0) sr.line(f.get(i).getPosition().x, f.get(i).getPosition().y, f.get(i - 1).getPosition().x, f.get(i - 1).getPosition().y);
		}
		for(int i = 0; i < cls.size; i++) {
			if(i - 1 > 0) sr.line(cls.get(i).getPosition().x, cls.get(i).getPosition().y, cls.get(i - 1).getPosition().x, cls.get(i - 1).getPosition().y);
		}
		sr.end();
		sb.begin();
		String bool = "Music Looping: " + Boolean.toString(Game.res.getMusic("bgm").isLooping());
		float bHeight = font.getBounds(bool).height;
		float bWidth = font.getBounds(bool).width;
		font.draw(sb, bool, 0, bHeight);
		String pPosi = "Player: " + pPos.toString();
		String cPosi = "Coin: " + cPos.toString();
		String iPosi;
		if(Gdx.input.isTouched()) {
			iPosi = "Touch: " + iPos.toString();
		} else {
			iPosi = "Not Touched";
		}
		float pWidth = font.getBounds(pPosi).width;
		float pHeight = font.getBounds(pPosi).height;
		float cHeight = font.getBounds(cPosi).height;
		float iHeight = font.getBounds(iPosi).height;
		font.draw(sb, pPosi, bWidth + 5, pHeight);
		font.draw(sb, cPosi, bWidth + pWidth + 10, cHeight);
		font.draw(sb, iPosi, 0, Math.max(Math.max(bHeight, cHeight), pHeight) + iHeight);
		sb.end();
	}

	@Override
	public void handleInput() {

	}

	@Override
	public void dispose() {
		if(Gdx.app.getType() == ApplicationType.Android){ 
			lJ.dispose();rJ.dispose();lS.dispose();rS.dispose();
		}
		g.dispose();
		sb.dispose();
		sr.dispose();
		font.dispose();
	}

}
