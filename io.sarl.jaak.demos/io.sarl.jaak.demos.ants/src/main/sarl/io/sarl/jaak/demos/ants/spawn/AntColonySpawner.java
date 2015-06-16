package io.sarl.jaak.demos.ants.spawn;

import io.sarl.jaak.demos.ants.behaviors.Forager;
import io.sarl.jaak.demos.ants.behaviors.Patroller;
import io.sarl.jaak.environment.EnvironmentArea;
import io.sarl.jaak.environment.body.TurtleBody;
import io.sarl.jaak.environment.spawner.JaakPointSpawner;
import io.sarl.jaak.environment.time.TimeManager;
import io.sarl.jaak.util.RandomNumber;

import java.util.UUID;

import org.arakhne.afc.math.MathConstants;

/** Spawner for the Ant Colony Demo.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class AntColonySpawner extends JaakPointSpawner {

	private int patrollerBudget;
	private int foragerBudget;

	/**
	 * @param environment is the environment in which the spawning may proceed.
	 * @param patrollerBudget is the maximal count of patroller ants to spawn.
	 * @param foragerBudget is the maximal count of forager ants to spawn.
	 * @param x is the spawning position.
	 * @param y is the spawning position.
	 */
	public AntColonySpawner(EnvironmentArea environment, int patrollerBudget, int foragerBudget, int x, int y) {
		super(environment, x,y);
		this.patrollerBudget = patrollerBudget;
		this.foragerBudget = foragerBudget;
	}

	@Override
	protected boolean isSpawnable(TimeManager timeManager) {
		return (this.patrollerBudget>0 || this.foragerBudget>0);
	}

	@Override
	protected float computeSpawnedTurtleOrientation(TimeManager timeManager) {
		return RandomNumber.nextFloat() * MathConstants.TWO_PI;
	}

	@Override
	protected void turtleSpawned(UUID turtle, TurtleBody body, TimeManager timeManager) {
		assert(this.patrollerBudget>0 || this.foragerBudget>0);
		if (this.patrollerBudget>0 && (this.foragerBudget==0 || RandomNumber.nextBoolean())) {
			--this.patrollerBudget;
			body.setSemantic(Patroller.class);
		} else if (this.foragerBudget>0) {
			--this.foragerBudget;
			body.setSemantic(Forager.class);
		}
	}

}
