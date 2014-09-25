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

import java.util.UUID;

import org.arakhne.afc.math.discrete.object2d.Point2i;

/** Factory of bodies for turtles.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public interface TurtleBodyFactory {

	/** Create an instance of a turtle body
	 * with to the given attributes.
	 * <p>
	 * The body is created by a factory implementation
	 * which is relevant to the current environment model. 
	 * <p>
	 * The given position could be discarted by the environment
	 * model if some internal rules is broken. In this case, the
	 * real position of the turtle is selected by the environment
	 * itself.
	 * <p>
	 * The given orientation angle could be discarted by the
	 * environment model according to internal rules. The
	 * orientation is then selected by the environment itself.
	 * 
	 * @param turtleId is the identifier of the turtle.
	 * @param desiredPosition is the position desired by the turtle.
	 * @param desiredAngle is the orientation angle desired by the turtle.
	 * @param semantic is the semantic to associated to the body.
	 * @return the created body, never <code>null</code>.
	 */
	public TurtleBody createTurtleBody(
			UUID turtleId,
			Point2i desiredPosition,
			float desiredAngle,
			Object semantic);
		
	/** Create an instance of a turtle body
	 * with to the given attributes.
	 * <p>
	 * The body is created by a factory implementation
	 * which is relevant to the current environment model. 
	 * <p>
	 * The given position could be discarted by the environment
	 * model if some internal rules is broken. In this case, the
	 * real position of the turtle is selected by the environment
	 * itself.
	 * <p>
	 * The given orientation angle could be discarted by the
	 * environment model according to internal rules. The
	 * orientation is then selected by the environment itself.
	 * 
	 * @param turtleId is the identifier of the turtle.
	 * @param desiredPosition is the position desired by the turtle.
	 * @param desiredAngle is the orientation angle desired by the turtle.
	 * @return the created body, never <code>null</code>.
	 */
	public TurtleBody createTurtleBody(
			UUID turtleId,
			Point2i desiredPosition,
			float desiredAngle);

	/** Create an instance of a turtle body
	 * with to the given attributes.
	 * <p>
	 * The body is created by a factory implementation
	 * which is relevant to the current environment model. 
	 * <p>
	 * The given position could be discarted by the environment
	 * model if some internal rules is broken. In this case, the
	 * real position of the turtle is selected by the environment
	 * itself.
	 * 
	 * @param turtleId is the identifier of the turtle.
	 * @param desiredPosition is the position desired by the turtle.
	 * @return the created body, never <code>null</code>.
	 */
	public TurtleBody createTurtleBody(
			UUID turtleId,
			Point2i desiredPosition);

	/** Create an instance of a turtle body
	 * with to the given attributes.
	 * <p>
	 * The body is created by a factory implementation
	 * which is relevant to the current environment model. 
	 * 
	 * @param turtleId is the identifier of the turtle.
	 * @return the created body, never <code>null</code>.
	 */
	public TurtleBody createTurtleBody(
			UUID turtleId);

	/** Create an instance of a turtle body
	 * with to the given attributes.
	 * <p>
	 * The body is created by a factory implementation
	 * which is relevant to the current environment model. 
	 * <p>
	 * The given orientation angle could be discarted by the
	 * environment model according to internal rules. The
	 * orientation is then selected by the environment itself.
	 * 
	 * @param turtleId is the identifier of the turtle.
	 * @param desiredAngle is the orientation angle desired by the turtle.
	 * @param semantic is the semantic to associated to the body.
	 * @return the created body, never <code>null</code>.
	 */
	public TurtleBody createTurtleBody(
			UUID turtleId,
			float desiredAngle,
			Object semantic);

	/** Create an instance of a turtle body
	 * with to the given attributes.
	 * <p>
	 * The body is created by a factory implementation
	 * which is relevant to the current environment model. 
	 * 
	 * @param turtleId is the identifier of the turtle.
	 * @param semantic is the semantic to associated to the body.
	 * @return the created body, never <code>null</code>.
	 */
	public TurtleBody createTurtleBody(
			UUID turtleId,
			Object semantic);

	/** Create an instance of a turtle body
	 * with to the given attributes.
	 * <p>
	 * The body is created by a factory implementation
	 * which is relevant to the current environment model. 
	 * <p>
	 * The given position could be discarted by the environment
	 * model if some internal rules is broken. In this case, the
	 * real position of the turtle is selected by the environment
	 * itself.
	 * 
	 * @param turtleId is the identifier of the turtle.
	 * @param desiredPosition is the position desired by the turtle.
	 * @param semantic is the semantic to associated to the body.
	 * @return the created body, never <code>null</code>.
	 */
	public TurtleBody createTurtleBody(
			UUID turtleId,
			Point2i desiredPosition,
			Object semantic);

	/** Create an instance of a turtle body
	 * with to the given attributes.
	 * <p>
	 * The body is created by a factory implementation
	 * which is relevant to the current environment model. 
	 * <p>
	 * The given position could be discarted by the environment
	 * model if some internal rules is broken. In this case, the
	 * real position of the turtle is selected by the environment
	 * itself.
	 * <p>
	 * The given orientation angle could be discarted by the
	 * environment model according to internal rules. The
	 * orientation is then selected by the environment itself.
	 * 
	 * @param turtleId is the identifier of the turtle.
	 * @param desiredPosition is the position desired by the turtle.
	 * @param desiredAngle is the orientation angle desired by the turtle.
	 * @param semantic is the semantic to associated to the body.
	 * @param frustum is the perception frustum to use.
	 * @return the created body, never <code>null</code>.
	 */
	public TurtleBody createTurtleBody(
			UUID turtleId,
			Point2i desiredPosition,
			float desiredAngle,
			Object semantic,
			TurtleFrustum frustum);
		
	/** Create an instance of a turtle body
	 * with to the given attributes.
	 * <p>
	 * The body is created by a factory implementation
	 * which is relevant to the current environment model. 
	 * <p>
	 * The given position could be discarted by the environment
	 * model if some internal rules is broken. In this case, the
	 * real position of the turtle is selected by the environment
	 * itself.
	 * <p>
	 * The given orientation angle could be discarted by the
	 * environment model according to internal rules. The
	 * orientation is then selected by the environment itself.
	 * 
	 * @param turtleId is the identifier of the turtle.
	 * @param desiredPosition is the position desired by the turtle.
	 * @param desiredAngle is the orientation angle desired by the turtle.
	 * @param frustum is the perception frustum to use.
	 * @return the created body, never <code>null</code>.
	 */
	public TurtleBody createTurtleBody(
			UUID turtleId,
			Point2i desiredPosition,
			float desiredAngle,
			TurtleFrustum frustum);

	/** Create an instance of a turtle body
	 * with to the given attributes.
	 * <p>
	 * The body is created by a factory implementation
	 * which is relevant to the current environment model. 
	 * <p>
	 * The given position could be discarted by the environment
	 * model if some internal rules is broken. In this case, the
	 * real position of the turtle is selected by the environment
	 * itself.
	 * 
	 * @param turtleId is the identifier of the turtle.
	 * @param desiredPosition is the position desired by the turtle.
	 * @param frustum is the perception frustum to use.
	 * @return the created body, never <code>null</code>.
	 */
	public TurtleBody createTurtleBody(
			UUID turtleId,
			Point2i desiredPosition,
			TurtleFrustum frustum);

	/** Create an instance of a turtle body
	 * with to the given attributes.
	 * <p>
	 * The body is created by a factory implementation
	 * which is relevant to the current environment model. 
	 * 
	 * @param turtleId is the identifier of the turtle.
	 * @param frustum is the perception frustum to use.
	 * @return the created body, never <code>null</code>.
	 */
	public TurtleBody createTurtleBody(
			UUID turtleId,
			TurtleFrustum frustum);

	/** Create an instance of a turtle body
	 * with to the given attributes.
	 * <p>
	 * The body is created by a factory implementation
	 * which is relevant to the current environment model. 
	 * <p>
	 * The given orientation angle could be discarted by the
	 * environment model according to internal rules. The
	 * orientation is then selected by the environment itself.
	 * 
	 * @param turtleId is the identifier of the turtle.
	 * @param desiredAngle is the orientation angle desired by the turtle.
	 * @param semantic is the semantic to associated to the body.
	 * @param frustum is the perception frustum to use.
	 * @return the created body, never <code>null</code>.
	 */
	public TurtleBody createTurtleBody(
			UUID turtleId,
			float desiredAngle,
			Object semantic,
			TurtleFrustum frustum);

	/** Create an instance of a turtle body
	 * with to the given attributes.
	 * <p>
	 * The body is created by a factory implementation
	 * which is relevant to the current environment model. 
	 * 
	 * @param turtleId is the identifier of the turtle.
	 * @param semantic is the semantic to associated to the body.
	 * @param frustum is the perception frustum to use.
	 * @return the created body, never <code>null</code>.
	 */
	public TurtleBody createTurtleBody(
			UUID turtleId,
			Object semantic,
			TurtleFrustum frustum);

	/** Create an instance of a turtle body
	 * with to the given attributes.
	 * <p>
	 * The body is created by a factory implementation
	 * which is relevant to the current environment model. 
	 * <p>
	 * The given position could be discarted by the environment
	 * model if some internal rules is broken. In this case, the
	 * real position of the turtle is selected by the environment
	 * itself.
	 * 
	 * @param turtleId is the identifier of the turtle.
	 * @param desiredPosition is the position desired by the turtle.
	 * @param semantic is the semantic to associated to the body.
	 * @param frustum is the perception frustum to use.
	 * @return the created body, never <code>null</code>.
	 */
	public TurtleBody createTurtleBody(
			UUID turtleId,
			Point2i desiredPosition,
			Object semantic,
			TurtleFrustum frustum);

	/** Replies if the cell at the given position is able to
	 * receive the new turtle body.
	 *
	 * @param position
	 * @return <code>true</code> if the new body could be put on the
	 * cell, otherwise <code>false</code>.
	 */
	public boolean isFreeCell(Point2i position);
	
}