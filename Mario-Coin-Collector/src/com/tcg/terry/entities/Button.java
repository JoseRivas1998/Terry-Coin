package com.tcg.terry.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.tcg.terry.main.Game;

public abstract class Button {
	
	protected ShapeRenderer sr = new ShapeRenderer();
	protected String text;
	BitmapFont font = new BitmapFont(Game.fontFile, Game.fontImage, false);
	SpriteBatch batch = new SpriteBatch();
	Rectangle bounds;
	float tWidth;
	float tHeight;
	
	public Button(String text, float x, float y) {
		this.text = text;
		this.tWidth = font.getBounds(text).width;
		this.tHeight = font.getBounds(text).height;
		this.bounds = new Rectangle(x, y, tWidth + 10, tHeight * 3);
	}
	
	public void draw() {
		sr.begin(ShapeType.Line);
		sr.setColor(Color.BLACK);
		sr.rect(this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height);
		sr.end();
		batch.begin();
		font.draw(batch, text, bounds.x + 5, bounds.y + tHeight * 2);
		batch.end();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	public void dispose() {
		sr.dispose();batch.dispose();
	}
	
}
