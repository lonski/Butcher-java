package org.lonski.butcher;

import org.lonski.butcher.dungeon.DungeonStage;
import org.lonski.butcher.dungeon.actors.Player;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Butcher extends ApplicationAdapter {

	private static DungeonStage dungeon;
	private static Player player;

	@Override
	public void create() {
		player = new Player();
		player.setPosition(10*64, 10*64);
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

	public static DungeonStage getDungeonStage() {
		return dungeon;
	}

	public static Player getPlayer() {
		return player;
	}
}
