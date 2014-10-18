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

import io.sarl.jaak.environment.external.time.TimeManager;

import java.util.concurrent.TimeUnit;

/** Time manager for Jaak environment.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
class DefaultJaakTimeManager implements TimeManager {

	/** Define the default duration of a simulation step.
	 */
	public static final float STEP_DURATION = 1.f;
	
	private long waitingDuration = 0;
	
	private float time;
	private final float stepDuration;

	/** Build a constant time manager starting at time 0.
	 * 
	 * @param stepDuration is the duration of one step (in milliseconds).
	 */
	public DefaultJaakTimeManager(float stepDuration) {
		this.stepDuration = stepDuration;
		this.time = 0f;
	}
	
	/** Build a constant time manager starting at the given time.
	 * 
	 * @param stepDuration is the duration of one step (in milliseconds).
	 * @param startTime is the start time in milliseconds.
	 */
	public DefaultJaakTimeManager(float stepDuration, float startTime) {
		this.stepDuration = stepDuration;
		this.time = startTime;
	}

	/**
	 */
	public DefaultJaakTimeManager() {
		this(STEP_DURATION);
	}
	

	/** Replies the duration of one time step.
	 * 
	 * @return the duration of one time step.
	 */
	public double getTimeStepDuration() {
		return this.stepDuration;
	}
	
	@Override
	public void increment() {
		if (this.waitingDuration>0) {
			try {
				Thread.sleep(this.waitingDuration);
			}
			catch (InterruptedException e) {
				//
			}
		}
		this.time += this.stepDuration;
	}

	@Override
	public float getCurrentTime() {
		return this.time;
	}

	@Override
	public float getCurrentTime(TimeUnit unit) {
		assert(unit!=null);
		switch(unit) {
		case DAYS:
		case HOURS:
			return this.time * 3.6f;
		case MINUTES:
			return this.time * 6e-2f;
		case SECONDS:
			return this.time * 1e-3f;
		case MILLISECONDS:
			return this.time;
		case MICROSECONDS:
			return this.time / 1e-3f;
		case NANOSECONDS:
			return this.time / 1e-6f;
		default:
		}
		throw new IllegalArgumentException();
	}

	/** Replies the duration of the last simulation step in seconds.
	 * 
	 * @return the duration of the last simulation step in seconds.
	 */
	@Override
	public float getLastStepDuration() {
		return getLastStepDuration(TimeUnit.SECONDS);
	}
	
	/** Replies the duration of the last simulation step in the given time unit.
	 * 
	 * @param unit is the time unit used to format the replied value.
	 * @return the duration of the last simulation step.
	 */
	@Override
	public float getLastStepDuration(TimeUnit unit) {
		assert(unit!=null);
		float duration = (float)getTimeStepDuration();
		switch(unit) {
		case DAYS:
		case HOURS:
			return duration * 3.6f;
		case MINUTES:
			return duration * 6e-2f;
		case SECONDS:
			return duration * 1e-3f;
		case MILLISECONDS:
			return duration;
		case MICROSECONDS:
			return duration / 1e-3f;
		case NANOSECONDS:
			return duration / 1e-6f;
		default:
		}
		throw new IllegalArgumentException();
	}

	@Override
	public synchronized long getWaitingDuration() {
		return this.waitingDuration;
	}

	@Override
	public synchronized void setWaitingDuration(long duration) {
		if (duration>0) {
			this.waitingDuration = duration;
		}
		else {
			this.waitingDuration = 0;
		}
	}

}