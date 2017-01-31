package org.lonski.butcher.dungeon.map;

import squidpony.squidgrid.mapping.DungeonUtility;
import squidpony.squidmath.Coord;

public class DungeonMap {

	private char[][] grid;
	private DungeonUtility utility;

	private Pathfinder pathfinder;

	public DungeonMap(char[][] grid) {
		this.grid = grid;
		this.utility = new DungeonUtility();
		this.pathfinder = new Pathfinder(grid);
	}

	public Pathfinder getPathfinder() {
		return pathfinder;
	}

	public int getHeight() {
		return grid[0].length;
	}

	public int getWidth() {
		return grid.length;
	}

	public char getTileChar(int x, int y) {
		if (x >= getWidth() || y >= getHeight()) {
			return DungeonMapSymbol.WALL;
		}

		return grid[x][y];
	}

	public char getTileChar(Coord coord) {
		return getTileChar(coord.getX(), coord.getY());
	}

	public Coord getRandomFloor() {
		return utility.randomFloor(grid);
	}

	public char[][] getGrid() {
		return grid;
	}

	@Override
	public String toString() {
		char[][] trans = new char[getHeight()][getWidth()];
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				trans[y][x] = grid[x][y];
			}
		}
		StringBuilder sb = new StringBuilder();
		for (int row = 0; row < getHeight(); row++) {
			sb.append(trans[row]);
			sb.append('\n');
		}
		return sb.toString();
	}
}
