/* 
 * $Id$
 * 
 * Janus platform is an open-source multiagent platform.
 * More details on <http://www.janus-project.org>
 * Copyright (C) 2010-2012 Janus Core Developers
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.sarl.jaak.demos.traffic;


/** Constants for the traffic application.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public final class TrafficConstants {

	/** Budget of driver agents for a specific spawner.
	 */
	public static final int STANDARD_DRIVER_BUDGET = 25;

	/** Budget of urgency driver agents for a specific spawner.
	 */
	public static final int URGENCY_DRIVER_BUDGET = 1;

	/** Time at which the crash occurs.
	 */
	public static final int CRASH_TIME = 50;

	/** Duration of the green phase.
	 */
	public static final float GREEN_PHASE_DURATION = 40;

	/** Duration of the orange phase.
	 */
	public static final float ORANGE_PHASE_DURATION = 4;

	/** Radius of the siren sound.
	 */
	public static final int SIREN_RADIUS = 15;
	
	/** Decrease the sound intensity of the given amount at each simulation step.
	 */
	public static final int SOUND_DECREASE = 3;
	
	private TrafficConstants() {
		//
	}
	
}