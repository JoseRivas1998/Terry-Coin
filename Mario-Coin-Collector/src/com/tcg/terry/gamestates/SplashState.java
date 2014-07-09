package com.tcg.terry.gamestates;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tcg.terry.main.Game;
import com.tcg.terry.managers.GameStateManager;

public class SplashState extends GameState {
	
	SpriteBatch sb;
	BitmapFont font;
	private Animation anim;
	private float timeElapsed;
	
	private static final TextureRegion[] frames = {
		new TextureRegion( new Texture("TCG/001.png")),
		new TextureRegion( new Texture("TCG/002.png")),
		new TextureRegion( new Texture("TCG/003.png")),
		new TextureRegion( new Texture("TCG/004.png")),
		new TextureRegion( new Texture("TCG/005.png")),
		new TextureRegion( new Texture("TCG/006.png")),
		new TextureRegion( new Texture("TCG/007.png")),
		new TextureRegion( new Texture("TCG/008.png")),
		new TextureRegion( new Texture("TCG/009.png")),
		new TextureRegion( new Texture("TCG/010.png")),
		new TextureRegion( new Texture("TCG/011.png")),
		new TextureRegion( new Texture("TCG/012.png")),
		new TextureRegion( new Texture("TCG/013.png")),
		new TextureRegion( new Texture("TCG/014.png")),
		new TextureRegion( new Texture("TCG/015.png")),
		new TextureRegion( new Texture("TCG/016.png"))
	};
	
	TextureRegion currentFrame;
	
	public float timer;
	public float time;

	public SplashState(GameStateManager gsm) {
		super(gsm);
		
	}
	
	static String splash = "Tiny Country Games";
	@Override
	public void init() {
		sb = new SpriteBatch();
		font = new BitmapFont(Game.fontFile, Game.fontImage, false);
		font.setScale(1.5f);
		
		
		timeElapsed = 0;
		anim = new Animation(0.15f, frames);
		timer = 0;
		time = 11;

		Game.res.getMusic("splash").play();
	}

	@Override
	public void update(float dt) {
		handleInput();
		timer += dt;
		if(timer > time) {
			Game.res.getMusic("splash").stop();
			gsm.setState(gsm.MENU);
		}
	}

	@Override
	public void draw() {
		timeElapsed += Gdx.graphics.getDeltaTime();
		currentFrame = anim.getKeyFrame(timeElapsed, true);
		int w = currentFrame.getRegionWidth();
		int h = currentFrame.getRegionHeight();
		float fw = font.getBounds(splash).width;
		float fh = font.getBounds(splash).height;
		float fx = (Game.WIDTH / 2) - (fw / 2);
		float fy = Game.HEIGHT / 2 + (fh / 2);
		sb.begin();
		font.draw(sb, splash, fx + (w / 2) + 10, fy);
		sb.draw(currentFrame, fx - (w / 2), fy - (h * .55f));
		sb.end();
	}

	@Override
	public void handleInput() {
		if(Gdx.input.isTouched() || (Gdx.input.isKeyPressed(Keys.ANY_KEY) && Gdx.app.getType() == ApplicationType.Desktop)) {
			gsm.setState(gsm.MENU);
		}

	}

	@Override
	public void dispose() {
		sb.dispose();
		font.dispose();
	}

}
