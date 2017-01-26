package org.lonski.butcher.actors.actions;

import org.lonski.butcher.Butcher;
import org.lonski.butcher.actors.AdaptedActor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

import squidpony.squidmath.Coord;

public class MoveAction extends AdaptedAction {
	private final MoveByAction animatedAction;
	private final int dx;
	private final int dy;

	public MoveAction(Coord dd) {
		this(dd.getX(), dd.getY());
	}

	public MoveAction(int dx, int dy) {
		this.dx = dx != 0 ? (dx / Math.abs(dx)) : dx;
		this.dy = dy != 0 ? (dy / Math.abs(dy)) : dy;

		animatedAction = new MoveByAction();
		animatedAction.setDuration(Butcher.ANIMATION_DURATION);
		animatedAction.setAmount((float) this.dx * Butcher.TILE_SIZE, (float) this.dy * Butcher.TILE_SIZE);
	}

	@Override
	public void setActor(Actor actor) {
		super.setActor(actor);
		animatedAction.setActor(getActor());
	}

	@Override
	public boolean act(float delta) {
		if (animatedAction.act(delta)) {
			setStatus(ActionStatus.SUCCESS);
		}

		return getStatus() != ActionStatus.ONGOING;
	}

	@Override
	public void perform() {
		Coord position = ((AdaptedActor) getActor()).getPositionOrtho();
		Coord destination = position.add(Coord.get(dx, dy));

		boolean destinationBlocked = Butcher.getDungeonStage().isBlocked(destination);

		setStatus(destinationBlocked ? ActionStatus.FAILED : ActionStatus.ONGOING);
	}
}
