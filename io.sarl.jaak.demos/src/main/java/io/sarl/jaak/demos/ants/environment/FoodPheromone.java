package io.sarl.jaak.demos.ants.environment;

import java.awt.Color;

/** This class defines a pheromon which is permitting to return to a food source.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class FoodPheromone extends Pheromone {

	private static final long serialVersionUID = 4699281292431261802L;

	/** Semantic of a colony pheromone.
	 */
	public static Object SEMANTIC = new Object(); 
	
	/** Evaporation amount for the pheromone.
	 */
	public static float EVAPORATION = 50f; 
	
	/**
	 */
	public FoodPheromone() {
		this(10f);
	}
		
	/**
	 * @param amount
	 */
	public FoodPheromone(float amount) {
		super(Math.max(0, amount), EVAPORATION, SEMANTIC, Color.PINK);
	}

}