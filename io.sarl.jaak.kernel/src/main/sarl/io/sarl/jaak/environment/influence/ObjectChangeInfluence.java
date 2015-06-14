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

import io.sarl.jaak.environment.body.TurtleBody;
import io.sarl.jaak.environment.body.TurtleObject;
import io.sarl.jaak.environment.model.GridModel;
import io.sarl.jaak.environment.perception.EnvironmentalObject;


/** This class defines an influence to change an object in the environment.
 * The change is described by the sub-classes
 *
 * @param <T> type of the environment objects.
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public abstract class ObjectChangeInfluence<T extends EnvironmentalObject> extends Influence {

	private final T object;
	
	/**
	 */
	public ObjectChangeInfluence() {
		super(null);
		this.object = null;
	}

	/**
	 * @param emitter the emitter of the influence.
	 */
	public ObjectChangeInfluence(TurtleObject emitter) {
		super(emitter);
		this.object = null;
	}

	/**
	 * @param object the object to change.
	 */
	public ObjectChangeInfluence(T object) {
		super(null);
		this.object = object;
	}

	/** Replies the object to change.
	 *  
	 * @return the object, or <code>null</code>.
	 */
	public T getObjectToChange() {
		return this.object;
	}
	
	/** Apply the influence on the object.
	 * 
	 * @param object the object to change.
	 * @return <code>true</code> if the given object has changed, <code>false</code> otherwise.
	 */
	public boolean changeObject(T object) {
		return false;
	}

	/** Apply the influence on the object.
	 * 
	 * @param model the model of the world.
	 * @return <code>true</code> if an object has changed, <code>false</code> otherwise.
	 */
	public boolean changeObjects(GridModel model) {
		return false;
	}

	/** Apply the influence on the body.
	 * 
	 * @param body the body to change
	 * @return <code>true</code> if the given body has changed, <code>false</code> otherwise.
	 */
	public boolean changeBody(TurtleBody body) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(getEmitter().getTurtleId().toString());
		buffer.append(": changeObject "); //$NON-NLS-1$
		return buffer.toString();
	}

}
