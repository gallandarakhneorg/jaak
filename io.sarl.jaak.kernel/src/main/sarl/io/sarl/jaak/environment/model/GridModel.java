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
package io.sarl.jaak.environment.model;

import io.sarl.jaak.environment.ValidationResult;
import io.sarl.jaak.environment.body.TurtleBody;
import io.sarl.jaak.environment.perception.EnvironmentalObject;

import org.arakhne.afc.math.discrete.object2d.Point2i;

/** This interface defines a grid for the Jaak environment model.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public interface GridModel {

	/** Replies the width of the grid.
	 *
	 * @return the width of the grid.
	 */
	int getWidth();

	/** Replies the height of the grid.
	 *
	 * @return the height of the grid.
	 */
	int getHeight();

	/** Replies the turtle body on the cell at the
	 * given coordinate.
	 *
	 * @param x is the coordinate of the cell.
	 * @param y is the coordinate of the cell.
	 * @return the turtle body at the given position, or
	 * <code>null</code> if no turtle body is located at
	 * the given position.
	 */
	TurtleBody getTurtle(int x, int y);

	/** Replies the environmental objects on the cell at the
	 * given coordinate.
	 *
	 * @param x is the coordinate of the cell.
	 * @param y is the coordinate of the cell.
	 * @return the environmental objects at the given position,
	 * never <code>null</code>.
	 */
	Iterable<? extends EnvironmentalObject> getObjects(int x, int y);

	/** Replies the environmental object on the cell at the
	 * given coordinate and with the given identifier.
	 *
	 * @param x is the coordinate of the cell.
	 * @param y is the coordinate of the cell.
	 * @param identifier the identifier of the object to reply.
	 * @return the environmental object at the given position,
	 * or <code>null</code> if the object was not found.
	 */
	EnvironmentalObject getObject(int x, int y, String identifier);

	/** Validate the given position to be on the grid.
	 * This function ensures that the given position is
	 * updated to fit the bounds of the grid.
	 *
	 * @param isWrapped indicates if the grid is using wrapped coordinate system.
	 * @param allowDiscard indicates if the coordinates are able to be discarded.
	 * @param position is the position to validate.
	 * @return how the position has been changed.
	 */
	ValidationResult validatePosition(boolean isWrapped, boolean allowDiscard, Point2i position);

}
