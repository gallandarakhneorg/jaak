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

import io.sarl.jaak.envinterface.influence.EnvironmentalObjectRemovalInfluence;
import io.sarl.jaak.envinterface.influence.Influence;

/** This class defines a situated object inside the Jaak environment
 * which is not an agent.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class EnvironmentalObject extends AbstractPerceivable implements JaakObject {

	private static final long serialVersionUID = 4218782489455701467L;

	/**
	 * @param semantic is the semantic associated to this environmental object.
	 */
	public EnvironmentalObject(Object semantic) {
		super();
		this.semantic = semantic;
	}

	/** Replies an identifier for this object.
	 * The identifier is unique for environmental objects
	 * which are not a {@link Substance} and is common
	 * to all instances of the same <code>Substance</code> class.
	 *
	 * @return the identifier of the environmental object.
	 */
	public String getEnvironmentalObjectIdentifier() {
		StringBuffer buf = new StringBuffer();
		buf.append(getClass().getCanonicalName());
		buf.append("-o-o-o-"); //$NON-NLS-1$
		buf.append(Integer.toHexString(System.identityHashCode(this)));
		return buf.toString();
	}

	/** Set the position of this object.
	 *
	 * @param x is the new position of the object.
	 * @param y is the new position of the object.
	 */
	void setPosition(int x, int y) {
		this.position.setX(x);
		this.position.setY(y);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean isTurtle() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isBurrow() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isObstacle() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSubstance() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		if (isTurtle()) {
			buffer.append("TURTLE("); //$NON-NLS-1$
		} else {
			buffer.append("OBJECT("); //$NON-NLS-1$
		}
		buffer.append(getEnvironmentalObjectIdentifier());
		buffer.append("); "); //$NON-NLS-1$
		buffer.append(super.toString());
		return buffer.toString();
	}

	/** Replies an influence which is permitting to remove this object from the environment.
	 *
	 * @return an influence, or <code>null</code> to not allow to remove the object.
	 */
	protected Influence createRemovalInfluenceForItself() {
		return new RemoveItself();
	}

	/**
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	private class RemoveItself extends EnvironmentalObjectRemovalInfluence {

		/**
		 */
		public RemoveItself() {
			//
		}

		@Override
		public EnvironmentalObject getRemovableObject() {
			return EnvironmentalObject.this;
		}

	}

}
