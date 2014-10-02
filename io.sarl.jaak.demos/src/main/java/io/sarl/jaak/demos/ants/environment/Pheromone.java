package io.sarl.jaak.demos.ants.environment;

import io.sarl.jaak.demos.ants.AntColonyConstants;
import io.sarl.jaak.envinterface.endogenous.AutonomousEndogenousProcess;
import io.sarl.jaak.envinterface.influence.Influence;
import io.sarl.jaak.envinterface.perception.FloatSubstance;
import io.sarl.jaak.envinterface.perception.Substance;

import java.awt.Color;

/** This class defines a pheromon.
 * <p>
 * Ants use a signaling communication system based on the deposition 
 * of pheromone over the path it follows, marking a trail. 
 * Pheromone is a hormone produced by ants that establishes a 
 * sort of indirect communication among them. Basically, an 
 * isolated ant moves at random, but when it finds a pheromone 
 * trail there is a high probability that this ant will 
 * decide to follow the trail.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public abstract class Pheromone extends FloatSubstance implements AutonomousEndogenousProcess, Cloneable {
	
	private static final long serialVersionUID = 791462106958634527L;

	private final float evaporationAmount;
	private final Color color;
	
	/**
	 * @param pheromoneIntensity is the initial intensity of the pheromone.
	 * @param evaporationAmount is the amount of pheromone which is evaporating
	 * during one second.
	 * @param color is the color of the pheromone.
	 */
	public Pheromone(float pheromoneIntensity, float evaporationAmount, Color color) {
		this(pheromoneIntensity, evaporationAmount, Pheromone.class, color);
	}
	
	/**
	 * @param pheromoneIntensity is the initial intensity of the pheromone.
	 * @param evaporationAmount is the amount of pheromone which is evaporating
	 * during one second.
	 * @param semantic is the semantic of the pheromone.
	 * @param color is the color of the pheromone.
	 */
	public Pheromone(float pheromoneIntensity, float evaporationAmount, Object semantic, Color color) {
		super(pheromoneIntensity, semantic);
		this.evaporationAmount = evaporationAmount;
		this.color = color;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Pheromone clone() {
		try {
			return (Pheromone)super.clone();
		}
		catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
	
	/** Replies the amount of pheromone which is evaporating during one second.
	 * 
	 * @return the amount of pheromone which is evaporating during one second.
	 */
	public final float getEvaporation() {
		return this.evaporationAmount;
	}

	/** Replies the intensity of the pheromone.
	 * 
	 * @return  the intensity of the pheromone.
	 */
	public final float getIntensity() {
		return getAmount().floatValue();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final Substance decrement(Substance s) {
		if (s instanceof Pheromone) {
			float oldValue = floatValue();
			decrement(s.floatValue());
			Pheromone p = clone();
			p.value = Math.abs(floatValue() - oldValue);
			return p;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final Substance increment(Substance s) {
		if (s instanceof Pheromone) {
			float oldValue = floatValue();
			increment(s.floatValue());
			Pheromone p = clone();
			p.value = Math.abs(floatValue() - oldValue);
			return p;
		}
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void increment(float a) {
		super.increment(a);
		if (this.value>AntColonyConstants.MAX_PHEROMONE_AMOUNT)
			this.value = AntColonyConstants.MAX_PHEROMONE_AMOUNT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Influence runAutonomousEndogenousProcess(
			float currentTime,
			float simulationStepDuration) {
		decrement(simulationStepDuration, this.evaporationAmount);
		if (floatValue()<=0f) {
			return createRemovalInfluenceForItself();
		}
		return null;
	}
	
	/** Replies the color associated to this pheromone.
	 * @return the color associated to this pheromone.
	 */
	public Color getColor() {
		float d = floatValue() / AntColonyConstants.MAX_PHEROMONE_AMOUNT;
		return new Color(
				(int)(this.color.getRed()*d),
				(int)(this.color.getGreen()*d),
				(int)(this.color.getBlue()*d));
	}
	
}