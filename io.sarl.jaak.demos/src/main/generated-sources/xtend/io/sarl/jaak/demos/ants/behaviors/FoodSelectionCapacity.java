package io.sarl.jaak.demos.ants.behaviors;

import io.sarl.jaak.demos.ants.environment.Food;
import io.sarl.lang.core.Capacity;

/**
 * This interface defines a selector of food.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public interface FoodSelectionCapacity extends Capacity {
  /**
   * Replies the best food location.
   * 
   * @param foods is the collection of food object in which a selection must be done.
   * @return the selected food.
   */
  public abstract Food getBestFood(final Iterable<Food> foods);
}
