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
package io.sarl.jaak.demos.ants;


/** Constants for the ant colony application.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public final class AntColonyConstants {

	/** Width of the ant colony environment.
	 */
	public static final int WIDTH = 200;

	/** Height of the ant colony environment.
	 */
	public static final int HEIGHT = 150;

	/** Max amount of food per food source.
	 */
	public static final int MAX_FOOD_PER_SOURCE = 1000;

	/** Max pheromone amount.
	 */
	public static final float MAX_PHEROMONE_AMOUNT = 10f;
		
	/** Number of food sources at start up.
	 */
	public static final int FOOD_SOURCES = 30;
	
	/** Number of ant colonies.
	 */
	public static final int ANT_COLONY_COUNT = 4;
	
	/** Number of patrollers in a new colony.
	 */
	public static final int ANT_COLONY_PATROLLER_POPULATION = 10;	

	/** Number of foragers in a new colony.
	 */
	public static final int ANT_COLONY_FORAGER_POPULATION = 50;	

	private AntColonyConstants() {
		//
	}
	
}