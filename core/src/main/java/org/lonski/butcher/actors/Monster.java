package org.lonski.butcher.actors;

import java.util.ArrayList;

import org.lonski.butcher.Butcher;
import org.lonski.butcher.actors.actions.MoveAction;
import org.lonski.butcher.common.Direction;
import org.lonski.butcher.common.Profiler;
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
//		Profiler profiler = new Profiler();
//		profiler.start();

		AdaptedActor player = Butcher.getPlayer();
		DungeonStage dungeon = Butcher.getDungeonStage();
		Coord newPos;

		if ( dungeon.isInFov(getPositionOrtho())) {

//		profiler.silentMeasure();
			DijkstraMap dijkstra = Butcher.getDungeonStage().getPathfindingMap();
			dijkstra.setGoal(player.getPositionOrtho());
			ArrayList<Coord> path = dijkstra.findPath(1, null, null, getPositionOrtho(), player.getPositionOrtho());
			newPos = path.get(0);
//		double[][] pathMap = dijkstra.scan(null);
//		profiler.log("dijkstra map generation");

//		Direction[] possibleDirections = new Direction[8];
//		rng.shuffle(Direction.OUTWARDS, possibleDirections);
//
//		double best = 9999.0;
//		Coord newPosition = getPositionOrtho();
//
//		for (Direction d : possibleDirections) {
////		Direction d = possibleDirections[0];
//			Coord tmp = getPositionOrtho().add(d.toCoord());
//			if (pathMap[tmp.x][tmp.y] < best && !dungeon.isBlocked(tmp)) {
//				best = pathMap[tmp.x][tmp.y];
//				newPosition = tmp;
//			}
//		}
		}
		else {
			Direction d = rng.getRandomElement(Direction.OUTWARDS);
			newPos = getPositionOrtho().add(d.toCoord());
		}

		setNextAction(new MoveAction(newPos.subtract(getPositionOrtho())));

//		profiler.log("determination");
//		profiler.logFromStart("all");
	}
}
