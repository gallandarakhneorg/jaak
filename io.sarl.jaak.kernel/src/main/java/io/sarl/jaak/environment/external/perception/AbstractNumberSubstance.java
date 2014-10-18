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
package io.sarl.jaak.environment.external.perception;

import java.io.Serializable;

/** This class defines a substance with a number
 * value as the internal substance value.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public abstract class AbstractNumberSubstance extends Substance {

	private static final long serialVersionUID = 184776510920605177L;

	/**
	 * @param semantic is the semantic associated to this environmental object.
	 */
	public AbstractNumberSubstance(Serializable semantic) {
		super(semantic);
	}

	/**
	 * Add the given amount a to the current substance.
	 *
	 * @param a is the amount of substance to add.
	 */
	protected abstract void increment(float a);

	/**
	 * Add the given amount a to the current substance.
	 *
	 * @param a is the amount of substance to add.
	 */
	protected abstract void decrement(float a);

	/**
	 * Add the given amount a (expressed per second) to the current substance
	 * during the given duration (expressed in seconds).
	 *
	 * @param duration is the duration of the addition in seconds.
	 * @param a is the amount of substance to add (per second).
	 */
	protected final void increment(float duration, Number a) {
		increment(duration * a.floatValue());
	}

	/**
	 * Substract the given amount a (expressed per second) from the current substance
	 * during the given duration (expressed in seconds).
	 *
	 * @param duration is the duration of the substraction in seconds.
	 * @param a is the amount of substance to substract (per second).
	 */
	protected final void decrement(float duration, Number a) {
		decrement(duration * a.floatValue());
	}

}
