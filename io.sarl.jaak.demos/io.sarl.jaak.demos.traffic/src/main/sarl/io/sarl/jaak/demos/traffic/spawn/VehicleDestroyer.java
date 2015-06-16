package io.sarl.jaak.demos.traffic.spawn;

import io.sarl.jaak.environment.perception.EnvironmentalObject;

/** This class defines a destroyer of vehicle.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class VehicleDestroyer extends EnvironmentalObject implements Cloneable {

	private static final long serialVersionUID = 3988316576311233677L;

	/**
	 */
	public VehicleDestroyer() {
		super(VehicleDestroyer.class);
	}

	@Override
	public VehicleDestroyer clone() {
		return (VehicleDestroyer) super.clone();
	}

}