package io.sarl.jaak.demos.traffic.environment;

import io.sarl.jaak.environment.perception.EnvironmentalObject;

/** This class defines a crashed vehicle.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class CrashedVehicle extends EnvironmentalObject implements Cloneable {

	private static final long serialVersionUID = 6054486998707433064L;

	public CrashedVehicle() {
		super(CrashedVehicle.class);
	}

	@Override
	public CrashedVehicle clone() {
		try {
			return (CrashedVehicle)super.clone();
		}
		catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

}