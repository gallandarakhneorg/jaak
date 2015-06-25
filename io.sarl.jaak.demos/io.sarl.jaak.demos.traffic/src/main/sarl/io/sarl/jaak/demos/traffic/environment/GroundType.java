package io.sarl.jaak.demos.traffic.environment;


/** Types of ground.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public enum GroundType {
	/** Road.
	 */
	ROAD {
		@Override
		public boolean canDrive() {
			return true;
		}
	},

	/** Cross-road.
	 */
	CROSS_ROAD {
		@Override
		public boolean canDrive() {
			return true;
		}
	},

	/** Emergency location.
	 */
	EMERGENCY_LOCATION {
		@Override
		public boolean canDrive() {
			return true;
		}
	},

	/** Agent destroyer..
	 */
	AGENT_DESTROYER {
		@Override
		public boolean canDrive() {
			return true;
		}
	};
	
	/** Replies if a vehicle can go on the ground of this type.
	 *
	 * @return <code>true</code> if a vehicle can go on the ground.
	 */
	public abstract boolean canDrive();

}