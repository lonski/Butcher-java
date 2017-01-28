package org.lonski.butcher;

import org.lonski.butcher.actors.AdaptedActor;
import org.lonski.butcher.actors.Player;
import org.lonski.butcher.actors.actions.ActionStatus;
import org.lonski.butcher.actors.actions.AdaptedAction;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

/**
 * Game loop adapter. Executes actors turns one by one.
 * Assures temporal action completion before processing next actor.
 */
class TurnProcessor {

	/**
	 * Provides access to all actors capable of taking turns
	 */
	interface ActorsGateway {
		Array<Actor> getActors();
	}

	private int currentActor;
	private AdaptedAction currentAction;
	private ActorsGateway gateway;

	TurnProcessor(ActorsGateway gateway) {
		this.currentActor = 0;
		this.currentAction = null;
		this.gateway = gateway;
	}

	void update(float delta) {
		int actorIndex;
		do {
			actorIndex = getCurrentActor();
			process(delta);
		} while (getCurrentActor() != actorIndex);
	}

	private void process(float delta) {
		Array<Actor> actors = gateway.getActors();

		//Fetch actor currently taking its turn
		final AdaptedActor actor = (AdaptedActor) actors.get(currentActor);
		if (actor == null) {
			return;
		}

		// Fetch actor's action
		if (currentAction == null) {
			actor.takeTurn();
			currentAction = actor.getNextAction();
		}

		//No action set, wait for actor to take one
		if (currentAction == null) {
			return;
		}

		//Action is just created - perform it
		if (currentAction.getStatus() == ActionStatus.CREATED) {
			currentAction.perform();
		}

		//Action not performed, wait for actor to take new one
		if (currentAction.getStatus() == ActionStatus.FAILED) {
			currentAction = null;
			currentActor = (currentActor + 1) % actors.size;
			return;
		}

		//Action is ongoing - tick time
		if (currentAction.getStatus() == ActionStatus.ONGOING) {
			currentAction.act(delta);
		}

		//Action completed, continue to next actor
		if (currentAction.getStatus() == ActionStatus.SUCCESS) {
			currentAction = null;
			currentActor = (currentActor + 1) % actors.size;

			if (actor instanceof Player) {
				Butcher.getDungeonStage().calculateFov();
			}
		} else if (currentAction.getStatus() == ActionStatus.FAILED) {
			currentAction = null;
		}
	}

	int getCurrentActor() {
		return currentActor;
	}

	AdaptedAction getCurrentAction() {
		return currentAction;
	}
}
