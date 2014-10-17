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

import io.sarl.jaak.envinterface.EnvironmentArea;
import io.sarl.jaak.envinterface.body.TurtleBody;
import io.sarl.jaak.envinterface.body.TurtleBodyFactory;
import io.sarl.jaak.envinterface.frustum.SquareTurtleFrustum;
import io.sarl.jaak.envinterface.frustum.TurtleFrustum;
import io.sarl.jaak.envinterface.influence.Influence;
import io.sarl.jaak.envinterface.perception.EnvironmentalObject;
import io.sarl.jaak.envinterface.perception.PerceivedTurtle;
import io.sarl.jaak.envinterface.perception.StandardObjectManipulator;
import io.sarl.jaak.envinterface.time.TimeManager;
import io.sarl.jaak.environment.ValidationResult;
import io.sarl.jaak.environment.endogenousengine.EnvironmentEndogenousEngine;
import io.sarl.jaak.environment.solver.ActionApplier;
import io.sarl.jaak.environment.solver.InfluenceSolver;
import io.sarl.jaak.environment.solver.PathBasedInfluenceSolver;
import io.sarl.jaak.util.MultiCollection;
import io.sarl.jaak.util.RandomNumber;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.arakhne.afc.math.continous.object2d.Vector2f;
import org.arakhne.afc.math.discrete.object2d.Point2i;

