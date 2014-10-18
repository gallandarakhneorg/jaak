package io.sarl.jaak.demos.ants.environment;

import io.sarl.jaak.environment.external.perception.FloatSubstance;
import io.sarl.jaak.environment.external.perception.Substance;

/** This class defines a source of food.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class Food extends FloatSubstance implements Cloneable {
	
	private static final long serialVersionUID = 472944785479015352L;

	/**
	 * @param foodQuantity is the quantity of food in this source.
	 */
	public Food(float foodQuantity) {
		super(foodQuantity, Food.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Food clone() {
		try {
			return (Food)super.clone();
		}
		catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Substance decrement(Substance s) {
		if (s instanceof Food) {
			float oldValue = floatValue();
			decrement(s.floatValue());
			Food c = clone();
			c.value = Math.abs(floatValue() - oldValue);
			return c;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Substance increment(Substance s) {
		return null;
	}
	
}