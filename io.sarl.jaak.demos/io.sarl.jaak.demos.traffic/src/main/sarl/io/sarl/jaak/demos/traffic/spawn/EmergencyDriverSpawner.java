package io.sarl.jaak.demos.traffic.spawn;

import io.sarl.jaak.demos.traffic.behaviors.EmergencyDriver;
import io.sarl.jaak.environment.EnvironmentArea;
import io.sarl.jaak.environment.body.TurtleBody;
import io.sarl.jaak.environment.spawner.JaakPointSpawner;
import io.sarl.jaak.environment.time.TimeManager;

import java.util.UUID;

import org.arakhne.afc.math.discrete.object2d.Vector2i;

/** Spawner for emergency driver.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class EmergencyDriverSpawner extends JaakPointSpawner {

	private final Vector2i direction;

	private int budget;

	/**
	 * @param environment is the environment in which the spawning may proceed.
	 * @param emergencyDriverBudget is the maximal count of emergency drivers to spawn.
	 * @param x is the spawning position.
	 * @param y is the spawning position.
	 * @param direction the direction of spawning.
	 */
	public EmergencyDriverSpawner(EnvironmentArea environment, int emergencyDriverBudget, int x, int y, Vector2i direction) {
		super(environment, x, y);
		this.direction = direction;
		this.budget = emergencyDriverBudget;
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
			body.setSemantic(EmergencyDriver.class);
		}
	}

}
