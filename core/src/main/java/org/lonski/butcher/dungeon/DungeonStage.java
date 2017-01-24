package org.lonski.butcher.dungeon;

import org.lonski.butcher.Butcher;
import org.lonski.butcher.actors.AdaptedActor;
import org.lonski.butcher.actors.actions.AdaptedAction;
import org.lonski.butcher.common.Layer;
import org.lonski.butcher.dungeon.layers.MapLayer;
import org.lonski.butcher.dungeon.map.DungeonMap;
import org.lonski.butcher.dungeon.map.StandardDungeonMap;
import org.lonski.butcher.dungeon.tilesets.DungeonTileset;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.Viewport;

public class DungeonStage extends Stage {

	private DungeonMap map;
	private Layer mapLayer;
	private Layer objectsLayer;

	private int currentActor;
	private AdaptedAction currentAction;

	public DungeonStage(Viewport viewport, Batch batch) {
		super(viewport, batch);

		map = new StandardDungeonMap(80, 40);
		map.generate(new StandardDungeonMap.Params(4, 4, 3, 8, 3, 8));

		mapLayer = new MapLayer(map, new DungeonTileset());
		addActor(mapLayer);

		objectsLayer = new Layer();
		objectsLayer.addActor(Butcher.getPlayer());
		addActor(objectsLayer);

		currentActor = 0;
	}

	@Override
	public void act(float delta) {
		AdaptedActor actor = (AdaptedActor) getUpdateableActors().get(currentActor);
		if (actor == null) {
			return;
		}

		if (currentAction == null) {
			currentAction = actor.getNextAction();
		}

		if (currentAction == null || currentAction.act(delta)) {
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
