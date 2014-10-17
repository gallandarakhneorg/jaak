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

import io.sarl.lang.core.SpaceID;
import io.sarl.lang.util.SynchronizedSet;
import io.sarl.util.Collections3;

import java.util.UUID;

/** Implementation of the physic space for Jaak.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
class JaakPhysicSpaceImpl implements JaakPhysicSpace {
	
	private final SpaceID id;
	private final UUID creator;
	
	/**
	 * @param id - the identifier of the space.
	 * @param creator - the identifier of the creator of the space, usually of a Jaak kernel agent.
	 */
	public JaakPhysicSpaceImpl(SpaceID id, UUID creator) {
		assert(id != null);
		assert(creator != null);
		this.id = id;
		this.creator = creator;
	}

	@Override
	public SpaceID getID() {
		return this.id;
	}

	@Override
	public SynchronizedSet<UUID> getParticipants() {
		return Collections3.emptySynchronizedSet();
	}

	@Override
	public UUID getCreatorID() {
		return this.creator;
	}

	@Override
	public void emit(Perception perception, UUID receiver) {
		
	}

	@Override
	public void emit(Move move) {
		
	}

	@Override
	public void spawnBody(UUID id, Object listener) {
		
	}

	@Override
	public void killBody(UUID id, Object listener) {
		
	}
	
}