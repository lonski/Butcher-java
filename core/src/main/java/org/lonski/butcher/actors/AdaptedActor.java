package org.lonski.butcher.actors;

import org.lonski.butcher.actors.actions.AdaptedAction;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class AdaptedActor extends Actor {

	private AdaptedAction currentAction;

	public AdaptedActor() {
		this.currentAction = null;
	}

	public void setCurrentAction(AdaptedAction currentAction) {
		this.currentAction = currentAction;
	}

	public AdaptedAction getCurrentAction() {
		AdaptedAction tmp = currentAction;
		currentAction = null;
		return tmp;
	}
}
