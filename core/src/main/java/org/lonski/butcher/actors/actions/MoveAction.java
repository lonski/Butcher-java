package org.lonski.butcher.actors.actions;

import org.lonski.butcher.Butcher;
import org.lonski.butcher.actors.AdaptedActor;
import org.lonski.butcher.common.Direction;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

import squidpony.squidmath.Coord;

public class MoveAction extends AdaptedAction {
	private final Direction direction;

	private MoveToAction animatedAction;

	public MoveAction(Direction direction) {
		this.direction = direction;
		this.animatedAction = null;
	}

	@Override
	public void setActor(Actor actor) {
		super.setActor(actor);

	}

	@Override
	public boolean act(float delta) {
		if (getStatus() == ActionStatus.SUCCESS) {
			return true;
		}

		if (animatedAction.act(delta)) {
			setStatus(ActionStatus.SUCCESS);
		}

		return getStatus() != ActionStatus.ONGOING;
	}

	@Override
	public void perform() {
		Coord position = ((AdaptedActor) getActor()).getPositionOrtho();
		Coord destination = position.add(direction.toCoord());

		if (Butcher.getDungeonStage().isBlocked(destination)) {
			setStatus(ActionStatus.FAILED);
			return;
		}

		if (Butcher.getDungeonStage().isInFov(destination)) {
			scheduleAnimation(Butcher.orthoToPosition(destination));
			setStatus(ActionStatus.ONGOING);
		} else {
			((AdaptedActor) getActor()).setPositionOrtho(destination);
			setStatus(ActionStatus.SUCCESS);
		}
	}

	private void scheduleAnimation(Vector2 destinationInPixels) {
		animatedAction = new MoveToAction();
		animatedAction.setActor(getActor());
		animatedAction.setDuration(Butcher.ANIMATION_DURATION);
		animatedAction.setPosition(destinationInPixels.x, destinationInPixels.y);
	}
}
