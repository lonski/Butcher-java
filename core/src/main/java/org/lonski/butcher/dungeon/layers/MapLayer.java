package org.lonski.butcher.dungeon.layers;

import org.lonski.butcher.Butcher;
import org.lonski.butcher.actors.Tile;
import org.lonski.butcher.common.Layer;
import org.lonski.butcher.dungeon.map.DungeonMap;
import org.lonski.butcher.dungeon.tilesets.Tileset;

import com.badlogic.gdx.scenes.scene2d.Actor;

import squidpony.squidmath.Coord;

public class MapLayer extends Layer {

	private final DungeonMap map;
	private final Tileset tileset;
	private Actor[][] tiles;

	public MapLayer(DungeonMap map, Tileset tileset) {
		this.map = map;
		this.tileset = tileset;
		initializeTiles();
	}

	public Actor getTile(Coord coord) {
		return tiles[coord.getX()][coord.getY()];
	}

	private void initializeTiles() {
		tiles = new Actor[map.getWidth()][map.getHeight()];

		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				char c = map.getTileChar(x, y);
				Actor tile = new Tile(c, tileset.getTexture(c), (float) x * Butcher.TILE_SIZE, (float) y * Butcher.TILE_SIZE);
				tiles[x][y] = tile;
				addActor(tile);
			}
		}
	}
}
