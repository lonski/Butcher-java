package org.lonski.butcher.dungeon.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends Actor {

	private final Texture texture;

	public Player() {
		texture = new Texture(Gdx.files.internal("actors/player.png"));
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(texture, getX(), getY());
	}
}
