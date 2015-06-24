package io.sarl.jaak.demos.traffic.environment.physic;

import io.sarl.jaak.demos.traffic.TrafficConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.arakhne.afc.math.discrete.object2d.Rectangle2i;
import org.eclipse.xtext.xbase.lib.Pair;

/** Group of traffic lights that are managed together.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public abstract class AbstractBaseCycleTrafficLightGroup extends TrafficLightGroup {

	private int currentCycleStep = 0;
	private int nextCycleStep = 0;
	private float currentCycleStepStartTime = 0;
	private boolean isGreenPhase = true;
	
	/**
	 * @param position
	 */
	public AbstractBaseCycleTrafficLightGroup(Rectangle2i position) {
		super(position);
	}
	
	/** Replies the no. of the current step in the traffic light cycle.
	 *
	 * @return the no. of the cycle step.
	 */
	protected int getCurrentCycleStep() {
		return this.currentCycleStep;
	}

	/** Replies the no. of the next step in the traffic light cycle.
	 *
	 * @return the no. of the cycle step.
	 */
	protected int getNextCycleStep() {
		return this.nextCycleStep;
	}

	/** Replies the time at which the current step in the traffic light cycle was started.
	 *
	 * @return the start time of the current step of the traffic light cycle.
	 */
	protected float getCurrentCycleStepStartTime() {
		return this.currentCycleStepStartTime;
	}
	
	/** Replies if the state of the group is currently discouraging passages.
	 *
	 * @return <code>true</code> if a least one traffic light is in the state
	 * {@link TrafficLightState#PASSAGE_DISCOURAGED}. Otherwise <code>false</code>.
	 */
	protected boolean isPassageDiscouragedPhase() {
		return !this.isGreenPhase;
	}
	
	/** Force the state of the traffic light cycle.
	 *
	 * @param cycleStepStartTime the start time of the current step of the traffic light cycle.
	 * @param currentCycleStep the index of the "green" sub-group.
	 * @param nextCycleStep the index of the sub-group that should be activated later. If
	 * <code>-1</code> the next step is the current one incremented by one, modulo the number
	 * of steps.
	 * @param isDiscouragedPassagePhase <code>true</code> if a least the lights in the sub-group are in the state
	 * {@link TrafficLightState#PASSAGE_DISCOURAGED}. Otherwise the lights are green.
	 */
	protected void setCycle(float cycleStepStartTime, int currentCycleStep, int nextCycleStep, boolean isDiscouragedPassagePhase) {
		this.currentCycleStepStartTime = cycleStepStartTime;
		this.currentCycleStep = currentCycleStep;
		this.nextCycleStep = nextCycleStep;
		this.isGreenPhase = !isDiscouragedPassagePhase;
	}
	
	/** Run the standard traffic light cycle.
	 *
	 * @param currentTime the current simulation time.
	 * @return the change of the traffic light states
	 */
	protected TrafficLightInfluence runStandardCycle(float currentTime) {
		Collection<Pair<TrafficLight, TrafficLightState>> changes = new ArrayList<>();
		float t = getCurrentCycleStepStartTime();
		int currentCycleStep = getCurrentCycleStep();
		int nextCycleStep = getNextCycleStep();
		boolean isPassing = !isPassageDiscouragedPhase();
		if (isPassing) {
			t += TrafficConstants.GREEN_PHASE_DURATION;
		} else {
			t += TrafficConstants.ORANGE_PHASE_DURATION;
		}
		if (currentTime >= t) {
			List<Integer> identifiers = getTrafficLightIdentifiers();
			Collection<TrafficLight> currentGreen = getTrafficLightsWithIdentifier(
					identifiers.get(currentCycleStep));
			if (currentGreen != null) {
				float newStartTime = currentTime;
				if (isPassing) {
					isPassing = false;
					for (TrafficLight light : currentGreen) {
						changes.add(new Pair<>(light, TrafficLightState.PASSAGE_DISCOURAGED));
					}
				} else {
					isPassing = true;
					for (TrafficLight light : currentGreen) {
						changes.add(new Pair<>(light, TrafficLightState.PASSAGE_FORBIDDEN));
					}
					currentCycleStep = (nextCycleStep < 0) ? (currentCycleStep + 1) % identifiers.size() : nextCycleStep;
					nextCycleStep = -1;
					Collection<TrafficLight> newGreen = getTrafficLightsWithIdentifier(identifiers.get(currentCycleStep));
					if (newGreen != null) {
						for (TrafficLight light : newGreen) {
							changes.add(new Pair<>(light, TrafficLightState.PASSAGE_ALLOWED));
						}
					}
				}
				setCycle(newStartTime, currentCycleStep, nextCycleStep, !isPassing);
			}
		}
		if (changes.isEmpty()) {
			return null;
		}
		return new TrafficLightInfluence(changes);
	}

	@Override
	public void initialize() {
		setCycle(0, 0, -1, false);
		super.initialize();
	}

}