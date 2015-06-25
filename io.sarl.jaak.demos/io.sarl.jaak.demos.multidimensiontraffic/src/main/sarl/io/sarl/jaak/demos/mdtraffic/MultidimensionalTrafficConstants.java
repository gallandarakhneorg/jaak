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
package io.sarl.jaak.demos.mdtraffic;

import java.util.UUID;



/** Constants for the traffic application.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public final class MultidimensionalTrafficConstants {

	/** Id of the communication space for receiving the agent's messages.
	 */
	public static final UUID OUTPUT_COMMUNICATION_SPACE_ID = UUID.randomUUID();

	/** Id of the communication space for emiting messages to the agents.
	 */
	public static final UUID INPUT_COMMUNICATION_SPACE_ID = UUID.randomUUID();
	
	private MultidimensionalTrafficConstants() {
		//
	}
	
}