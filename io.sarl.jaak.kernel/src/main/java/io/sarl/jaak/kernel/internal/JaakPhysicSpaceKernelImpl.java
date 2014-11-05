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
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.EventListener;
import io.sarl.lang.core.Scope;
import io.sarl.lang.core.SpaceID;

import java.util.UUID;

/** Implementation of the physic space for Jaak Kernel.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
class JaakPhysicSpaceKernelImpl extends JaakPhysicSpaceTurtleImpl {

	private EventListener environmentAgent;

	/**
	 * @param id - the identifier of the space.
	 * @param factory - the factory to be used for creating distributed data structures.
	 * @param environmentAgent - the reference to the agent listener which is managing the environment,
	 * or <code>null</code> if the current instance of the space is not directly linked to the
	 * environment agent.
	 */
	public JaakPhysicSpaceKernelImpl(SpaceID id, DistributedDataStructureService factory,
			EventListener environmentAgent) {
		super(id, factory);
		this.environmentAgent = environmentAgent;
		if (environmentAgent != null) {
			this.sharedAttributes.put(KEY_CREATORID, environmentAgent.getID());
		}
	}

	@Override
	public void destroy() {
		super.destroy();
		if (this.environmentAgent != null) {
			this.environmentAgent = null;
			this.sharedAttributes.remove(KEY_CREATORID);
		}
	}

	@Override
	public void notifyPerception(Perception perception) {
		UUID id = perception.body.getTurtleId();
		if (!putOnEventBus(perception, id)) {
			putOnNetwork(perception, id);
		}
	}

	@Override
	public void influence(float influenceTime, Influence influence) {
		Event event;
		if (influence != null) {
			event = new AgentInfluence(influenceTime, 0, influence);
			event.setSource(new Address(getID(), influence.getEmitter().getTurtleId()));
		} else {
			event = new SynchronizeBody(influenceTime, 0);
		}
		if (this.environmentAgent != null) {
			fireAsync(this.environmentAgent, event);
		} else {
			putOnNetwork(event, getCreatorID());
		}
	}

	@Override
	public void eventReceived(SpaceID space, Scope<?> scope, Event event) {
		if (scope instanceof UUIDScope) {
			UUID id = ((UUIDScope) scope).getID();
			if (this.environmentAgent != null && this.environmentAgent.getID().equals(id)) {
				fireAsync(this.environmentAgent, event);
				return;
			}
		}
		super.eventReceived(space, scope, event);
	}

}
