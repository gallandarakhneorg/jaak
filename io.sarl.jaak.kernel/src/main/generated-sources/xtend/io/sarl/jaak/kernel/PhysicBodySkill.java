package io.sarl.jaak.kernel;

import io.sarl.jaak.envinterface.perception.EnvironmentalObject;
import io.sarl.jaak.envinterface.perception.Perceivable;
import io.sarl.jaak.kernel.PhysicBody;
import io.sarl.lang.annotation.DefaultValueUse;
import io.sarl.lang.annotation.Generated;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.Skill;
import org.arakhne.afc.math.continous.object2d.Vector2f;
import org.arakhne.afc.math.discrete.object2d.Point2i;

@SuppressWarnings("all")
public class PhysicBodySkill extends Skill implements PhysicBody {
  public void install() {
  }
  
  public void beIddle() {
  }
  
  public void move(final Vector2f direction, final boolean changeHeading) {
  }
  
  public void moveForward(final int cells) {
  }
  
  public void moveBackward(final int cells) {
  }
  
  public void turnLeft(final float radians) {
  }
  
  public void turnRight(final float radians) {
  }
  
  public void setHeading(final float radians) {
  }
  
  public void setHeading(final Vector2f direction) {
  }
  
  public void dropOff(final EnvironmentalObject object) {
  }
  
  public Perceivable pickUp(final Class<? extends Perceivable> type) {
    return null;
  }
  
  public void pickUp(final EnvironmentalObject object) {
  }
  
  public EnvironmentalObject touchUp(final Class<? extends EnvironmentalObject> type) {
    return null;
  }
  
  public void setSemantic(final Object semantic) {
  }
  
  public void setPerceptionEnable(final boolean enable) {
  }
  
  public float getHeadingAngle() {
    return 0.0f;
  }
  
  public Vector2f getHeadingVector() {
    return null;
  }
  
  public float getOrientation() {
    return 0.0f;
  }
  
  public Point2i getPosition() {
    return null;
  }
  
  public Object getSemantic() {
    return null;
  }
  
  public float getSpeed() {
    return 0.0f;
  }
  
  public Vector2f getViewVector() {
    return null;
  }
  
  public int getX() {
    return 0;
  }
  
  public int getY() {
    return 0;
  }
  
  public boolean hasInfluences() {
    return true;
  }
  
  public boolean isPerceptionEnable() {
    return true;
  }
  
  @DefaultValueUse("org.arakhne.afc.math.continous.object2d.Vector2f,boolean")
  public final void move(final Vector2f direction) {
    move(direction, io.sarl.jaak.kernel.PhysicBody.___FORMAL_PARAMETER_DEFAULT_VALUE_1_1);
  }
  
  /**
   * Construct a skill.
   * @param owner - agent that is owning this skill. 
   * 
   */
  @Generated
  public PhysicBodySkill(final Agent owner) {
    super(owner);
  }
  
  /**
   * Construct a skill. The owning agent is unknown. 
   * 
   */
  @Generated
  public PhysicBodySkill() {
    super();
  }
}
