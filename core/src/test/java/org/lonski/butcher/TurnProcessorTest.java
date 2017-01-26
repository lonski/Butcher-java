package org.lonski.butcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lonski.butcher.actors.AdaptedActor;
import org.lonski.butcher.actors.actions.ActionStatus;
import org.lonski.butcher.actors.actions.AdaptedAction;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

@RunWith(MockitoJUnitRunner.class)
public class TurnProcessorTest {

	@Mock
	private TurnProcessor.ActorsGateway actorsGateway;
	@Mock
	private Array<Actor> actorArray;
	@Mock
	AdaptedActor actor;
	@Mock
	AdaptedAction action;

	@Before
	public void setUp() {
		actorArray.size = 50;
	}

	@Test
	public void shouldInitializeFields() {
		TurnProcessor processor = new TurnProcessor(actorsGateway);

		assertThat(processor.getCurrentActor()).isEqualTo(0);
		assertThat(processor.getCurrentAction()).isNull();
	}

	@Test
	public void shouldAskGatewayForActors() {
		TurnProcessor processor = new TurnProcessor(actorsGateway);
		when(actorsGateway.getActors()).thenReturn(actorArray);
		when(actorArray.get(anyInt())).thenReturn(null);

		processor.update(1);

		verify(actorsGateway).getActors();
	}

	@Test
	public void shouldNotAdvanceIfGetNullActor() {
		TurnProcessor processor = new TurnProcessor(actorsGateway);
		when(actorsGateway.getActors()).thenReturn(actorArray);
		when(actorArray.get(anyInt())).thenReturn(null);

		processor.update(1);

		assertThat(processor.getCurrentActor()).isEqualTo(0);
	}

	@Test
	public void shouldFetchActionIfNotSet() {
		TurnProcessor processor = new TurnProcessor(actorsGateway);
		when(actorsGateway.getActors()).thenReturn(actorArray);
		when(actorArray.get(anyInt())).thenReturn(actor);
		when(actor.getNextAction()).thenReturn(null);

		processor.update(1);

		verify(actor).getNextAction();
	}

	@Test
	public void shouldPerformNewlyCreatedAction() {
		TurnProcessor processor = new TurnProcessor(actorsGateway);
		happyPathMockSetting();
		when(action.getStatus()).thenReturn(ActionStatus.CREATED);
		processor.update(1);

		verify(action).perform();
		assertThat(processor.getCurrentAction()).isNotNull();
	}

	@Test
	public void shouldClearActionAndStepToNextActorWhenCurrentActionSuccess() {
		TurnProcessor processor = new TurnProcessor(actorsGateway);
		int currentActor = processor.getCurrentActor();
		happyPathMockSetting();
		when(action.getStatus()).thenReturn(ActionStatus.CREATED).thenReturn(ActionStatus.SUCCESS);

		processor.update(1);

		assertThat(processor.getCurrentAction()).isNull();
		assertThat(processor.getCurrentActor()).isEqualTo(currentActor + 1);
	}

	@Test
	public void shouldClearActionAndNotAdvanceActorWhenCurrentActionFails() {
		TurnProcessor processor = new TurnProcessor(actorsGateway);
		int currentActor = processor.getCurrentActor();
		happyPathMockSetting();
		when(action.getStatus()).thenReturn(ActionStatus.CREATED).thenReturn(ActionStatus.FAILED);

		processor.update(1);

		assertThat(processor.getCurrentAction()).isNull();
		assertThat(processor.getCurrentActor()).isEqualTo(currentActor);
	}

	@Test
	public void shouldContinueProcessingActionWhenItIsOngoing() {
		TurnProcessor processor = new TurnProcessor(actorsGateway);
		int currentActor = processor.getCurrentActor();
		happyPathMockSetting();
		when(action.getStatus()).thenReturn(ActionStatus.CREATED).thenReturn(ActionStatus.ONGOING);

		processor.update(1);

		verify(action).act(anyFloat());
		assertThat(processor.getCurrentAction()).isNotNull();
		assertThat(processor.getCurrentActor()).isEqualTo(currentActor);
	}

	@Test
	public void shouldStepToNextActorWhenOngoingActionFinishes() {
		TurnProcessor processor = new TurnProcessor(actorsGateway);
		int currentActor = processor.getCurrentActor();
		happyPathMockSetting();
		when(action.getStatus())
				.thenReturn(ActionStatus.CREATED)
				.thenReturn(ActionStatus.ONGOING)
				.thenReturn(ActionStatus.ONGOING)
				.thenReturn(ActionStatus.SUCCESS);

		processor.update(1);

		verify(action).act(anyFloat());
		assertThat(processor.getCurrentAction()).isNull();
		assertThat(processor.getCurrentActor()).isEqualTo(currentActor + 1);
	}

	@Test
	public void shouldClearActionAndStayOnCurrentActorWhenOngoingActionFails() {
		TurnProcessor processor = new TurnProcessor(actorsGateway);
		int currentActor = processor.getCurrentActor();
		happyPathMockSetting();
		when(action.getStatus())
				.thenReturn(ActionStatus.CREATED)
				.thenReturn(ActionStatus.ONGOING)
				.thenReturn(ActionStatus.ONGOING)
				.thenReturn(ActionStatus.FAILED);

		processor.update(1);

		verify(action).act(anyFloat());
		assertThat(processor.getCurrentActor()).isEqualTo(currentActor);
		assertThat(processor.getCurrentAction()).isNull();
	}

	private void happyPathMockSetting() {
		when(actorsGateway.getActors()).thenReturn(actorArray);
		when(actorArray.get(anyInt())).thenReturn(actor);
		when(actor.getNextAction()).thenReturn(action);
	}
}