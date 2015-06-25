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
package io.sarl.jaak.demos.mdtraffic;

import io.sarl.jaak.demos.mdtraffic.environment.communication.CommunicationEvent;
import io.sarl.jaak.environment.endogenous.EnvironmentEndogenousEngine;
import io.sarl.jaak.environment.influence.Influence;
import io.sarl.jaak.environment.model.GridModel;
import io.sarl.jaak.environment.model.JaakEnvironment;
import io.sarl.jaak.environment.time.TimeManager;
import io.sarl.util.OpenEventSpace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.xtext.xbase.lib.Functions.Function3;
import org.eclipse.xtext.xbase.lib.Pair;



/** Database of rules.
 * 
 * The case a illustrates when the interaction in one dimension is constrained by the second.
 * The case b illustrates when the same interaction has different forms in the two dimensions.
 * The case c illustrates when an interaction initiated by an agent in a dimension generates
 * an interaction in the other dimension.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class RuleDatabase implements EnvironmentEndogenousEngine {
	
	private final JaakEnvironment physicEnvironment;
	private final OpenEventSpace communicationEnvironment;
	
	private final List<Pair<
					Function3<Object, JaakEnvironment, OpenEventSpace, Boolean>,
					Function3<Object, JaakEnvironment, OpenEventSpace, Boolean>>> rules = new ArrayList<>();
	
	private List<Influence> influences = new ArrayList<>();
	
	/**
	 * @param physicEnvironment
	 * @param communicationEnvironment
	 */
	public RuleDatabase(JaakEnvironment physicEnvironment, OpenEventSpace communicationEnvironment) {
		super();
		this.physicEnvironment = physicEnvironment;
		this.communicationEnvironment = communicationEnvironment;
	}
	
	public void add(Pair<Function3<Object, JaakEnvironment, OpenEventSpace, Boolean>,
			Function3<Object, JaakEnvironment, OpenEventSpace, Boolean>> rule) {
		if (rule != null) {
			this.rules.add(rule);
		}
	}
	
	protected boolean applyRules(Object source) {
		boolean isPropagated = true;
		for (Pair<Function3<Object, JaakEnvironment, OpenEventSpace, Boolean>,
				  Function3<Object, JaakEnvironment, OpenEventSpace, Boolean>> rule : this.rules) {
			Function3<Object, JaakEnvironment, OpenEventSpace, Boolean> predicat = rule.getKey();
			assert (predicat != null);
			if (predicat.apply(source, this.physicEnvironment, this.communicationEnvironment)) {
				Function3<Object, JaakEnvironment, OpenEventSpace, Boolean> generator = rule.getValue();
				assert (generator != null);
				isPropagated = isPropagated && generator.apply(source, this.physicEnvironment, this.communicationEnvironment);
			}
		}
		return isPropagated;
	}
	
	public synchronized boolean filter(CommunicationEvent event) {
		return applyRules(event);
	}

	public synchronized boolean filter(Influence influence) {
		return applyRules(influence);
	}

	@Override
	public synchronized Collection<Influence> computeInfluences(
			GridModel grid,
			TimeManager timeManager) {
		Collection<Influence> infs = this.influences;
		this.influences = new ArrayList<>();
		return infs;
	}
		
}
