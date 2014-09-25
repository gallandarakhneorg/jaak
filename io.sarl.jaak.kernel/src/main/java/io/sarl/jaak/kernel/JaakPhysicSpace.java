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
	 * @return the identifier of the space's creator.
	 */
	public UUID getCreatorID();
	
	/** Emit a perception for the given agent.
	 * 
	 * @param perception - the perception to send.
	 * @param receiver - the receiver of the perception.
	 */
	public void emit(Perception perception, UUID receiver);

	/** Emit a motion influence for the given agent.
	 * 
	 * @param move - the influence to emit
	 */
	public void emit(Move move);

}