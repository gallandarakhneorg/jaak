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
package io.sarl.jaak.kernel;

import io.sarl.jaak.environment.body.TurtleBody;
import io.sarl.jaak.environment.body.TurtleBodyFactory;
import io.sarl.jaak.environment.frustum.PointTurtleFrustum;
import io.sarl.jaak.environment.frustum.TurtleFrustum;
import io.sarl.jaak.environment.model.JaakEnvironment;
import io.sarl.jaak.environment.spawner.JaakBodyCreator;
import io.sarl.jaak.environment.time.TimeManager;

import java.lang.reflect.Constructor;
import java.util.UUID;

import org.arakhne.afc.math.discrete.object2d.Point2i;

/** Creator of bodies for the SARL agents.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
class AgentBodyCreator implements JaakBodyCreator {

	private TurtleCreated parameters;

	/**
	 */
	public AgentBodyCreator() {
		//
	}

	/** Check if the position is forced.
	 *
	 * @param environment - the environment.
	 * @return <code>true</code> if the position if forced.
	 */
	public boolean isPositionForced(JaakEnvironment environment) {
		Point2i p = new Point2i(this.parameters.x, this.parameters.y);
		return (environment.isFree(p.x(), p.y()));
	}

	/** Change the creation parameters.
	 *
	 * @param creationEvent - the event used for creating.
	 */
	public void set(TurtleCreated creationEvent) {
		this.parameters = creationEvent;
	}

	@Override
	public TurtleBody createBody(
			UUID turtleId,
			TurtleBodyFactory bodyFactory,
			TimeManager timeManager) {
		if (this.parameters != null) {
			TurtleFrustum f = createFrustum();
			if (f != null) {
				return bodyFactory.createTurtleBody(turtleId,
						new Point2i(this.parameters.x, this.parameters.y),
						f);
			}
		}
		return null;
	}

	private TurtleFrustum createFrustum() {
		if (this.parameters.frustumType != null
				&& this.parameters.frustumType.isEmpty()
				&& this.parameters.frustumLength > 0) {
			try {
				Class<?> f = Class.forName(this.parameters.frustumType);
				if (TurtleFrustum.class.isAssignableFrom(f)) {
					Constructor<?> c = f.getConstructor(int.class);
					return (TurtleFrustum) c.newInstance(this.parameters.frustumLength);
				}
			} catch (Throwable exception) {
				//
			}
		}
		return new PointTurtleFrustum();
	}

}
