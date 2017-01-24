package org.lonski.butcher.dungeon.layers;

import org.lonski.butcher.common.Layer;
import org.lonski.butcher.dungeon.actors.Tile;
import org.lonski.butcher.dungeon.map.DungeonMap;

public class MapLayer extends Layer{

	private final DungeonMap map;

	public MapLayer(DungeonMap map){
		this.map = map;
		initializeTiles();
	}

	private void initializeTiles() {
		for(int x = 0; x < map.getWidth(); x++){
			for(int y = 0; y < map.getHeight(); y++){
				addActor(new Tile(map.getTile(x,y)));
			}
		}
	}
}
