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


/** This interface defines a controller for the Jaak simulation.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public interface JaakController {

	/** Start or re-start the simulation.
	 */
	void startSimulation();

	/** Make a pause in the simulation.
	 */
	void pauseSimulation();

	/** Stop the simulation and close the simulator.
	 */
	void stopSimulation();

	/** Replies the time out that must be considered for waiting for action's influences.
	 *
	 * @return the time out in milliseconds.
	 */
	int getSimulationStepTimeOut();

	/** Set the waiting during at each simulation step.
	 *
	 * @param duration - the time out in milliseconds at each simulation step.
	 */
	void setSimulationStepTimeOut(int duration);

	/** Replies the time during which the simulator is frozen at each simulation step.
	 *
	 * @return the duration out in milliseconds.
	 */
	int getWaitingDuration();

	/** Set the time during which the simulator is frozen at each simulation step.
	 *
	 * @param duration - the duration in milliseconds at each simulation step.
	 */
	void setWaitingDuration(int duration);

}
