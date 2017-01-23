package org.lonski.butcher.dungeon.map;

public interface DungeonMap {
	void generate(DungeonMapParameters parameters);

	int getHeight();

	int getWidth();
}
