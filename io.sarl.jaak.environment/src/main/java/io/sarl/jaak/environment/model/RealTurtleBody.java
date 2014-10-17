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

import io.sarl.jaak.envinterface.body.TurtleBody;
import io.sarl.jaak.envinterface.frustum.TurtleFrustum;
import io.sarl.jaak.envinterface.influence.DropDownInfluence;
import io.sarl.jaak.envinterface.influence.Influence;
import io.sarl.jaak.envinterface.influence.MotionInfluence;
import io.sarl.jaak.envinterface.influence.MotionInfluenceStatus;
import io.sarl.jaak.envinterface.influence.PickUpInfluence;
import io.sarl.jaak.envinterface.perception.EnvironmentalObject;
import io.sarl.jaak.envinterface.perception.Perceivable;
import io.sarl.jaak.envinterface.perception.PerceivedTurtle;
import io.sarl.jaak.envinterface.perception.PickedObject;
import io.sarl.jaak.util.MultiCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.arakhne.afc.math.MathUtil;
import org.arakhne.afc.math.continous.object2d.Vector2f;
import org.arakhne.afc.math.discrete.object2d.Point2i;

/** This class defines an implementation of turtle body.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public final class RealTurtleBody implements TurtleBody, Comparable<RealTurtleBody> {

	private final UUID turtle;
	private final TurtleFrustum frustum;
	private Object semantic;
	private Collection<EnvironmentalObject> perceivedObjects;
	private Collection<PerceivedTurtle> perceivedBodies;
	private Collection<PickedObject> pickingResults;
	private MotionInfluence lastMotionInfluence;
	private MotionInfluenceStatus lastMotionInfluenceStatus;
	private List<Influence> otherInfluences;
	private int x;
	private int y;
	private float heading;
	private Vector2f headingVector;
	private float speed;
	private boolean isPerceptionEnable = true;

	/**
	 * @param turtle is the identifier of the turtle which is owning this body.
	 * @param frustum is the perception frustum to be used by this body, or <code>null</code>
	 * if this body is not able to perceive.
	 * @param headingAngle is the orientation angle of the turtle head.
	 * @param semantic is the semantic associated with the body.
	 */
	RealTurtleBody(
			UUID turtle,
			TurtleFrustum frustum,
			float headingAngle,
			Object semantic) {
		assert (turtle != null);
		this.turtle = turtle;
		this.frustum = frustum;
		this.semantic = semantic;
		this.heading = headingAngle;
	}

	private void fireInfluenceReception() {
		//
	}

	/** {@inheritDoc}
	 */
	@Override
	public synchronized boolean hasInfluences() {
		return this.lastMotionInfluence != null
				|| (this.otherInfluences != null && !this.otherInfluences.isEmpty());
	}

	/** Register the perceptions for this body.
	 *
	 * @param bodies are the perceptions of turtle bodies.
	 * @param objects are the perceptions of environmental objects.
	 */
	synchronized void setPerceptions(Collection<PerceivedTurtle> bodies, Collection<EnvironmentalObject> objects) {
		this.lastMotionInfluence = null;
		this.otherInfluences = null;
		assert (bodies != null);
		assert (objects != null);
		this.perceivedBodies = bodies;
		this.perceivedObjects = objects;
	}

	/**
	 * Save the given picking action for the next perception query.
	 * <p>
	 * The picking actions are put back in the turtle perceptions to
	 * notify the turtle about a picking influence result.
	 *
	 * @param action is the picking action description.
	 */
	synchronized void putBackPickingAction(PickedObject action) {
		if (action != null) {
			if (this.pickingResults == null) {
				this.pickingResults = new LinkedList<>();
			}
			this.pickingResults.add(action);
		}
	}

	/**
	 * Save the status of the last motion influence.
	 *
	 * @param status is the status of the last motion influence.
	 */
	synchronized void putBackMotionInfluenceStatus(MotionInfluenceStatus status) {
		this.lastMotionInfluenceStatus = status;
	}

	/** Update the physical attribute of the body, ie. its position and
	 * its orientation.
	 *
	 * @param x is the new position of the turtle body.
	 * @param y is the new position of the turtle body.
	 * @param heading is the orientation of the turtle body head.
	 * @param speed is the speed of the body in cells per second.
	 */
	synchronized void setPhysicalState(int x, int y, float heading, float speed) {
		this.x = x;
		this.y = y;
		this.heading = MathUtil.clampRadian(heading);
		this.headingVector = null;
		this.speed = speed;
	}

	/** Replies the last motion influence stored by the body and remove it
	 * from the body.
	 *
	 * @return the collected motion influence.
	 */
	public synchronized MotionInfluence consumeMotionInfluence() {
		MotionInfluence inf = this.lastMotionInfluence;
		this.lastMotionInfluence = null;
		return inf;
	}

	/** Replies the not-motion influences stored by the body and remove them
	 * from the body.
	 *
	 * @return the collected motion influence.
	 */
	public synchronized List<Influence> consumeOtherInfluences() {
		List<Influence> infs = this.otherInfluences;
		this.otherInfluences = null;
		this.pickingResults = null;
		return infs;
	}

	/** {@inheritDoc}
	 */
	@Override
	public synchronized void beIddle() {
		fireInfluenceReception();
		this.lastMotionInfluence = null;
		this.otherInfluences = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void dropOff(EnvironmentalObject object) {
		fireInfluenceReception();
		if (this.otherInfluences == null) {
			this.otherInfluences = new LinkedList<>();
		}
		this.otherInfluences.add(new DropDownInfluence(this, object));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized <T extends Perceivable> T pickUp(Class<T> type) {
		assert (type != null);
		if (this.perceivedObjects == null || this.perceivedObjects.isEmpty()) {
			return null;
		}
		Iterator<EnvironmentalObject> iterator = this.perceivedObjects.iterator();
		EnvironmentalObject obj;
		while (iterator.hasNext()) {
			obj = iterator.next();
			assert (obj != null);
			if (type.isInstance(obj) && getPosition().equals(obj.getPosition())) {
				fireInfluenceReception();
				if (this.otherInfluences == null) {
					this.otherInfluences = new LinkedList<>();
				}
				this.otherInfluences.add(new PickUpInfluence(this, obj));
				return type.cast(obj);
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EnvironmentalObject pickUpWithSemantic(Object semantic) {
		if (semantic != null && this.perceivedObjects != null && !this.perceivedObjects.isEmpty()) {
			Iterator<EnvironmentalObject> iterator = this.perceivedObjects.iterator();
			EnvironmentalObject obj;
			Object sem;
			while (iterator.hasNext()) {
				obj = iterator.next();
				assert (obj != null);
				sem = obj.getSemantic();
				if (sem != null && sem.equals(semantic) && getPosition().equals(obj.getPosition())) {
					fireInfluenceReception();
					if (this.otherInfluences == null) {
						this.otherInfluences = new LinkedList<>();
					}
					this.otherInfluences.add(new PickUpInfluence(this, obj));
					return obj;
				}
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void pickUp(EnvironmentalObject object) {
		fireInfluenceReception();
		if (this.otherInfluences == null) {
			this.otherInfluences = new LinkedList<>();
		}
		this.otherInfluences.add(new PickUpInfluence(this, object));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized <T extends EnvironmentalObject> T touchUp(Class<T> type) {
		assert (type != null);
		if (this.perceivedObjects == null || this.perceivedObjects.isEmpty()) {
			return null;
		}
		Iterator<EnvironmentalObject> iterator = this.perceivedObjects.iterator();
		EnvironmentalObject obj;
		while (iterator.hasNext()) {
			obj = iterator.next();
			assert (obj != null);
			if (type.isInstance(obj) && getPosition().equals(obj.getPosition())) {
				return type.cast(obj);
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EnvironmentalObject touchUpWithSemantic(Object semantic) {
		if (semantic != null && this.perceivedObjects != null && !this.perceivedObjects.isEmpty()) {
			Iterator<EnvironmentalObject> iterator = this.perceivedObjects.iterator();
			EnvironmentalObject obj;
			Object sem;
			while (iterator.hasNext()) {
				obj = iterator.next();
				assert (obj != null);
				sem = obj.getSemantic();
				if (sem != null && sem.equals(semantic) && getPosition().equals(obj.getPosition())) {
					return obj;
				}
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void move(Vector2f direction, boolean changeHeading) {
		assert (direction != null);
		fireInfluenceReception();

		if (this.lastMotionInfluence == null) {
			this.lastMotionInfluence = new MotionInfluence(this, new Vector2f(direction.getX(), direction.getY()));
		} else {
			this.lastMotionInfluence.setLinearMotion(direction.getX(), direction.getY());
		}

		if (changeHeading) {
			setHeading(direction);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void moveBackward(int cells) {
		if (cells > 0) {
			fireInfluenceReception();
			Vector2f head = getHeadingVector();
			float f = -cells / head.length();
			float x = head.getX() * f;
			float y = head.getY() * f;
			if (this.lastMotionInfluence == null) {
				this.lastMotionInfluence = new MotionInfluence(this, new Vector2f(x, y));
			} else {
				this.lastMotionInfluence.setLinearMotion(x, y);
			}
			this.lastMotionInfluence.setAngularMotion(0);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void moveForward(int cells) {
		if (cells > 0) {
			fireInfluenceReception();
			Vector2f head = getHeadingVector();
			float x = head.getX() * cells;
			float y = head.getY() * cells;
			if (this.lastMotionInfluence == null) {
				this.lastMotionInfluence = new MotionInfluence(this, new Vector2f(x, y));
			} else {
				this.lastMotionInfluence.setLinearMotion(x, y);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void setHeading(float radians) {
		fireInfluenceReception();
		float v = radians - this.heading;
		if (this.lastMotionInfluence == null) {
			this.lastMotionInfluence = new MotionInfluence(this, v);
		} else {
			this.lastMotionInfluence.setAngularMotion(v);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void setHeading(Vector2f direction) {
		assert (direction != null);
		setHeading(direction.getOrientationAngle());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void turnLeft(float radians) {
		fireInfluenceReception();
		if (this.lastMotionInfluence == null) {
			this.lastMotionInfluence = new MotionInfluence(this, -radians);
		} else {
			this.lastMotionInfluence.setAngularMotion(-radians);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void turnRight(float radians) {
		fireInfluenceReception();
		if (this.lastMotionInfluence == null) {
			this.lastMotionInfluence = new MotionInfluence(this, radians);
		} else {
			this.lastMotionInfluence.setAngularMotion(radians);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized float getHeadingAngle() {
		return this.heading;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized Vector2f getHeadingVector() {
		if (this.headingVector == null) {
			this.headingVector = Vector2f.toOrientationVector(this.heading);
		}
		return this.headingVector;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized Collection<EnvironmentalObject> getPerceivedObjects() {
		if (this.perceivedObjects == null) {
			throw new IllegalStateException();
		}
		return Collections.unmodifiableCollection(this.perceivedObjects);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized Collection<PerceivedTurtle> getPerceivedTurtles() {
		if (this.perceivedBodies == null) {
			throw new IllegalStateException();
		}
		return Collections.unmodifiableCollection(this.perceivedBodies);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized Collection<Perceivable> getPerception() {
		if (this.perceivedBodies == null || this.perceivedObjects == null) {
			throw new IllegalStateException();
		}
		MultiCollection<Perceivable> perceps = new MultiCollection<>();
		perceps.addCollection(this.perceivedBodies);
		perceps.addCollection(this.perceivedObjects);
		if (this.pickingResults != null && !this.pickingResults.isEmpty()) {
			perceps.addCollection(this.pickingResults);
		}
		return perceps;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized <T extends Perceivable> Collection<T> getPerception(Class<T> type) {
		assert (type != null);
		if (this.perceivedBodies == null || this.perceivedObjects == null) {
			throw new IllegalStateException();
		}
		Collection<T> perceps = new ArrayList<>();
		if (PerceivedTurtle.class.isAssignableFrom(type)) {
			for (Perceivable p : this.perceivedBodies) {
				if (type.isInstance(p)) {
					perceps.add(type.cast(p));
				}
			}
		}
		if (EnvironmentalObject.class.isAssignableFrom(type)) {
			for (Perceivable p : this.perceivedObjects) {
				if (type.isInstance(p)) {
					perceps.add(type.cast(p));
				}
			}
		}
		if (this.pickingResults != null && PickedObject.class.isAssignableFrom(type)) {
			for (Perceivable p : this.pickingResults) {
				if (type.isInstance(p)) {
					perceps.add(type.cast(p));
				}
			}
		}
		return perceps;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized <T extends Perceivable> T getFirstPerception(Class<T> type) {
		assert (type != null);
		if (this.perceivedBodies == null || this.perceivedObjects == null) {
			throw new IllegalStateException();
		}
		if (PerceivedTurtle.class.isAssignableFrom(type)) {
			for (Perceivable p : this.perceivedBodies) {
				if (type.isInstance(p)) {
					return type.cast(p);
				}
			}
		}
		if (EnvironmentalObject.class.isAssignableFrom(type)) {
			for (Perceivable p : this.perceivedObjects) {
				if (type.isInstance(p)) {
					return type.cast(p);
				}
			}
		}
		if (this.pickingResults != null && PickedObject.class.isAssignableFrom(type)) {
			for (Perceivable p : this.pickingResults) {
				if (type.isInstance(p)) {
					return type.cast(p);
				}
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized Point2i getPosition() {
		return new Point2i(this.x, this.y);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized int getX() {
		return this.x;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized int getY() {
		return this.y;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized boolean hasPerceivedObject() {
		return this.perceivedObjects != null && !this.perceivedObjects.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized boolean hasPerceivedTurtle() {
		return this.perceivedBodies != null && !this.perceivedBodies.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasPerception() {
		return hasPerceivedObject() || hasPerceivedTurtle();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(RealTurtleBody o) {
		return this.turtle.compareTo(o.turtle);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TurtleFrustum getPerceptionFrustum() {
		return this.frustum;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getSemantic() {
		return this.semantic;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSemantic(Object semantic) {
		this.semantic = semantic;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UUID getTurtleId() {
		return this.turtle;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getSpeed() {
		return this.speed;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPerceptionEnable(boolean enable) {
		this.isPerceptionEnable = enable;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPerceptionEnable() {
		return this.isPerceptionEnable;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MotionInfluenceStatus getLastMotionInfluenceStatus() {
		return this.lastMotionInfluenceStatus == null ? MotionInfluenceStatus.NOT_AVAILABLE : this.lastMotionInfluenceStatus;
	}

}
