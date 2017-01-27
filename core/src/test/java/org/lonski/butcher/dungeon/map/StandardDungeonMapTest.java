package org.lonski.butcher.dungeon.map;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class StandardDungeonMapTest {

	@Test
	public void shouldGenerateDungeon() {
		StandardDungeonMap map = new StandardDungeonMap(80, 40);

		map.generate(getParams());

		System.out.println(map);
		assertThat(map.toString()).isNotNull();
		assertThat(map.toString().isEmpty()).isFalse();
	}

	@Test
	public void shouldReturnTile() {
		StandardDungeonMap map = new StandardDungeonMap(80, 40);

		map.generate(getParams());

		assertThat(map.getTileChar(10,30)).isNotEqualTo(' ');
	}

	private StandardDungeonMap.Params getParams() {
		return new StandardDungeonMap.Params(4, 3, 3, 8, 3, 8);
	}
}