package io.sarl.jaak.demos.traffic.environment.physic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.arakhne.afc.math.discrete.object2d.Point2i;
import org.arakhne.afc.math.discrete.object2d.Tuple2iComparator;
import org.arakhne.afc.math.discrete.object2d.Vector2i;
import org.arakhne.afc.util.ListUtil;

/** A* Algorithm.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public final class Astar {

	private static final int TRAVEL_COST = 1;
	
	private static final CostComparator COST_COMPARATOR = new CostComparator();
	private static final PositionComparator POSITION_COMPARATOR = new PositionComparator();
	
	private Astar() {
		//
	}
		
	/** Find a path.
	 *
	 * @param from the start point.
	 * @param to the end point.
	 * @param vehicleDirection the direction of the vehicle.
	 * @return the path, except the start point.
	 */
	public static Path find(Point2i from, Point2i to, Vector2i vehicleDirection) {
		Node targetNode = searchPath(from, to, vehicleDirection);
		return buildPath(targetNode);
	}
	
	private static Path buildPath(Node node) {
		Path p = new Path();
		if (node != null) {
			Node n = node;
			do {
				p.add(0, n.getPosition());
				n = n.getPrevious();
			} while (n != null);
		}
		return p;
	}

//	public static final BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
//	public static JComponent imgRefresher;
//	
//	private static void writeImage(int w, int h, List<Node> openList, List<Node> closeList, Point2i target) {
//		synchronized(img) {
//			for (Node n : closeList) {
//				img.setRGB(n.getPosition().x(), n.getPosition().y(), Color.RED.getRGB());
//			}
//			for (Node n : openList) {
//				img.setRGB(n.getPosition().x(), n.getPosition().y(), Color.GREEN.getRGB());
//			}
//			img.setRGB(target.x(), target.y(), Color.YELLOW.getRGB());
//			if (imgRefresher != null) {
//				imgRefresher.repaint();
//			}
//		}
//	}
	
	private static Node searchPath(Point2i from, Point2i to, Vector2i vehicleDirection) {
		GroundType[][] world = MapUtil.getWorldModel();

		List<Node> openList = new ArrayList<>();
		List<Node> closeList = new LinkedList<>();
		if (isRoad(world, from.x(), from.y())) {
			Node n = new Node(from, vehicleDirection);
			n.setEstimatedCost(heuristic(from, to));
			openList.add(n);
		}
		
		while (!openList.isEmpty()) {
			Node cheapest = openList.remove(0);
			if (cheapest.getPosition().equals(to)) {
				// Found a path
				return cheapest;
			}
			ListUtil.add(closeList, POSITION_COMPARATOR, cheapest, false, false);
			for (Point2i n : getNeightbors(world, cheapest.getPosition(),
					cheapest.getPreviousVehicleDirection(),
					cheapest.getVehicleDirection())) {
				Node candidate = new Node(n);
				candidate.setCostToReach(cheapest.getCostToReach() + TRAVEL_COST);
				candidate.setEstimatedCost(heuristic(n, to));
				candidate.setPrevious(cheapest);
				if (!ListUtil.contains(closeList, POSITION_COMPARATOR, candidate)) {
					int oldIndex = openList.indexOf(candidate);
					if (oldIndex != -1) {
						Node old = openList.get(oldIndex);
						if (old.getGlobalCost() > candidate.getGlobalCost()) {
							// Better cost found
							openList.remove(oldIndex);
							ListUtil.add(openList, COST_COMPARATOR, candidate, false, false);
						}
					} else {
						ListUtil.add(openList, COST_COMPARATOR, candidate, false, false);
					}
				}
			}
		}
		
		// Path no found
		return null;
	}
	
	private static boolean isRoad(GroundType[][] world, int x, int y) {
		if (x >= 0 && x < world.length
			&& y >= 0 && y < world[x].length) {
			GroundType t = world[x][y];
			if (t != null) {
				return t.canDrive();
			}
		}
		return false;
	}
	
	private static float heuristic(Point2i from, Point2i to) {
		return (float) Math.sqrt(from.distanceSquared(to));
	}
	
	private static Iterable<Point2i> getNeightbors(GroundType[][] world, Point2i position,
			Vector2i previousVehicleDirection, Vector2i vehicleDirection) {
		Map<Vector2i, Serializable> entries = new TreeMap<>(new Tuple2iComparator());
		for (int i = position.x() - 2; i <= position.x() + 2; ++i) {
			for (int j = position.y() - 2; j <= position.y() + 2; ++j) {
				if (i >= 0 && i < world.length && j >= 0 && j < world[i].length && world[i][j] != null) {
					Vector2i v = new Vector2i(i, j);
					v.sub(position.x(), position.y());
					entries.put(v, world[i][j]);
				}
			}
		}
		//
		List<Vector2i> directions = MapUtil.detectDirections(position, vehicleDirection, previousVehicleDirection, entries);
		//
		List<Point2i> points = new ArrayList<>(directions.size());
		for (Vector2i v : directions) {
			points.add(new Point2i(position.x() + v.x(), position.y() + v.y()));
		}
		return points;
	}

	/**
	 * 
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	public static class Node {

		private final Vector2i vehicleDirection;
		private final Point2i position;
		private float costToReach = 0;
		private float estimatedCost = 0;
		private Node previous;
		
		/**
		 * @param position the position of the node.
		 * @param vehicleDirection the direction of the vehicle.
		 */
		public Node(Point2i position, Vector2i vehicleDirection) {
			this.position = position;
			this.vehicleDirection = vehicleDirection;
		}

		/**
		 * @param position the position of the node.
		 */
		public Node(Point2i position) {
			this.position = position;
			this.vehicleDirection = null;
		}
		
		@Override
		public String toString() {
			return this.position.toString() + " - (" + getCostToReach() + "+" + getEstimatedCost() + ")=" + getGlobalCost();
		}
		
		/** Replies the position of the node.
		 *
		 * @return the position of the node.
		 */
		public Point2i getPosition() {
			return this.position;
		}
		
		/** Replies the previous node.
		 * 
		 * @return the previous node.
		 */
		public Node getPrevious() {
			return previous;
		}

		/** Replies the vehicle direction.
		 * 
		 * @return the vehicle direction.
		 */
		public Vector2i getVehicleDirection() {
			if (this.vehicleDirection != null) {
				return this.vehicleDirection;
			}
			Vector2i v = new Vector2i();
			if (this.previous != null) {
				v.sub(getPosition(), this.previous.getPosition());
				return v;
			}
			return null;
		}
		
		/** Replies the previous vehicle direction.
		 * 
		 * @return the previous vehicle direction.
		 */
		public Vector2i getPreviousVehicleDirection() {
			if (this.vehicleDirection != null) {
				return this.vehicleDirection;
			}
			return this.previous.getVehicleDirection();
		}

		/** Change the previous node.
		 *
		 * @param previous the previous node.
		 */
		public void setPrevious(Node previous) {
			this.previous = previous;
		}
		
		/** Replies the cost to reach.
		 * 
		 * @return the cost to reach.
		 */
		public float getCostToReach() {
			return costToReach;
		}
		
		/** Change the cost to reach.
		 * 
		 * @param costToReach the cost to reach.
		 */
		public void setCostToReach(float costToReach) {
			this.costToReach = costToReach;
		}
		
		/** Replies the estimated cost.
		 * 
		 * @return the estimated cost.
		 */
		public float getEstimatedCost() {
			return estimatedCost;
		}
		
		/** Change the estimated cost.
		 * 
		 * @param estimated cost the estimated cost.
		 */
		public void setEstimatedCost(float estimatedCost) {
			this.estimatedCost = estimatedCost;
		}
		
		/** Replies the global cost of the node.
		 * 
		 * @return the global cost.
		 */
		public float getGlobalCost() {
			return getCostToReach() + getEstimatedCost();
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Node) {
				return POSITION_COMPARATOR.compare(this, (Node) obj) == 0;
			}
			return false;
		}
		
		@Override
		public int hashCode() {
			return this.position.hashCode();
		}

	}

	/**
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	private static class CostComparator implements Comparator<Node> {

		private final PositionComparator positionComparator = new PositionComparator();
		
		/**
		 */
		public CostComparator() {
			//
		}
		
		@Override
		public int compare(Node a, Node b) {
			if (a == b) {
				return 0;
			}
			if (a == null) {
				return Integer.MIN_VALUE;
			}
			if (b == null) {
				return Integer.MAX_VALUE;
			}
			int cmp = Float.compare(a.getGlobalCost(), b.getGlobalCost());
			if (cmp != 0) {
				return cmp;
			}
			return this.positionComparator.compare(a, b);
		}

	}

	/**
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	private static class PositionComparator implements Comparator<Node> {

		/**
		 */
		public PositionComparator() {
			//
		}
		
		@Override
		public int compare(Node a, Node b) {
			if (a == b) {
				return 0;
			}
			if (a == null) {
				return Integer.MIN_VALUE;
			}
			if (b == null) {
				return Integer.MAX_VALUE;
			}
			Point2i p1 = a.getPosition();
			Point2i p2 = b.getPosition();
			int cmp = p1.x() - p2.x();
			if (cmp != 0) {
				return cmp;
			}
			return p1.y() - p2.y();
		}

	}

}