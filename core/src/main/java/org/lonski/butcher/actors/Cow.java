package org.lonski.butcher.actors;

import org.lonski.butcher.Butcher;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Cow extends Monster{

	public Cow() {
		super(new Texture(Gdx.files.internal("actors/cow.png")));
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(getTexture(), getX(), getY(), Butcher.TILE_SIZE, Butcher.TILE_SIZE);
	}
}
