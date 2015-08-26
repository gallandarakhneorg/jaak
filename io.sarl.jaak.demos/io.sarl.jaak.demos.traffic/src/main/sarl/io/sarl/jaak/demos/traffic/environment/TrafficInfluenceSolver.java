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
package io.sarl.jaak.demos.traffic.environment;

import io.sarl.jaak.environment.body.TurtleBody;
import io.sarl.jaak.environment.influence.DropDownInfluence;
import io.sarl.jaak.environment.influence.Influence;
import io.sarl.jaak.environment.influence.MotionInfluenceStatus;
import io.sarl.jaak.environment.solver.ActionApplier;
import io.sarl.jaak.environment.solver.PathBasedInfluenceSolver;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.arakhne.afc.math.discrete.object2d.Circle2i;
import org.arakhne.afc.math.discrete.object2d.Point2i;
import org.arakhne.afc.util.Pair;


/** 
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class TrafficInfluenceSolver extends PathBasedInfluenceSolver {

	@Override
	protected void applyInfluence(ActionApplier actionApplier,
			Influence influence, MotionInfluenceStatus motionStatus) {
		super.applyInfluence(actionApplier, influence, motionStatus);
		if (influence instanceof DropDownInfluence) {
			DropDownInfluence ddInfluence = (DropDownInfluence) influence;
			if (ddInfluence.getDropOffObject() instanceof Sound) {
				TurtleBody emitter = (TurtleBody) ddInfluence.getEmitter();
				assert (emitter != null);
				Point2i position = emitter.getPosition();
				assert (position != null);
				Sound sound = (Sound) ddInfluence.getDropOffObject();
				float amount = sound.getAmount().floatValue();
				Circle2i propagation = new Circle2i(position.x(), position.y(), (int) amount);
				Iterator<Point2i> iterator = propagation.getPointIterator();
				Map<Integer, Pair<Integer, Integer>> lines = new HashMap<>();
				while (iterator.hasNext()) {
					Point2i point = iterator.next();
					Pair<Integer, Integer> pair = lines.get(point.y());
					if (pair == null) {
						pair = new Pair<>(point.x(), point.x());
						lines.put(point.y(), pair);
					} else {
						if (point.x() < pair.getA()) {
							pair.setA(point.x());
						}
						if (point.x() > pair.getB()) {
							pair.setB(point.x());
						}
					}
				}
				Point2i point = new Point2i();
				for (Entry<Integer, Pair<Integer, Integer>> entry : lines.entrySet()) {
					int y = entry.getKey();
					for (int x = entry.getValue().getA(); x <= entry.getValue().getB(); ++x) {
						if (x != position.x() || y != position.y()) {
							point.set(x, y);
							int d = (int) position.distance(point);
							if (d > 0) {
								float intensity = (float) amount / d;
								if (intensity > 0f) {
									Sound s = sound.copy(intensity, sound.getSoundSourcePosition());
									actionApplier.putObject(x, y, s);
								}
							}
						}
					}
				}
			}
		}
	}
	
}
