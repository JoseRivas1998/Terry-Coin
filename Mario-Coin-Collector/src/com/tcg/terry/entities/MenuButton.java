package com.tcg.terry.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuButton extends Button {
	
	private float x, y;

	public MenuButton(float x, float y) {
		super("menu", x, y);
		this.x = x;
		this.y = y;
	}

	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
	}

	public void dispose() {
		super.dispose();
	}
	
	public void debug() {
		super.debug();
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}

}
