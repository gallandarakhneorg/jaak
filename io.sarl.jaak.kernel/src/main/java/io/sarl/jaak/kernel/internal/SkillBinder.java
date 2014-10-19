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

import io.sarl.jaak.environment.external.Perception;
import io.sarl.jaak.environment.external.body.TurtleBody;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.EventListener;

import java.util.UUID;

/** Object that may be used to link a skill to a JaakPhysicSpace.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class SkillBinder implements EventListener {

	private final EventListener owner;
	private TurtleBody body;
	private float currentTime;
	private float currentStepSize;

	/**
	 * @param owner - the owner of the skill.
	 */
	public SkillBinder(EventListener owner) {
		this.owner = owner;
	}

	/** Replies the owner.
	 *
	 * @return the owner.
	 */
	public EventListener getOwner() {
		return this.owner;
	}

	/** Replies the body.
	 *
	 * @return the body.
	 */
	public TurtleBody getBody() {
		return this.body;
	}

	/** Replies the current simulation time.
	 *
	 * @return the current time.
	 */
	public float getCurrentTime() {
		return this.currentTime;
	}

	/** Replies the size of the current simulation step.
	 *
	 * @return the current simulation step.
	 */
	public float getCurrentStepSize() {
		return this.currentStepSize;
	}

	@Override
	public UUID getID() {
		return this.owner.getID();
	}

	@Override
	public void receiveEvent(Event event) {
		assert (event instanceof Perception);
		Perception perception = (Perception) event;
		this.currentTime = perception.currentTime;
		this.currentStepSize = perception.lastStepDuration;
		this.body = perception.body;
		//
		this.owner.receiveEvent(event);
	}

}
