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

import io.janusproject.kernel.repository.UniqueAddressParticipantRepository;
import io.janusproject.kernel.space.DistributedSpace;
import io.janusproject.services.distributeddata.DMap;
import io.janusproject.services.distributeddata.DistributedDataStructureService;
import io.janusproject.services.executor.ExecutorService;
import io.janusproject.services.logging.LogService;
import io.janusproject.services.network.NetworkService;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.EventListener;
import io.sarl.lang.core.Scope;
import io.sarl.lang.core.SpaceID;
import io.sarl.lang.util.SynchronizedSet;
import io.sarl.util.Collections3;

import java.io.Serializable;
import java.util.UUID;

import com.google.inject.Inject;

/** Implementation of the physic space for Jaak that is dedicated to the turtle's skills.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
abstract class AbstractJaakPhysicSpace implements JaakPhysicSpace, DistributedSpace {

	/** Name of the shared attribute that is the ID of the creator of the space.
	 */
	public static final String KEY_CREATORID = "creatorID"; //$NON-NLS-1$

	/** Repository of the agents in the space.
	 */
	protected final UniqueAddressParticipantRepository<UUID> agents;

	/** Shared attributes in the space.
	 */
	protected final DMap<String, Serializable> sharedAttributes;

	/** The logging service.
	 */
	@Inject
	protected LogService logger;

	private final SpaceID id;

	@Inject
	private ExecutorService executorService;

	@Inject
	private NetworkService network;

	/**
	 * @param id - the identifier of the space.
	 * @param factory - the factory to be used for creating distributed data structures.
	 */
	public AbstractJaakPhysicSpace(SpaceID id, DistributedDataStructureService factory) {
		assert (id != null);
		this.id = id;
		this.agents = new UniqueAddressParticipantRepository<>(
				getID().getID().toString() + "-jaak-physicspace-agents", //$NON-NLS-1$
				factory);
		this.sharedAttributes = factory.getMap(getID().getID().toString() + "-jaak-physicspace-attributes"); //$NON-NLS-1$
	}

	@Override
	public SpaceID getID() {
		return this.id;
	}

	@Override
	public SynchronizedSet<UUID> getParticipants() {
		synchronized (this.agents) {
			return Collections3.unmodifiableSynchronizedSet(this.agents.getParticipantIDs());
		}
	}

	@Override
	public UUID getCreatorID() {
		return (UUID) this.sharedAttributes.get(KEY_CREATORID);
	}

	/** Do the emission of the event on the local event bus.
	 *
	 * @param event - the event to emit.
	 * @param scope - the scope.
	 * @return <code>true</code> if the event was dispatched on the local bus.
	 */
	protected boolean putOnEventBus(Event event, UUID scope) {
		synchronized (this.agents) {
			for (EventListener agent : this.agents.getListeners()) {
				if (scope.equals(agent.getID())) {
					fireAsync(agent, event);
					return true;
				}
			}
			return false;
		}
	}

	/** Do the emission of the event over the network.
	 *
	 * @param event - the event to emit.
	 * @param scope - the scope.
	 */
	protected void putOnNetwork(Event event, UUID scope) {
		try {
			this.network.publish(new UUIDScope(scope), event);
		} catch (Exception e) {
			this.logger.error(AbstractJaakPhysicSpace.class,
					"CANNOT_NOTIFY_OVER_NETWORK", scope, event, e); //$NON-NLS-1$
		}
	}

	/** Send the event to the given listener asyncronously.
	 *
	 * @param agent - the listener to notify.
	 * @param event - the event to send.
	 */
	protected void fireAsync(EventListener agent, Event event) {
		this.executorService.submit(new AsyncRunner(agent, event));
	}

	/** Implement a scope matching a single UUID.
	 *
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	protected static class UUIDScope implements Scope<UUID> {

		private static final long serialVersionUID = -2678036964365090540L;

		private final UUID id;

		/**
		 * @param id - the identifier.
		 */
		public UUIDScope(UUID id) {
			this.id = id;
		}

		/** Replies the identifier that is matched by this scope.
		 *
		 * @return the identifier.
		 */
		public UUID getID() {
			return this.id;
		}

		@Override
		public boolean matches(UUID element) {
			return this.id.equals(element);
		}

	}

	/**
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	private static class AsyncRunner implements Runnable {

		private final EventListener agent;
		private final Event event;

		/**
		 * @param agent
		 * @param event
		 */
		public AsyncRunner(EventListener agent, Event event) {
			this.agent = agent;
			this.event = event;
		}

		@Override
		public void run() {
			this.agent.receiveEvent(this.event);
		}

		@Override
		public String toString() {
			return "[agent=" + this.agent + "; event=" + this.event + "]"; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		}

	}

}
