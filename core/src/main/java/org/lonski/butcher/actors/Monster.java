package org.lonski.butcher.actors;

import java.util.Queue;

import org.lonski.butcher.Butcher;
import org.lonski.butcher.actors.actions.MoveAction;
import org.lonski.butcher.common.Direction;
import org.lonski.butcher.dungeon.DungeonStage;

import com.badlogic.gdx.graphics.Texture;

import squidpony.squidmath.AStarSearch;
import squidpony.squidmath.Coord;
import squidpony.squidmath.RNG;

public class Monster extends AdaptedActor {

	private RNG rng;
	private Thread pathfindThread;

	public Monster(Texture texture) {
		super(texture);
		rng = new RNG(System.currentTimeMillis());
		pathfindThread = null;
	}

	@Override
	public void takeTurn() {

		final DungeonStage dungeon = Butcher.getDungeonStage();

		if (dungeon.isInFov(getPositionOrtho())) {

			if (pathfindThread == null || !pathfindThread.isAlive()) {

				pathfindThread = new Thread(new Runnable() {
					@Override
					public void run() {
						AStarSearch astar = Butcher.getDungeonStage().getPathfinder().getAStarSearch();

						Coord start = getPositionOrtho();
						Coord goal = Butcher.getPlayer().getPositionOrtho();

						Queue<Coord> path = astar.path(start.getX(), start.getY(), goal.getX(), goal.getY());

						if (path != null) {
							setNextAction(new MoveAction(Direction.fromCoords(getPositionOrtho(), path.peek())));
						} else {
							randomMove(dungeon);
						}
					}
				});

				pathfindThread.start();
			}
		} else {
			randomMove(dungeon);
		}

	}

	private void randomMove(DungeonStage dungeon) {
		Direction direction = Direction.NONE;
		Direction[] possibleDirections = new Direction[8];
		rng.shuffle(Direction.OUTWARDS, possibleDirections);
		for (Direction d : possibleDirections) {
			if (!dungeon.isBlocked(getPositionOrtho().add(d.toCoord()))) {
				direction = d;
				break;
			}
		}
		setNextAction(new MoveAction(direction));
	}
}
