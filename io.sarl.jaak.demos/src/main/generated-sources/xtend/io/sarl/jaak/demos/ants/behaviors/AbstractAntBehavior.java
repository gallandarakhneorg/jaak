package io.sarl.jaak.demos.ants.behaviors;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import io.sarl.jaak.demos.ants.behaviors.PheromoneFollowingCapacity;
import io.sarl.jaak.demos.ants.environment.Pheromone;
import io.sarl.jaak.environment.external.influence.MotionInfluenceStatus;
import io.sarl.jaak.environment.external.perception.EnvironmentalObject;
import io.sarl.jaak.environment.external.perception.Perceivable;
import io.sarl.jaak.util.RandomNumber;
import io.sarl.lang.annotation.Generated;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.Behavior;
import java.io.Serializable;
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
  @Generated
  protected void dropOff(final EnvironmentalObject arg0) {
    getSkill(io.sarl.jaak.environment.external.PhysicBody.class).dropOff(arg0);
  }
  
  @Generated
  protected float getHeadingAngle() {
    return getSkill(io.sarl.jaak.environment.external.PhysicBody.class).getHeadingAngle();
  }
  
  @Generated
  protected Vector2f getHeadingVector() {
    return getSkill(io.sarl.jaak.environment.external.PhysicBody.class).getHeadingVector();
  }
  
  @Generated
  protected Point2i getPosition() {
    return getSkill(io.sarl.jaak.environment.external.PhysicBody.class).getPosition();
  }
  
  @Generated
  protected Serializable getSemantic() {
    return getSkill(io.sarl.jaak.environment.external.PhysicBody.class).getSemantic();
  }
  
  @Generated
  protected float getSpeed() {
    return getSkill(io.sarl.jaak.environment.external.PhysicBody.class).getSpeed();
  }
  
  @Generated
  protected int getX() {
    return getSkill(io.sarl.jaak.environment.external.PhysicBody.class).getX();
  }
  
  @Generated
  protected int getY() {
    return getSkill(io.sarl.jaak.environment.external.PhysicBody.class).getY();
  }
  
  @Generated
  protected void move(final Vector2f arg0) {
    getSkill(io.sarl.jaak.environment.external.PhysicBody.class).move(arg0);
  }
  
  @Generated
  protected void move(final Vector2f arg0, final boolean arg1) {
    getSkill(io.sarl.jaak.environment.external.PhysicBody.class).move(arg0, arg1);
  }
  
  @Generated
  protected void moveBackward(final int arg0) {
    getSkill(io.sarl.jaak.environment.external.PhysicBody.class).moveBackward(arg0);
  }
  
  @Generated
  protected void moveForward(final int arg0) {
    getSkill(io.sarl.jaak.environment.external.PhysicBody.class).moveForward(arg0);
  }
  
  @Generated
  protected void pickUp(final EnvironmentalObject arg0) {
    getSkill(io.sarl.jaak.environment.external.PhysicBody.class).pickUp(arg0);
  }
  
  @Generated
  protected Perceivable pickUp(final Class<? extends Perceivable> arg0) {
    return getSkill(io.sarl.jaak.environment.external.PhysicBody.class).pickUp(arg0);
  }
  
  @Generated
  protected void setHeading(final float arg0) {
    getSkill(io.sarl.jaak.environment.external.PhysicBody.class).setHeading(arg0);
  }
  
  @Generated
  protected void setHeading(final Vector2f arg0) {
    getSkill(io.sarl.jaak.environment.external.PhysicBody.class).setHeading(arg0);
  }
  
  @Generated
  protected void setSemantic(final Serializable arg0) {
    getSkill(io.sarl.jaak.environment.external.PhysicBody.class).setSemantic(arg0);
  }
  
  @Generated
  protected EnvironmentalObject touchUp(final Class<? extends EnvironmentalObject> arg0) {
    return getSkill(io.sarl.jaak.environment.external.PhysicBody.class).touchUp(arg0);
  }
  
  @Generated
  protected void turnLeft(final float arg0) {
    getSkill(io.sarl.jaak.environment.external.PhysicBody.class).turnLeft(arg0);
  }
  
  @Generated
  protected void turnRight(final float arg0) {
    getSkill(io.sarl.jaak.environment.external.PhysicBody.class).turnRight(arg0);
  }
  
  /**
   * Select and reply a pheromone.
   * 
   * @param pheromoneType is the type of pheromone to follow
   * @return the pheromone to reach.
   */
  public Pheromone followPheromone(final Class<? extends Pheromone> pheromoneType, final Iterable<? extends Perceivable> perception) {
    boolean _isEmpty = IterableExtensions.isEmpty(perception);
    boolean _not = (!_isEmpty);
    if (_not) {
      PheromoneFollowingCapacity followPheromoneSkill = this.<PheromoneFollowingCapacity>getSkill(PheromoneFollowingCapacity.class);
      Point2i _position = this.getPosition();
      Iterable<Pheromone> _filter = Iterables.<Pheromone>filter(perception, Pheromone.class);
      return followPheromoneSkill.followPheromone(_position, _filter);
    }
    return null;
  }
  
  /**
   * Move randomly.
   */
  public void randomMotion() {
    float _nextFloat = RandomNumber.nextFloat();
    float _nextFloat_1 = RandomNumber.nextFloat();
    float _minus = (_nextFloat - _nextFloat_1);
    float dAngle = (_minus * MathConstants.DEMI_PI);
    if ((dAngle > 0)) {
      this.turnLeft(dAngle);
    } else {
      this.turnRight((-dAngle));
    }
    this.moveForward(1);
  }
  
  /**
   * Turn back.
   */
  public void randomTurnBack() {
    float _nextFloat = RandomNumber.nextFloat();
    float _nextFloat_1 = RandomNumber.nextFloat();
    float _minus = (_nextFloat - _nextFloat_1);
    float dAngle = (_minus * MathConstants.DEMI_PI);
    if ((dAngle > 0)) {
      this.turnLeft((MathConstants.DEMI_PI + dAngle));
    } else {
      this.turnRight((MathConstants.DEMI_PI - dAngle));
    }
    this.moveForward(1);
  }
  
  /**
   * Random patrol.
   */
  public void randomPatrol(final MotionInfluenceStatus status) {
    boolean _equals = Objects.equal(status, MotionInfluenceStatus.NO_MOTION);
    if (_equals) {
      this.randomTurnBack();
    } else {
      this.randomMotion();
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
  public boolean gotoMotion(final Point2i target, final boolean enableRandom) {
    int _x = target.x();
    int _x_1 = this.getX();
    int dx = (_x - _x_1);
    int _y = target.y();
    int _y_1 = this.getY();
    int dy = (_y - _y_1);
    if (((dx != 0) || (dy != 0))) {
      Vector2f motion = new Vector2f(dx, dy);
      motion.normalize();
      this.move(motion, true);
      return false;
    }
    if (enableRandom) {
      this.randomMotion();
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
