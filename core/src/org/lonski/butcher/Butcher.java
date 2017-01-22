package org.lonski.butcher;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Butcher extends ApplicationAdapter {

	@Override
	public void create() {
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
}
