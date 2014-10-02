package io.sarl.jaak.demos.ants.behaviors;

import io.sarl.jaak.demos.ants.environment.Pheromone;
import io.sarl.lang.core.Capacity;
import org.arakhne.afc.math.discrete.object2d.Point2i;

/**
 * This interface defines the capacity to select a route of pheromone.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public interface PheromoneFollowingCapacity extends Capacity {
  /**
   * Select and replies a pheromone to follow.
   * 
   * @param position is the current position of the follower.
   * @param pheromones are the pheromones in which the selection should be done.
   * @return a selectesd pheromone to follow or <code>null</code> to follow nothing.
   */
  public abstract Pheromone followPheromone(final Point2i position, final Iterable<? extends Pheromone> pheromones);
}
