package org.lonski.butcher.dungeon;

import squidpony.squidgrid.FOV;
import squidpony.squidgrid.mapping.DungeonUtility;
import squidpony.squidmath.Coord;

class Fov {

	/**
	 * Function to be applied on given map coordinate
	 * to apply tile visibility.
	 */
	interface LevelApplier {
		void apply(Coord coord, float fovLevel);
	}

	private final char[][] map;
	private final LevelApplier fovLevelApplier;

	private FOV calculator;
	private Coord lastFovPoint;
	private double[][] fovMap;

	Fov(char[][] map, LevelApplier fovLevelApplier) {
		this.map = map;
		this.fovLevelApplier = fovLevelApplier;
		this.calculator = new FOV(FOV.RIPPLE);
		this.lastFovPoint = Coord.get(0, 0);
	}

	void calculate(Coord center) {
		if (!center.equals(lastFovPoint)) {

			fovMap = calculator.calculateFOV(DungeonUtility.generateResistances(map),
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

	public double getFovLevel(Coord coord){
		if ( coord.getX() >= fovMap.length || coord.getY() >= fovMap[0].length )
			return 0.0;

		return fovMap[coord.getX()][coord.getY()];
	}
}
