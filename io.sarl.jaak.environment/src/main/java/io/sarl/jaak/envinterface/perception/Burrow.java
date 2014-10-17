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

import io.sarl.jaak.envinterface.influence.Influence;

/** This class defines a location on the grid where turtles are living.
 * When a cell contains a burrow, it has no more restriction about
 * the number of turtles to be on the cell.
 * Moreover, all turtles inside the burrow are not perceivable by the
 * other turtles.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class Burrow extends EnvironmentalObject {

	/** Default Burrow semantic.
	 */
	public static final Object BURROW_SEMANTIC = new Object();

	private static final long serialVersionUID = 7690399061756387663L;

	/**
	 * @param semantic is the semantic associated to this environmental object.
	 */
	public Burrow(Object semantic) {
		super(semantic);
	}

	/**
	 */
	public Burrow() {
		this(BURROW_SEMANTIC);
	}

	/** {@inheritDoc}
	 */
	@Override
	protected final Influence createRemovalInfluenceForItself() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean isBurrow() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean isObstacle() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean isSubstance() {
		return false;
	}

}
