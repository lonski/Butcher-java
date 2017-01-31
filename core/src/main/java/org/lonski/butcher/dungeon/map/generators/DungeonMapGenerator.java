package org.lonski.butcher.dungeon.map.generators;

import org.lonski.butcher.dungeon.map.DungeonMap;

public interface DungeonMapGenerator {
	DungeonMap generate(DungeonMapParameters parameters);
}
