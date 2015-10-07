package io.sarl.jaak.demos.traffic.spawn;

import io.sarl.jaak.demos.traffic.TrafficConstants;
import io.sarl.jaak.demos.traffic.behaviors.StandardDriver;
import io.sarl.jaak.environment.EnvironmentArea;
import io.sarl.jaak.environment.body.TurtleBody;
import io.sarl.jaak.environment.spawner.JaakPointSpawner;
import io.sarl.jaak.environment.time.TimeManager;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import org.arakhne.afc.math.discrete.object2d.Vector2i;

/** Spawner of standard drivers.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class StandardDriverSpawner extends JaakPointSpawner {

	private final Vector2i direction;

	private int budget;
	
	private final Random random = new Random();
	private final AtomicBoolean firstSpawn = new AtomicBoolean(true);

	/**
	 * @param environment is the environment in which the spawning may proceed.
	 * @param budget is the maximal count of standard drivers to spawn.
	 * @param x is the spawning position.
	 * @param y is the spawning position.
	 * @param direction the direction of spawning.
	 */
	public StandardDriverSpawner(EnvironmentArea environment, int budget, int x, int y, Vector2i direction) {
		super(environment, x, y);
		this.direction = direction;
		this.budget = budget;
	}

	@Override
	protected boolean isSpawnable(TimeManager timeManager) {
		return (this.budget > 0) && (this.firstSpawn.get() || this.random.nextFloat() <= TrafficConstants.SPAWN_RATE);
	}

	@Override
	protected float computeSpawnedTurtleOrientation(TimeManager timeManager) {
		return this.direction.getOrientationAngle();
	}

	@Override
	protected void turtleSpawned(UUID turtle, TurtleBody body, TimeManager timeManager) {
		this.firstSpawn.set(false);
		assert(this.budget>0);
		if (this.budget>0) {
			--this.budget;
			body.setSemantic(StandardDriver.class);
		}
	}

}
