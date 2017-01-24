package org.lonski.butcher;

import org.lonski.butcher.actors.AdaptedActor;
import org.lonski.butcher.actors.Player;
import org.lonski.butcher.dungeon.DungeonStage;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.SnapshotArray;

public class Butcher extends ApplicationAdapter {

	private static DungeonStage dungeon;
	private static Player player;

	@Override
	public void create() {
		player = new Player();
		player.setPosition(10 * 64, 10 * 64);
		dungeon = new DungeonStage();
	}

	/**
	 * Main game loop
	 */
	@Override
	public void render() {
		SnapshotArray<Actor> actors = getDungeonStage().getUpdateableActors();

		for (Actor a : actors) {
			AdaptedActor actor = (AdaptedActor) a;

			if (actor == null) {
				break;
			}

			Action action = actor.getCurrentAction();
			if (action == null) {
				break;
			}

			while( !action.act(Gdx.graphics.getDeltaTime()) ){
				draw();
			}
		}

		draw();
	}

	private void draw(){
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
