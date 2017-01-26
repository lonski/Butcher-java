package org.lonski.butcher.dungeon.map;

import squidpony.squidmath.Coord;

public interface DungeonMap {
	void generate(DungeonMapParameters parameters);

	int getHeight();

	int getWidth();

	char getTileChar(int x, int y);

	char getTileChar(Coord coord);

	char[][] getGrid();

	Coord getRandomFloor();
}
