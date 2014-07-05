package com.tcg.terry.managers;

import com.tcg.terry.gamestates.*;


public class GameStateManager {
	
	private GameState gameState;
	
	public final int SPLASH = 0;
	public final int MENU = 1;
	public final int PLAY = 2;
	public final int GAMEOVER = 3;
	public final int CONTROLS = 4;
	
	public GameStateManager() {
		setState(SPLASH);
	}
	
	public void update(float dt) {
		gameState.update(dt);
	}
	
	public void draw() {
		gameState.draw();
	}
	
	public void setState(int state) {
		if(gameState != null) gameState.dispose();
		if(state == SPLASH) {
			gameState = new SplashState(this);
		}
		if(state == MENU) {
			gameState = new MenuState(this);
		}
		if(state == PLAY) {
			gameState = new PlayState(this);
		}
		if(state == CONTROLS) {
			gameState = new ControlsState(this);
		}
	}
	
	public void GameOver(long c) {
		gameState = new GameOverState(this, c);
	}

}
