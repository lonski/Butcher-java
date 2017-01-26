package org.lonski.butcher.dungeon;

import squidpony.squidgrid.FOV;
import squidpony.squidgrid.mapping.DungeonUtility;
import squidpony.squidmath.Coord;

/**
 * Function to be applied on given map coordinate
 * to apply tile visibility.
 */
interface FovLevelApplier {
	void apply(Coord coord, float fovLevel);
}

class Fov {

	private final char[][] map;
	private final FovLevelApplier fovLevelApplier;

	private FOV calculator;
	private Coord lastFovPoint;

	Fov(char[][] map, FovLevelApplier fovLevelApplier) {
		this.map = map;
		this.fovLevelApplier = fovLevelApplier;
		this.calculator = new FOV(FOV.RIPPLE_LOOSE);
		this.lastFovPoint = Coord.get(0, 0);
	}

	void calculate(Coord center) {
		if (!center.equals(lastFovPoint)) {

			double[][] fovMap = calculator.calculateFOV(DungeonUtility.generateResistances(map),
					center.getX(), center.getY(), 8);

			for (int x = 0; x < fovMap.length; x++) {
				for (int y = 0; y < fovMap[0].length; y++) {
					float fovLevel = (float) fovMap[x][y];
					fovLevelApplier.apply(Coord.get(x, y), fovLevel);
				}
			}

			lastFovPoint = center;
		}
	}
}
