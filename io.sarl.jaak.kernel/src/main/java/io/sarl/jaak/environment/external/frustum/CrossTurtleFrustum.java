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
package io.sarl.jaak.environment.external.frustum;

import io.sarl.jaak.environment.external.EnvironmentArea;

import java.util.BitSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.arakhne.afc.math.discrete.object2d.Point2i;

/** This class defines a frustum for for a turtle which is
 * a cross in the horizontal and vertical directions.
 * This frustum is not orientable.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class CrossTurtleFrustum implements TurtleFrustum {

	private static final int SIDES = 4;

	private final int crossLength;

	/**
	 * @param crossLength is the length of the cross branches
	 */
	public CrossTurtleFrustum(int crossLength) {
		this.crossLength = Math.max(1, crossLength);
	}

	/** Replies the length of each cross branch.
	 *
	 * @return the length of each cross branch.
	 */
	public int getCrossBranchLength() {
		return this.crossLength;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<Point2i> getPerceivedCells(Point2i origin, float direction, EnvironmentArea environment) {
		return new CrossIterator(origin, this.crossLength, environment);
	}

	/** This class defines a frustum for for a turtle which is
	 * a cross in the horizontal and vertical directions.
	 * This frustum is not orientable.
	 *
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	private static class CrossIterator implements Iterator<Point2i> {

		private final EnvironmentArea environment;
		private final Point2i origin;
		private final int length;
		private final BitSet directions = new BitSet(4);
		private int level = 1;
		private int nextDirectionIndex;
		private Point2i next;

		/**
		 * @param origin
		 * @param length
		 * @param environment
		 */
		public CrossIterator(Point2i origin, int length, EnvironmentArea environment) {
			this.origin = origin;
			this.environment = environment;
			this.length = length;
			this.directions.set(0, SIDES);
			this.nextDirectionIndex = 0;
			this.next = new Point2i(this.origin);
		}

		private void searchNext() {
			this.next = null;
			Point2i p = new Point2i();
			while (this.next == null
					&& !this.directions.isEmpty()
					&& this.level <= this.length) {

				while (this.next == null
						&& !this.directions.isEmpty()
						&& this.level <= this.length
						&& this.nextDirectionIndex < SIDES) {
					if (this.directions.get(this.nextDirectionIndex)) {
						switch(this.nextDirectionIndex) {
						case 0:
							p.set(this.origin.x() + this.level, this.origin.y());
							break;
						case 1:
							p.set(this.origin.x(), this.origin.y() + this.level);
							break;
						case 2:
							p.set(this.origin.x() - this.level, this.origin.y());
							break;
						case 3:
							p.set(this.origin.x(), this.origin.y() - this.level);
							break;
						default:
							throw new IllegalStateException();
						}

						if (this.environment.hasObstacle(p.x(), p.y())) {
							this.directions.set(this.nextDirectionIndex, false);
						}

						this.next = p;
					}
					++this.nextDirectionIndex;
				}

				if (this.nextDirectionIndex > 3) {
					++this.level;
					this.nextDirectionIndex = 0;
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
		public Point2i next() {
			Point2i n = this.next;
			if (n == null) {
				throw new NoSuchElementException();
			}
			searchNext();
			return n;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

}
