package org.lonski.butcher.dungeon.map;

import java.util.HashMap;

import squidpony.squidgrid.mapping.DungeonUtility;
import squidpony.squidmath.AStarSearch;

public class Pathfinder {

	private double[][] costMap;

	public Pathfinder(char[][] map){
		costMap = DungeonUtility.generateCostMap(map, new HashMap<Character, Double>(), 1.0);
	}

	public AStarSearch getAStarSearch() {
		return new AStarSearch(costMap, AStarSearch.SearchType.CHEBYSHEV);
	}
}
