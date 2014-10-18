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

import java.util.Iterator;

import org.arakhne.afc.math.discrete.object2d.Point2i;

/** This class defines a frustum for for a turtle which is
 * restricted to a square.
 * This frustum is not orientable.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class SquareTurtleFrustum implements TurtleFrustum {

	private final int side;

	/**
	 * @param side is the length of the square side
	 */
	public SquareTurtleFrustum(int side) {
		this.side = side;
	}

	/** Replies the side of the square.
	 *
	 * @return the side of the square.
	 */
	public int getSideLength() {
		return this.side;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<Point2i> getPerceivedCells(Point2i origin, float direction, EnvironmentArea environment) {
		return new PointIterator(origin);
	}

	/** This class defines a frustum for for a turtle which is
	 * restricted to a square.
	 *
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	private class PointIterator implements Iterator<Point2i> {

		private final Point2i replied = new Point2i();
		private final int sx;
		private final int ex;
		private final int ey;
		private int x;
		private int y;

		/**
		 * @param center
		 */
		public PointIterator(Point2i center) {
			int s = getSideLength();
			int ds = s / 2;
			this.sx = center.x() - ds;
			this.x = this.sx;
			this.y = center.y() - ds;

			this.ex = center.x() + ds;
			this.ey = center.y() + ds;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			return this.x <= this.ex && this.y <= this.ey;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Point2i next() {
			this.replied.set(this.x, this.y);
			++this.x;
			if (this.x > this.ex) {
				++this.y;
				this.x = this.sx;
			}
			return this.replied;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void remove() {
			//
		}

	}

}
