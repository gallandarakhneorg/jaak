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

	/** Width of the traffic environment.
	 */
	public static final int WIDTH = 200;

	/** Height of the traffic environment.
	 */
	public static final int HEIGHT = 150;

	/** Budget of driver agents for a specific spawner.
	 */
	public static final int STANDARD_DRIVER_BUDGET = 1;

	/** Budget of urgency driver agents for a specific spawner.
	 */
	public static final int URGENCY_DRIVER_BUDGET = 0;

	/** Duration of the green phase.
	 */
	public static final float GREEN_PHASE_DURATION = 60;

	/** Duration of the orange phase.
	 */
	public static final float ORANGE_PHASE_DURATION = 5;

	/** Color for the roads.
	 */
	public static final int COLOR_ROAD = 0x000000;
	
	/** Color where urgency vehicles are spawned.
	 */
	public static final int COLOR_URGENCY_SPAWNER = 0xffffff;

	/** Color for the roads where an urgency event is located.
	 */
	public static final int COLOR_URGENCY_LOCATION = 0xffff00;

	/** Color where agent destroyers are located.
	 */
	public static final int COLOR_AGENT_DESTROYER = 0x00007f;

	/** Color where agent spawners are located.
	 */
	public static final int COLOR_AGENT_SPAWNER = 0x0000ff;

	private TrafficConstants() {
		//
	}
	
}