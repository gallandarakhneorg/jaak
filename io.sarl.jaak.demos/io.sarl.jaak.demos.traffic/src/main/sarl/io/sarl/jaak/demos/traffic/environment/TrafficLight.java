package io.sarl.jaak.demos.traffic.environment;

import io.sarl.jaak.environment.perception.EnvironmentalObject;

import org.arakhne.afc.math.discrete.object2d.Point2i;

/** This class defines a traffic light.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class TrafficLight extends EnvironmentalObject implements Cloneable {

	private static final long serialVersionUID = 7871995342349359810L;

	private TrafficLightState state;

	/**
	 * @param state the state of the traffic light.
	 */
	TrafficLight() {
		super(TrafficLight.class);
		this.state = TrafficLightState.DANGER;
	}
	
	@Override
	public String toString() {
		Point2i p = getPosition();
		return "x=" + p.x()
				+ "y=" + p.y()
				+ "s=" + this.state;
	}

	@Override
	public TrafficLight clone() {
		try {
			return (TrafficLight)super.clone();
		}
		catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	/** Replies the state of the traffic light.
	 * 
	 * @return the state of the traffic light.
	 */
	public TrafficLightState getState() {
		return this.state;
	}

	/** Replies the state of the traffic light.
	 * 
	 * @param state the state of the traffic light.
	 */
	void setState(TrafficLightState state) {
		assert (state != null);
		this.state = state;
	}

}