package org.lonski.butcher.dungeon.layers;

import org.lonski.butcher.Butcher;
import org.lonski.butcher.common.Layer;
import org.lonski.butcher.actors.Tile;
import org.lonski.butcher.dungeon.map.DungeonMap;
import org.lonski.butcher.dungeon.tilesets.Tileset;

import com.badlogic.gdx.graphics.Texture;

public class MapLayer extends Layer{

	private final DungeonMap map;
	private final Tileset tileset;

	public MapLayer(DungeonMap map, Tileset tileset){
		this.map = map;
		this.tileset = tileset;
		initializeTiles();
	}

	private void initializeTiles() {
		for(int x = 0; x < map.getWidth(); x++){
			for(int y = 0; y < map.getHeight(); y++){
				char c = map.getTile(x,y);
				addActor(new Tile(c, tileset.getTexture(c), x * Butcher.TILE_SIZE, y * Butcher.TILE_SIZE));
			}
		}
	}
}
