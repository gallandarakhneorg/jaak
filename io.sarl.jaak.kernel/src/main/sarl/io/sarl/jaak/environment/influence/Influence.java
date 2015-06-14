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
package io.sarl.jaak.environment.influence;

import io.sarl.jaak.environment.body.TurtleObject;

/** This class defines an influence from turtle to environment.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public abstract class Influence {

	private TurtleObject emitter;

	/**
	 * @param emitter is the emitter of the influence.
	 */
	public Influence(TurtleObject emitter) {
		this.emitter = emitter;
	}

	/** Replies the influence emitter.
	 *
	 * @return the influence emitter.
	 */
	public TurtleObject getEmitter() {
		return this.emitter;
	}

	/** Set the influence emitter.
	 *
	 * @param emitter - the influence emitter.
	 */
	public void setEmitter(TurtleObject  emitter) {
		this.emitter = emitter;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.emitter == null ? "" : this.emitter.getTurtleId().toString(); //$NON-NLS-1$
	}

}
