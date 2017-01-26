package org.lonski.butcher.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Tile extends AdaptedActor {

	private final char character;
	private final Texture texture;

	public Tile(char character, Texture texture, float x, float y) {
		super();
		this.character = character;
		this.texture = texture;
		setX(x);
		setY(y);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color c = batch.getColor();
		batch.setColor(getColor());
		batch.draw(texture, getX(), getY());
		batch.setColor(c.r, c.g, c.b, 1.f);
	}

	public char getCharacter() {
		return character;
	}
}
