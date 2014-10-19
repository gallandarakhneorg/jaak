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
package io.sarl.jaak.kernel.external;

import io.sarl.jaak.environment.external.EnvironmentArea;
import io.sarl.jaak.environment.external.body.BodySpawner;

import java.util.Collection;
import java.util.EventObject;

/** This event describes something appended in a Jaak simulation.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class JaakEvent extends EventObject {

	private static final long serialVersionUID = -5008386864452410572L;

	private final EnvironmentArea environment;
	private final Collection<BodySpawner> spawners;
	private final float currentTime;
	private final float lastStepDuration;

	/**
	 * @param source - source of the event.
	 * @param environment - the environment.
	 * @param spawners - the existing spawners.
	 * @param currentTime - the current simulation time.
	 * @param lastStepDuration - the duration of the last simulation step.
	 */
	public JaakEvent(Object source, EnvironmentArea environment, Collection<BodySpawner> spawners,
			float currentTime, float lastStepDuration) {
		super(source);
		this.environment = environment;
		this.spawners = spawners;
		this.currentTime = currentTime;
		this.lastStepDuration = lastStepDuration;
	}

	/** Replies the environment to which this event is related to.
	 *
	 * @return the Jaak environment.
	 */
	public EnvironmentArea getEnvironment() {
		return this.environment;
	}

	/** Replies the spawners associated to the environment.
	 *
	 * @return the spawners.
	 */
	public Collection<BodySpawner> getBodySpawners() {
		return this.spawners;
	}

	/** Replies the time at which the event occurs.
	 *
	 * @return the time at which the event occurs.
	 */
	public float getCurrentTime() {
		return this.currentTime;
	}

	/** Replies the duration of the last simulation step.
	 *
	 * @return the duration of the last simulation step.
	 */
	public float getLastStepDuration() {
		return this.lastStepDuration;
	}

}
