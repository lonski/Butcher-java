package org.lonski.butcher.dungeon;

import org.lonski.butcher.Butcher;
import org.lonski.butcher.actors.AdaptedActor;
import org.lonski.butcher.actors.actions.ActionStatus;
import org.lonski.butcher.actors.actions.AdaptedAction;
import org.lonski.butcher.common.Layer;
import org.lonski.butcher.dungeon.layers.MapLayer;
import org.lonski.butcher.dungeon.map.DungeonMap;
import org.lonski.butcher.dungeon.map.DungeonMapSymbol;
import org.lonski.butcher.dungeon.map.StandardDungeonMap;
import org.lonski.butcher.dungeon.tilesets.DungeonTileset;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.Viewport;

import squidpony.squidmath.Coord;

public class DungeonStage extends Stage {

	private DungeonMap map;
	private Layer mapLayer;
	private Layer objectsLayer;

	private int currentActor;
	private AdaptedAction currentAction;

	public DungeonStage(Viewport viewport, Batch batch) {
		super(viewport, batch);

		map = new StandardDungeonMap(80, 50);
		map.generate(new StandardDungeonMap.Params(4, 4, 3, 8, 3, 8));

		mapLayer = new MapLayer(map, new DungeonTileset());
		addActor(mapLayer);

		objectsLayer = new Layer();
		addActor(objectsLayer);

		putPlayer();

		currentActor = 0;
	}

	public boolean isBlocked(Coord coord){
		return map.getTile(coord) == DungeonMapSymbol.WALL;
	}

	private void putPlayer() {
		Butcher.getPlayer().setPosition(map.getRandomFloor().multiply(Butcher.TILE_SIZE));
		objectsLayer.addActor(Butcher.getPlayer());
	}

	@Override
	public void act(float delta) {
		//Fetch actor currently taking its turn
		AdaptedActor actor = (AdaptedActor) getUpdateableActors().get(currentActor);
		if (actor == null) {
			return;
		}

		// Fetch actor's action
		if (currentAction == null) {
			currentAction = actor.getNextAction();
		}

		//No action set, wait for actor to take one
		if (currentAction == null ) {
			return;
		}

		//Action is just created - perform it
		if (currentAction.getStatus() == ActionStatus.CREATED){
			currentAction.perform();
		}

		//Action not performed, wait for actor to take new one
		if(currentAction.getStatus() == ActionStatus.FAILED){
			return;
		}

		//Action is ongoing - tick time
		if(currentAction.getStatus() == ActionStatus.ONGOING){
			currentAction.act(delta);
		}

		//Action completed, continue to next actor
		if (currentAction.getStatus() == ActionStatus.SUCCESS){
			currentAction = null;
			currentActor = (currentActor + 1) % getUpdateableActors().size;
		}
	}

	/**
	 * @return List of actors on the map: mobs, npcs, objects, player. Omits passive actors like
	 */
	private SnapshotArray<Actor> getUpdateableActors() {
		return objectsLayer.getChildren();
	}

	@Override
	public void draw() {
		super.draw();
	}
}
