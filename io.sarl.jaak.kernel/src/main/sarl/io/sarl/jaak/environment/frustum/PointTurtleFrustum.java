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

import java.util.Collections;
import java.util.Iterator;

import org.arakhne.afc.math.discrete.object2d.Point2i;

/** This class defines a frustum for for a turtle which is
 * restricted to a point.
 * This frustum is not orientable.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class PointTurtleFrustum implements TurtleFrustum {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<Point2i> getPerceivedCells(Point2i origin, float direction, EnvironmentArea environment) {
		return Collections.singleton(origin).iterator();
	}

}
