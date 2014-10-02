package io.sarl.jaak.demos.ants.behaviors;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import io.sarl.jaak.demos.ants.behaviors.PheromoneFollowingCapacity;
import io.sarl.jaak.demos.ants.environment.Pheromone;
import io.sarl.jaak.envinterface.influence.MotionInfluenceStatus;
import io.sarl.jaak.envinterface.perception.Perceivable;
import io.sarl.jaak.kernel.PhysicBody;
import io.sarl.jaak.util.RandomNumber;
import io.sarl.lang.annotation.Generated;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.Behavior;
import org.arakhne.afc.math.MathConstants;
import org.arakhne.afc.math.continous.object2d.Vector2f;
import org.arakhne.afc.math.discrete.object2d.Point2i;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * This abstract class defines a behavior for all ants.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public class AbstractAntBehavior extends Behavior {
  /**
   * Select and reply a pheromone.
   * 
   * @param pheromoneType is the type of pheromone to follow
   * @return the pheromone to reach.
   */
  public Pheromone followPheromone(final PhysicBody body, final Class<? extends Pheromone> pheromoneType, final Iterable<? extends Perceivable> perception) {
    boolean _isEmpty = IterableExtensions.isEmpty(perception);
    boolean _not = (!_isEmpty);
    if (_not) {
      PheromoneFollowingCapacity followPheromoneSkill = this.<PheromoneFollowingCapacity>getSkill(PheromoneFollowingCapacity.class);
      Point2i _position = body.getPosition();
      Iterable<Pheromone> _filter = Iterables.<Pheromone>filter(perception, Pheromone.class);
      return followPheromoneSkill.followPheromone(_position, _filter);
    }
    return null;
  }
  
  /**
   * Move randomly.
   */
  public void randomMotion(final PhysicBody body) {
    float _nextFloat = RandomNumber.nextFloat();
    float _nextFloat_1 = RandomNumber.nextFloat();
    float _minus = (_nextFloat - _nextFloat_1);
    float dAngle = (_minus * MathConstants.DEMI_PI);
    if ((dAngle > 0)) {
      body.turnLeft(dAngle);
    } else {
      body.turnRight((-dAngle));
    }
    body.moveForward(1);
  }
  
  /**
   * Turn back.
   */
  public void randomTurnBack(final PhysicBody body) {
    float _nextFloat = RandomNumber.nextFloat();
    float _nextFloat_1 = RandomNumber.nextFloat();
    float _minus = (_nextFloat - _nextFloat_1);
    float dAngle = (_minus * MathConstants.DEMI_PI);
    if ((dAngle > 0)) {
      body.turnLeft((MathConstants.DEMI_PI + dAngle));
    } else {
      body.turnRight((MathConstants.DEMI_PI - dAngle));
    }
    body.moveForward(1);
  }
  
  /**
   * Random patrol.
   */
  public void randomPatrol(final PhysicBody body, final MotionInfluenceStatus status) {
    boolean _equals = Objects.equal(status, MotionInfluenceStatus.NO_MOTION);
    if (_equals) {
      this.randomTurnBack(body);
    } else {
      this.randomMotion(body);
    }
  }
  
  /**
   * Go to the given target position.
   * 
   * @param target is the point to reach.
   * @param enableRandom indicates if the random behavior should be used when
   * the given point was already reached.
   * @return <code>true</code> if the ant does not move according to this function,
   * <code>false</code> if the ant is moving.
   */
  public boolean gotoMotion(final PhysicBody body, final Point2i target, final boolean enableRandom) {
    int _x = target.x();
    Point2i _position = body.getPosition();
    int _x_1 = _position.x();
    int dx = (_x - _x_1);
    int _y = target.y();
    Point2i _position_1 = body.getPosition();
    int _y_1 = _position_1.y();
    int dy = (_y - _y_1);
    if (((dx != 0) || (dy != 0))) {
      Vector2f motion = new Vector2f(dx, dy);
      motion.normalize();
      body.move(motion, true);
      return false;
    }
    if (enableRandom) {
      this.randomMotion(body);
      return false;
    }
    return true;
  }
  
  /**
   * Construct a behavior.
   * @param owner - reference to the agent that is owning this behavior.
   */
  @Generated
  public AbstractAntBehavior(final Agent owner) {
    super(owner);
  }
}
