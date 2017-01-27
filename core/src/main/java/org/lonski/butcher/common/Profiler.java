package org.lonski.butcher.common;

import com.badlogic.gdx.Gdx;

public class Profiler {

	private long startTime;
	private long lastMeasurement;

	public Profiler(){
	}

	public Profiler start(boolean log){
		if ( log )
			Gdx.app.log("Profiler", "---START---");

		startTime = lastMeasurement = System.currentTimeMillis();

		return this;
	}

	public void log(String msg){
		long currentMeasurement = System.currentTimeMillis();
		Gdx.app.log("Profiler", String.format("[%s]: %d", msg, currentMeasurement - lastMeasurement));
		lastMeasurement = currentMeasurement;
	}

	public void logFromStart(String msg){
		long currentMeasurement = System.currentTimeMillis();
		Gdx.app.log("Profiler", String.format("[%s] From start: %d", msg, currentMeasurement - startTime));
	}

	public void logFromStartNonZero(String msg){
		long currentMeasurement = System.currentTimeMillis();
		long diff = currentMeasurement - startTime;

		if ( diff > 0 )
			Gdx.app.log("Profiler", String.format("[%s] From start: %d", msg, diff));
	}

	public void silentMeasure(){
		lastMeasurement = System.currentTimeMillis();
	}

}
