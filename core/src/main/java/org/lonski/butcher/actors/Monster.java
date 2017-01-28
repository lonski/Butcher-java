package org.lonski.butcher.actors;

import java.util.ArrayList;

import org.lonski.butcher.Butcher;
import org.lonski.butcher.actors.actions.MoveAction;
import org.lonski.butcher.common.Direction;
import org.lonski.butcher.dungeon.DungeonStage;

import com.badlogic.gdx.graphics.Texture;

import squidpony.squidai.DijkstraMap;
import squidpony.squidmath.Coord;
import squidpony.squidmath.RNG;

public class Monster extends AdaptedActor {

	private RNG rng;

	public Monster(Texture texture) {
		super(texture);
		rng = new RNG(System.currentTimeMillis());
	}

	@Override
	public void takeTurn() {

		DungeonStage dungeon = Butcher.getDungeonStage();

		if (dungeon.isInFov(getPositionOrtho())) {

			new Thread(new Runnable() {
				@Override
				public void run() {
					char[][] grid = Butcher.getDungeonStage().getMapGrid();
					DijkstraMap dijkstra = new DijkstraMap(grid, DijkstraMap.Measurement.CHEBYSHEV);
					dijkstra.setGoal(Butcher.getPlayer().getPositionOrtho());
					ArrayList<Coord> path =
							dijkstra.findPath(1, null, null, getPositionOrtho(), Butcher.getPlayer().getPositionOrtho());
					setNextAction(new MoveAction(path.get(0).subtract(getPositionOrtho())));
				}
			}).start();

		} else {
			Coord newPos = getPositionOrtho();
			Direction[] possibleDirections = new Direction[8];
			rng.shuffle(Direction.OUTWARDS, possibleDirections);
			for (Direction d : possibleDirections) {
				Coord tmp = getPositionOrtho().add(d.toCoord());
				if (!dungeon.isBlocked(tmp)) {
					newPos = tmp;
					break;
				}
			}
			setNextAction(new MoveAction(newPos.subtract(getPositionOrtho())));
		}


	}
}
