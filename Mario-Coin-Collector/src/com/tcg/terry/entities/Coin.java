package com.tcg.terry.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.tcg.terry.main.Game;
import com.tcg.terry.managers.Timer;

public class Coin {
	
	private Player player;
	long coins;
	private Sprite sprite;
	private Timer t;
	
	private Texture coin = new Texture(Gdx.files.internal("entities/coin.png"));
	
	public Coin(Player player, float x, Timer t) {
		this.player = player;
		this.t = t;
		sprite = new Sprite(coin);
		sprite.setPosition(x, Game.HEIGHT * .5f);		
		sprite.setScale(2);
	}
	
	public void draw(SpriteBatch sb) {
		if(t.isCounting()) sprite.draw(sb);
	}
	
	public void update(float dt) {
		if(t.isCounting()) {	
			if(sprite.getBoundingRectangle().overlaps(player.getBounds())) {
				sprite.setX(MathUtils.random(Game.WIDTH - sprite.getWidth()));
				Game.res.getSound("coin").play();
				coins++;
			}
		}
	}
	
	public long getCoins() { return coins; }
	public String coinString() { return Long.toString(coins); }
	
	public Rectangle getBounds() { return sprite.getBoundingRectangle(); }
	
	public void dispose() {
		coin.dispose();
	}

}
