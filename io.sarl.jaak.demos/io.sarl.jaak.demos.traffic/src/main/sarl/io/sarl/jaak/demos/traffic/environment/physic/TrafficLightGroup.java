package io.sarl.jaak.demos.traffic.environment.physic;

import io.sarl.jaak.demos.traffic.TrafficConstants;
import io.sarl.jaak.environment.endogenous.AutonomousEndogenousProcess;
import io.sarl.jaak.environment.influence.Influence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.arakhne.afc.math.discrete.object2d.Rectangle2i;
import org.eclipse.xtext.xbase.lib.Pair;

/** Group of traffic lights that are managed together.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class TrafficLightGroup implements AutonomousEndogenousProcess, Cloneable {

	private final Rectangle2i position;
	private Map<Integer, Collection<TrafficLight>> lights = new TreeMap<>();
	private List<Integer> ids = new ArrayList<>();
	private int greenGroup = 0;
	private float previousChange = 0;
	private boolean isGreenPhase = true;

	/**
	 * @param position
	 */
	public TrafficLightGroup(Rectangle2i position) {
		this.position = position;
	}
	
	@Override
	public String toString() {
		return "x=" + this.position.getMinX()
				+ ", y=" + this.position.getMinY()
				+ ", w=" + this.position.getWidth()
				+ ", h=" + this.position.getHeight()
				+ ", lights=" + this.lights;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public TrafficLightGroup clone() {
		try {
			TrafficLightGroup group = (TrafficLightGroup)super.clone();
			group.ids= new ArrayList<>(this.ids);
			group.lights = new TreeMap<>();
			for (Entry<Integer, Collection<TrafficLight>> entry : this.lights.entrySet()) {
				Collection<TrafficLight> list = new ArrayList<>();
				group.lights.put(entry.getKey(), list);
				for (TrafficLight light : entry.getValue()) {
					list.add(light.clone());
				}
			}
			return group;
		}
		catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
	
	/** Replies the traffic light in this group.
	 * 
	 * @return the traffic lights.
	 */
	public Map<Integer, Collection<TrafficLight>> getTrafficLights() {
		return Collections.unmodifiableMap(this.lights);
	}
	
	/** Create a traffic light in the given group.
	 * 
	 * @param group the id of the group.
	 * @return the traffic light.
	 */
	public TrafficLight createTrafficLight(int group) {
		Collection<TrafficLight> list = this.lights.get(group);
		if (list == null) {
			list = new ArrayList<>();
			this.lights.put(group, list);
		}
		TrafficLight light = new TrafficLight();
		list.add(light);
		return light;
	}
	
	@Override
	public Influence runAutonomousEndogenousProcess(float currentTime, float simulationStepDuration) {
		Collection<Pair<TrafficLight, TrafficLightState>> changes = new ArrayList<>();
		float t;
		if (this.isGreenPhase) {
			t = this.previousChange + TrafficConstants.GREEN_PHASE_DURATION;
		} else {
			t = this.previousChange + TrafficConstants.ORANGE_PHASE_DURATION;
		}
		if (currentTime >= t) {
			Collection<TrafficLight> currentGreen = this.lights.get(this.ids.get(this.greenGroup));
			if (currentGreen != null) {
				this.previousChange = currentTime;
				if (this.isGreenPhase) {
					this.isGreenPhase = false;
					for (TrafficLight light : currentGreen) {
						changes.add(new Pair<>(light, TrafficLightState.PASSAGE_DISCOURAGED));
					}
				} else {
					this.isGreenPhase = true;
					for (TrafficLight light : currentGreen) {
						changes.add(new Pair<>(light, TrafficLightState.PASSAGE_FORBIDDEN));
					}
					this.greenGroup = (this.greenGroup + 1) % this.ids.size();
					Collection<TrafficLight> newGreen = this.lights.get(this.ids.get(this.greenGroup));
					if (newGreen != null) {
						for (TrafficLight light : newGreen) {
							changes.add(new Pair<>(light, TrafficLightState.PASSAGE_ALLOWED));
						}
					}
				}
			}
		}
		if (changes.isEmpty()) {
			return null;
		}
		return new TrafficLightInfluence(changes);
	}
	
	/** Initialize the group
	 */
	public void initialize() {
		if (!this.lights.isEmpty()) {
			TrafficLightState state = TrafficLightState.PASSAGE_ALLOWED;
			this.isGreenPhase = true;
			this.previousChange = 0;
			this.greenGroup = 0;
			this.ids.addAll(this.lights.keySet());
			for (Collection<TrafficLight> lights : this.lights.values()) {
				for (TrafficLight light : lights) {
					light.setState(state);
				}
				state = TrafficLightState.PASSAGE_FORBIDDEN;
			}
		}
	}
	
}