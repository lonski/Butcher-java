package org.lonski.butcher.dungeon;

import org.lonski.butcher.Butcher;
import org.lonski.butcher.actors.AdaptedActor;
import org.lonski.butcher.actors.Cow;
import org.lonski.butcher.dungeon.layers.MapLayer;
import org.lonski.butcher.dungeon.layers.ObjectsLayer;
import org.lonski.butcher.dungeon.map.*;
import org.lonski.butcher.dungeon.map.generators.DungeonMapGenerator;
import org.lonski.butcher.dungeon.map.generators.StandardDungeonMapGenerator;
import org.lonski.butcher.dungeon.tilesets.DungeonTileset;
import org.lonski.butcher.dungeon.map.Pathfinder;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.Viewport;

import squidpony.squidmath.Coord;

public class DungeonStage extends Stage {

	private DungeonMap map;
	private Fov fov;
	private MapLayer mapLayer;
	private ObjectsLayer objectsLayer;

	public DungeonStage(Viewport viewport, Batch batch) {
		super(viewport, batch);
		generateMap();
		initializeMapView();
		initializeObjectsView();
		createFovCalculator();
		calculateFov();
	}

	public Pathfinder getPathfinder() {
		return map.getPathfinder();
	}

	public boolean isInFov(Coord coord) {
		return getFovLevel(coord) > 0.1;
	}

	public double getFovLevel(Coord coord) {
		return fov.getFovLevel(coord);
	}

	public boolean isBlocked(Coord coord) {
		return map.getTileChar(coord) == DungeonMapSymbol.WALL || isBlockedByActor(coord);
	}

	public boolean isBlockedByActor(Coord coord) {
		for (Actor a : getUpdateableActors()) {
			AdaptedActor actor = (AdaptedActor) a;
			if (actor.getPositionOrtho().equals(coord)) {
				return true;
			}
		}
		return false;
	}

	public void calculateFov() {
		fov.calculate(Butcher.getPlayer().getPositionOrtho());
	}

	@Override
	public void draw() {
		super.draw();
	}

	/**
	 * @return List of actors on the map: mobs, npcs, objects, player. Omits passive actors like tiles.
	 */
	public SnapshotArray<Actor> getUpdateableActors() {
		return objectsLayer.getChildren();
	}

	private void generateMap() {
		DungeonMapGenerator generator = new StandardDungeonMapGenerator(80, 50);
		map = generator.generate(new StandardDungeonMapGenerator.Params(6, 6, 3, 5, 3, 5));
	}

	private void initializeMapView() {
		mapLayer = new MapLayer(map, new DungeonTileset(), false);
		addActor(mapLayer);
	}

	private void initializeObjectsView() {
		objectsLayer = new ObjectsLayer();
		addActor(objectsLayer);
		putPlayer();
		putCows();
	}

	private void putPlayer() {
		Butcher.getPlayer().setPositionOrtho(map.getRandomFloor());
		objectsLayer.addActor(Butcher.getPlayer());
	}

	private void putCows() {
		for (int n = 0; n < 5; n++) {
			Cow cow = new Cow();
			Coord pos = map.getRandomFloor();
			if (!isBlocked(pos)) {
				cow.setPositionOrtho(pos);
				objectsLayer.addActor(cow);
			}
		}
	}

	private void createFovCalculator() {
		fov = new Fov(map.getGrid(), new Fov.LevelApplier() {
			@Override
			public void apply(Coord coord, float fovLevel) {
				Actor tile = mapLayer.getTile(coord);
				Color c = tile.getColor();
				if (fovLevel <= 0.1) {
					tile.setColor(c.r, c.g, c.b, 0.1f);
				} else {
					tile.setColor(c.r, c.g, c.b, fovLevel);
					tile.setVisible(true);
				}
			}
		});
	}

}
