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
package io.sarl.jaak.environment.body;

import io.sarl.jaak.environment.frustum.TurtleFrustum;
import io.sarl.jaak.environment.influence.MotionInfluenceStatus;
import io.sarl.jaak.environment.perception.EnvironmentalObject;
import io.sarl.jaak.environment.perception.JaakObject;
import io.sarl.jaak.environment.perception.Perceivable;
import io.sarl.jaak.environment.perception.PerceivedTurtle;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.arakhne.afc.math.continous.object2d.Vector2f;
import org.arakhne.afc.math.discrete.object2d.Vector2i;

/** This interface defines a body for a turtle.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public interface TurtleObject extends JaakObject {

	/** Replies the owner of this body.
	 *
	 * @return the owner of this body.
	 */
	UUID getTurtleId();

	/** Replies the orientation of the turtle head
	 * in radians according to a trigonometric circle.
	 *
	 * @return the orientation of the head in radians.
	 */
	float getHeadingAngle();

	/** Replies the orientation of the turtle head.
	 *
	 * @return the orientation of the head in radians.
	 */
	Vector2f getHeadingVector();

	/** Replies the motion vector of the turtle.
	 *
	 * @return the orientation of the head in radians.
	 */
	Vector2i getMotionVector();

	/** Replies x-coordinate of the position of the body.
	 *
	 * @return the x-coordinate of the body.
	 */
	int getX();

	/** Replies y-coordinate of the position of the body.
	 *
	 * @return the y-coordinate of the body.
	 */
	int getY();

	/** Replies the all the perceptions of the body.
	 *
	 * @return the collection of perceived objects.
	 */
	Collection<Perceivable> getPerception();

	/** Replies the perception of the types of the ground cells.
	 *
	 * @return the pairs of relative positions and ground types.
	 */
	Map<Vector2i, Serializable> getGroundPerception();

	/** Replies the type of the ground cell on which the turtle is located.
	 *
	 * @return the ground type where the turtle is located.
	 */
	Serializable getCurrentGroundType();

	/** Replies the all the perceptions of the body of a given type.
	 *
	 * @param <T> is the type of the objects to perceived.
	 * @param type is the type of the objects to perceived.
	 * @return the collection of perceived objects.
	 */
	<T extends Perceivable> Collection<T> getPerception(Class<T> type);

	/** Replies the first perception of the body of a given type.
	 * The replied value depends on the order of the perceived elements,
	 * that is defined in the environment model.
	 *
	 * @param <T> is the type of the objects to perceived.
	 * @param type is the type of the objects to perceived.
	 * @return the collection of perceived objects.
	 */
	<T extends Perceivable> T getFirstPerception(Class<T> type);

	/** Replies the all the environmental objects perceived by the body.
	 *
	 * @return the collection of perceived environmental objects.
	 */
	Collection<EnvironmentalObject> getPerceivedObjects();

	/** Replies the all the turtles perceived by the body.
	 *
	 * @return the collection of perceived turtles.
	 */
	Collection<PerceivedTurtle> getPerceivedTurtles();

	/** Replies if this body has perceived something.
	 *
	 * @return <code>true</code> if something is perceived,
	 * otherwise <code>false</code>.
	 */
	boolean hasPerception();

	/** Replies if this body has perceived environmental objects.
	 *
	 * @return <code>true</code> if an environmental
	 * object is perceived, otherwise <code>false</code>.
	 */
	boolean hasPerceivedObject();

	/** Replies if this body has perceived turtles.
	 *
	 * @return <code>true</code> if a turtle
	 * is perceived, otherwise <code>false</code>.
	 */
	boolean hasPerceivedTurtle();

	/** Replies the perception frustum owned by this body.
	 *
	 * @return the perception frustum or <code>null</code> if
	 * this body is not able to perceive.
	 */
	TurtleFrustum getPerceptionFrustum();

	/** Replies the instant speed of the turtle.
	 *
	 * @return the instant speed of the turtle in cells per second.
	 */
	float getSpeed();

	/** Replies the status of the application of the last motion influence
	 * sent by via this turtle body.
	 *
	 * @return the application status of the last motion influence.
	 */
	MotionInfluenceStatus getLastMotionInfluenceStatus();

}
