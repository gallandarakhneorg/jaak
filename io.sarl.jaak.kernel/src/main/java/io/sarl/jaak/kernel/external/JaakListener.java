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
package io.sarl.jaak.kernel.external;

import java.util.EventListener;

/** This interface defines a listener on Jaak
 * events.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public interface JaakListener extends EventListener {

	/** Invoked when the simulated has started.
	 *
	 * @param event - the description of the event.
	 */
	void simulationStarted(JaakEvent event);

	/** Invoked when the state of the environment model
	 * has changed.
	 *
	 * @param event - the description of the event.
	 */
	void environmentStateChanged(JaakEvent event);

	/** Invoked when the simulated has stopped.
	 *
	 * @param event - the description of the event.
	 */
	void simulationStopped(JaakEvent event);

}
