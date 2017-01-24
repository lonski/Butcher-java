package org.lonski.butcher.dungeon.tilesets;

import org.lonski.butcher.dungeon.map.DungeonMapSymbol;

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
		case DungeonMapSymbol.WALL:
			return WALL;
		case DungeonMapSymbol.DOOR_CLOSED:
		case DungeonMapSymbol.DOOR_OPEN:
		case DungeonMapSymbol.FLOOR:
			return FLOOR;
		default:
			return WALL;
		}
	}

}
