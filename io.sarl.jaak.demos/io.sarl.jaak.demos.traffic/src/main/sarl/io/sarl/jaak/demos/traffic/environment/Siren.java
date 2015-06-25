package io.sarl.jaak.demos.traffic.environment;

import io.sarl.jaak.demos.traffic.TrafficConstants;


/** This class defines a siren sound.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class Siren extends Sound {

	private static final long serialVersionUID = -7159194278058336621L;

	/**
	 * @param soundSource the position of the sound source.
	 */
	public Siren() {
		super(TrafficConstants.SIREN_RADIUS, Siren.class);
	}

}