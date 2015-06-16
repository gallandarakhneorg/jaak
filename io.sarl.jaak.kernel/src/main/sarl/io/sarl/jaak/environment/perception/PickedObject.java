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
package io.sarl.jaak.environment.perception;

import io.sarl.jaak.environment.body.TurtleObject;

import java.io.Serializable;

import org.arakhne.afc.math.discrete.object2d.Point2i;
import org.arakhne.afc.math.discrete.object2d.Vector2i;

/** This class defines a object which was picked up from the cell
 * according to a previous picking-up influence.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class PickedObject implements Perceivable, Serializable {

	private static final long serialVersionUID = -5408636984133012977L;

	private EnvironmentalObject pickedObject;

	/**
	 * @param pickedUpObject is the picked-up object.
	 */
	public PickedObject(EnvironmentalObject pickedUpObject) {
		this.pickedObject = pickedUpObject;
	}
	
	@Override
	protected PickedObject clone() {
		try {
			PickedObject o = (PickedObject) super.clone();
			o.pickedObject = this.pickedObject.clone();
			return o;
		} catch (CloneNotSupportedException e) {
			throw new Error(e);
		}
	}

	/** Replies the picked-up object.
	 *
	 * @return the picked-up object.
	 */
	public EnvironmentalObject getPickedUpObject() {
		return this.pickedObject;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("PICKED("); //$NON-NLS-1$
		buffer.append(this.pickedObject.getEnvironmentalObjectIdentifier());
		buffer.append(")@("); //$NON-NLS-1$
		Point2i position = getPosition();
		buffer.append(position.getX());
		buffer.append(';');
		buffer.append(position.getY());
		buffer.append(")"); //$NON-NLS-1$
		return buffer.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Point2i getPosition() {
		return this.pickedObject.getPosition();
	}

	/** {@inheritDoc}
	 */
	@Override
	public Vector2i getRelativePosition(TurtleObject body) {
		Point2i p = this.pickedObject.getPosition();
		if (body == null) {
			return null;
		}
		Point2i bp = body.getPosition();
		return new Vector2i(bp.x() - p.x(), bp.y() - p.y());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getSemantic() {
		return this.pickedObject.getSemantic();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isTurtle() {
		return this.pickedObject.isTurtle();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isBurrow() {
		return this.pickedObject.isBurrow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isObstacle() {
		return this.pickedObject.isObstacle();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSubstance() {
		return this.pickedObject.isSubstance();
	}

}
