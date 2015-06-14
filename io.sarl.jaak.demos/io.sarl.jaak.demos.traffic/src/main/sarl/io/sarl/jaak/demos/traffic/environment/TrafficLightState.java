package io.sarl.jaak.demos.traffic.environment;


/** Traffic light state.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public enum TrafficLightState {
	/** Green.
	 */
	PASSAGE_ALLOWED,
	/** Orange.
	 */
	PASSAGE_DISCOURAGED,
	/** Red.
	 */
	PASSAGE_FORBIDDEN,
	/** Flashing orange.
	 */
	DANGER,
}
