package com.tcg.terry.gamestates;

import com.tcg.terry.main.Game;
import com.tcg.terry.managers.GameStateManager;

public abstract class GameState {
	
	protected GameStateManager gsm;
	
	long c;
	
	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
		Game.res.stopAllSound();
		init();
	}
	
	public GameState(GameStateManager gsm, long c) {
		this.gsm = gsm;
		this.c = c;
	}
	
	public abstract void init();
	public abstract void update(float dt);
	public abstract void draw();
	public abstract void handleInput();
	public abstract void dispose();
	
}