/** This class defines the Jaak environment model.
 * <p>
 * The environment is a grid composed of cells.
 * Each cell is able to contain zero or one turtle
 * body and many environmental objects.
 * <p>
 * The environment may be wrapped or not. When the
 * environment is wrapped and a turtle is trying
 * to go outside the environment grid, it is moved
 * on the opposite side of the grid.
 * If the envrionment is not wrapped, when a turtle
 * is trying to move outside the grid, it is moved
 * until it reach the border of the grid.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class JaakEnvironment implements EnvironmentArea {

	/** Defines the default perception distance for turtles.
	 */
	public static final int DEFAULT_PERCEPTION_DISTANCE = 7;

	private final UUID id = UUID.randomUUID();
	private final Map<UUID, RealTurtleBody> bodies = new TreeMap<>();
	private final JaakGrid grid;
	private TimeManager timeManager;
	private final AtomicBoolean isWrapped = new AtomicBoolean(false);
	private volatile EnvironmentEndogenousEngine endogenousEngine;
	private volatile Collection<Influence> endogenousInfluences;
	private volatile InfluenceSolver<RealTurtleBody> solver;
	private float lastSimulationTime = Float.NaN;

	private final LinkedList<JaakEnvironmentListener> listeners = new LinkedList<>();

	private final RealTurtleBodyFactory factory = new RealTurtleBodyFactory();

	/**
	 * @param width is the width of the world grid.
	 * @param height is the height of the world grid.
	 * @param timeManager is the time manager used to run Jaak.
	 */
	public JaakEnvironment(int width, int height, TimeManager timeManager) {
		this.grid = new JaakGrid(width, height, new StandardObjectManipulator());
		this.timeManager = timeManager;
	}

	/**
	 * @param width is the width of the world grid.
	 * @param height is the height of the world grid.
	 */
	public JaakEnvironment(int width, int height) {
		this(width, height, null);
	}

	/** Add listener on environment events.
	 *
	 * @param listener - the listener.
	 */
	public void addJaakEnvironmentListener(JaakEnvironmentListener listener) {
		synchronized (this.listeners) {
			this.listeners.add(listener);
		}
	}

	/** Remove listener on environment events.
	 *
	 * @param listener - the listener.
	 */
	public void removeJaakEnvironmentListener(JaakEnvironmentListener listener) {
		synchronized (this.listeners) {
			this.listeners.remove(listener);
		}
	}

	/** Fire pre agent scheduling event.
	 */
	protected void firePreAgentScheduling() {
		JaakEnvironmentListener[] list;
		synchronized (this.listeners) {
			list = new JaakEnvironmentListener[this.listeners.size()];
			this.listeners.toArray(list);
		}
		for (JaakEnvironmentListener listener : list) {
			listener.preAgentScheduling();
		}
	}

	/** Fire post agent scheudling event.
	 */
	protected void firePostAgentScheduling() {
		JaakEnvironmentListener[] list;
		synchronized (this.listeners) {
			list = new JaakEnvironmentListener[this.listeners.size()];
			this.listeners.toArray(list);
		}
		for (JaakEnvironmentListener listener : list) {
			listener.postAgentScheduling();
		}
	}

	/** {@inheritDoc}
	 */
	@Override
	public int getX() {
		return 0;
	}

	/** {@inheritDoc}
	 */
	@Override
	public int getY() {
		return 0;
	}

	/** {@inheritDoc}
	 */
	@Override
	public int getWidth() {
		return this.grid.getWidth();
	}

	/** {@inheritDoc}
	 */
	@Override
	public int getHeight() {
		return this.grid.getHeight();
	}

	/** Set the time manager for the environment.
	 *
	 * @param timeManager is the time manager which must be used by the environment.
	 */
	public void setTimeManager(TimeManager timeManager) {
		assert (timeManager != null);
		this.timeManager = timeManager;
	}

	/** Replies the action applier for this environment.
	 *
	 * @return the action applier for this environment.
	 */
	public ActionApplier getActionApplier() {
		return this.grid;
	}

	/** Replies the unique identifier of this environment object.
	 *
	 * @return the identifier of the environment.
	 */
	public UUID getIdentifier() {
		return this.id;
	}

	/** Replies a factory for the bodies which is connected to this environment.
	 * <p>
	 * Caution: this factory create bodies, and adds them inside the environment.
	 *
	 * @return a body factory.
	 */
	public TurtleBodyFactory getTurtleBodyFactory() {
		return this.factory;
	}

	/** Replies if the environment is wrapped.
	 *
	 * @return <code>true</code> if the environment is
	 * wrapped, otherwise <code>false</code>.
	 */
	public boolean isWrapped() {
		return this.isWrapped.get();
	}

	/** Change the wrapping flag of the environment.
	 *
	 * @param wrapped indicates if the environment is
	 * wrapped or not.
	 */
	public void setWrapped(boolean wrapped) {
		this.isWrapped.set(wrapped);
	}

	@Override
	public boolean isFree(int x, int y) {
		return (this.grid.isFree(x, y));
	}

	@Override
	public boolean hasObstacle(int x, int y) {
		return (this.grid.hasObstacle(x, y));
	}

	@Override
	public synchronized boolean hasTurtle(int x, int y) {
		return this.grid.getTurtle(x, y) != null;
	}

	@Override
	public synchronized float getTurtleSpeed(int x, int y) {
		TurtleBody body = this.grid.getTurtle(x, y);
		if (body != null) {
			return body.getSpeed();
		}
		return Float.NaN;
	}

	/** Replies the turtles at the given position.
	 *
	 * @param x is the coordinate of the cell.
	 * @param y is the coordinate of the cell.
	 * @return the turtles even if they are in a burrow.
	 */
	public synchronized Collection<TurtleBody> getTurtles(int x, int y) {
		return this.grid.getTurtles(x, y);
	}

	/** Replies the instant orientation of the turtle at the given position.
	 *
	 * @param x is the coordinate of the cell.
	 * @param y is the coordinate of the cell.
	 * @return the instant orientation of the turtle in radians,
	 * or {@link Float#NaN} if no turtle.
	 */
	public synchronized float getTurtleOrientation(int x, int y) {
		TurtleBody body = this.grid.getTurtle(x, y);
		if (body != null) {
			return body.getHeadingAngle();
		}
		return Float.NaN;
	}

	/** Replies the instant direction of the turtle at the given position.
	 *
	 * @param x is the coordinate of the cell.
	 * @param y is the coordinate of the cell.
	 * @return the instant direction of the turtle in radians,
	 * or <code>null</code> if no turtle.
	 */
	public synchronized Vector2f getTurtleDirection(int x, int y) {
		TurtleBody body = this.grid.getTurtle(x, y);
		if (body != null) {
			return body.getHeadingVector();
		}
		return null;
	}

	@Override
	public synchronized int getTurtleCount() {
		return this.bodies.size();
	}

	/** Replies the objects in the cell at the given position.
	 *
	 * @param <T> is the type of the desired objects.
	 * @param x is the position of the cell.
	 * @param y is the position of the cell.
	 * @param type is the type of the desired objects.
	 * @return the environment objects in the given cell.
	 */
	public synchronized <T extends EnvironmentalObject> Iterable<T> getEnvironmentalObjects(int x, int y, Class<T> type) {
		return new FilteringIterable<>(type, this.grid.getObjects(x, y));
	}

	@Override
	public synchronized Collection<EnvironmentalObject> getEnvironmentalObjects(int x, int y) {
		return this.grid.getObjects(x, y);
	}

	/** Set the endogenous engine to use.
	 *
	 * @param engine is the endogenous engine to use.
	 */
	public void setEndogenousEngine(EnvironmentEndogenousEngine engine) {
		this.endogenousEngine = engine;
	}

	/** Set the solver of influence conflicts.
	 *
	 * @param solver is the solver of influence conflicts to use.
	 */
	public void setInfluenceSolver(InfluenceSolver<RealTurtleBody> solver) {
		this.solver = solver;
	}

	/** Replies a free cell near a random position.
	 *
	 * @return the position of the first free cell, or <code>null</code>
	 * if no more cell is free.
	 */
	Point2i getFreeRandomPosition() {
		Point2i p = new Point2i();
		int i = 0;
		int max = this.grid.getWidth() * this.grid.getHeight();
		do {
			p.set(
					RandomNumber.nextInt(this.grid.getWidth()),
					RandomNumber.nextInt(this.grid.getHeight()));
			++i;
		}
		while (!isFree(p.x(), p.y()) && i < max);
		return (isFree(p.x(), p.y())) ? p : null;
	}

	/** Add a body in the environment.
	 *
	 * @param body is the body to add.
	 * @param position is the position of the body.
	 * @return <code>true</code> if the body was successfully added,
	 * <code>false</code> otherwise.
	 */
	synchronized boolean addBody(RealTurtleBody body, Point2i position) {
		assert (body != null);
		assert (position != null);
		if (!this.bodies.containsKey(body.getTurtleId())) {
			if (this.grid.putTurtle(position.x(), position.y(), body)) {
				this.bodies.put(body.getTurtleId(), body);
				body.setPhysicalState(
						position.x(), position.y(),
						body.getHeadingAngle(),
						0f);
				return true;
			}
		}
		return false;
	}

	/** Remove a body from the environment.
	 *
	 * @param turtle is the identifier of the turtle for which the body must be removed.
	 * @return the success state of the removal action.
	 */
	public synchronized boolean removeBodyFor(UUID turtle) {
		if (turtle != null) {
			RealTurtleBody body = this.bodies.remove(turtle);
			if (body != null) {
				Point2i position = body.getPosition();
				this.grid.removeTurtle(position.x(), position.y(), body);
				return true;
			}
		}
		return false;
	}

	/** Replies if the given address is associated to a body in the environment.
	 *
	 * @param turtle is the identifier of the turtle.
	 * @return <code>true</code> if the turtle has a body, otherwise <code>false</code>.
	 */
	public synchronized boolean hasBodyFor(UUID turtle) {
		if (turtle != null) {
			return this.bodies.containsKey(turtle);
		}
		return false;
	}


	/** Replies the body for the given address.
	 *
	 * @param turtle is the identifier of the turtle.
	 * @return the turtle body or <code>null</code>.
	 */
	public synchronized TurtleBody getBodyFor(UUID turtle) {
		if (turtle != null) {
			return this.bodies.get(turtle);
		}
		return null;
	}

	/** Apply the given function on all the bodies.
	 *
	 * @param function - the function to apply.
	 */
	public synchronized void apply(Lambda<TurtleBody> function) {
		for (TurtleBody body : this.bodies.values()) {
			function.apply(body);
		}
	}

	/** Run the environment behaviour before any turtle execution.
	 */
	public synchronized void runPreTurtles() {
		computePerceptions();
		firePreAgentScheduling();
	}

	/** Run the environment behaviour after all turtle executions.
	 */
	public synchronized void runPostTurtles() {
		runEndogenousEngine();
		solveConflicts();
		firePostAgentScheduling();
	}

	private void runEndogenousEngine() {
		// Run autonomous environmental processes
		float currentTime = this.timeManager.getCurrentTime(TimeUnit.SECONDS);
		float simulationStepDuration;
		if (Float.isNaN(this.lastSimulationTime)) {
			simulationStepDuration = 0f;
		} else {
			simulationStepDuration = currentTime - this.lastSimulationTime;
		}

		MultiCollection<Influence> endoInfluences = new MultiCollection<>();

		this.lastSimulationTime = currentTime;
		Collection<Influence> col = this.grid.runAutonomousProcesses(currentTime, simulationStepDuration);
		if (col != null && !col.isEmpty()) {
			endoInfluences.addCollection(col);
		}

		// Run endogenous engine
		EnvironmentEndogenousEngine engine = this.endogenousEngine;
		if (engine != null) {
			col = engine.computeInfluences(this.grid, this.timeManager);
			if (col != null && !col.isEmpty()) {
				endoInfluences.addCollection(col);
			}
		}

		if (!endoInfluences.isEmpty()) {
			this.endogenousInfluences = endoInfluences;
		}
	}

	private void computePerceptions() {
		Point2i position;
		TurtleFrustum frustum;
		TurtleBody turtleBody;
		Iterator<Point2i> iterator;
		List<PerceivedTurtle> bodies;
		MultiCollection<EnvironmentalObject> objects;
		int x;
		int y;
		for (RealTurtleBody body : this.bodies.values()) {
			bodies = new ArrayList<>();
			objects = new MultiCollection<>();
			if (body.isPerceptionEnable()) {
				frustum = body.getPerceptionFrustum();
				if (frustum != null) {
					iterator = frustum.getPerceivedCells(body.getPosition(), body.getHeadingAngle(), this);
					if (iterator != null) {
						while (iterator.hasNext()) {
							position = iterator.next();
							if (this.grid.validatePosition(isWrapped(), true, position) != ValidationResult.DISCARDED) {
								x = position.x();
								y = position.y();
								turtleBody = this.grid.getTurtle(x, y);
								if (turtleBody != null && turtleBody != body) {
									bodies.add(new PerceivedTurtle(
											turtleBody.getTurtleId(),
											new Point2i(position),
											turtleBody.getPosition(),
											turtleBody.getSpeed(),
											turtleBody.getHeadingAngle(),
											turtleBody.getSemantic()));
								}
								objects.addCollection(this.grid.getObjects(x, y));
							}
						}
					}
				}
			}
			body.setPerceptions(bodies, objects);
		}
	}

	private void solveConflicts() {
		InfluenceSolver<RealTurtleBody> theSolver = this.solver;
		if (theSolver == null) {
			this.solver = new PathBasedInfluenceSolver();
			theSolver = this.solver;
			theSolver.setGridModel(this.grid);
			theSolver.setTimeManager(this.timeManager);
		}
		theSolver.setWrapped(isWrapped());
		theSolver.solve(this.endogenousInfluences, this.bodies.values(), getActionApplier());
	}

	/** This class defines an iterable object which is able to filter
	 * its content.
	 *
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	private static class FilteringIterable<T> implements Iterable<T> {

		private final Class<T> type;
		private final Collection<?> collection;

		/**
		 * @param type is the type of the elements to reply
		 * @param collection is the collection to iterate on.
		 */
		public FilteringIterable(Class<T> type, Collection<?> collection) {
			this.type = type;
			this.collection = collection;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Iterator<T> iterator() {
			return new FilteringIterator<>(this.type, this.collection.iterator());
		}

	}

	/** This class defines an iterable object which is able to filter
	 * its content.
	 *
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	private static class FilteringIterator<T> implements Iterator<T> {

		private final Class<T> type;
		private final Iterator<?> iterator;
		private T next;

		/**
		 * @param type is the type of the elements to reply
		 * @param iterator is the iterator to use.
		 */
		public FilteringIterator(Class<T> type, Iterator<?> iterator) {
			this.type = type;
			this.iterator = iterator;
			searchNext();
		}

		private void searchNext() {
			this.next = null;
			Object o;
			while (this.iterator.hasNext()) {
				o = this.iterator.next();
				if (o != null && this.type.isInstance(o)) {
					this.next = this.type.cast(o);
					return;
				}
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			return this.next != null;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public T next() {
			T n = this.next;
			searchNext();
			return n;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void remove() {
			//
		}

	}

	/** This class defines an implementation of turtle body factory.
	 *
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	private class RealTurtleBodyFactory implements TurtleBodyFactory {

		/**
		 */
		public RealTurtleBodyFactory() {
			//
		}

		private TurtleFrustum getDefaultFrustum() {
			return new SquareTurtleFrustum(DEFAULT_PERCEPTION_DISTANCE);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean isFreeCell(Point2i position) {
			return isFree(position.x(), position.y());
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TurtleBody createTurtleBody(
				UUID turtleId,
				Point2i desiredPosition,
				float desiredAngle,
				Object semantic) {
			return createTurtleBody(
					turtleId, desiredPosition,
					desiredAngle, semantic,
					getDefaultFrustum());
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TurtleBody createTurtleBody(
				UUID turtleId,
				Point2i desiredPosition,
				float desiredAngle) {
			return createTurtleBody(
					turtleId, desiredPosition,
					desiredAngle,
					getDefaultFrustum());
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TurtleBody createTurtleBody(
				UUID turtleId,
				Point2i desiredPosition) {
			return createTurtleBody(
					turtleId, desiredPosition,
					getDefaultFrustum());
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TurtleBody createTurtleBody(UUID turtleId) {
			return createTurtleBody(
					turtleId,
					getDefaultFrustum());
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TurtleBody createTurtleBody(
				UUID turtleId,
				float desiredAngle,
				Object semantic) {
			return createTurtleBody(
					turtleId,
					desiredAngle, semantic,
					getDefaultFrustum());
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TurtleBody createTurtleBody(
				UUID turtleId,
				Object semantic) {
			return createTurtleBody(
					turtleId, semantic,
					getDefaultFrustum());
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TurtleBody createTurtleBody(
				UUID turtleId,
				Point2i desiredPosition,
				Object semantic) {
			return createTurtleBody(
					turtleId, desiredPosition,
					semantic,
					getDefaultFrustum());
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TurtleBody createTurtleBody(UUID turtleId,
				Point2i desiredPosition, float desiredAngle, Object semantic,
				TurtleFrustum frustum) {
			RealTurtleBody body = new RealTurtleBody(turtleId, frustum, desiredAngle, semantic);
			Point2i position = null;

			if (desiredPosition == null) {
				position = getFreeRandomPosition();
			} else if (isFree(desiredPosition.x(), desiredPosition.y())) {
				position = desiredPosition;
			}

			if (position == null) {
				return null;
			}

			if (JaakEnvironment.this.addBody(body, position)) {
				return body;
			}
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TurtleBody createTurtleBody(UUID turtleId,
				Point2i desiredPosition, float desiredAngle,
				TurtleFrustum frustum) {
			return createTurtleBody(turtleId,
					desiredPosition, desiredAngle,
					null, frustum);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TurtleBody createTurtleBody(UUID turtleId,
				Point2i desiredPosition, TurtleFrustum frustum) {
			return createTurtleBody(turtleId,
					desiredPosition, Float.NaN,
					null, frustum);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TurtleBody createTurtleBody(UUID turtleId,
				TurtleFrustum frustum) {
			return createTurtleBody(turtleId,
					null, Float.NaN,
					null, frustum);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TurtleBody createTurtleBody(UUID turtleId,
				float desiredAngle, Object semantic, TurtleFrustum frustum) {
			return createTurtleBody(turtleId,
					null, desiredAngle,
					semantic, frustum);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TurtleBody createTurtleBody(UUID turtleId,
				Object semantic, TurtleFrustum frustum) {
			return createTurtleBody(turtleId,
					null, Float.NaN,
					semantic, frustum);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TurtleBody createTurtleBody(UUID turtleId,
				Point2i desiredPosition, Object semantic, TurtleFrustum frustum) {
			return createTurtleBody(turtleId,
					desiredPosition, Float.NaN,
					semantic, frustum);
		}

	} /* class RealTurtleBodyFactory */

	/** Definition of a function.
	 *
	 * @param <T> - the type of the lambda's parameter.
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	public interface Lambda<T> {

		/** Apply the lambda.
		 *
		 * @param object - the object on whic hthe lambda must be applied.
		 */
		void apply(T object);

	} /* interface Lambda */

}
