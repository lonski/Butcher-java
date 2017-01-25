package org.lonski.butcher.dungeon.map;

import squidpony.squidmath.Coord;

public interface DungeonMap {
	void generate(DungeonMapParameters parameters);

	int getHeight();

	int getWidth();

	char getTile(int x, int y);

	char getTile(Coord coord);

	Coord getRandomFloor();
}
