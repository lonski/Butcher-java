package org.lonski.butcher.common;

import squidpony.squidmath.Coord;

public enum Direction {
	UP(0, 1),
	DOWN(0, -1),
	LEFT(-1, 0),
	RIGHT(1, 0),
	UP_LEFT(-1, 1),
	UP_RIGHT(1, 1),
	DOWN_LEFT(-1, -1),
	DOWN_RIGHT(1, -1),
	NONE(0, 0);

	public static final Direction[] CARDINALS = {UP, DOWN, LEFT, RIGHT};
	public static final Direction[] DIAGONALS = {UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT};
	public static final Direction[] OUTWARDS = {UP, DOWN, LEFT, RIGHT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT};

	public final int deltaX;
	public final int deltaY;

	Direction(int x, int y) {
		deltaX = x;
		deltaY = y;
	}

	public Coord toCoord() {
		return Coord.get(deltaX, deltaY);
	}
}
