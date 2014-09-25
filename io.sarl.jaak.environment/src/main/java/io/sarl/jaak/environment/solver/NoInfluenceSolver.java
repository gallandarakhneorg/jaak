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
package io.sarl.jaak.environment.solver;

import io.sarl.jaak.envinterface.influence.Influence;
import io.sarl.jaak.envinterface.influence.MotionInfluence;
import io.sarl.jaak.envinterface.influence.MotionInfluenceStatus;
import io.sarl.jaak.environment.model.AbstractJaakEnvironmentInfluenceSolver;
import io.sarl.jaak.environment.model.RealTurtleBody;

import java.util.Collection;
import java.util.List;


/** This class defines an implementation for influence solver which
 * does nothing.
 * This implementation:<ul>
 * <li>generate a motion action corresponding to all the influences;</li>
 * <li>does not validate the other influences.</li>
 * </ul>
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class NoInfluenceSolver extends AbstractJaakEnvironmentInfluenceSolver {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void solve(
			Collection<? extends Influence> endogenousInfluences,
			Collection<RealTurtleBody> bodies,
			ActionApplier actionApplier) {
		if (actionApplier!=null) {
			if (endogenousInfluences!=null) {
				for(Influence influence : endogenousInfluences) {
					applyInfluence(actionApplier, influence, null);
				}
			}
			if (bodies!=null) {
				List<Influence> influences;
				for(RealTurtleBody body : bodies) {
					influences = body.consumeOtherInfluences();
					if (influences!=null) {
						for(Influence influence : influences) {
							applyInfluence(actionApplier, influence, null);
						}
					}
					MotionInfluence influence = body.consumeMotionInfluence();
					if (influence!=null) {
						applyInfluence(actionApplier, influence, MotionInfluenceStatus.COMPLETE_MOTION);
					}
				}
			}
		}
	}
	
}