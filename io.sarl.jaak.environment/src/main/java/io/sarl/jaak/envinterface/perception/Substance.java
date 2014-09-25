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

import java.math.BigDecimal;
import java.math.BigInteger;

import org.arakhne.afc.math.discrete.object2d.Point2i;

/** This class defines a situated object which has specifical
 * physical properties.
 * <p>
 * An environmental object is atomic or not:<ul>
 * <li>an atomic object is an object which has an identity and cannot be merge
 * with another object with the same type and semantic.</li>
 * <li>a non-atomic object is an object which has not an identity. Consequently,
 * it may be merge with other objects with the same type and semantic.</li>
 * </ul>
 * <p>
 * A substance is a non-atomic object.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public abstract class Substance extends EnvironmentalObject {

	private static final long serialVersionUID = -1696947800104633172L;
	
	/**
	 * @param semantic is the semantic associated to this environmental object.
	 */
	public Substance(Object semantic) {
		super(semantic);
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
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean isSubstance() {
		return true;
	}

	/** Replies an identifier for this object.
	 * The identifier is unique for environmental objects
	 * which are not a {@link Substance} and is common
	 * to all instances of the same <code>Substance</code> class.
	 * 
	 * @return the identifier of the environmental object.
	 */
	@Override
	public String getEnvironmentalObjectIdentifier() {
		return getClass().getCanonicalName() + "-o-o-o-*"; //$NON-NLS-1$
	}
	
	/**
	 * Add the given substance <var>s</var> to the current substance.
	 * <p>
	 * The concept of substance combination depends on the semantic of the substance.
	 * <p>
	 * 
	 * @param s is the substance to combine with.
	 * @return the amount of substance added to the current substance, or <code>null</code> if
	 * the operation is impossible.
	 */
	protected abstract Substance increment(Substance s);
	
	/**
	 * Add the given substance <var>s</var> to the current substance.
	 * <p>
	 * The concept of substance combination depends on the semantic of the substance.
	 * <p>
	 * 
	 * @param s is the substance to combine with.
	 * @return the amount of substance removed from the the current substance, or <code>null</code> if
	 * the operation is impossible.
	 */
	protected abstract Substance decrement(Substance s);

	/**
	 * Replies if this substance has disappeared or not.
	 * 
	 * @return <code>true</code> if this substance has disappeared,
	 * otherwise <code>false</code>.
	 */
	public abstract boolean isDisappeared();
	
	/** Replies the amount of substance.
	 * 
	 * @return the amount of substance.
	 */
	public abstract Number getAmount();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("SUBSTANCE("); //$NON-NLS-1$
		buffer.append(getEnvironmentalObjectIdentifier());
		buffer.append(")="); //$NON-NLS-1$
		buffer.append(getAmount().toString());
		Point2i position = getPosition();
		buffer.append("@("); //$NON-NLS-1$
		buffer.append(position.getX());
		buffer.append(';');
		buffer.append(position.getY());
		buffer.append(")"); //$NON-NLS-1$
		return buffer.toString();
	}

	/** Replies byte representation of the amount of substance.
	 * 
	 * @return byte representation of the amount of substance.
	 */
	public abstract byte byteValue();
	
	/** Replies short representation of the amount of substance.
	 * 
	 * @return short representation of the amount of substance.
	 */
	public abstract short shortValue();

	/** Replies int representation of the amount of substance.
	 * 
	 * @return int representation of the amount of substance.
	 */
	public abstract int intValue();

	/** Replies long representation of the amount of substance.
	 * 
	 * @return long representation of the amount of substance.
	 */
	public abstract long longValue();

	/** Replies float representation of the amount of substance.
	 * 
	 * @return float representation of the amount of substance.
	 */
	public abstract float floatValue();

	/** Replies double representation of the amount of substance.
	 * 
	 * @return doublerepresentation of the amount of substance.
	 */
	public abstract double doubleValue();

	/** Replies big int representation of the amount of substance.
	 * 
	 * @return big int representation of the amount of substance.
	 */
	public abstract BigInteger bigIntegerValue();

	/** Replies big decimal representation of the amount of substance.
	 * 
	 * @return big decimal representation of the amount of substance.
	 */
	public abstract BigDecimal bigDecimalValue();

}