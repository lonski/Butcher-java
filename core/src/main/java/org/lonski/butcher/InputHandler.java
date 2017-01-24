package org.lonski.butcher;

import org.lonski.butcher.actors.actions.MoveAction;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class InputHandler implements InputProcessor {
	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Input.Keys.LEFT:
			Butcher.getPlayer().setNextAction(new MoveAction(-1, 0));
			return true;
		case Input.Keys.RIGHT:
			Butcher.getPlayer().setNextAction(new MoveAction(1, 0));
			return true;
		case Input.Keys.UP:
			Butcher.getPlayer().setNextAction(new MoveAction(0, 1));
			return true;
		case Input.Keys.DOWN:
			Butcher.getPlayer().setNextAction(new MoveAction(0, -1));
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
