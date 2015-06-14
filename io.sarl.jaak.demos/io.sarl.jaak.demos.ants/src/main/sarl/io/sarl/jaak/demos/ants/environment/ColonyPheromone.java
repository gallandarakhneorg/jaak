package io.sarl.jaak.demos.ants.environment;

import io.sarl.jaak.demos.ants.AntColonyConstants;

import java.awt.Color;
import java.io.Serializable;

/** This class defines a pheromon which is permitting to return to a colony.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class ColonyPheromone extends Pheromone {

	private static final long serialVersionUID = 1334755592290003927L;

	/** Semantic of a colony pheromone.
	 */
	public static Serializable SEMANTIC = ColonyPheromone.class.getName(); 
	
	/** Evaporation amount for the pheromone.
	 */
	public static float EVAPORATION = 100f; 
	
	/**
	 */
	public ColonyPheromone() {
		this(AntColonyConstants.MAX_PHEROMONE_AMOUNT);
	}
		
	/**
	 * @param amount
	 */
	public ColonyPheromone(float amount) {
		super(Math.max(0, amount), EVAPORATION, SEMANTIC, Color.WHITE);
	}

}