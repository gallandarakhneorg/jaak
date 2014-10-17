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
package io.sarl.jaak.envinterface.perception;

/** This class defines a location on the grid where no turtle nor other
 * environment object stay.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class Obstacle extends EnvironmentalObject {

	/** Default Obstacle semantic.
	 */
	public static final Object OBSTACLE_SEMANTIC = new Object();

	private static final long serialVersionUID = 7180672612817853149L;

	/**
	 * @param semantic is the semantic associated to this environmental object.
	 */
	public Obstacle(Object semantic) {
		super(semantic);
	}

	/**
	 */
	public Obstacle() {
		this(OBSTACLE_SEMANTIC);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean isBurrow() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean isObstacle() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean isSubstance() {
		return false;
	}

}
