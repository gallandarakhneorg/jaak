package io.sarl.jaak.demos.traffic.environment;

import io.sarl.jaak.environment.model.GridModel;
import io.sarl.jaak.environment.time.TimeManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.arakhne.afc.math.discrete.object2d.Point2i;
import org.arakhne.afc.math.discrete.object2d.Rectangle2i;
import org.arakhne.afc.math.discrete.object2d.Vector2i;

/** Group of traffic lights that are managed together.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public abstract class TrafficLightGroup implements Cloneable, Iterable<TrafficLight> {

	private final Rectangle2i position;
	private Map<Integer, Collection<TrafficLight>> lights = new TreeMap<>();
	private List<Integer> ids = new ArrayList<>();

	/**
	 * @param position
	 */
	public TrafficLightGroup(Rectangle2i position) {
		this.position = position;
	}

	/** Replies the position of the group.
	 *
	 * @return the position of the group.
	 */
	public Rectangle2i getPosition() {
		return this.position.clone();
	}

	@Override
	public String toString() {
		return "x=" + this.position.getMinX()
				+ ", y=" + this.position.getMinY()
				+ ", w=" + this.position.getWidth()
				+ ", h=" + this.position.getHeight()
				+ ", lights=" + this.lights;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TrafficLightGroup clone() {
		try {
			TrafficLightGroup group = (TrafficLightGroup)super.clone();
			group.ids = new ArrayList<>(this.ids);
			group.lights = new TreeMap<>();
			for (Entry<Integer, Collection<TrafficLight>> entry : this.lights.entrySet()) {
				Collection<TrafficLight> list = new ArrayList<>();
				group.lights.put(entry.getKey(), list);
				for (TrafficLight light : entry.getValue()) {
					list.add(light.clone());
				}
			}
			return group;
		}
		catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	/** Replies the indexes of the lights.
	 * 
	 * @return the indexes of the lights.
	 */
	protected List<Integer> getTrafficLightIdentifiers() {
		return this.ids;
	}

	/** Replies the traffic lights with the given index.
	 * 
	 * @param id is the identifier for the group of traffic lights.
	 * @return the lights, or <code>null</code>.
	 */
	protected Collection<TrafficLight> getTrafficLightsWithIdentifier(int index) {
		return this.lights.get(index);
	}

	/** Replies the traffic light in this group.
	 * 
	 * @return the traffic lights.
	 */
	public Map<Integer, Collection<TrafficLight>> getTrafficLightsPerIdentifier() {
		return Collections.unmodifiableMap(this.lights);
	}

	/** Create a traffic light in the given group.
	 * 
	 * @param group the id of the group.
	 * @return the traffic light.
	 */
	public TrafficLight createTrafficLight(int group) {
		Collection<TrafficLight> list = this.lights.get(group);
		if (list == null) {
			list = new ArrayList<>();
			this.lights.put(group, list);
		}
		TrafficLight light = new TrafficLight();
		list.add(light);
		return light;
	}

	/** Run the behavior of the traffic light group.
	 *
	 * @param currentTime
	 * @param simulationStepDuration
	 * @return the influence.
	 */
	public abstract TrafficLightInfluence runBehavior(GridModel grid, TimeManager timeManager);

	/** Initialize the group
	 */
	public void initialize() {
		assert (!this.lights.isEmpty());
		TrafficLightState state = TrafficLightState.PASSAGE_ALLOWED;
		this.ids.addAll(this.lights.keySet());
		for (Collection<TrafficLight> lights : this.lights.values()) {
			for (TrafficLight light : lights) {
				light.setState(state);
			}
			state = TrafficLightState.PASSAGE_FORBIDDEN;
		}
	}

	@Override
	public Iterator<TrafficLight> iterator() {
		return new TrafficLightIterator();
	}

	/** Replies the index of the traffic light that is facing the given position.
	 *
	 * @param position the position.
	 * @return the index of the group of <code>null</code>.
	 */
	protected Integer getGroupNumberOfTrafficLightFacing(Point2i position) {
		Rectangle2i crossRoadArea = getPosition();
		Point2i center = new Point2i(crossRoadArea.getCenterX(), crossRoadArea.getCenterY());
		
		Integer selectedGroup = null;
		if (!center.equals(position)) {
			Vector2i soundDirection = new Vector2i();
			soundDirection.sub(position, center);

			float angle = Float.MAX_VALUE;

			for (Entry<Integer, Collection<TrafficLight>> entry : this.lights.entrySet()) {
				for (TrafficLight light : entry.getValue()) {
					Vector2i lightDirection = new Vector2i();
					lightDirection.sub(light.getPosition(), center);
					float a = soundDirection.angle(lightDirection);
					if (a < angle) {
						angle = a;
						selectedGroup = entry.getKey();
					}
				}
			}
		}

		return selectedGroup;
	}

	/** Replies the cycle step associated to the given group number.
	 *
	 * @param groupNumber the number of the group of traffic lights.
	 * @return the cycle step for the given group number, or
	 * <code>-1</code> if the group number is unknown.
	 */
	protected int getCycleStepForGroupNumber(int groupNumber) {
		return this.ids.indexOf(groupNumber);
	}

	/**
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	private class TrafficLightIterator implements Iterator<TrafficLight> {

		private final Iterator<Collection<TrafficLight>> collections;
		private Iterator<TrafficLight> lights;
		private TrafficLight next;

		/**
		 */
		public TrafficLightIterator() {
			this.collections = getTrafficLightsPerIdentifier().values().iterator();
			searchNext();
		}

		private void searchNext() {
			this.next = null;
			while (this.next == null &&
					((this.lights != null && this.lights.hasNext())
							||((this.lights == null || !this.lights.hasNext()) && this.collections.hasNext()))) {
				if (this.lights == null || !this.lights.hasNext()) {
					assert (this.collections.hasNext());
					this.lights = this.collections.next().iterator();
				} else {
					this.next = this.lights.next();
				}
			}
		}

		@Override
		public boolean hasNext() {
			return this.next != null;
		}

		@Override
		public TrafficLight next() {
			assert (this.next != null);
			TrafficLight n = this.next;
			searchNext();
			return n;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

}