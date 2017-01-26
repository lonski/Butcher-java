package org.lonski.butcher.dungeon.map;

import squidpony.squidgrid.mapping.ClassicRogueMapGenerator;
import squidpony.squidgrid.mapping.DungeonGenerator;
import squidpony.squidmath.Coord;
import squidpony.squidmath.RNG;

public class StandardDungeonMap implements DungeonMap {

	private int height;
	private int width;
	private DungeonGenerator generator;
	private RNG rng;

	public StandardDungeonMap(int width, int height) {
		this.width = width;
		this.height = height;
		this.rng = new RNG(System.currentTimeMillis());
		this.generator = new DungeonGenerator(width, height);
	}

	@Override
	public void generate(DungeonMapParameters parameters) {
		Params params = (Params) parameters;

		if (params == null) {
			throw new RuntimeException("Invalid parameters passed!");
		}

		ClassicRogueMapGenerator rogueGen = new ClassicRogueMapGenerator(
				params.horizontalRooms, params.verticalRooms,
				width, height,
				params.minRoomWidth, params.maxRoomWidth,
				params.minRoomHeight, params.maxRoomHeight,
				rng);

		generator.addDoors(30, false);
		generator.generate(rogueGen.generate());
	}

	@Override
	public String toString() {
		return generator.toString();
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public char getTileChar(int x, int y){
		return generator.getDungeon()[x][y];
	}

	@Override
	public char getTileChar(Coord coord){
		return generator.getDungeon()[coord.getX()][coord.getY()];
	}

	@Override
	public Coord getRandomFloor() {
		return generator.utility.randomFloor(generator.getBareDungeon());
	}

	@Override
	public char[][] getGrid(){
		return generator.getDungeon();
	}

	@Override
	public int getWidth() {
		return width;
	}

	public static class Params implements DungeonMapParameters {
		public Params(int horizontalRooms, int verticalRooms, int minRoomHeight, int maxRoomHeight,
				int minRoomWidth, int maxRoomWidth) {
			this.horizontalRooms = horizontalRooms;
			this.verticalRooms = verticalRooms;
			this.minRoomHeight = minRoomHeight;
			this.maxRoomHeight = maxRoomHeight;
			this.minRoomWidth = minRoomWidth;
			this.maxRoomWidth = maxRoomWidth;
		}

		int horizontalRooms;
		int verticalRooms;
		int minRoomHeight;
		int maxRoomHeight;
		int minRoomWidth;
		int maxRoomWidth;
	}

}
