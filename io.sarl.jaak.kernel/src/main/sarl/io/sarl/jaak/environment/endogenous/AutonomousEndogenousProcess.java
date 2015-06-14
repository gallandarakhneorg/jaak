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
package io.sarl.jaak.environment.endogenous;

import io.sarl.jaak.environment.influence.Influence;

/** This interface defines an endogenous environmental process.
 * <p>
 * An endogenous process is a process which do something
 * inside the environment. An autonomous endogenous process
 * does not provide a side effect in environment but
 * does not output any influence.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public interface AutonomousEndogenousProcess {

	/** Run the endogenous process.
	 *
	 * @param currentTime is the current simulation time
	 * @param simulationStepDuration is the duration of the current simulation step.
	 * @return the influence created by the autonomous process, or <code>null</code> if
	 * no external influence was generated.
	 */
	Influence runAutonomousEndogenousProcess(float currentTime, float simulationStepDuration);

}
