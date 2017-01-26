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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.Viewport;

import squidpony.squidgrid.FOV;
import squidpony.squidgrid.mapping.DungeonUtility;
import squidpony.squidmath.Coord;

public class DungeonStage extends Stage {

	private DungeonMap map;
	private MapLayer mapLayer;
	private Layer objectsLayer;

	private FOV fov;
	private double[][] fovMap;
	private Coord lastFovPoint;

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

		fov = new FOV(FOV.RIPPLE_LOOSE);
		lastFovPoint = Coord.get(0,0);

		putPlayer();

		currentActor = 0;
	}

	public boolean isBlocked(Coord coord) {
		return map.getTileChar(coord) == DungeonMapSymbol.WALL;
	}

	public double getFov(Coord coord) { return fovMap[coord.getX()][coord.getY()]; }

	private void putPlayer() {
		Butcher.getPlayer().setPositionOrtho(map.getRandomFloor());
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
		if (currentAction == null) {
			return;
		}

		//Action is just created - perform it
		if (currentAction.getStatus() == ActionStatus.CREATED) {
			currentAction.perform();
		}

		//Action not performed, wait for actor to take new one
		if (currentAction.getStatus() == ActionStatus.FAILED) {
			return;
		}

		//Action is ongoing - tick time
		if (currentAction.getStatus() == ActionStatus.ONGOING) {
			currentAction.act(delta);
		}

		//Action completed, continue to next actor
		if (currentAction.getStatus() == ActionStatus.SUCCESS) {
			currentAction = null;
			currentActor = (currentActor + 1) % getUpdateableActors().size;
		}
	}

	@Override
	public void draw() {
		calculateFov();
		super.draw();
	}

	/**
	 * @return List of actors on the map: mobs, npcs, objects, player. Omits passive actors like
	 */
	private SnapshotArray<Actor> getUpdateableActors() {
		return objectsLayer.getChildren();
	}

	private void calculateFov() {
		Coord position = Butcher.getPlayer().getPositionOrtho();
		if ( !position.equals(lastFovPoint) ) {

			fovMap = fov.calculateFOV(DungeonUtility.generateResistances(map.getGrid()),
					position.getX(), position.getY(), 8);

			for (int x = 0; x < fovMap.length; x++) {
				for (int y = 0; y < fovMap[0].length; y++) {
					double fovLevel = fovMap[x][y];
					Actor tile = mapLayer.getTile(Coord.get(x, y));
					Color c = tile.getColor();
					tile.setColor(c.r, c.g, c.b, (float) fovLevel);
					tile.setVisible(fovLevel > 0.01);
				}
			}

			lastFovPoint = position;
		}
	}
}
