package io.sarl.jaak.demos.ants.behaviors;

import io.sarl.jaak.demos.ants.behaviors.FoodSelectionCapacity;
import io.sarl.jaak.demos.ants.environment.Food;
import io.sarl.jaak.kernel.PhysicBody;
import io.sarl.lang.annotation.Generated;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.Skill;
import org.arakhne.afc.math.discrete.object2d.Point2i;

/**
 * A selector of food.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public class FoodSelectionSkill extends Skill implements FoodSelectionCapacity {
  public Food getBestFood(final Iterable<Food> foods) {
    Food bestFood = null;
    int distance = Integer.MAX_VALUE;
    PhysicBody _skill = this.<PhysicBody>getSkill(PhysicBody.class);
    Point2i ap = _skill.getPosition();
    for (final Food food : foods) {
      {
        Point2i fp = food.getPosition();
        int _x = fp.x();
        int _x_1 = ap.x();
        int _minus = (_x - _x_1);
        int _abs = Math.abs(_minus);
        int _y = fp.y();
        int _y_1 = ap.y();
        int _minus_1 = (_y - _y_1);
        int _abs_1 = Math.abs(_minus_1);
        int d = (_abs + _abs_1);
        boolean _or = false;
        boolean _tripleEquals = (bestFood == null);
        if (_tripleEquals) {
          _or = true;
        } else {
          _or = (d < distance);
        }
        if (_or) {
          distance = d;
          bestFood = food;
        }
      }
    }
    return bestFood;
  }
  
  /**
   * Construct a skill.
   * @param owner - agent that is owning this skill.
   */
  @Generated
  public FoodSelectionSkill(final Agent owner) {
    super(owner);
  }
  
  /**
   * Construct a skill. The owning agent is unknown.
   */
  @Generated
  public FoodSelectionSkill() {
    super();
  }
}
