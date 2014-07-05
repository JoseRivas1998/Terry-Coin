package com.tcg.terry.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Flower {
	
	private float x;
	private float y;
	private Animation a;
	private TextureRegion currentFrame;
	private float stateTime;
	private TextureRegion[] frames = {
			new TextureRegion(new Texture(Gdx.files.internal("entities/flower/01.png"))),
			new TextureRegion(new Texture(Gdx.files.internal("entities/flower/02.png")))
	};
	
	public Flower(float x, float y) {
		this.x = x;
		this.y = y;
		
		stateTime = 0;
		a = new Animation(MathUtils.random(.25f, .5f), frames);
	}
	
	public void draw(SpriteBatch sb) {
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = a.getKeyFrame(stateTime, true);
		sb.draw(currentFrame, this.x, this.y);
	}
	
	public Vector2 getPosition() {
		return new Vector2(this.x, this.y);
	}
}
