package io.sarl.jaak.demos.ants.behaviors;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import io.sarl.jaak.demos.ants.AntColonyConstants;
import io.sarl.jaak.demos.ants.behaviors.AbstractAntBehavior;
import io.sarl.jaak.demos.ants.behaviors.FoodSelectionCapacity;
import io.sarl.jaak.demos.ants.behaviors.ForagerState;
import io.sarl.jaak.demos.ants.environment.AntColony;
import io.sarl.jaak.demos.ants.environment.ColonyPheromone;
import io.sarl.jaak.demos.ants.environment.Food;
import io.sarl.jaak.demos.ants.environment.FoodPheromone;
import io.sarl.jaak.demos.ants.environment.Pheromone;
import io.sarl.jaak.environment.external.Perception;
import io.sarl.jaak.environment.external.body.TurtleBody;
import io.sarl.jaak.environment.external.influence.MotionInfluenceStatus;
import io.sarl.jaak.environment.external.perception.EnvironmentalObject;
import io.sarl.jaak.environment.external.perception.Perceivable;
import io.sarl.jaak.environment.external.perception.PickedObject;
import io.sarl.lang.annotation.Generated;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.Percept;
import java.util.Collection;
import org.arakhne.afc.math.discrete.object2d.Point2i;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * This class defines a forager role.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public class Forager extends AbstractAntBehavior {
  protected ForagerState state = ForagerState.SEARCH_FOOD;
  
  protected Food bag;
  
  public Food selectFood(final Iterable<Food> foods) {
    boolean _and = false;
    boolean _notEquals = (!Objects.equal(foods, null));
    if (!_notEquals) {
      _and = false;
    } else {
      boolean _isEmpty = IterableExtensions.isEmpty(foods);
      boolean _not = (!_isEmpty);
      _and = _not;
    }
    if (_and) {
      FoodSelectionCapacity foodSelection = this.<FoodSelectionCapacity>getSkill(FoodSelectionCapacity.class);
      return foodSelection.getBestFood(foods);
    }
    return null;
  }
  
  /**
   * Ensures that the behavior _handle_Perception_1 is called only when the guard <XFeatureCallImplCustom> == <XMemberFeatureCallImplCustom> is valid.
   */
  public boolean _handle_Perception_1_Guard(final Perception occurrence) {
    boolean _equals = Objects.equal(this.state, ForagerState.SEARCH_FOOD);
    return _equals;
  }
  
  @Percept
  public void _handle_Perception_1(final Perception occurrence) {
    if ( _handle_Perception_1_Guard(occurrence)) { 
    TurtleBody body = occurrence.body;
    Collection<EnvironmentalObject> _perceivedObjects = body.getPerceivedObjects();
    Iterable<Food> _filter = Iterables.<Food>filter(_perceivedObjects, Food.class);
    Food selectedFood = this.selectFood(_filter);
    boolean _tripleNotEquals = (selectedFood != null);
    if (_tripleNotEquals) {
      MotionInfluenceStatus _lastMotionInfluenceStatus = body.getLastMotionInfluenceStatus();
      boolean _equals = Objects.equal(_lastMotionInfluenceStatus, MotionInfluenceStatus.NO_MOTION);
      if (_equals) {
        MotionInfluenceStatus _lastMotionInfluenceStatus_1 = body.getLastMotionInfluenceStatus();
        this.randomPatrol(_lastMotionInfluenceStatus_1);
        ColonyPheromone _colonyPheromone = new ColonyPheromone(AntColonyConstants.MAX_PHEROMONE_AMOUNT);
        this.dropOff(_colonyPheromone);
      } else {
        Point2i _position = selectedFood.getPosition();
        boolean _gotoMotion = this.gotoMotion(_position, false);
        if (_gotoMotion) {
          Food _food = new Food(5);
          this.pickUp(_food);
          this.state = ForagerState.PICK_UP_FOOD;
        } else {
          ColonyPheromone _colonyPheromone_1 = new ColonyPheromone(AntColonyConstants.MAX_PHEROMONE_AMOUNT);
          this.dropOff(_colonyPheromone_1);
        }
      }
    } else {
      MotionInfluenceStatus _lastMotionInfluenceStatus_2 = body.getLastMotionInfluenceStatus();
      boolean _equals_1 = Objects.equal(_lastMotionInfluenceStatus_2, MotionInfluenceStatus.NO_MOTION);
      if (_equals_1) {
        MotionInfluenceStatus _lastMotionInfluenceStatus_3 = body.getLastMotionInfluenceStatus();
        this.randomPatrol(_lastMotionInfluenceStatus_3);
      } else {
        Collection<EnvironmentalObject> _perceivedObjects_1 = body.getPerceivedObjects();
        Pheromone selected = this.followPheromone(FoodPheromone.class, _perceivedObjects_1);
        boolean _tripleNotEquals_1 = (selected != null);
        if (_tripleNotEquals_1) {
          Point2i _position_1 = selected.getPosition();
          this.gotoMotion(_position_1, true);
        } else {
          MotionInfluenceStatus _lastMotionInfluenceStatus_4 = body.getLastMotionInfluenceStatus();
          this.randomPatrol(_lastMotionInfluenceStatus_4);
        }
      }
      ColonyPheromone _colonyPheromone_2 = new ColonyPheromone(AntColonyConstants.MAX_PHEROMONE_AMOUNT);
      this.dropOff(_colonyPheromone_2);
    }}
  }
  
  /**
   * Ensures that the behavior _handle_Perception_2 is called only when the guard <XFeatureCallImplCustom> == <XMemberFeatureCallImplCustom> is valid.
   */
  public boolean _handle_Perception_2_Guard(final Perception occurrence) {
    boolean _equals = Objects.equal(this.state, ForagerState.PICK_UP_FOOD);
    return _equals;
  }
  
  @Percept
  public void _handle_Perception_2(final Perception occurrence) {
    if ( _handle_Perception_2_Guard(occurrence)) { 
    TurtleBody body = occurrence.body;
    Collection<Perceivable> _perception = body.getPerception();
    final Function1<Perceivable, Boolean> _function = new Function1<Perceivable, Boolean>() {
      public Boolean apply(final Perceivable it) {
        return Boolean.valueOf((it instanceof PickedObject));
      }
    };
    Perceivable pickedObject = IterableExtensions.<Perceivable>findFirst(_perception, _function);
    if ((pickedObject instanceof PickedObject)) {
      EnvironmentalObject food = ((PickedObject)pickedObject).getPickedUpObject();
      if ((food instanceof Food)) {
        boolean _isDisappeared = ((Food)food).isDisappeared();
        boolean _not = (!_isDisappeared);
        if (_not) {
          this.bag = ((Food)food);
          FoodPheromone _foodPheromone = new FoodPheromone(AntColonyConstants.MAX_PHEROMONE_AMOUNT);
          this.dropOff(_foodPheromone);
          this.state = ForagerState.RETURN_TO_COLONY;
          return;
        }
      }
    }
    this.state = ForagerState.SEARCH_FOOD;}
  }
  
  /**
   * Ensures that the behavior _handle_Perception_3 is called only when the guard <XFeatureCallImplCustom> == <XMemberFeatureCallImplCustom> is valid.
   */
  public boolean _handle_Perception_3_Guard(final Perception occurrence) {
    boolean _equals = Objects.equal(this.state, ForagerState.RETURN_TO_COLONY);
    return _equals;
  }
  
  @Percept
  public void _handle_Perception_3(final Perception occurrence) {
    if ( _handle_Perception_3_Guard(occurrence)) { 
    TurtleBody body = occurrence.body;
    Food bag = this.bag;
    boolean _and = false;
    boolean _tripleNotEquals = (bag != null);
    if (!_tripleNotEquals) {
      _and = false;
    } else {
      boolean _isDisappeared = bag.isDisappeared();
      boolean _not = (!_isDisappeared);
      _and = _not;
    }
    if (_and) {
      MotionInfluenceStatus _lastMotionInfluenceStatus = body.getLastMotionInfluenceStatus();
      boolean _equals = Objects.equal(_lastMotionInfluenceStatus, MotionInfluenceStatus.NO_MOTION);
      if (_equals) {
        MotionInfluenceStatus _lastMotionInfluenceStatus_1 = body.getLastMotionInfluenceStatus();
        this.randomPatrol(_lastMotionInfluenceStatus_1);
        FoodPheromone _foodPheromone = new FoodPheromone(AntColonyConstants.MAX_PHEROMONE_AMOUNT);
        this.dropOff(_foodPheromone);
      } else {
        Collection<EnvironmentalObject> _perceivedObjects = body.getPerceivedObjects();
        final Function1<EnvironmentalObject, Boolean> _function = new Function1<EnvironmentalObject, Boolean>() {
          public Boolean apply(final EnvironmentalObject it) {
            return Boolean.valueOf((it instanceof AntColony));
          }
        };
        Iterable<EnvironmentalObject> colony = IterableExtensions.<EnvironmentalObject>filter(_perceivedObjects, _function);
        if ((colony instanceof AntColony)) {
          Point2i _position = ((AntColony)colony).getPosition();
          boolean _gotoMotion = this.gotoMotion(_position, false);
          if (_gotoMotion) {
            this.bag = null;
            this.state = ForagerState.SEARCH_FOOD;
          } else {
            FoodPheromone _foodPheromone_1 = new FoodPheromone(AntColonyConstants.MAX_PHEROMONE_AMOUNT);
            this.dropOff(_foodPheromone_1);
          }
        } else {
          Collection<EnvironmentalObject> _perceivedObjects_1 = body.getPerceivedObjects();
          Pheromone selected = this.followPheromone(ColonyPheromone.class, _perceivedObjects_1);
          boolean _tripleNotEquals_1 = (selected != null);
          if (_tripleNotEquals_1) {
            Point2i _position_1 = selected.getPosition();
            this.gotoMotion(_position_1, true);
          } else {
            this.randomMotion();
          }
          FoodPheromone _foodPheromone_2 = new FoodPheromone(AntColonyConstants.MAX_PHEROMONE_AMOUNT);
          this.dropOff(_foodPheromone_2);
        }
      }
    } else {
      this.state = ForagerState.SEARCH_FOOD;
    }}
  }
  
  /**
   * Construct a behavior.
   * @param owner - reference to the agent that is owning this behavior.
   */
  @Generated
  public Forager(final Agent owner) {
    super(owner);
  }
}
