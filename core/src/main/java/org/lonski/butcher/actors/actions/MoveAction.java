package org.lonski.butcher.actors.actions;

import org.lonski.butcher.Butcher;

import com.badlogic.gdx.Gdx;

public class MoveAction extends AdaptedAction {
	private final int dx;
	private final int dy;

	public MoveAction(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	@Override
	public boolean act(float delta) {

		getActor().setX(getActor().getX() + dx * Butcher.TILE_SIZE);
		getActor().setY(getActor().getY() + dy * Butcher.TILE_SIZE);

		return true;
	}
}
