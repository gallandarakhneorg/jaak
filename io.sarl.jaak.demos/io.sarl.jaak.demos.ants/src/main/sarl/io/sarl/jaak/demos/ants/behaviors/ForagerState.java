package io.sarl.jaak.demos.ants.behaviors;

/** State of the foragers.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public enum ForagerState {
	/** The ant is searching food.
	 */
	SEARCH_FOOD,
	/** The ant is getting some food.
	 */
	PICK_UP_FOOD,
	/** The ant is returning to the colony.
	 */
	RETURN_TO_COLONY;
}
