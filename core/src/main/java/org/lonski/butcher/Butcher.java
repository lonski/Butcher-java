package org.lonski.butcher;

import org.lonski.butcher.dungeon.DungeonStage;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Butcher extends ApplicationAdapter {

	private DungeonStage dungeon;

	@Override
	public void create() {
		dungeon = new DungeonStage();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		dungeon.draw();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
}
