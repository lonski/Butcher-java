package org.lonski.butcher.actors;

import org.lonski.butcher.Butcher;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Tile extends AdaptedActor {

	private final char character;

	public Tile(char character, Texture texture, float x, float y) {
		super(texture);
		this.character = character;
		setX(x);
		setY(y);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color previousColor = batch.getColor();

		batch.setColor(getColor());
		batch.draw(getTexture(), getX(), getY(), Butcher.TILE_SIZE, Butcher.TILE_SIZE);

		batch.setColor(previousColor);
	}

	public char getCharacter() {
		return character;
	}
}
