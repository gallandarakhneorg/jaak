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

import io.sarl.jaak.environment.body.TurtleBody;
import io.sarl.jaak.environment.influence.MotionInfluenceStatus;
import io.sarl.jaak.environment.perception.PickedObject;
import io.sarl.jaak.environment.solver.InfluenceSolver;

/** Abstract implementation of an influence solver which is able to access
 * to the internal data structures of the Jaak environment.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public abstract class AbstractJaakEnvironmentInfluenceSolver extends InfluenceSolver<RealTurtleBody> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void putBackPickingAction(TurtleBody body, PickedObject action) {
		if (body instanceof RealTurtleBody) {
			RealTurtleBody b = (RealTurtleBody) body;
			b.putBackPickingAction(action);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void putBackMotionInfluenceStatus(TurtleBody body, MotionInfluenceStatus status) {
		if (body instanceof RealTurtleBody) {
			RealTurtleBody b = (RealTurtleBody) body;
			b.putBackMotionInfluenceStatus(status);
		}
	}

}
