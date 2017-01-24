package org.lonski.butcher.dungeon;

import org.lonski.butcher.Butcher;
import org.lonski.butcher.common.Layer;
import org.lonski.butcher.dungeon.layers.MapLayer;
import org.lonski.butcher.dungeon.map.DungeonMap;
import org.lonski.butcher.dungeon.map.StandardDungeonMap;
import org.lonski.butcher.dungeon.tilesets.DungeonTileset;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class DungeonStage extends Stage {

	private DungeonMap map;
	private Layer mapLayer;
	private Layer objectsLayer;

	public DungeonStage() {
		super();

		map = new StandardDungeonMap(80,40);
		map.generate(new StandardDungeonMap.Params(4, 4, 3, 8, 3, 8));

		mapLayer = new MapLayer(map, new DungeonTileset());
		addActor(mapLayer);

		objectsLayer = new Layer();
		objectsLayer.addActor(Butcher.getPlayer());
		addActor(objectsLayer);
	}

	@Override
	public void draw() {
		super.draw();
	}
}
