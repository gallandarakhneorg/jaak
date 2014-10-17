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
package io.sarl.jaak.spawner;

import io.sarl.jaak.envinterface.body.BodySpawner;
import io.sarl.jaak.envinterface.body.TurtleBody;
import io.sarl.jaak.envinterface.body.TurtleBodyFactory;
import io.sarl.jaak.envinterface.frustum.TurtleFrustum;
import io.sarl.jaak.envinterface.time.TimeManager;

import java.util.UUID;

import org.arakhne.afc.math.discrete.object2d.Point2i;

/** Provide implementation for a turtle spawner in Jaak environment.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public abstract class JaakSpawner implements BodySpawner {

	/**
	 * Is the number of retries to compute the position while
	 * the cell at the given position is not free.
	 */
	public static final int FREE_POSITION_COMPUTATION_RETRIES = 10;

	/**
	 */
	public JaakSpawner() {
		//
	}

	/** Spawn only a turtle body in environment and bind it with the
	 * turtle with the given identifier.
	 * <p>
	 * It is assumed that the turtle was already launched, but never
	 * binded with Jaak environment.
	 *
	 * @param turtleId is the identifier of the agent for which a body should be spawn.
	 * @param kernelAddress is the address of the kernel on which the spawned agent may be run.
	 * @param bodyFactory is the body factory to use.
	 * @param timeManager is the time manager used by the Jaak simulation.
	 * @param creator - reference to the agent body creator.
	 * @return <code>true</code> on success.
	 */
	public final boolean spawnBodyFor(
			UUID turtleId,
			UUID kernelAddress,
			TurtleBodyFactory bodyFactory,
			TimeManager timeManager,
			JaakBodyCreator creator) {
		assert (turtleId != null);
		assert (kernelAddress != null);
		assert (bodyFactory != null);

		if (creator != null || isSpawnable(timeManager)) {
			TurtleBodyFactory wrappedFactory = new SpawnedBodyFactory(
					bodyFactory,
					computeSpawnedTurtleOrientation(timeManager));

			TurtleBody body;
			if (creator != null) {
				body = creator.createBody(turtleId, wrappedFactory, timeManager);
			} else {
				body = wrappedFactory.createTurtleBody(turtleId);
			}
			if (body != null) {
				turtleSpawned(turtleId, body, timeManager);
				return true;
			}
		}
		return false;
	}

	/** Replies if a turtle is spawnable according to the spawning law and
	 * the current time mananager.
	 *
	 * @param timeManager is the time manager used by the Jaak simulation.
	 * @return <code>true</code> if a turtle is spawnable, otherwise <code>false</code>.
	 */
	protected abstract boolean isSpawnable(TimeManager timeManager);

	/** Replies the orientation for a newly spawned turtle.
	 *
	 * @param timeManager is the time manager used by the Jaak simulation.
	 * @return the orientation angle for a newly spawned turtle.
	 */
	protected abstract float computeSpawnedTurtleOrientation(TimeManager timeManager);

	/** Invoked when a turtle was successfully spawned outside the spawner and
	 * a body was created by this spawner.
	 *
	 * @param turtle is the spawned turtle.
	 * @param body is the spawned turtle body.
	 * @param timeManager is the time manager used by the Jaak simulation.
	 */
	protected abstract void turtleSpawned(UUID turtle, TurtleBody body, TimeManager timeManager);

	/** Replies the position where to spawn a turtle.
	 * The replied position could be different from the reference
	 * position replied by {@link #getReferenceSpawningPosition()}.
	 *
	 * @param desiredPosition is the position desired by the factory invoker.
	 * @return a position.
	 */
	protected abstract Point2i computeCurrentSpawningPosition(Point2i desiredPosition);

	/** Provide implementation for a body factory dedicated to spawners.
	 *
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	private class SpawnedBodyFactory implements TurtleBodyFactory {

		private final TurtleBodyFactory factory;
		private final float orientation;

		/**
		 * @param factory is the turtle body factory to wrap.
		 * @param orientation is the turtle body.
		 */
		public SpawnedBodyFactory(TurtleBodyFactory factory, float orientation) {
			this.factory = factory;
			this.orientation = orientation;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean isFreeCell(Point2i position) {
			return this.factory.isFreeCell(position);
		}

		/** Compute a free position.
		 *
		 * @param desiredPosition is the desired position given by the factory invoker.
		 * @return a free position
		 */
		public Point2i computeValidPosition(Point2i desiredPosition) {
			Point2i dp = desiredPosition;
			Point2i p;
			for (int i = 0; i < FREE_POSITION_COMPUTATION_RETRIES; ++i) {
				p = computeCurrentSpawningPosition(dp);
				assert (p != null);
				if (isFreeCell(p)) {
					return p;
				}
				dp = null;
			}
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TurtleBody createTurtleBody(UUID turtleId,
				Point2i desiredPosition, float desiredAngle, Object semantic) {
			Point2i p = computeValidPosition(desiredPosition);
			if (p == null) {
				return null;
			}
			return this.factory.createTurtleBody(
					turtleId,
					p,
					desiredAngle,
					semantic);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TurtleBody createTurtleBody(UUID turtleId,
				Point2i desiredPosition, float desiredAngle) {
			Point2i p = computeValidPosition(desiredPosition);
			if (p == null) {
				return null;
			}
			return this.factory.createTurtleBody(
					turtleId,
					p,
					desiredAngle);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TurtleBody createTurtleBody(UUID turtleId,
				Point2i desiredPosition) {
			Point2i p = computeValidPosition(desiredPosition);
			if (p == null) {
				return null;
			}
			return this.factory.createTurtleBody(
					turtleId,
					p,
					this.orientation);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TurtleBody createTurtleBody(UUID turtleId) {
			Point2i p = computeValidPosition(null);
			if (p == null) {
				return null;
			}
			return this.factory.createTurtleBody(
					turtleId,
					p,
					this.orientation);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TurtleBody createTurtleBody(UUID turtleId,
				float desiredAngle, Object semantic) {
			Point2i p = computeValidPosition(null);
			if (p == null) {
				return null;
			}
			return this.factory.createTurtleBody(
					turtleId,
					p,
					desiredAngle,
					semantic);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TurtleBody createTurtleBody(UUID turtleId,
				Object semantic) {
			Point2i p = computeValidPosition(null);
			if (p == null) {
				return null;
			}
			return this.factory.createTurtleBody(
					turtleId,
					p,
					this.orientation,
					semantic);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TurtleBody createTurtleBody(UUID turtleId,
				Point2i desiredPosition, Object semantic) {
			Point2i p = computeValidPosition(null);
			if (p == null) {
				return null;
			}
			return this.factory.createTurtleBody(
					turtleId,
					p,
					this.orientation,
					semantic);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TurtleBody createTurtleBody(UUID turtleId,
				Point2i desiredPosition, float desiredAngle, Object semantic,
				TurtleFrustum frustum) {
			Point2i p = computeValidPosition(desiredPosition);
			if (p == null) {
				return null;
			}
			return this.factory.createTurtleBody(
					turtleId,
					p,
					desiredAngle,
					semantic,
					frustum);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TurtleBody createTurtleBody(UUID turtleId,
				Point2i desiredPosition, float desiredAngle,
				TurtleFrustum frustum) {
			Point2i p = computeValidPosition(desiredPosition);
			if (p == null) {
				return null;
			}
			return this.factory.createTurtleBody(
					turtleId,
					p,
					desiredAngle,
					frustum);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TurtleBody createTurtleBody(UUID turtleId,
				Point2i desiredPosition, TurtleFrustum frustum) {
			Point2i p = computeValidPosition(desiredPosition);
			if (p == null) {
				return null;
			}
			return this.factory.createTurtleBody(
					turtleId,
					p,
					this.orientation,
					frustum);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TurtleBody createTurtleBody(UUID turtleId, TurtleFrustum frustum) {
			Point2i p = computeValidPosition(null);
			if (p == null) {
				return null;
			}
			return this.factory.createTurtleBody(
					turtleId,
					p,
					this.orientation,
					frustum);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TurtleBody createTurtleBody(UUID turtleId,
				float desiredAngle, Object semantic, TurtleFrustum frustum) {
			Point2i p = computeValidPosition(null);
			if (p == null) {
				return null;
			}
			return this.factory.createTurtleBody(
					turtleId,
					p,
					desiredAngle,
					semantic,
					frustum);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TurtleBody createTurtleBody(UUID turtleId,
				Object semantic, TurtleFrustum frustum) {
			Point2i p = computeValidPosition(null);
			if (p == null) {
				return null;
			}
			return this.factory.createTurtleBody(
					turtleId,
					p,
					this.orientation,
					semantic,
					frustum);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TurtleBody createTurtleBody(UUID turtleId,
				Point2i desiredPosition, Object semantic, TurtleFrustum frustum) {
			Point2i p = computeValidPosition(desiredPosition);
			if (p == null) {
				return null;
			}
			return this.factory.createTurtleBody(
					turtleId,
					p,
					this.orientation,
					semantic,
					frustum);
		}

	}

}
