package org.lonski.butcher;

import org.lonski.butcher.actors.Player;
import org.lonski.butcher.dungeon.DungeonStage;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Butcher extends ApplicationAdapter {

	public static final int TILE_SIZE = 64;
	public static final float ANIMATION_DURATION = 0.15f;

	private static DungeonStage dungeon;
	private static Player player;

	@Override
	public void create() {
		player = new Player();
		player.setPosition(10 * TILE_SIZE, 10 * TILE_SIZE);
		dungeon = new DungeonStage();

		Gdx.input.setInputProcessor(new InputHandler());
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		getDungeonStage().process(Gdx.graphics.getDeltaTime());

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
