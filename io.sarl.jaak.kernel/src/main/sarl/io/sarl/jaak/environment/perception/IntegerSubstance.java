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
package io.sarl.jaak.environment.perception;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/** This class defines a substance with an integervalue as the internal substance value.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public abstract class IntegerSubstance extends AbstractNumberSubstance {

	private static final long serialVersionUID = 7465772824758226247L;

	/** Value of the substance.
	 */
	private int value;

	/**
	 * @param semantic is the semantic associated to this environmental object.
	 */
	public IntegerSubstance(Serializable semantic) {
		this(0, semantic);
	}

	/**
	 * @param initialValue is the initial value of this substance.
	 * @param semantic is the semantic associated to this environmental object.
	 */
	public IntegerSubstance(int initialValue, Serializable semantic) {
		super(semantic);
		this.value = initialValue;
	}
	
	@Override
	protected IntegerSubstance clone() {
		return (IntegerSubstance) super.clone();
	}
	
	/** Force the value of the substance.
	 *
	 * @param value the new value.
	 */
	protected void setValue(int value) {
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void increment(float a) {
		this.value += a;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void decrement(float a) {
		this.value -= a;
		if (this.value < 0f) {
			this.value = 0;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean isDisappeared() {
		return this.value <= 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Float getAmount() {
		return Float.valueOf(this.value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final BigDecimal bigDecimalValue() {
		return new BigDecimal(Integer.toString(this.value));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final BigInteger bigIntegerValue() {
		return new BigInteger(Integer.toString(this.value));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final byte byteValue() {
		return (byte) this.value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double doubleValue() {
		return this.value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final float floatValue() {
		return this.value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int intValue() {
		return this.value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final long longValue() {
		return this.value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final short shortValue() {
		return (short) this.value;
	}

}
