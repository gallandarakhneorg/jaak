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

import io.sarl.jaak.environment.external.Perception;
import io.sarl.jaak.environment.external.influence.Influence;
import io.sarl.lang.core.EventListener;
import io.sarl.lang.core.Space;

import java.util.UUID;

/** Space that is representing the Jaak environment.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public interface JaakPhysicSpace extends Space {

	/** Replies the identifier of the creator of the space.
	 *
	 * @return the identifier of the space creator.
	 */
	UUID getCreatorID();

	/** Destroy the space.
	 */
	void destroy();

	/** Spawn the body with the given ID.
	 *
	 * @param binder - the binder between the skill and the space.
	 */
	void spawnBody(EventListener binder);

	/** Destroy the body with the given ID.
	 *
	 * @param binder - the binder between the skill and the space.
	 */
	void killBody(EventListener binder);

	/** Give the perceptions to the agents that is owning the given body.
	 *
	 * @param perception - the event to give to the agent.
	 */
	void notifyPerception(Perception perception);

	/** Emit an influence for the given agent.
	 *
	 * @param influenceTime - the time at which the influence is applied.
	 * @param influence - the influence to emit.
	 */
	void influence(float influenceTime, Influence influence);

}
