package io.sarl.jaak.demos.traffic.environment.physic;

import io.sarl.jaak.environment.model.GridModel;
import io.sarl.jaak.environment.time.TimeManager;

import org.arakhne.afc.math.discrete.object2d.Rectangle2i;

/** Group of traffic lights that are managed together.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class NotIntelligentTrafficLightGroup extends AbstractBaseCycleTrafficLightGroup {

	/**
	 * @param position
	 */
	public NotIntelligentTrafficLightGroup(Rectangle2i position) {
		super(position);
	}
		
	@Override
	public TrafficLightInfluence runBehavior(GridModel grid, TimeManager timeManager) {
		return runStandardCycle(timeManager.getCurrentTime());
	}

}