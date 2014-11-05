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
package io.sarl.jaak.kernel.internal;

import io.janusproject.services.distributeddata.DistributedDataStructureService;
import io.sarl.jaak.environment.external.Perception;
import io.sarl.jaak.environment.external.influence.Influence;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.EventListener;
import io.sarl.lang.core.Scope;
import io.sarl.lang.core.SpaceID;

import java.util.UUID;

/** Implementation of the physic space for Jaak that is dedicated to the turtle's skills.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
class JaakPhysicSpaceTurtleImpl extends AbstractJaakPhysicSpace {

	/**
	 * @param id - the identifier of the space.
	 * @param factory - the factory to be used for creating distributed data structures.
	 */
	public JaakPhysicSpaceTurtleImpl(SpaceID id, DistributedDataStructureService factory) {
		super(id, factory);
	}

	@Override
	public void destroy() {
		//
	}

	@Override
	public void spawnBody(EventListener binder) {
		synchronized (this.agents) {
			this.agents.registerParticipant(binder.getID(), binder);
		}
	}

	@Override
	public void killBody(EventListener binder) {
		synchronized (this.agents) {
			this.agents.unregisterParticipant(binder);
		}
	}

	@Override
	public void notifyPerception(Perception perception) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void influence(float influenceTime, Influence influence) {
		AgentInfluence event = new AgentInfluence(influenceTime, 0, influence);
		putOnNetwork(event, getCreatorID());
	}

	@Override
	public void eventReceived(SpaceID space, Scope<?> scope, Event event) {
		if (scope instanceof UUIDScope) {
			UUID id = ((UUIDScope) scope).getID();
			putOnEventBus(event, id);
		} else {
			this.logger.error(JaakPhysicSpaceTurtleImpl.class,
					"INVALID_SCOPE", scope, event); //$NON-NLS-1$
		}
	}

}
