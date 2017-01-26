package org.lonski.butcher.actors;

import org.lonski.butcher.Butcher;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Player extends AdaptedActor {

	private final Texture texture;

	public Player() {
		texture = new Texture(Gdx.files.internal("actors/player.png"));
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(texture, getX(), getY(), Butcher.TILE_SIZE, Butcher.TILE_SIZE);
	}
}
