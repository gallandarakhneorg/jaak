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
package io.sarl.jaak.environment.influence;

import io.sarl.jaak.environment.body.TurtleBody;
import io.sarl.jaak.environment.body.TurtleObject;
import io.sarl.jaak.environment.perception.EnvironmentalObject;
import io.sarl.jaak.environment.perception.JaakObject;

import org.arakhne.afc.math.continous.object2d.Vector2f;

/** This class defines a motion influence.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class MotionInfluence extends Influence {

	private final Vector2f linearMotion;
	private float angularMotion;
	private final JaakObject moveObject;

	/**
	 * @param emitter is the identifier of the influence emitter.
	 * @param linearMotion is the linear motion to apply. The vector
	 * describes the motion direction and the length of the vector
	 * is the number of cells to traverse.
	 * @param angularMotion is the rotational motion to apply.
	 */
	public MotionInfluence(TurtleObject emitter, Vector2f linearMotion, float angularMotion) {
		super(emitter);
		assert (linearMotion != null);
		assert (emitter != null);
		this.moveObject = emitter;
		this.linearMotion = linearMotion;
		this.angularMotion = angularMotion;
	}

	/**
	 * @param emitter is the identifier of the influence emitter.
	 * @param angularMotion is the rotational motion to apply.
	 */
	public MotionInfluence(TurtleObject emitter, float angularMotion) {
		super(emitter);
		assert (emitter != null);
		this.moveObject = emitter;
		this.linearMotion = new Vector2f();
		this.angularMotion = angularMotion;
	}

	/**
	 * @param emitter is the identifier of the influence emitter.
	 */
	public MotionInfluence(TurtleObject emitter) {
		super(emitter);
		assert (emitter != null);
		this.moveObject = emitter;
		this.linearMotion = new Vector2f();
		this.angularMotion = 0;
	}

	/**
	 * @param emitter is the identifier of the influence emitter.
	 * @param linearMotion is the linear motion to apply. The vector
	 * describes the motion direction and the length of the vector
	 * is the number of cells to traverse.
	 */
	public MotionInfluence(TurtleObject emitter, Vector2f linearMotion) {
		super(emitter);
		assert (linearMotion != null);
		assert (emitter != null);
		this.moveObject = emitter;
		this.linearMotion = linearMotion;
		this.angularMotion = 0f;
	}

	/**
	 * @param emitter is the identifier of the influence emitter.
	 */
	public MotionInfluence(TurtleBody emitter) {
		super(emitter);
		assert (emitter != null);
		this.moveObject = emitter;
		this.linearMotion = new Vector2f();
		this.angularMotion = 0f;
	}

	/**
	 * @param emitter is the identifier of the influence emitter.
	 * @param object is the object to move.
	 * @param linearMotion is the linear motion to apply. The vector
	 * describes the motion direction and the length of the vector
	 * is the number of cells to traverse.
	 * @param angularMotion is the rotational motion to apply.
	 */
	public MotionInfluence(TurtleBody emitter, EnvironmentalObject object, Vector2f linearMotion, float angularMotion) {
		super(emitter);
		assert (linearMotion != null);
		assert (object != null);
		this.moveObject = object;
		this.linearMotion = linearMotion;
		this.angularMotion = angularMotion;
	}

	/**
	 * @param emitter is the identifier of the influence emitter.
	 * @param object is the object to move.
	 * @param angularMotion is the rotational motion to apply.
	 */
	public MotionInfluence(TurtleBody emitter, EnvironmentalObject object, float angularMotion) {
		super(emitter);
		assert (object != null);
		this.moveObject = object;
		this.linearMotion = new Vector2f();
		this.angularMotion = angularMotion;
	}

	/**
	 * @param emitter is the identifier of the influence emitter.
	 * @param object is the object to move.
	 * @param linearMotion is the linear motion to apply. The vector
	 * describes the motion direction and the length of the vector
	 * is the number of cells to traverse.
	 */
	public MotionInfluence(TurtleBody emitter, EnvironmentalObject object, Vector2f linearMotion) {
		super(emitter);
		assert (linearMotion != null);
		assert (object != null);
		this.moveObject = object;
		this.linearMotion = linearMotion;
		this.angularMotion = 0f;
	}

	/**
	 * @param emitter is the identifier of the influence emitter.
	 * @param object is the object to move.
	 */
	public MotionInfluence(TurtleBody emitter, EnvironmentalObject object) {
		super(emitter);
		assert (object != null);
		this.moveObject = object;
		this.linearMotion = new Vector2f();
		this.angularMotion = 0f;
	}

	/** Set the linear motion to apply. The vector
	 * describes the motion direction and the length of the vector
	 * is the number of cells to traverse.
	 *
	 * @param x is the x-component of the motion vector.
	 * @param y is the y-component of the motion vector.
	 */
	public void setLinearMotion(float x, float y) {
		this.linearMotion.set(x, y);
	}

	/** Set the rotational motion to apply.
	 *
	 * @param angularMotion is the rotational motion to apply.
	 */
	public void setAngularMotion(float angularMotion) {
		this.angularMotion = angularMotion;
	}

	/** Fill the given vector with the linear motion vector
	 * to apply.
	 *
	 * @param motion is the vector which is filled.
	 */
	public void getLinearMotion(Vector2f motion) {
		assert (motion != null);
		motion.set(this.linearMotion);
	}

	/** Replies the linear motion
	 * vector to apply (not a copy).
	 *
	 * @return the linear motion vector to apply.
	 */
	public Vector2f getLinearMotion() {
		return this.linearMotion;
	}

	/** Replies the x-component of the linear motion
	 * vector to apply.
	 *
	 * @return the x-component of the linear motion
	 * vector to apply.
	 */
	public float getLinearMotionX() {
		return this.linearMotion.getX();
	}

	/** Replies the y-component of the linear motion
	 * vector to apply.
	 *
	 * @return the y-component of the linear motion
	 * vector to apply.
	 */
	public float getLinearMotionY() {
		return this.linearMotion.getY();
	}

	/** Replies the angular motion angle to apply.
	 *
	 * @return the angular motion angle to apply.
	 */
	public float getAngularMotion() {
		return this.angularMotion;
	}

	/** Replies the moved object.
	 *
	 * @return the moved object.
	 */
	public JaakObject getMovedObject() {
		return this.moveObject;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(getEmitter().getTurtleId().toString());
		buffer.append(": linear=("); //$NON-NLS-1$
		buffer.append(getLinearMotionX());
		buffer.append(';');
		buffer.append(getLinearMotionY());
		buffer.append("); angular="); //$NON-NLS-1$
		buffer.append(getAngularMotion());
		return buffer.toString();
	}

	/** Replies if this influence has a linear motion.
	 *
	 * @return <code>true</code> if the influence has a linear motion.
	 */
	public boolean hasLinearMotion() {
		return this.linearMotion != null && this.linearMotion.lengthSquared() != 0f;
	}

	/** Replies if this influence has an angular motion.
	 *
	 * @return <code>true</code> if the influence has an angular motion.
	 */
	public boolean hasAngularMotion() {
		return this.angularMotion != 0f;
	}

}
