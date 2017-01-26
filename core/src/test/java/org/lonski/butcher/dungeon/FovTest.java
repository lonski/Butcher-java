package org.lonski.butcher.dungeon;

import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import squidpony.squidmath.Coord;

@RunWith(MockitoJUnitRunner.class)
public class FovTest {

	@Mock
	private FovLevelApplier fovLevelApplier;

	@Test
	public void shouldApplyFovLevel() {
		Fov fov = new Fov(getExampleMap(), fovLevelApplier);
		Coord point = Coord.get(4, 2);

		fov.calculate(point);

		verify(fovLevelApplier, atLeastOnce()).apply(isA(Coord.class), anyFloat());
	}

	@Test
	public void shouldNotApplyFovLevelTwiceOnTheSamePoint() {
		Coord point = Coord.get(4, 2);
		Fov fov = new Fov(getExampleMap(), fovLevelApplier);

		fov.calculate(point);
		fov.calculate(point);
		fov.calculate(point);

		verify(fovLevelApplier, times(countCells(getExampleMap()))).apply(isA(Coord.class), anyFloat());
	}

	private char[][] getExampleMap() {
		return new char[][]{
				"##########".toCharArray(),
				"#........#".toCharArray(),
				"#........#".toCharArray(),
				"#........#".toCharArray(),
				"##########".toCharArray(),
		};
	}

	private int countCells(char[][] grid){
		return grid.length * grid[0].length;
	}

}