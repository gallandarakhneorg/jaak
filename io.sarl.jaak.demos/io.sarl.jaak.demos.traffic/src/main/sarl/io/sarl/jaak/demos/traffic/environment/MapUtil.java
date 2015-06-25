package io.sarl.jaak.demos.traffic.environment;

import io.sarl.jaak.demos.traffic.TrafficConstants;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import org.arakhne.afc.math.discrete.object2d.Point2i;
import org.arakhne.afc.math.discrete.object2d.Rectangle2i;
import org.arakhne.afc.math.discrete.object2d.Tuple2iComparator;
import org.arakhne.afc.math.discrete.object2d.Vector2i;
import org.arakhne.afc.vmutil.Resources;

/** Utilities for path.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class MapUtil {

	/** Color for the roads.
	 */
	public static final int COLOR_ROAD = 0x000000;
	
	/** Color where emergency vehicles are spawned.
	 */
	public static final int COLOR_EMERGENCY_SPAWNER = 0xffffff;

	/** Color for the roads where an urgency event is located.
	 */
	public static final int COLOR_EMERGENCY_LOCATION = 0xffff00;

	/** Color where agent destroyers are located.
	 */
	public static final int COLOR_AGENT_DESTROYER = 0x00007f;

	/** Color where agent spawners are located.
	 */
	public static final int COLOR_AGENT_SPAWNER = 0x0000ff;

	private static List<Point2i> crashPositions;
	private static GroundType[][] worldModel;
	private static Map<Point2i, Integer> trafficLights;
	private static List<Point2i> standardVehicleSpawners;
	private static List<Point2i> emergencyVehicleSpawners;

	private MapUtil() {
		//
	}

	/** Replies the available direction from the given cell.
	 * 
	 * @param position the position of the cell.
	 * @param orientation the orientation of the vehicle.
	 * @param previousOrientation the previous orientation of the vehicle.
	 * @param ground the ground perception.
	 * @return the available directions.
	 */
	public static List<Vector2i> detectDirections(
			Point2i position,
			Vector2i orientation, Vector2i previousOrientation,
			Map<Vector2i, Serializable> ground) {
		List<Vector2i> availableDirections = new ArrayList<>();

		Serializable myCell = ground.get(new Vector2i());
		if (myCell == GroundType.CROSS_ROAD) {
			// Test forward
			Vector2i forward = orientation.clone();
			boolean canGoForward = canGoDirection(ground, forward);

			// Go left
			Vector2i left = orientation.clone();
			left.perpendicularize();
			Vector2i back = orientation.clone();
			back.negate();
			Vector2i v = new Vector2i();
			v.add(left, back);
			boolean canGoLeft = (isSimpleRoad(ground.get(back)) && !isRoad(ground.get(v)) && canGoDirection(ground, left));

			// Go right
			Vector2i right1 = left.clone();
			right1.negate();
			Vector2i right2 = right1.clone();
			right2.scale(2);
			boolean canGoRight = false;

			if (canGoLeft) {
				// Enabling forward only if go right will be available
				if (!canGoForward) {
					Vector2i futureRight = new Vector2i();
					futureRight.add(right1, orientation);
					if (isSimpleRoad(ground.get(futureRight))) {
						canGoForward = isRoad(ground.get(orientation));
					} else {
						futureRight.add(right2, orientation);
						if (isSimpleRoad(ground.get(futureRight))) {
							canGoForward = isRoad(ground.get(orientation));
						}
					}
				}
			} else if (previousOrientation != null
					&& (previousOrientation.x() != -right1.x() || previousOrientation.x() != -right1.x())) {
				// Test right
				v = new Vector2i();
				v.add(left, orientation);
				Vector2i v2 = new Vector2i();
				v2.add(right1, orientation);
				Vector2i v3 = new Vector2i();
				v3.add(right2, orientation);
				canGoRight = (!isRoad(ground.get(v)) && 
						((isSimpleRoad(ground.get(right1)) && (!isRoad(ground.get(v2))))
								|| (isSimpleRoad(ground.get(right2))) && (!isRoad(ground.get(v3))))
								&& canGoDirection(ground, right1));
				if (!canGoRight) {
					// Enabling forward only if go right will be available
					if (!canGoForward) {
						Vector2i futureRight = new Vector2i();
						futureRight.add(right1, orientation);
						if (isSimpleRoad(ground.get(futureRight))) {
							canGoForward = isRoad(ground.get(orientation));
						} else {
							futureRight.add(right2, orientation);
							if (isSimpleRoad(ground.get(futureRight))) {
								canGoForward = isRoad(ground.get(orientation));
							}
						}
					}
				}
			}		

			// Go
			if (canGoLeft) {
				left.normalize();
				availableDirections.add(left);
			}
			if (canGoRight) {
				right1.normalize();
				availableDirections.add(right1);
			}
			if (canGoForward) {
				forward.normalize();
				availableDirections.add(forward);
			}
		} else if (isRoad(ground.get(orientation))) {
			availableDirections.add(orientation.clone());
		}

		return availableDirections;
	}

	private static boolean canGoDirection(Map<Vector2i, Serializable> ground, Vector2i direction) {
		Vector2i forward = direction;
		int i = 1;
		while (ground.get(forward) == GroundType.CROSS_ROAD) {
			i++;
			forward = new Vector2i(i * direction.x(), i * direction.y());
		}
		return (isRoad(ground.get(forward)));
	}

	private static boolean isRoad(Serializable s) {
		if (s instanceof GroundType) {
			return ((GroundType) s).canDrive();
		}
		return false;
	}

	private static boolean isSimpleRoad(Serializable s) {
		if (s instanceof GroundType) {
			GroundType gt = (GroundType) s;
			return gt.canDrive() && gt != GroundType.CROSS_ROAD;
		}
		return false;
	}	

	/** Normalize the given point to fit the world map.
	 *
	 * @param position the position.
	 * @return the normalized position.
	 */
	public static Point2i normalizePoint(Point2i position) {
		GroundType[][] world = getWorldModel();
		Rectangle2i r = new Rectangle2i(0, 0, world.length - 1, world[0].length - 1);
		Point2i closest = r.getClosestPointTo(position);
		if (isDestroyer(world, closest.x(), closest.y())) {
			return closest;
		}

		Point2i p1 = seachClosestVerticalDestroyer(world, r.getMinX(), closest.y());
		float dist = (p1 == null) ? Float.POSITIVE_INFINITY : position.distance(p1);
		
		Point2i p2 = seachClosestVerticalDestroyer(world, r.getMaxX(), closest.y());
		float d = (p2 == null) ? Float.POSITIVE_INFINITY : position.distance(p2);
		if (d < dist) {
			dist = d;
			p1 = p2;
		}

		p2 = seachClosestHorizontalDestroyer(world, closest.x(), r.getMinY());
		d = (p2 == null) ? Float.POSITIVE_INFINITY : position.distance(p2);
		if (d < dist) {
			dist = d;
			p1 = p2;
		}

		p2 = seachClosestHorizontalDestroyer(world, closest.x(), r.getMaxY());
		d = (p2 == null) ? Float.POSITIVE_INFINITY : position.distance(p2);
		if (d < dist) {
			dist = d;
			p1 = p2;
		}
		
		assert (p1 != null);
		return p1; 
	}

	private static boolean isValid(GroundType[][] world, int x, int y) {
		return (x >= 0 && x < world.length && y >= 0 && y < world[x].length);
	}
	
	private static Point2i seachClosestVerticalDestroyer(GroundType[][] world, int x, int y) {
		int i = 1;
		while (isValid(world, x, y - i) || isValid(world, x, y + i)) {
			if (isDestroyer(world, x, y + i)) {
				return new Point2i(x, y + i);
			}
			if (isDestroyer(world, x, y - i)) {
				return new Point2i(x, y - i);
			}
			++i;
		}
		return null;
	}

	private static Point2i seachClosestHorizontalDestroyer(GroundType[][] world, int x, int y) {
		int i = 1;
		while (isValid(world, x - i, y) || isValid(world, x + i, y)) {
			if (isDestroyer(world, x + i, y)) {
				return new Point2i(x + i, y);
			}
			if (isDestroyer(world, x - i, y)) {
				return new Point2i(x - i, y);
			}
			++i;
		}
		return null;
	}

	private static GroundType wmg(int x, int y) {
		if (x >= 0 && x < worldModel.length && y >= 0 && y < worldModel[x].length) {
			return worldModel[x][y];
		}
		return null;
	}
	
	private static boolean wms(int x, int y, GroundType type) {
		if (x >= 0 && x < worldModel.length && y >= 0 && y < worldModel[x].length) {
			worldModel[x][y] = type;
			return true;
		}
		return false;
	}

	/** Replies the positions of the crashs.
	 * 
	 * @return the positions.
	 */
	public static List<Point2i> getCrashPositions() {
		synchronized(Astar.class) {
			ensureWorldModel();
			return crashPositions;
		}
	}
	
	/** Replies the positions of the spawning for standard vehicles.
	 * 
	 * @return the positions.
	 */
	public static List<Point2i> getStandardVehicleSpawningPositions() {
		synchronized(Astar.class) {
			ensureWorldModel();
			return standardVehicleSpawners;
		}
	}

	/** Replies the positions of the spawning for emergency vehicles.
	 * 
	 * @return the positions.
	 */
	public static List<Point2i> getEmergencyVehicleSpawningPositions() {
		synchronized(Astar.class) {
			ensureWorldModel();
			return emergencyVehicleSpawners;
		}
	}

	/** Read the model of the map.
	 *
	 * @return the ground types.
	 */
	public static GroundType[][] getWorldModel() {
		synchronized(Astar.class) {
			ensureWorldModel();
			return worldModel;
		}
	}
	
	/** Read the model of the map and replies the traffic lights.
	 *
	 * @return the traffic lights.
	 */
	public static Map<Point2i, Integer> getTrafficLights() {
		synchronized(Astar.class) {
			ensureWorldModel();
			return trafficLights;
		}
	}

	private static void ensureWorldModel() {
		if (worldModel == null || trafficLights == null || crashPositions == null || standardVehicleSpawners == null || emergencyVehicleSpawners == null) {
			URL mapUrl = Resources.getResource(TrafficConstants.class, "map.png");
			try {
				BufferedImage img = ImageIO.read(mapUrl);
				worldModel = new GroundType[img.getWidth()][img.getHeight()];
				trafficLights = new TreeMap<>(new Tuple2iComparator());
				crashPositions = new ArrayList<>();
				standardVehicleSpawners = new ArrayList<>();
				emergencyVehicleSpawners = new ArrayList<>();
				
				for (int x = 0; x < img.getWidth(); ++x) {
					for (int y = 0; y < img.getHeight(); ++y) {
						int pixel = img.getRGB(x, y);
						Color col = normalizeColor(pixel);
						switch (col.getRGB()) {
						case COLOR_ROAD:
							wms(x, y, GroundType.ROAD);
							break;
						case COLOR_EMERGENCY_SPAWNER:
							wms(x, y, GroundType.ROAD);
							emergencyVehicleSpawners.add(new Point2i(x, y));
							break;
						case COLOR_AGENT_SPAWNER:
							wms(x, y, GroundType.ROAD);
							standardVehicleSpawners.add(new Point2i(x, y));
							break;
						case COLOR_AGENT_DESTROYER:
							wms(x, y, GroundType.AGENT_DESTROYER);
							break;
						case COLOR_EMERGENCY_LOCATION:
							wms(x, y, GroundType.EMERGENCY_LOCATION);
							crashPositions.add(new Point2i(x, y));
							break;
						default:
							if (col.getGreen() == 0 && col.getBlue() == 0) {
								// Traffic lights are road parts
								if (wms(x, y, GroundType.ROAD)) {
									trafficLights.put(new Point2i(x, y), col.getRed());
								}
							}
						}
					}
				}
				// Detect cross roads
				//   Detect the lower point of the cross roads and fill the three other cells if possible
				for (int x = 0; x < img.getWidth(); ++x) {
					for (int y = 0; y < img.getHeight(); ++y) {
						boolean c = wmg(x, y) == GroundType.ROAD;
						if (c) {
							boolean t = isRoad(wmg(x, y - 1));
							boolean l = isRoad(wmg(x - 1, y));
							boolean b = isRoad(wmg(x, y + 1));
							boolean r = isRoad(wmg(x + 1, y));
							boolean tl = isRoad(wmg(x - 1, y - 1));
							boolean tr = isRoad(wmg(x + 1, y - 1));
							boolean bl = isRoad(wmg(x - 1, y + 1));
							boolean br = isRoad(wmg(x + 1, y + 1));
							if (t && l && !tl) {
								wms(x, y, GroundType.CROSS_ROAD);
								if (isRoad(wmg(x + 1, y))) {
									wms(x + 1, y, GroundType.CROSS_ROAD);
								}
								if (isRoad(wmg(x + 1, y + 1))) {
									wms(x + 1, y + 1, GroundType.CROSS_ROAD);
								}
								if (isRoad(wmg(x, y + 1))) {
									wms(x, y + 1, GroundType.CROSS_ROAD);
								}
							}
							if (t && r && !tr) {
								wms(x, y, GroundType.CROSS_ROAD);
								if (isRoad(wmg(x - 1, y))) {
									wms(x - 1, y, GroundType.CROSS_ROAD);
								}
								if (isRoad(wmg(x - 1, y + 1))) {
									wms(x - 1, y + 1, GroundType.CROSS_ROAD);
								}
								if (isRoad(wmg(x, y + 1))) {
									wms(x, y + 1, GroundType.CROSS_ROAD);
								}
							}
							if (b && l && !bl) {
								wms(x, y, GroundType.CROSS_ROAD);
								if (isRoad(wmg(x + 1, y))) {
									wms(x + 1, y, GroundType.CROSS_ROAD);
								}
								if (isRoad(wmg(x + 1, y - 1))) {
									wms(x + 1, y - 1, GroundType.CROSS_ROAD);
								}
								if (isRoad(wmg(x, y - 1))) {
									wms(x, y - 1, GroundType.CROSS_ROAD);
								}
							}
							if (b && r && !br) {
								wms(x, y, GroundType.CROSS_ROAD);
								if (isRoad(wmg(x - 1, y))) {
									wms(x - 1, y, GroundType.CROSS_ROAD);
								}
								if (isRoad(wmg(x - 1, y - 1))) {
									wms(x - 1, y - 1, GroundType.CROSS_ROAD);
								}
								if (isRoad(wmg(x, y - 1))) {
									wms(x, y - 1, GroundType.CROSS_ROAD);
								}
							}
						}
					}
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private static boolean isDestroyer(GroundType[][] world, int x, int y) {
		if (x >= 0 && x < world.length
			&& y >= 0 && y < world[x].length) {
			GroundType t = world[x][y];
			return (t == GroundType.AGENT_DESTROYER);
		}
		return false;
	}

	private static Color normalizeColor(int p) {
		Color c = new Color(p);
		return new Color(c.getRed(), c.getGreen(), c.getBlue(), 0);
	}

}