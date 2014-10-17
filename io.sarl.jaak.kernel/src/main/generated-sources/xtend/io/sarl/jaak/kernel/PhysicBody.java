package io.sarl.jaak.kernel;

import io.sarl.jaak.envinterface.perception.EnvironmentalObject;
import io.sarl.jaak.envinterface.perception.Perceivable;
import io.sarl.lang.annotation.DefaultValue;
import io.sarl.lang.annotation.DefaultValueSource;
import io.sarl.lang.annotation.DefaultValueUse;
import io.sarl.lang.annotation.Generated;
import io.sarl.lang.core.Capacity;
import org.arakhne.afc.math.continous.object2d.Vector2f;
import org.arakhne.afc.math.discrete.object2d.Point2i;

@SuppressWarnings("all")
public interface PhysicBody extends Capacity {
  /**
   * Default value for the parameter changeHeading
   */
  @Generated
  public final static boolean ___FORMAL_PARAMETER_DEFAULT_VALUE_0_1 = false;
  
  /**
   * Move the turtle along the given direction and
   * change the heading orientation if necessary.
   * The norm of the <var>direction</var> is the number
   * of cells to traverse.
   * 
   * @param direction is the motion direction.
   * @param changeHeading is <code>true</code> to force
   * the head to see at the same direction as the motion,
   * otherwise <code>false</code>.
   */
  @DefaultValueSource
  public abstract void move(final Vector2f direction, @DefaultValue("0_1") final boolean changeHeading);
  
  /**
   * Move the turtle along the given direction and
   * change the heading orientation if necessary.
   * The norm of the <var>direction</var> is the number
   * of cells to traverse.
   * 
   * @param direction is the motion direction.
   * @param changeHeading is <code>true</code> to force
   * the head to see at the same direction as the motion,
   * otherwise <code>false</code>.
   */
  @DefaultValueUse("org.arakhne.afc.math.continous.object2d.Vector2f,boolean")
  public abstract void move(final Vector2f direction);
  
  /**
   * Move the turtle straight ahead about the given number
   * of cells.
   * 
   * @param cells is the count of cells to traverse.
   */
  public abstract void moveForward(final int cells);
  
  /**
   * Move the turtle backward about the given number
   * of cells.
   * 
   * @param cells is the count of cells to traverse.
   */
  public abstract void moveBackward(final int cells);
  
  /**
   * Turn the head on the left of the turtle about the given
   * number of radians.
   * 
   * @param radians is the rotation angle.
   */
  public abstract void turnLeft(final float radians);
  
  /**
   * Turn the head on the right of the turtle about the given
   * number of radians.
   * 
   * @param radians is the rotation angle.
   */
  public abstract void turnRight(final float radians);
  
  /**
   * Set the orientation of the turtle head
   * to the given angle according to the trigonometric
   * circle.
   * 
   * @param radians is the orientation angle.
   */
  public abstract void setHeading(final float radians);
  
  /**
   * Set the orientation of the turtle head
   * to the given direction.
   * 
   * @param direction is the new direction of the head.
   */
  public abstract void setHeading(final Vector2f direction);
  
  /**
   * Put an object on the current cell of the environment.
   * 
   * @param object is the object to drop off.
   */
  public abstract void dropOff(final EnvironmentalObject object);
  
  /**
   * Remove an object from the current environment cell.
   * <p>
   * Caution: the object is not immediately removed from the environment
   * according to the influence mechanism.
   * 
   * @param <T> is the type of the object to pick up.
   * @param type is the type of the object to pick up.
   * @return the picked up object.
   */
  public abstract Perceivable pickUp(final Class<? extends Perceivable> type);
  
  /**
   * Remove an object from the current environment cell.
   * <p>
   * Caution: the object is not immediately removed from the environment
   * according to the influence mechanism.
   * 
   * @param object is the object to remove from the cell.
   */
  public abstract void pickUp(final EnvironmentalObject object);
  
  /**
   * Get an object from the current environment cell but do not
   * remove it from the cell.
   * 
   * @param <T> is the type of the object to touch up.
   * @param type is the type of the object to touch up.
   * @return the touched up object.
   */
  public abstract EnvironmentalObject touchUp(final Class<? extends EnvironmentalObject> type);
  
  /**
   * Set the semantic associated to the body.
   * 
   * @param semantic is the semantic of the body.
   */
  public abstract void setSemantic(final Object semantic);
  
  /**
   * Replies the position of the body in the environment.
   */
  public abstract Point2i getPosition();
  
  /**
   * Replies the orientation angle of the body on the cell.
   */
  public abstract float getOrientation();
  
  /**
   * Replies the orientation vector of the body on the cell.
   */
  public abstract Vector2f getOrientationVector();
  
  /**
   * Replies the orientation of the turtle head
   * in radians according to a trigonometric circle.
   * 
   * @return the orientation of the head in radians.
   */
  public abstract float getHeadingAngle();
  
  /**
   * Replies the orientation of the turtle head.
   * 
   * @return the orientation of the head in radians.
   */
  public abstract Vector2f getHeadingVector();
  
  /**
   * Replies x-coordinate of the position of the body.
   * 
   * @return the x-coordinate of the body.
   */
  public abstract int getX();
  
  /**
   * Replies y-coordinate of the position of the body.
   * 
   * @return the y-coordinate of the body.
   */
  public abstract int getY();
  
  /**
   * Replies the semantic associated to the body.
   * 
   * @param semantic is the semantic of the body.
   */
  public abstract Object getSemantic();
  
  /**
   * Replies the instant speed of the turtle.
   * 
   * @return the instant speed of the turtle in cells per second.
   */
  public abstract float getSpeed();
}
