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
package io.sarl.jaak.demos.traffic.environment.physic;

import io.sarl.jaak.environment.body.TurtleObject;
import io.sarl.jaak.environment.influence.ObjectChangeInfluence;
import io.sarl.jaak.environment.model.GridModel;

import java.util.Collection;

import org.eclipse.xtext.xbase.lib.Pair;

/** Influence for changing the traffic light state.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class TrafficLightInfluence extends ObjectChangeInfluence<TrafficLight> {

	private final Collection<Pair<TrafficLight, TrafficLightState>> changes;

	/**
	 * @param changes - the changes of traffic light.
	 */
	TrafficLightInfluence(Collection<Pair<TrafficLight, TrafficLightState>> changes) {
		super((TurtleObject) null);
		this.changes = changes;
	}
	
	/** Replies if this influence contains changes.
	 *
	 * @return <code>true</code> if the influence contains a change. <code>false</code> otherwise.
	 */
	boolean containsChange() {
		return !this.changes.isEmpty();
	}

	@Override
	public boolean changeObjects(GridModel model) {
		if (changes.isEmpty()) {
			return false;
		}
		for (Pair<TrafficLight, TrafficLightState> entry : this.changes) {
			entry.getKey().setState(entry.getValue());
		}
		return true;	
	}
	
}
