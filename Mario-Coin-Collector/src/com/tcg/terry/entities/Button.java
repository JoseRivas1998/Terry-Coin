package com.tcg.terry.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public abstract class Button {
	
	protected ShapeRenderer sr = new ShapeRenderer();
	Rectangle bounds;
	protected Texture tex;
	protected String s;
	
	public Button(String path, float x, float y) {
		this.tex = new Texture("entities/buttons/" + path + ".png");
		this.s = path;
		this.bounds = new Rectangle(x, y, tex.getWidth(), tex.getHeight());
	}
	
	public void draw(SpriteBatch batch) {
		System.out.println(this.tex.getWidth() + " is the width");
		batch.draw(this.tex, bounds.x, bounds.y);
	}
	
	public void debug() {
		sr.begin(ShapeType.Line);
		sr.setColor(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1);
		sr.rect(this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height);
		sr.end();
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	public void dispose() {
		sr.dispose();
	}
	
}
