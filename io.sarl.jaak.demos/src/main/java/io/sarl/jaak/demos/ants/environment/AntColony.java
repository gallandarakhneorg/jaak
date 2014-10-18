package io.sarl.jaak.demos.ants.environment;

import io.sarl.jaak.environment.external.perception.Burrow;

/** Defines an ant colony.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class AntColony extends Burrow {
	
	private static final long serialVersionUID = 1535270256418318232L;

	private final int colonyId;
	
	/**
	 * @param colonyId is the identifier of the colony.
	 */
	public AntColony(int colonyId) {
		this.colonyId = colonyId;
	}
	
	/** Replies the identifier of the colony.
	 * 
	 * @return the identifier of the colony.
	 */
	public int getColonyId() {
		return this.colonyId;
	}
	
}