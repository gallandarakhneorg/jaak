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
package io.sarl.jaak.environment.frustum;

import io.sarl.jaak.environment.EnvironmentArea;

import java.util.Iterator;

import org.arakhne.afc.math.discrete.object2d.Point2i;

/** This class defines a frustum for for a turtle which is
 * restricted to a circle.
 * This frustum is not orientable.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class CircleTurtleFrustum implements TurtleFrustum {

	private final int radius;

	/**
	 * @param radius is the radius of the perception frustum.
	 */
	public CircleTurtleFrustum(int radius) {
		this.radius = radius;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<Point2i> getPerceivedCells(Point2i origin, float direction, EnvironmentArea environment) {
		return new PointIterator(origin);
	}

	/** Replies the perception radius.
	 *
	 * @return the perception radius.
	 */
	public int getRadius() {
		return this.radius;
	}

	/** This class defines a frustum for for a turtle which is
	 * restricted to a circle.
	 *
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	private class PointIterator implements Iterator<Point2i> {

		private final int cx;
		private final int cy;
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
			this.cx = center.x();
			this.cy = center.y();

			int r = getRadius();
			this.sx = this.cx - r;
			this.x = this.sx;
			this.y = this.cy - r;

			this.ex = this.cx + r;
			this.ey = this.cy + r;

			searchNext();
		}

		private void searchNext() {
			int sr;
			int dx;
			int dy;

			sr = getRadius();
			sr = sr * sr;

			while (this.x <= this.ex || this.y <= this.ey) {
				dx = Math.abs(this.x - this.cx);
				dy = Math.abs(this.y - this.cy);
				if ((dx * dx + dy * dy) <= sr) {
					return;
				}
				inc();
			}
		}

		private void inc() {
			++this.x;
			if (this.x > this.ex) {
				++this.y;
				this.x = this.sx;
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			return this.x <= this.ex || this.y <= this.ey;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Point2i next() {
			this.replied.set(this.x, this.y);
			inc();
			searchNext();
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
