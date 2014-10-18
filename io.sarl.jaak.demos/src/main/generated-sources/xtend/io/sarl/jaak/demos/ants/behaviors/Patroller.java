package io.sarl.jaak.demos.ants.behaviors;

import com.google.common.base.Objects;
import io.sarl.jaak.demos.ants.AntColonyConstants;
import io.sarl.jaak.demos.ants.behaviors.AbstractAntBehavior;
import io.sarl.jaak.demos.ants.behaviors.PatrollerState;
import io.sarl.jaak.demos.ants.environment.AntColony;
import io.sarl.jaak.demos.ants.environment.ColonyPheromone;
import io.sarl.jaak.demos.ants.environment.Pheromone;
import io.sarl.jaak.environment.external.Perception;
import io.sarl.jaak.environment.external.body.TurtleBody;
import io.sarl.jaak.environment.external.influence.MotionInfluenceStatus;
import io.sarl.jaak.environment.external.perception.EnvironmentalObject;
import io.sarl.lang.annotation.Generated;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.Percept;
import java.util.Collection;
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
    TurtleBody body = occurrence.body;
    boolean _isNaN = Float.valueOf(this.deadTime).isNaN();
    if (_isNaN) {
      this.deadTime = (occurrence.currentTime + ((AntColonyConstants.MAX_PHEROMONE_AMOUNT / ColonyPheromone.EVAPORATION) / 3));
      MotionInfluenceStatus _lastMotionInfluenceStatus = body.getLastMotionInfluenceStatus();
      this.randomPatrol(_lastMotionInfluenceStatus);
    } else {
      if ((occurrence.currentTime >= this.deadTime)) {
        this.deadTime = Float.NaN;
        this.state = PatrollerState.RETURN_TO_COLONY;
        Collection<EnvironmentalObject> _perceivedObjects = body.getPerceivedObjects();
        Pheromone goHome = this.followPheromone(ColonyPheromone.class, _perceivedObjects);
        boolean _notEquals = (!Objects.equal(goHome, null));
        if (_notEquals) {
          Point2i _position = goHome.getPosition();
          this.gotoMotion(_position, true);
        } else {
          MotionInfluenceStatus _lastMotionInfluenceStatus_1 = body.getLastMotionInfluenceStatus();
          this.randomPatrol(_lastMotionInfluenceStatus_1);
        }
      } else {
        MotionInfluenceStatus _lastMotionInfluenceStatus_2 = body.getLastMotionInfluenceStatus();
        this.randomPatrol(_lastMotionInfluenceStatus_2);
      }
    }
    ColonyPheromone _colonyPheromone = new ColonyPheromone();
    this.dropOff(_colonyPheromone);}
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
    TurtleBody body = occurrence.body;
    Collection<EnvironmentalObject> _perceivedObjects = body.getPerceivedObjects();
    final Function1<EnvironmentalObject, Boolean> _function = new Function1<EnvironmentalObject, Boolean>() {
      public Boolean apply(final EnvironmentalObject it) {
        return Boolean.valueOf((it instanceof AntColony));
      }
    };
    EnvironmentalObject colony = IterableExtensions.<EnvironmentalObject>findFirst(_perceivedObjects, _function);
    if ((colony instanceof AntColony)) {
      boolean _or = false;
      MotionInfluenceStatus _lastMotionInfluenceStatus = body.getLastMotionInfluenceStatus();
      boolean _isFailure = _lastMotionInfluenceStatus.isFailure();
      if (_isFailure) {
        _or = true;
      } else {
        Point2i _position = ((AntColony)colony).getPosition();
        boolean _gotoMotion = this.gotoMotion(_position, false);
        _or = _gotoMotion;
      }
      if (_or) {
        this.state = PatrollerState.PATROL;
      }
    } else {
      MotionInfluenceStatus _lastMotionInfluenceStatus_1 = body.getLastMotionInfluenceStatus();
      boolean _isFailure_1 = _lastMotionInfluenceStatus_1.isFailure();
      if (_isFailure_1) {
        MotionInfluenceStatus _lastMotionInfluenceStatus_2 = body.getLastMotionInfluenceStatus();
        this.randomPatrol(_lastMotionInfluenceStatus_2);
      } else {
        Collection<EnvironmentalObject> _perceivedObjects_1 = body.getPerceivedObjects();
        Pheromone selected = this.followPheromone(ColonyPheromone.class, _perceivedObjects_1);
        boolean _tripleNotEquals = (selected != null);
        if (_tripleNotEquals) {
          Point2i _position_1 = selected.getPosition();
          this.gotoMotion(_position_1, true);
        } else {
          MotionInfluenceStatus _lastMotionInfluenceStatus_3 = body.getLastMotionInfluenceStatus();
          this.randomPatrol(_lastMotionInfluenceStatus_3);
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
