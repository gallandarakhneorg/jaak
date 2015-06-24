package io.sarl.jaak.demos.traffic.environment.physic;

import io.sarl.jaak.demos.traffic.TrafficConstants;
import io.sarl.jaak.environment.model.GridModel;
import io.sarl.jaak.environment.perception.EnvironmentalObject;
import io.sarl.jaak.environment.time.TimeManager;

import org.arakhne.afc.math.discrete.object2d.Point2i;
import org.arakhne.afc.math.discrete.object2d.Rectangle2i;
import org.arakhne.afc.util.Pair;

/** Group of traffic lights that are managed together and that are
 * able to adapt the behavior according to the position of the urgency vehicle.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class ReactiveTrafficLightGroup extends AbstractBaseCycleTrafficLightGroup {

	private State state = State.STANDARD;
	private String sirenId;

	/**
	 * @param position
	 */
	public ReactiveTrafficLightGroup(Rectangle2i position) {
		super(position);
	}

	private Siren findSiren(GridModel grid) {
		Rectangle2i crossRoadArea = getPosition();
		Point2i center = new Point2i(crossRoadArea.getCenterX(), crossRoadArea.getCenterY());
		for (EnvironmentalObject object : grid.getObjects(center.x(), center.y())) {
			if (object instanceof Siren) {
				return (Siren) object;
			}
		}
		return null;
	}

	/** Run the behavior related to the state {@link State#STOPPING_OTHER_LANES}.
	 *
	 * @param grid the world model.
	 * @param timeManager the time manager.
	 * @return the next state.
	 */
	protected Pair<State, TrafficLightInfluence> runStoppingOtherLanes(GridModel grid, TimeManager timeManager) {
		TrafficLightInfluence influence = runStandardCycle(timeManager.getCurrentTime());
		if (influence != null && influence.containsChange()) {
			// Red signal for the other lanes
			return new Pair<>(State.WAITING_ARRIVAL, influence);			
		}
		return null;
	}

	/** Run the behavior related to the state {@link State#WAITING_ARRIVAL}.
	 *
	 * @param grid the world model.
	 * @param timeManager the time manager.
	 * @return the next state.
	 */
	protected Pair<State, TrafficLightInfluence> runWaitingArrival(GridModel grid, TimeManager timeManager) {
		Rectangle2i crossRoad = getPosition();
		Siren siren = findSiren(grid);
		if (siren == null) {
			TrafficLightInfluence influence = runStandardCycle(timeManager.getCurrentTime());
			return new Pair<>(State.STANDARD, influence);
		} else if (crossRoad.contains(siren.getSoundSourcePosition())) {
			this.sirenId = siren.getEnvironmentalObjectIdentifier();
			return new Pair<>(State.WAITING_DESAPPEAR, null);
		}
		return null;
	}

	/** Run the behavior related to the state {@link State#WAITING_DESAPPEAR}.
	 *
	 * @param grid the world model.
	 * @param timeManager the time manager.
	 * @return the next state.
	 */
	protected Pair<State, TrafficLightInfluence> runWaitingDesappear(GridModel grid, TimeManager timeManager) {
		Siren siren = findSiren(grid);
		if (siren == null || !siren.getEnvironmentalObjectIdentifier().equals(this.sirenId)) {
			TrafficLightInfluence influence = runStandardCycle(timeManager.getCurrentTime());
			return new Pair<>(State.STANDARD, influence);
		}
		return null;
	}

	/** Run the behavior related to the state {@link State#STANDARD}.
	 *
	 * @param grid the world model.
	 * @param timeManager the time manager.
	 * @return the next state.
	 */
	protected Pair<State, TrafficLightInfluence> runStandard(GridModel grid, TimeManager timeManager) {
		Siren siren = findSiren(grid);
		if (siren != null) {
			Integer groupNumber = getGroupNumberOfTrafficLightFacing(siren.getSoundSourcePosition());
			if (groupNumber != null) {
				int cycleStep = getCycleStepForGroupNumber(groupNumber);
				if (cycleStep >= 0) {

					if (getCurrentCycleStep() == cycleStep) {
						setCycle(
								timeManager.getCurrentTime(),
								getCurrentCycleStep(),
								getNextCycleStep(),
								false);
						TrafficLightInfluence influence = runStandardCycle(timeManager.getCurrentTime());
						return new Pair<>(State.WAITING_ARRIVAL, influence);
					}

					setCycle(
							timeManager.getCurrentTime() - TrafficConstants.GREEN_PHASE_DURATION,
							getCurrentCycleStep(),
							cycleStep,
							false);
					TrafficLightInfluence influence = runStandardCycle(timeManager.getCurrentTime());
					return new Pair<>(State.STOPPING_OTHER_LANES, influence);
				}
			}
		}

		TrafficLightInfluence influence = runStandardCycle(timeManager.getCurrentTime());
		return new Pair<>(null, influence);
	}

	@Override
	public TrafficLightInfluence runBehavior(GridModel grid, TimeManager timeManager) {
		Pair<State,TrafficLightInfluence> future = null;
		switch (this.state) {
		case STOPPING_OTHER_LANES:
			future = runStoppingOtherLanes(grid, timeManager);
			break;
		case WAITING_ARRIVAL:
			future = runWaitingArrival(grid, timeManager);
			break;
		case WAITING_DESAPPEAR:
			future = runWaitingDesappear(grid, timeManager);
			break;
		case STANDARD:
		default:
			future = runStandard(grid, timeManager);
			break;
		}
		if (future != null) {
			State s = future.getA();
			if (s != null) {
				this.state = s;
			}
			return future.getB();
		}
		return null;
	}

	@Override
	public void initialize() {
		this.state = State.STANDARD;
		this.sirenId = null;
		super.initialize();
	}

	/** State of the group.
	 *
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	protected enum State {
		/** Standard behavior. */
		STANDARD,
		/** Stopping vehicles on other lanes. */
		STOPPING_OTHER_LANES,
		/** Waiting for arrival of the priority vehicle. */
		WAITING_ARRIVAL,
		/** Waiting for the priority vehicle to disappear. */
		WAITING_DESAPPEAR;
	}

}