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
package io.sarl.jaak.envinterface.perception;

import io.sarl.jaak.envinterface.body.TurtleBody;

import org.arakhne.afc.math.discrete.object2d.Point2i;

/** This interface defines a situated object which is perceivable
 * inside the Jaak environment.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public interface Perceivable {

	/** Replies the absolute position of this perceived object.
	 *
	 * @return the absolute position of this perceived object.
	 */
	Point2i getPosition();

	/** Replies the relative position of this perceived object to the given body.
	 *
	 * @param body - the body for which the relative position should be computed.
	 * @return the relative position of this perceived object to the given body.
	 */
	Point2i getRelativePosition(TurtleBody body);

	/** Replies if this perceived object is a turtle.
	 *
	 * @return <code>true</code> if this perceived object is a turtle,
	 * otherwise <code>false</code>.
	 */
	boolean isTurtle();

	/** Replies if this perceived object is an obstacle.
	 *
	 * @return <code>true</code> if this perceived object is an obstacle,
	 * otherwise <code>false</code>.
	 */
	boolean isObstacle();

	/** Replies if this perceived object is a burrow.
	 *
	 * @return <code>true</code> if this perceived object is a burrow,
	 * otherwise <code>false</code>.
	 */
	boolean isBurrow();

	/** Replies if this perceived object is a substance.
	 *
	 * @return <code>true</code> if this perceived object is a substance,
	 * otherwise <code>false</code>.
	 */
	boolean isSubstance();

	/** Replies the semantic associated to this object.
	 *
	 * @return the semantic associated to this object.
	 */
	Object getSemantic();

}
