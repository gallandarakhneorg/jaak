/*
 * $Id$
 *
 * Jaak environment model is an open-source multiagent library.
 * More details on http://www.sarl.io
 *
 * Copyright (C) 2014 Stéphane GALLAND.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.sarl.jaak.envinterface.body;

import io.sarl.jaak.envinterface.frustum.TurtleFrustum;
import io.sarl.jaak.envinterface.influence.MotionInfluenceStatus;
import io.sarl.jaak.envinterface.perception.EnvironmentalObject;
import io.sarl.jaak.envinterface.perception.JaakObject;
import io.sarl.jaak.envinterface.perception.Perceivable;
import io.sarl.jaak.envinterface.perception.PerceivedTurtle;

import java.util.Collection;
import java.util.UUID;

import org.arakhne.afc.math.continous.object2d.Vector2f;

/** This interface defines a body for a turtle.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public interface TurtleBody extends JaakObject {

	/** Replies the owner of this body.
	 * 
	 * @return the owner of this body.
	 */
	public UUID getTurtleId();
	
	/** Notify the body that is should do nothing.
	 * <p>
	 * This method should be invoked when no other kind of
	 * influence is sent by the turtle.
	 */
	public void beIddle();

	/** Move the turtle along the given direction and
	 * change the heading orientation if necessary.
	 * The norm of the <var>direction</var> is the number
	 * of cells to traverse.
	 * 
	 * @param direction is the motion direction.
	 * @param changeHeading is <code>true</code> to force
	 * the head to see at the same direction as the motion,
	 * otherwise <code>false</code>.
	 */
	public void move(Vector2f direction, boolean changeHeading);
	
	/** Move the turtle straight ahead about the given number 
	 * of cells.
	 * 
	 * @param cells is the count of cells to traverse.
	 */
	public void moveForward(int cells);
	
	/** Move the turtle backward about the given number 
	 * of cells.
	 * 
	 * @param cells is the count of cells to traverse.
	 */
	public void moveBackward(int cells);

	/** Turn the head on the left of the turtle about the given
	 * number of radians.
	 * 
	 * @param radians is the rotation angle.
	 */
	public void turnLeft(float radians);

	/** Turn the head on the right of the turtle about the given
	 * number of radians.
	 * 
	 * @param radians is the rotation angle.
	 */
	public void turnRight(float radians);

	/** Set the orientation of the turtle head 
	 * to the given angle according to the trigonometric
	 * circle.
	 * 
	 * @param radians is the orientation angle.
	 */
	public void setHeading(float radians);
	
	/** Set the orientation of the turtle head 
	 * to the given direction.
	 * 
	 * @param direction is the new direction of the head.
	 */
	public void setHeading(Vector2f direction);

	/** Replies the orientation of the turtle head
	 * in radians according to a trigonometric circle.
	 * 
	 * @return the orientation of the head in radians.
	 */
	public float getHeadingAngle();
	
	/** Replies the orientation of the turtle head.
	 * 
	 * @return the orientation of the head in radians.
	 */
	public Vector2f getHeadingVector();

	/** Put an object on the current cell of the environment.
	 * 
	 * @param object is the object to drop off.
	 */
	public void dropOff(EnvironmentalObject object);

	/** Remove an object from the current environment cell.
	 * <p>
	 * Caution: the object is not immediately removed from the environment
	 * according to the influence mechanism.
	 * 
	 * @param <T> is the type of the object to pick up.
	 * @param type is the type of the object to pick up.
	 * @return the picked up object.
	 */
	public <T extends Perceivable> T pickUp(Class<T> type);

	/** Remove an object with the given semantic from the current environment cell.
	 * <p>
	 * Caution: the object is not immediately removed from the environment
	 * according to the influence mechanism.
	 * 
	 * @param semantic is the searched semantic.
	 * @return the picked up object or <code>null</code>.
	 */
	public EnvironmentalObject pickUpWithSemantic(Object semantic);

	/** Remove an object from the current environment cell.
	 * <p>
	 * Caution: the object is not immediately removed from the environment
	 * according to the influence mechanism.
	 * 
	 * @param object is the object to remove from the cell.
	 */
	public void pickUp(EnvironmentalObject object);

	/** Get an object from the current environment cell but do not
	 * remove it from the cell.
	 * 
	 * @param <T> is the type of the object to touch up.
	 * @param type is the type of the object to touch up.
	 * @return the touched up object.
	 */
	public <T extends EnvironmentalObject> T touchUp(Class<T> type);

	/** Get an object with the given semantic from the current environment cell but do not
	 * remove it from the cell.
	 * 
	 * @param semantic is the searched semantic
	 * @return the touched up object.
	 */
	public EnvironmentalObject touchUpWithSemantic(Object semantic);

	/** Replies x-coordinate of the position of the body.
	 * 
	 * @return the x-coordinate of the body.
	 */
	public int getX();
	
	/** Replies y-coordinate of the position of the body.
	 * 
	 * @return the y-coordinate of the body.
	 */
	public int getY();
	
	/** Replies the all the perceptions of the body.
	 * 
	 * @return the collection of perceived objects.
	 */
	public Collection<Perceivable> getPerception();

	/** Replies the all the perceptions of the body of a given type.
	 * 
	 * @param <T> is the type of the objects to perceived.
	 * @param type is the type of the objects to perceived.
	 * @return the collection of perceived objects.
	 */
	public <T extends Perceivable> Collection<T> getPerception(Class<T> type);

	/** Replies the first perception of the body of a given type.
	 * 
	 * @param <T> is the type of the objects to perceived.
	 * @param type is the type of the objects to perceived.
	 * @return the collection of perceived objects.
	 */
	public <T extends Perceivable> T getFirstPerception(Class<T> type);

	/** Replies the all the environmental objects perceived by the body.
	 * 
	 * @return the collection of perceived environmental objects.
	 */
	public Collection<EnvironmentalObject> getPerceivedObjects();
	
	/** Replies the all the turtles perceived by the body.
	 * 
	 * @return the collection of perceived turtles.
	 */
	public Collection<PerceivedTurtle> getPerceivedTurtles();

	/** Replies if this body has perceived something.
	 * 
	 * @return <code>true</code> if something is perceived,
	 * otherwise <code>false</code>.
	 */
	public boolean hasPerception();

	/** Replies if this body has perceived environmental objects.
	 * 
	 * @return <code>true</code> if an environmental
	 * object is perceived, otherwise <code>false</code>.
	 */
	public boolean hasPerceivedObject();

	/** Replies if this body has perceived turtles.
	 * 
	 * @return <code>true</code> if a turtle
	 * is perceived, otherwise <code>false</code>.
	 */
	public boolean hasPerceivedTurtle();
	
	/** Replies the perception frustum owned by this body.
	 * 
	 * @return the perception frustum or <code>null</code> if
	 * this body is not able to perceive.
	 */
	public TurtleFrustum getPerceptionFrustum();

	/** Replies the semantic associated to the body.
	 * 
	 * @param semantic is the semantic of the body.
	 */
	public void setSemantic(Object semantic);

	/** Replies if this body has registered influences which are not
	 * yet consumed by the environment.
	 * 
	 * @return <code>true</code> if the body has not-consumed influences,
	 * otherwise <code>false</code>.
	 */
	public boolean hasInfluences();

	/** Replies the instant speed of the turtle.
	 * 
	 * @return the instant speed of the turtle in cells per second.
	 */
	public float getSpeed();

	/** Notifies the body that perceptions should be enabled or not.
	 * 
	 * @param enable is <code>true</code> to enable perception from the body,
	 * <code>false</code> to disable perceptions.
	 */
	public void setPerceptionEnable(boolean enable);

	/** Replies if the perceptions are computed or not.
	 * 
	 * @return <code>true</code> if perceptions are enable from the body,
	 * <code>false</code> if they are disable.
	 */
	public boolean isPerceptionEnable();

	/** Replies the status of the application of the last motion influence
	 * sent by via this turtle body.
	 * 
	 * @return the application status of the last motion influence.
	 */
	public MotionInfluenceStatus getLastMotionInfluenceStatus();

}