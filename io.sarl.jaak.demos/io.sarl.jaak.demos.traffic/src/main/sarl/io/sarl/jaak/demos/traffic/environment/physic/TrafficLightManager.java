package io.sarl.jaak.demos.traffic.environment.physic;

import io.sarl.jaak.environment.endogenous.EnvironmentEndogenousEngine;
import io.sarl.jaak.environment.influence.Influence;
import io.sarl.jaak.environment.model.GridModel;
import io.sarl.jaak.environment.time.TimeManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/** Endogenous engine that is changnig the traffic light states.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class TrafficLightManager extends ArrayList<TrafficLightGroup> implements EnvironmentEndogenousEngine {

	private static final long serialVersionUID = 2605027929020837388L;

	/**
	 */
	public TrafficLightManager() {
		//
	}
	
	/** Initialize the traffic lights.
	 */
	public void initialize() {
		for (TrafficLightGroup group : this) {
			group.initialize();
		}
	}

	@Override
	public Collection<Influence> computeInfluences(GridModel grid, TimeManager timeManager) {
		Collection<Influence> influences = new LinkedList<>();
		for (TrafficLightGroup group : this) {
			Influence i = group.runBehavior(grid, timeManager);
			if (i != null) {
				influences.add(i);
			}
		}
		return influences;
	}

}