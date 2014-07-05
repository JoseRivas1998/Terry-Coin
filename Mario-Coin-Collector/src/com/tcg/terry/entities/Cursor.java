package com.tcg.terry.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.tcg.terry.main.Game;

public class Cursor {

	private Rectangle bounds = new Rectangle();
	
	public void update() {
		float touchY = Game.HEIGHT - Gdx.input.getY();
		if(Gdx.input.isTouched()) {
			bounds.set(Gdx.input.getX(), touchY, 5, 5);
		} else {
			bounds.set(0, 0, 5, 5);
		}
	}
	
	public void draw(ShapeRenderer sr) {
		sr.rect(bounds.x, bounds.y, bounds.width, bounds.height);
		sr.line(bounds.x + 2, Game.HEIGHT, bounds.x + 2, 0);
		sr.line(0, bounds.y + 2, Game.WIDTH, bounds.y + 2);
	}

	public Rectangle getBounds() {
		return bounds;
	}
	
}
