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
package io.sarl.jaak.demos.mdtraffic.environment.physic;

import io.sarl.jaak.environment.body.TurtleObject;
import io.sarl.jaak.environment.influence.Influence;

import org.arakhne.afc.math.discrete.object2d.Point2i;

/** Influence for changing the traffic light state.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class CrashInfluence extends Influence {

	private final Point2i position;
	
	/**
	 * @param position - the position of the crash.
	 */
	CrashInfluence(Point2i position) {
		super((TurtleObject) null);
		this.position = position.clone();
	}
	
	/** The position of the crash.
	 * 
	 * @return the crash position.
	 */
	public Point2i getPosition() {
		return this.position.clone();
	}

}
