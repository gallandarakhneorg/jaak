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
package io.sarl.jaak.environment.external.influence;

import java.io.Serializable;

/** This class defines an influence to change the semantic
 * of an object in the environment.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class SemanticChangeInfluence extends Influence {

	private final Serializable newSemantic;

	/**
	 * @param semantic - the new semantic.
	 */
	public SemanticChangeInfluence(Serializable semantic) {
		super(null);
		this.newSemantic = semantic;
	}

	/** Replies the new semantic.
	 *
	 * @return the new semantic.
	 */
	public Serializable getSemantic() {
		return this.newSemantic;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(getEmitter().getTurtleId().toString());
		buffer.append(": setSemantic "); //$NON-NLS-1$
		Serializable semantic = getSemantic();
		buffer.append(semantic == null ? null : semantic.toString());
		return buffer.toString();
	}

}
