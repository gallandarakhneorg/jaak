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

import io.sarl.jaak.util.RandomNumber;

import org.arakhne.afc.math.discrete.object2d.Point2i;
import org.arakhne.afc.math.discrete.object2d.Rectangle2i;
import org.arakhne.afc.math.discrete.object2d.Shape2i;

/** Provide implementation for a turtle spawner on a rectangle.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public abstract class JaakAreaSpawner extends JaakSpawner {
	
	private final int x;
	private final int y;
	private final int w;
	private final int h;
	
	/**
	 * @param x is the position of the spawner.
	 * @param y is the position of the spawner.
	 * @param width is the width of the spawner.
	 * @param height is the width of the spawner.
	 */
	public JaakAreaSpawner(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.w = width;
		this.h = height;
	}
	
	/** {@inheritDoc}
	 */
	@Override
	public Point2i computeCurrentSpawningPosition(Point2i desiredPosition) {
		if (desiredPosition!=null
		 && desiredPosition.x()>=this.x
		 && desiredPosition.y()>=this.y
		 && desiredPosition.x()<=this.x+this.w
		 && desiredPosition.y()<=this.y+this.h) {
			return new Point2i(desiredPosition);
		}
		int dx = RandomNumber.nextInt(this.w);
		int dy = RandomNumber.nextInt(this.h);
		return new Point2i(this.x+dx, this.y+dy);
	}
		
	/** {@inheritDoc}
	 */
	@Override
	public Point2i getReferenceSpawningPosition() {
		return new Point2i(this.x, this.y);
	}
	
	/** {@inheritDoc}
	 */
	@Override
	public Shape2i toShape() {
		return new Rectangle2i(this.x, this.y, this.w, this.h);
	}

}