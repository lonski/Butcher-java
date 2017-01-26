package org.lonski.butcher;

import org.lonski.butcher.actors.Player;
import org.lonski.butcher.dungeon.DungeonStage;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

import squidpony.squidmath.Coord;

public class Butcher extends ApplicationAdapter {

	public static final float TILE_SIZE = 64.f;
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

	/**
	 * Transforms position coordinates to orthogonal map of tiles coordinate
	 */
	public static Coord positionToOrtho(float x, float y){
		return Coord.get(Math.round(x / Butcher.TILE_SIZE), Math.round(y / Butcher.TILE_SIZE));
	}

	/**
	 * Transforms orthogonal map coordinates to position in pixels.
	 */
	public static Vector2 orthoToPosition(Coord coord){
		return new Vector2((float) coord.getX() * Butcher.TILE_SIZE, (float) coord.getY() * Butcher.TILE_SIZE);
	}
}
