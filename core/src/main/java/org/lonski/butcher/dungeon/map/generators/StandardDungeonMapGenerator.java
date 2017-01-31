package org.lonski.butcher.dungeon.map.generators;

import org.lonski.butcher.dungeon.map.DungeonMap;

import squidpony.squidgrid.mapping.ClassicRogueMapGenerator;
import squidpony.squidgrid.mapping.DungeonGenerator;
import squidpony.squidmath.RNG;

public class StandardDungeonMapGenerator implements DungeonMapGenerator {

	private int height;
	private int width;
	private DungeonGenerator generator;
	private RNG rng;

	public StandardDungeonMapGenerator(int width, int height) {
		this.width = width;
		this.height = height;
		this.rng = new RNG(System.currentTimeMillis());
		this.generator = new DungeonGenerator(width, height);
	}

	@Override
	public DungeonMap generate(DungeonMapParameters parameters) {
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
		runGenerateUntilSuccess(rogueGen);

		return new DungeonMap(generator.getDungeon());
	}

	private void runGenerateUntilSuccess(ClassicRogueMapGenerator rogueGen) {
		try {
			generator.generate(rogueGen.generate());
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Regenerating map..");
			runGenerateUntilSuccess(rogueGen);
		}
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
