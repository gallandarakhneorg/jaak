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

import io.sarl.jaak.environment.SimulationStarted;
import io.sarl.jaak.environment.SimulationStopped;
import io.sarl.jaak.environment.time.TimeManager;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.EventSpace;

import java.lang.ref.WeakReference;


/** Standard implementation of a JaakController.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
class JaakKernelController implements JaakController {

	private static final int DEFAULT_WAITING_DURATION = 0;
	private static final int DEFAULT_TIMEOUT = 5000;

	private State state = State.NOT_INITIALIZED;

	private WeakReference<EventSpace> space;
	private WeakReference<TimeManager> timeManager;
	private Address address;
	private int timeout = DEFAULT_TIMEOUT;
	private int waitingDuration = DEFAULT_WAITING_DURATION;

	/**
	 */
	public JaakKernelController() {
		//
	}

	private void ensureInitialized() {
		State s;
		synchronized (this) {
			s = this.state;
		}
		if (s == State.NOT_INITIALIZED) {
			do {
				Thread.yield();
				synchronized (this) {
					s = this.state;
				}
			}
			while (s != State.NEVER_STARTED);
		}
	}

	/** Set the communication space used by the controller.
	 *
	 * @param space the space.
	 * @param address the address.
	 * @param timeManager the time manager.
	 */
	synchronized void initialize(EventSpace space, Address address, TimeManager timeManager) {
		this.space = (space == null) ? null : new WeakReference<>(space);
		this.timeManager = (timeManager == null) ? null : new WeakReference<>(timeManager);
		this.address = address;
		this.state = State.NEVER_STARTED;
	}

	private EventSpace getSpace() {
		return this.space == null ? null : this.space.get();
	}

	private TimeManager getTimeManager() {
		return this.timeManager == null ? null : this.timeManager.get();
	}

	/** Wake the simulator by running one step of the simulation.
	 * <p>
	 * This function sends the event <code>ExecuteSimulationStep</code>.
	 */
	public synchronized void wakeSimulator() {
		if (this.state == State.RUNNING) {
			EventSpace s = getSpace();
			if (s != null) {
				ExecuteSimulationStep event = new ExecuteSimulationStep();
				event.setSource(this.address);
				s.emit(event);
			}
		}
	}

	@Override
	public void startSimulation() {
		ensureInitialized();
		synchronized (this) {
			if (this.state == State.NEVER_STARTED) {
				EventSpace s = getSpace();
				TimeManager tm = getTimeManager();
				if (s != null && tm != null) {
					this.state = State.RUNNING;
					SimulationStarted startEvent = new SimulationStarted(
							tm.getCurrentTime(),
							tm.getLastStepDuration());
					startEvent.setSource(this.address);
					s.emit(startEvent);
				}
			} else if (this.state == State.PAUSED) {
				EventSpace s = getSpace();
				TimeManager tm = getTimeManager();
				if (s != null && tm != null) {
					this.state = State.RUNNING;
					wakeSimulator();
				}
			}
		}
	}

	@Override
	public synchronized void pauseSimulation() {
		if (this.state == State.RUNNING) {
			this.state = State.PAUSED;
		}
	}

	@Override
	public synchronized void stopSimulation() {
		if (this.state == State.RUNNING || this.state == State.PAUSED) {
			EventSpace s = getSpace();
			TimeManager tm = getTimeManager();
			if (s != null && tm != null) {
				this.state = State.STOPPED;
				SimulationStopped stopEvent = new SimulationStopped(
						tm.getCurrentTime(),
						tm.getLastStepDuration());
				stopEvent.setSource(this.address);
				s.emit(stopEvent);
			}
		}
	}
	
	@Override
	public int getSimulationStepTimeOut() {
		return this.timeout;
	}

	@Override
	public void setSimulationStepTimeOut(int duration) {
		if (duration > 0) {
			this.timeout = duration;
		} else {
			this.timeout = 0;
		}
	}
	
	@Override
	public int getWaitingDuration() {
		return this.waitingDuration;
	}
	
	@Override
	public void setWaitingDuration(int duration) {
		if (duration > 0) {
			this.waitingDuration = duration;
		} else {
			this.waitingDuration = 0;
		}
	}

	/**
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	private enum State {
		NOT_INITIALIZED,
		NEVER_STARTED,
		RUNNING,
		PAUSED,
		STOPPED;
	}

}
