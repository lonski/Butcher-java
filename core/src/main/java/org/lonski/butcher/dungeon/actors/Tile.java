package org.lonski.butcher.dungeon.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class Tile extends Actor{

	private final char character;

	public Tile(char character){
		super();
		this.character = character;
	}

	public char getCharacter() {
		return character;
	}
}
