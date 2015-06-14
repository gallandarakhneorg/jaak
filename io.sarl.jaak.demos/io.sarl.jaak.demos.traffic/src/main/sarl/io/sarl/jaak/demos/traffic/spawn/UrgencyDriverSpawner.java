package io.sarl.jaak.demos.traffic.spawn;

import io.sarl.jaak.demos.traffic.behaviors.UrgencyDriver;
import io.sarl.jaak.environment.body.TurtleBody;
import io.sarl.jaak.environment.spawner.JaakPointSpawner;
import io.sarl.jaak.environment.time.TimeManager;

import java.util.UUID;

import org.arakhne.afc.math.discrete.object2d.Vector2i;

/** Spawner for the Ant Colony Demo.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class UrgencyDriverSpawner extends JaakPointSpawner {

	private final Vector2i direction;

	private int budget;

	/**
	 * @param urgencyDriverBudget is the maximal count of urgency drivers to spawn.
	 * @param x is the spawning position.
	 * @param y is the spawning position.
	 * @param direction the direction of spawning.
	 */
	public UrgencyDriverSpawner(int urgencyDriverBudget, int x, int y, Vector2i direction) {
		super(x,y);
		this.direction = direction;
		this.budget = urgencyDriverBudget;
	}

	@Override
	protected boolean isSpawnable(TimeManager timeManager) {
		return (this.budget>0);
	}

	@Override
	protected float computeSpawnedTurtleOrientation(TimeManager timeManager) {
		return this.direction.getOrientationAngle();
	}

	@Override
	protected void turtleSpawned(UUID turtle, TurtleBody body, TimeManager timeManager) {
		assert(this.budget>0);
		if (this.budget>0) {
			--this.budget;
			body.setSemantic(UrgencyDriver.class);
		}
	}

}
