package io.sarl.jaak.demos.mdtraffic.environment.physic;

import io.sarl.jaak.demos.traffic.TrafficConstants;
import io.sarl.jaak.demos.traffic.environment.MapUtil;
import io.sarl.jaak.demos.traffic.environment.TrafficLightGroup;
import io.sarl.jaak.environment.endogenous.EnvironmentEndogenousEngine;
import io.sarl.jaak.environment.influence.Influence;
import io.sarl.jaak.environment.model.GridModel;
import io.sarl.jaak.environment.time.TimeManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.arakhne.afc.math.discrete.object2d.Point2i;

/** Endogenous process that create emergency cases in the physic environment.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class EmergencyGenerator extends ArrayList<TrafficLightGroup> implements EnvironmentEndogenousEngine {

	private static final long serialVersionUID = 2605027929020837388L;

	private boolean isCrashGenerated = false;
	
	/**
	 */
	public EmergencyGenerator() {
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
		if (!this.isCrashGenerated && timeManager.getCurrentTime() >= TrafficConstants.CRASH_TIME) {
			this.isCrashGenerated = true;
			List<Point2i> crashes = MapUtil.getCrashPositions();
			Point2i position = crashes.get((int) Math.random() * crashes.size());
			return Collections.<Influence>singleton(new CrashInfluence(position));
		}
		return Collections.emptyList();
	}

}