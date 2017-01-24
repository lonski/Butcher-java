package org.lonski.butcher.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Tile extends AdaptedActor {

	private final char character;
	private final Texture texture;

	public Tile(char character, Texture texture, int x, int y) {
		super();
		this.character = character;
		this.texture = texture;
		setX((float) x);
		setY((float) y);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(texture, getX(), getY());
	}

	public char getCharacter() {
		return character;
	}
}
