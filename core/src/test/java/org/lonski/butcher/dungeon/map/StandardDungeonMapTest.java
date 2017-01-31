package org.lonski.butcher.dungeon.map;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.lonski.butcher.dungeon.map.generators.StandardDungeonMapGenerator;

public class StandardDungeonMapTest {

	@Test
	public void shouldGenerateDungeon() {
		StandardDungeonMapGenerator generator = new StandardDungeonMapGenerator(80, 40);
		DungeonMap map = generator.generate(getParams());

		System.out.println(map);
		assertThat(map.toString()).isNotNull();
		assertThat(map.toString().isEmpty()).isFalse();
	}

	@Test
	public void shouldReturnTile() {
		StandardDungeonMapGenerator
				generator = new StandardDungeonMapGenerator(80, 40);

		DungeonMap map = generator.generate(getParams());

		assertThat(map.getTileChar(10, 30)).isNotEqualTo(' ');
	}

	private StandardDungeonMapGenerator.Params getParams() {
		return new StandardDungeonMapGenerator.Params(6, 6, 2, 4, 2, 4);
	}
}