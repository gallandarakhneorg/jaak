package io.sarl.jaak.demos.ants.behaviors;

import com.google.common.base.Objects;
import io.sarl.jaak.demos.ants.AntColonyConstants;
import io.sarl.jaak.demos.ants.behaviors.AbstractAntBehavior;
import io.sarl.jaak.demos.ants.behaviors.PatrollerState;
import io.sarl.jaak.demos.ants.environment.AntColony;
import io.sarl.jaak.demos.ants.environment.ColonyPheromone;
import io.sarl.jaak.demos.ants.environment.Pheromone;
import io.sarl.jaak.envinterface.perception.Perceivable;
import io.sarl.jaak.kernel.Perception;
import io.sarl.jaak.kernel.PhysicBody;
import io.sarl.lang.annotation.Generated;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.Percept;
import org.arakhne.afc.math.discrete.object2d.Point2i;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * This class defines a patroller role.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public class Patroller extends AbstractAntBehavior {
  protected PatrollerState state = PatrollerState.PATROL;
  
  protected float deadTime = Float.NaN;
  
  /**
   * Ensures that the behavior _handle_Perception_1 is called only when the guard <XFeatureCallImplCustom> == <XMemberFeatureCallImplCustom> is valid.
   */
  public boolean _handle_Perception_1_Guard(final Perception occurrence) {
    boolean _equals = Objects.equal(this.state, PatrollerState.PATROL);
    return _equals;
  }
  
  @Percept
  public void _handle_Perception_1(final Perception occurrence) {
    if ( _handle_Perception_1_Guard(occurrence)) { 
    PhysicBody body = this.<PhysicBody>getSkill(PhysicBody.class);
    boolean _isNaN = Float.valueOf(this.deadTime).isNaN();
    if (_isNaN) {
      this.deadTime = (occurrence.currentTime + ((AntColonyConstants.MAX_PHEROMONE_AMOUNT / ColonyPheromone.EVAPORATION) / 3));
      this.randomPatrol(body, occurrence.lastMotionStatus);
    } else {
      if ((occurrence.currentTime >= this.deadTime)) {
        this.deadTime = Float.NaN;
        this.state = PatrollerState.RETURN_TO_COLONY;
        Pheromone goHome = this.followPheromone(body, ColonyPheromone.class, occurrence.perceivedObjects);
        boolean _notEquals = (!Objects.equal(goHome, null));
        if (_notEquals) {
          Point2i _position = goHome.getPosition();
          this.gotoMotion(body, _position, true);
        } else {
          this.randomPatrol(body, occurrence.lastMotionStatus);
        }
      } else {
        this.randomPatrol(body, occurrence.lastMotionStatus);
      }
    }
    ColonyPheromone _colonyPheromone = new ColonyPheromone();
    body.dropOff(_colonyPheromone);}
  }
  
  /**
   * Ensures that the behavior _handle_Perception_2 is called only when the guard <XFeatureCallImplCustom> == <XMemberFeatureCallImplCustom> is valid.
   */
  public boolean _handle_Perception_2_Guard(final Perception occurrence) {
    boolean _equals = Objects.equal(this.state, PatrollerState.RETURN_TO_COLONY);
    return _equals;
  }
  
  @Percept
  public void _handle_Perception_2(final Perception occurrence) {
    if ( _handle_Perception_2_Guard(occurrence)) { 
    PhysicBody body = this.<PhysicBody>getSkill(PhysicBody.class);
    final Function1<Perceivable, Boolean> _function = new Function1<Perceivable, Boolean>() {
      public Boolean apply(final Perceivable it) {
        return Boolean.valueOf((it instanceof AntColony));
      }
    };
    Perceivable colony = IterableExtensions.findFirst(occurrence.perceivedObjects, _function);
    if ((colony instanceof AntColony)) {
      boolean _or = false;
      boolean _isFailure = occurrence.lastMotionStatus.isFailure();
      if (_isFailure) {
        _or = true;
      } else {
        Point2i _position = ((AntColony)colony).getPosition();
        boolean _gotoMotion = this.gotoMotion(body, _position, false);
        _or = _gotoMotion;
      }
      if (_or) {
        this.state = PatrollerState.PATROL;
      }
    } else {
      boolean _isFailure_1 = occurrence.lastMotionStatus.isFailure();
      if (_isFailure_1) {
        this.randomPatrol(body, occurrence.lastMotionStatus);
      } else {
        Pheromone selected = this.followPheromone(body, ColonyPheromone.class, occurrence.perceivedObjects);
        boolean _tripleNotEquals = (selected != null);
        if (_tripleNotEquals) {
          Point2i _position_1 = selected.getPosition();
          this.gotoMotion(body, _position_1, true);
        } else {
          this.randomPatrol(body, occurrence.lastMotionStatus);
        }
      }
    }}
  }
  
  /**
   * Construct a behavior.
   * @param owner - reference to the agent that is owning this behavior.
   */
  @Generated
  public Patroller(final Agent owner) {
    super(owner);
  }
}
