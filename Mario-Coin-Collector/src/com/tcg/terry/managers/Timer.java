package com.tcg.terry.managers;

public class Timer {
	
	private float totalTime;
	private float currentTime;
	private boolean count;
	
	public Timer(float timer) {
		this.totalTime = timer;
		this.currentTime = this.totalTime;
		count = true;
	}
	
	public void update(float dt) {
		if(count)currentTime -= dt;
	}
	
	public void stop() {
		count = false;
	}
	
	public boolean isCounting() {
		return count;
	}

	public long getCurrentTime() {
		return (long)currentTime;
	}

	public void setTotalTime(float totalTime) {
		this.totalTime = totalTime;
	}
}
