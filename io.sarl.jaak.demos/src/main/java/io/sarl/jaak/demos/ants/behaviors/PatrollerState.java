package io.sarl.jaak.demos.ants.behaviors;

/** State of the foragers.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public enum PatrollerState {
	/** The ant is pratolling.
	 */
	PATROL,
	/** The ant is returning to the colony.
	 */
	RETURN_TO_COLONY;
}
