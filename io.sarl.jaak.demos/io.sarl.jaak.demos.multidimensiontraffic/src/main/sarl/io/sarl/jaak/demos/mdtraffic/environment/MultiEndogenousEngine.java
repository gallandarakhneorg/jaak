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
package io.sarl.jaak.demos.mdtraffic.environment;

import io.sarl.jaak.environment.endogenous.EnvironmentEndogenousEngine;
import io.sarl.jaak.environment.influence.Influence;
import io.sarl.jaak.environment.model.GridModel;
import io.sarl.jaak.environment.time.TimeManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/** 
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class MultiEndogenousEngine extends ArrayList<EnvironmentEndogenousEngine> implements EnvironmentEndogenousEngine {

	private static final long serialVersionUID = 7410675000405070368L;

	/**
	 */
	public MultiEndogenousEngine() {
		super();
	}

	@Override
	public Collection<Influence> computeInfluences(GridModel grid, TimeManager timeManager) {
		List<Influence> list = new ArrayList<>();
		for (EnvironmentEndogenousEngine engine : this) {
			Collection<Influence> l = engine.computeInfluences(grid, timeManager);
			if (l != null) {
				list.addAll(l);
			}
		}
		return list;
	}
		
}
