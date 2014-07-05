package com.tcg.terry.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tcg.terry.main.Game;

public class Ground {
	
	Vector2 position, size;
	Rectangle bounds;
	ShapeRenderer sr;
	
	public Ground() {
		this.position = new Vector2(-10, 0);
		this.size = new Vector2(Game.WIDTH + 20, Game.HEIGHT * .25f);
		sr = new ShapeRenderer();
		bounds = new Rectangle(position.x, position.y, size.x, size.y);
	}
	
	public void draw() {
		sr.begin(ShapeType.Filled);
		sr.setColor(0, 1, 0, 1);
		sr.rect(bounds.x, bounds.y, bounds.width, bounds.height);
		sr.end();
	}

	public Rectangle getBounds() {
		return bounds;
	}
	
	public void dispose() {
		sr.dispose();
	}
	
}
