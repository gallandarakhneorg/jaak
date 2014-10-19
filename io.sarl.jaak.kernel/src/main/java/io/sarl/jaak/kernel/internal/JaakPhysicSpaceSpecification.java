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

import java.util.UUID;

import io.janusproject.services.distributeddata.DistributedDataStructureService;
import io.sarl.jaak.kernel.external.JaakPhysicSpace;
import io.sarl.lang.core.EventListener;
import io.sarl.lang.core.Space;
import io.sarl.lang.core.SpaceID;
import io.sarl.lang.core.SpaceSpecification;

/** Space that is representing the Jaak environment.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class JaakPhysicSpaceSpecification implements SpaceSpecification<JaakPhysicSpace> {

	/**
	 */
	public JaakPhysicSpaceSpecification() {
		//
	}

	@Override
	public JaakPhysicSpace create(SpaceID id, Object... params) {
		assert (params.length >= 1);
		assert (params[0] instanceof UUID);
		assert (params[1] instanceof DistributedDataStructureService);
		if (params.length >= 3 && params[2] instanceof EventListener) {
			return createSpace(id,
					(UUID) params[0],
					(DistributedDataStructureService) params[1],
					(EventListener) params[2]);
		}
		return createSpace(id,
				(UUID) params[0],
				(DistributedDataStructureService) params[1]);
	}

	/**
	 * Creates a {@link Space} that respects this specification.
	 *
	 * @param spaceId - the {@link SpaceID} for the newly created {@link Space}
	 * @param creatorId - the Id of the creator of the space.
	 * @param factory - the factory to be used for creating distributed data structures.
	 * @param environmentAgent - the reference to the agent listener which is managing the environment,
	 * or <code>null</code> if the current instance of the space is not directly linked to the
	 * environment agent.
	 * @return an instance of {@link Space}
	 */
	@SuppressWarnings("static-method")
	public JaakPhysicSpace createSpace(SpaceID spaceId, UUID creatorId, DistributedDataStructureService factory,
			EventListener environmentAgent) {
		return new JaakPhysicSpaceImpl(spaceId, creatorId, factory, environmentAgent);
	}

	/**
	 * Creates a {@link Space} that respects this specification.
	 *
	 * @param spaceId - the {@link SpaceID} for the newly created {@link Space}
	 * @param creatorId - the Id of the creator of the space.
	 * @param factory - the factory to be used for creating distributed data structures.
	 * @return an instance of {@link Space}
	 */
	public JaakPhysicSpace createSpace(SpaceID spaceId, UUID creatorId, DistributedDataStructureService factory) {
		return create(spaceId, creatorId, factory, null);
	}

}
