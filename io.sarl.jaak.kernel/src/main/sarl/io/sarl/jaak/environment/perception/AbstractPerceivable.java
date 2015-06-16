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

/** This class defines a perceived turtle.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public abstract class AbstractPerceivable implements Perceivable, Serializable {

	private static final long serialVersionUID = -7970321275288727456L;

	/** Position of the perceived object.
	 */
	private Point2i position = new Point2i();

	/** Is the semantic associated to this perceived object.
	 */
	private Serializable semantic;

	/**
	 */
	public AbstractPerceivable() {
		//
	}
	
	@Override
	protected AbstractPerceivable clone() {
		try {
			AbstractPerceivable c = (AbstractPerceivable) super.clone();
			c.position = this.position.clone();
			return c;
		} catch (CloneNotSupportedException e) {
			throw new Error(e);
		}
	}
	
	/** Change the position of the object.
	 *
	 * @param position the new position.
	 */
	protected void setPosition(Point2i position) {
		this.position.set(position);
	}

	/** Change the position of the object.
	 *
	 * @param x the new position.
	 * @param y the new position.
	 */
	protected void setPosition(float x, float y) {
		this.position.set(x, y);
	}

	/** Change the semantic of the object.
	 *
	 * @param semantic the new semantic.
	 */
	protected void setSemantic(Serializable semantic) {
		this.semantic = semantic;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Point2i getPosition() {
		return this.position;
	}

	/** {@inheritDoc}
	 */
	@Override
	public Vector2i getRelativePosition(TurtleObject body) {
		Point2i p = this.position;
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
	public Serializable getSemantic() {
		return this.semantic;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		Point2i position = getPosition();
		buffer.append('(');
		buffer.append(position.getX());
		buffer.append(';');
		buffer.append(position.getY());
		buffer.append("); semantic="); //$NON-NLS-1$
		Object semantic = getSemantic();
		if (semantic == null) {
			buffer.append((String) null);
		} else {
			buffer.append(semantic.toString());
		}
		return buffer.toString();
	}

}
