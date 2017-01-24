package org.lonski.butcher;

import org.lonski.butcher.actors.Player;
import org.lonski.butcher.dungeon.DungeonStage;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Butcher extends ApplicationAdapter {

	public static final int TILE_SIZE = 64;
	public static final float ANIMATION_DURATION = 0.15f;

	private static DungeonStage dungeon;
	private static Player player;

	private OrthographicCamera camera;
	private FitViewport viewport;
	private SpriteBatch batch;

	@Override
	public void create() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

		player = new Player();
		player.setPosition(10 * TILE_SIZE, 10 * TILE_SIZE);

		dungeon = new DungeonStage(viewport, batch);

		Gdx.input.setInputProcessor(new InputHandler());
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		getDungeonStage().act(Gdx.graphics.getDeltaTime());

		camera.position.set(player.getX() - TILE_SIZE / 2, player.getY() - TILE_SIZE / 2, 0);
		batch.setProjectionMatrix(camera.combined);
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
