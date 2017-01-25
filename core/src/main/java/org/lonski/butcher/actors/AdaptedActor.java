package org.lonski.butcher.actors;

import org.lonski.butcher.actors.actions.AdaptedAction;

import com.badlogic.gdx.scenes.scene2d.Actor;

import squidpony.squidmath.Coord;

public class AdaptedActor extends Actor {

	private AdaptedAction nextAction;

	public AdaptedActor() {
		this.nextAction = null;
	}

	public void setNextAction(AdaptedAction currentAction) {
		this.nextAction = currentAction;
		this.nextAction.setActor(this);
	}

	public AdaptedAction getNextAction() {
		AdaptedAction tmp = nextAction;
		nextAction = null;
		return tmp;
	}

	public void setPosition(Coord coord) {
		setPosition(coord.getX(), coord.getY());
	}
}
