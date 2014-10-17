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

import io.sarl.jaak.envinterface.body.TurtleBody;
import io.sarl.jaak.envinterface.body.TurtleBodyFactory;
import io.sarl.jaak.envinterface.time.TimeManager;

import java.util.UUID;

/** An object that want to create a body.
 * This interface permits to create objects
 * that will create the bodies according to
 * their own preferences.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public interface JaakBodyCreator {

	/** Create a body.
	 *
	 * @param turtleId is the identifier of the agent for which a body should be spawn.
	 * @param bodyFactory is the body factory to use.
	 * @param timeManager is the time manager used by the Jaak simulation.
	 * @return the body or <code>null</code>
	 */
	TurtleBody createBody(UUID turtleId,
			TurtleBodyFactory bodyFactory,
			TimeManager timeManager);

}
