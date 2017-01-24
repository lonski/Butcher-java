package org.lonski.butcher.actors.actions;

import org.lonski.butcher.Butcher;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

public class MoveAction extends AdaptedAction {
	private final MoveByAction animatedAction;

	public MoveAction(int dx, int dy) {
		animatedAction = new MoveByAction();
		animatedAction.setDuration(Butcher.ANIMATION_DURATION);
		animatedAction.setAmount(dx * Butcher.TILE_SIZE, dy * Butcher.TILE_SIZE);
	}

	@Override
	public void setActor(Actor actor) {
		super.setActor(actor);
		animatedAction.setActor(getActor());
	}

	@Override
	public boolean act(float delta) {
		return animatedAction.act(delta);
	}
}
