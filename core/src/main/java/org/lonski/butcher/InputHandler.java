package org.lonski.butcher;

import org.lonski.butcher.actors.actions.MoveAction;
import org.lonski.butcher.common.Direction;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import squidpony.squidmath.Coord;

public class InputHandler implements InputProcessor {

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Coord coord = screenPositionToOrtho(screenX, screenY);
		Coord playerCoord = Butcher.getPlayer().getPositionOrtho();

		Direction direction = Direction.fromCoords(playerCoord, coord);
		System.out.println(direction);
		Butcher.getPlayer().setNextAction(new MoveAction(direction));

		return true;
	}

	private Coord screenPositionToOrtho(int x, int y) {
		Stage stage = Butcher.getDungeonStage();
		Vector2 position = stage.screenToStageCoordinates(new Vector2((float) x, (float) y));
		position.sub(Butcher.TILE_SIZE / 2.f, Butcher.TILE_SIZE / 2.f);
		return Butcher.positionToOrtho(position);
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
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
