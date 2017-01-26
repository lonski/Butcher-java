package org.lonski.butcher.dungeon;

import org.lonski.butcher.Butcher;
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

import squidpony.squidmath.Coord;

public class DungeonStage extends Stage {

	private DungeonMap map;
	private Fov fov;

	private MapLayer mapLayer;
	private Layer objectsLayer;

	public DungeonStage(Viewport viewport, Batch batch) {
		super(viewport, batch);
		generateMap();
		initializeMapView();
		initializeObjectsView();
		createFovCalculator();
	}

	public boolean isBlocked(Coord coord) {
		return map.getTileChar(coord) == DungeonMapSymbol.WALL;
	}

	@Override
	public void draw() {
		fov.calculate(Butcher.getPlayer().getPositionOrtho());
		super.draw();
	}

	/**
	 * @return List of actors on the map: mobs, npcs, objects, player. Omits passive actors like tiles.
	 */
	public SnapshotArray<Actor> getUpdateableActors() {
		return objectsLayer.getChildren();
	}

	private void generateMap() {
		map = new StandardDungeonMap(80, 50);
		map.generate(new StandardDungeonMap.Params(4, 4, 3, 8, 3, 8));
	}

	private void initializeMapView() {
		mapLayer = new MapLayer(map, new DungeonTileset());
		addActor(mapLayer);
	}

	private void initializeObjectsView() {
		objectsLayer = new Layer();
		addActor(objectsLayer);
		putPlayer();
	}

	private void putPlayer() {
		Butcher.getPlayer().setPositionOrtho(map.getRandomFloor());
		objectsLayer.addActor(Butcher.getPlayer());
	}

	private void createFovCalculator() {
		fov = new Fov(map.getGrid(), new Fov.LevelApplier() {
			@Override
			public void apply(Coord coord, float fovLevel) {
				Actor tile = mapLayer.getTile(coord);
				Color c = tile.getColor();
				tile.setColor(c.r, c.g, c.b, fovLevel);
			}
		});
	}

}
