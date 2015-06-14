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

import java.io.Serializable;
import java.util.UUID;

import org.arakhne.afc.math.continous.object2d.Vector2f;
import org.arakhne.afc.math.discrete.object2d.Point2i;

/** This class defines a perceived turtle.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class PerceivedTurtle extends AbstractPerceivable {

	private static final long serialVersionUID = -758584526816638042L;

	private final UUID turtle;
	private final float speed;
	private final float angle;

	/**
	 * @param perceivedTurtle is the identifier of the perceived turtle.
	 * @param observer is the position of the observer.
	 * @param observed is the position of the perceived turtle.
	 * @param observedSpeed is the speed of the observed turtle.
	 * @param observedOrientation is the orientation angle of the observed turtle.
	 * @param semantic is the semantic associated to the turtle body.
	 */
	public PerceivedTurtle(UUID perceivedTurtle, Point2i observer, Point2i observed,
			float observedSpeed, float observedOrientation, Serializable semantic) {
		super();
		assert (perceivedTurtle != null);
		assert (observer != null);
		assert (observed != null);
		this.turtle = perceivedTurtle;
		this.position.set(observed);
		this.speed = observedSpeed;
		this.angle = observedOrientation;
		this.semantic = semantic;
	}

	/** Replies the identifier of this perceived object.
	 *
	 * @return the identifier of this perceived object, or <code>null</code>
	 * if the perceived object has no address.
	 */
	public UUID getIdentity() {
		return this.turtle;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean isTurtle() {
		return true;
	}

	/**
	 * Replies the instant speed of the perceived turtle.
	 *
	 * @return the instant speed in cell per second.
	 */
	public float getSpeed() {
		return this.speed;
	}

	/**
	 * Replies the current orientation of the perceived turtle.
	 *
	 * @return the current orientation angle of the perceived turtle.
	 */
	public float getHeadingAngle() {
		return this.angle;
	}

	/**
	 * Replies the current orientation of the perceived turtle.
	 *
	 * @return the current orientation vector of the perceived turtle.
	 */
	public Vector2f getHeadingVector() {
		return Vector2f.toOrientationVector(this.angle);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean isBurrow() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean isObstacle() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean isSubstance() {
		return false;
	}

}
