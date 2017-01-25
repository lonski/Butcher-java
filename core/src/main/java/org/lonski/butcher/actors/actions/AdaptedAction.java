package org.lonski.butcher.actors.actions;

import com.badlogic.gdx.scenes.scene2d.Action;

public abstract class AdaptedAction extends Action {

	private ActionStatus status;

	public AdaptedAction() {
		this.status = ActionStatus.CREATED;
	}

	public void setStatus(ActionStatus status) {
		this.status = status;
	}

	public ActionStatus getStatus() {
		return status;
	}

	public abstract void perform();

}
