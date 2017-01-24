package org.lonski.butcher.dungeon.tilesets;

import org.lonski.butcher.dungeon.map.TileSymbol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class DungeonTileset implements Tileset {

	private final Texture WALL;
	private final Texture FLOOR;

	public DungeonTileset() {
		WALL = new Texture(Gdx.files.internal("tilesets/standard_dungeon/wall.png"));
		FLOOR = new Texture(Gdx.files.internal("tilesets/standard_dungeon/floor.png"));
	}

	@Override
	public Texture getTexture(char c) {
		switch (c) {
		case TileSymbol.WALL:
			return WALL;
		case TileSymbol.DOOR_CLOSED:
		case TileSymbol.DOOR_OPEN:
		case TileSymbol.FLOOR:
			return FLOOR;
		default:
			return WALL;
		}
	}

}
