package com.tcg.terry.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Cloud {
	
	private float x;
	private float y;
	private Rectangle bounds;
	private Texture tex = new Texture("entities/cloud.png");
	
	public Cloud(float x, float y) {
		this.x = x;
		this.y = y;
		bounds = new Rectangle(x, y, tex.getWidth(), tex.getHeight());
	}
	
	public void draw(SpriteBatch sb) {
		sb.draw(tex, this.x, this.y);
	}
	
	public Vector2 getPosition() {
		return new Vector2(this.x, this.y);
	}
	
	public Rectangle bounds() {
		return this.bounds;
	}
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
		bounds.set(x, y, tex.getWidth(), tex.getHeight());
	}
}
