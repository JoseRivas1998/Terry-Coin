package com.tcg.terry.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.tcg.terry.main.Game;

public class Player {
	
	private Sprite sprite;
	private String path = "entities/terry/";
	private TextureRegion currentFrame;
	private float delay = .10f;
	private Ground g;
	private Cursor c;
	private float xVel;
	private Rectangle bounds;
	private JumpButton lJ, rJ;
	private SprintButton lS, rS;
	float yVel;
	
	private TextureRegion[] rj = {
		new TextureRegion(new Texture(Gdx.files.internal(path+"jump-right.png")))	
	};
	
	private TextureRegion[] lj = {
			new TextureRegion(new Texture(Gdx.files.internal(path+"jump-left.png")))	
	};
	
	private TextureRegion[] ri = {
			new TextureRegion(new Texture(Gdx.files.internal(path+"idle-right.png")))
	};
	
	private TextureRegion[] li = {
			new TextureRegion(new Texture(Gdx.files.internal(path+"idle-left.png")))
	};
	
	private TextureRegion[] rw = {
			new TextureRegion(new Texture(Gdx.files.internal(path+"walk-right-03.png"))),
			new TextureRegion(new Texture(Gdx.files.internal(path+"walk-right-02.png"))),
			new TextureRegion(new Texture(Gdx.files.internal(path+"walk-right-01.png")))
	};
	
	private TextureRegion[] lw = {
			new TextureRegion(new Texture(Gdx.files.internal(path+"walk-left-03.png"))),
			new TextureRegion(new Texture(Gdx.files.internal(path+"walk-left-02.png"))),
			new TextureRegion(new Texture(Gdx.files.internal(path+"walk-left-01.png")))
	};
	
	private Animation jumpR;
	private Animation jumpL;
	private Animation idleR;
	private Animation idleL;
	private Animation walkR;
	private Animation walkL;
	private float stateTime;
	
	private enum Direction {
		LEFT, RIGHT
	}
	
	private enum Movement {
		IDLE, JUMP, WALK
	}

	Direction dir;
	Movement move;

	public Player(Direction dir) {
		this.dir = dir;
	}
	
	public Player(Movement move) {
		this.move = move;
	}
	
	public Player(Ground g, JumpButton lJ, JumpButton rJ, Cursor c, SprintButton lS, SprintButton rS) {
		sprite = new Sprite();
		bounds = new Rectangle();
		
		this.g = g;
		this.rJ = rJ;
		this.lJ = lJ;
		this.lS = lS;
		this.rS = rS;
		this.c = c;
		
		stateTime = 0;
		
		jumpR = new Animation(delay, rj);
		jumpL = new Animation(delay, lj);
		idleR = new Animation(delay, ri);
		idleL = new Animation(delay, li);
		walkR = new Animation(delay, rw);
		walkL = new Animation(delay, lw);
		
		this.dir = Direction.RIGHT;
		this.move = Movement.IDLE;
		
		sprite.setPosition(Game.WIDTH / 2 - 16, Game.HEIGHT * .75f);
		yVel = 0;
	}
	
	@SuppressWarnings("incomplete-switch")
	public void update(float dt) {
		switch(Gdx.app.getType()) {
		case Desktop: handleDeskTopInput(); break;
		case Android: handleTouch(); break;
		}
		sprite.translateY(yVel);
		bounds.set(sprite.getX(), sprite.getY(), 64, 64);
	}
	
	public void handleDeskTopInput() {
		this.move = Movement.IDLE;
		if(bounds.overlaps(g.getBounds())) {
			yVel = 0;
			if (Gdx.input.isKeyPressed(Keys.SPACE)) {
				this.move = Movement.JUMP;
				Game.res.getSound("jump").play();
				yVel = 20;
			}
		} else {
			yVel--;
		}
		if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT))   {
			if (this.move == Movement.IDLE)
			{
				this.move = Movement.WALK;
				if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT)) {
					xVel = -7;
				} else {
					xVel = -5;
				}
			}
			this.dir = Direction.LEFT;
		} else if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
			if (this.move == Movement.IDLE)
			{
				this.move = Movement.WALK;
				if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT)) {
					xVel = 7;
				} else {
					xVel = 5;
				}
			}
			this.dir = Direction.RIGHT;
		} else {
			if(bounds.overlaps(g.getBounds())) {
				this.move = Movement.IDLE;
				xVel = 0;
			} else {
				this.move = Movement.JUMP;
				xVel = 0;
			}
		}
		if((sprite.getX() <= 0 && this.dir == Direction.LEFT) || ((sprite.getX() + 48) >= Game.WIDTH && this.dir == Direction.RIGHT)) {
			xVel = 0;
		}
		sprite.translateX(xVel);
	}
	
	public void handleTouch() {
		this.move = Movement.IDLE;
			if(bounds.overlaps(g.getBounds())) {
				yVel = 0;
				if (c.getBounds().overlaps(lJ.getBounds()) || c.getBounds().overlaps(rJ.getBounds())) {
					this.move = Movement.JUMP;
					Game.res.getSound("jump").play();
					yVel = 20;
				}
			} else {
				yVel--;
			}
			if(Gdx.input.isTouched()) {
				if (Gdx.input.getX() < Game.WIDTH * .5)   {
					if (this.move == Movement.IDLE)
					{
						this.move = Movement.WALK;
						if(c.getBounds().overlaps(lS.getBounds())) {
							xVel = -7;
						} else {
							xVel = -5;
						}
					}
					this.dir = Direction.LEFT;
				} else if (Gdx.input.getX() > Game.WIDTH * .5) {
					if (this.move == Movement.IDLE)
					{
						this.move = Movement.WALK;
						if(c.getBounds().overlaps(rS.getBounds())) {
							xVel = 7;
						} else {
							xVel = 5;
						}
					}
					this.dir = Direction.RIGHT;
				}
			} else {
				if(bounds.overlaps(g.getBounds())) {
					this.move = Movement.IDLE;
					xVel = 0;
				} else {
					this.move = Movement.JUMP;
					xVel = 0;
				}
		}
		if((sprite.getX() <= 0 && this.dir == Direction.LEFT) || ((bounds.x + bounds.width) >= Game.WIDTH && this.dir == Direction.RIGHT)) {
			xVel = 0;
		}
		sprite.translateX(xVel);
	}
	
	public void draw(SpriteBatch sb, float dt) {
		stateTime += dt;
		switch(move) {
		case IDLE:
			switch(dir) {
			case LEFT :
				currentFrame = idleL.getKeyFrame(stateTime, true);
				break;
			case RIGHT :
				currentFrame = idleR.getKeyFrame(stateTime, true);
				break;
			}
			break;
		case WALK:
			switch(dir) {
			case LEFT :
				currentFrame = walkL.getKeyFrame(stateTime, true);
				break;
			case RIGHT :
				currentFrame = walkR.getKeyFrame(stateTime, true);
				break;
			}
			break;
		case JUMP:
			switch(dir) {
			case LEFT :
				currentFrame = jumpL.getKeyFrame(stateTime, true);
				break;
			case RIGHT :
				currentFrame = jumpR.getKeyFrame(stateTime, true);
				break;
			}
			break;
		}
		sb.draw(currentFrame, sprite.getX(), sprite.getY(), sprite.getOriginX(), sprite.getOriginY(), bounds.width, bounds.height, 1, 1, 0);
	}
	
	public Rectangle getBounds() { return bounds; }

}
