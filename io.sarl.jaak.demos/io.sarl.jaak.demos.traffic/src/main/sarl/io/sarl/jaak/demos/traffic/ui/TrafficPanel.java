package io.sarl.jaak.demos.traffic.ui;

import io.sarl.jaak.demos.traffic.behaviors.StandardDriver;
import io.sarl.jaak.demos.traffic.behaviors.UrgencyDriver;
import io.sarl.jaak.demos.traffic.environment.CrashedVehicle;
import io.sarl.jaak.demos.traffic.environment.GroundType;
import io.sarl.jaak.demos.traffic.environment.TrafficLight;
import io.sarl.jaak.environment.EnvironmentArea;
import io.sarl.jaak.environment.perception.EnvironmentalObject;
import io.sarl.jaak.kernel.JaakEvent;
import io.sarl.jaak.kernel.JaakListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.Iterator;

import javax.swing.JPanel;

/**
 * Graphic User Interface for the ant demo.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class TrafficPanel extends JPanel implements JaakListener {

	private static final long serialVersionUID = 4513244867249144515L;
	
	/** Color for grace
	 */
	public static final Color GRACE_COLOR = Color.LIGHT_GRAY;

	/** Color for roads.
	 */
	public static final Color ROAD_COLOR = Color.BLACK;

	/** Color for standard vehicles.
	 */
	public static final Color STANDARD_VEHICLE_COLOR = Color.BLUE;

	/** Color for urgency vehicles.
	 */
	public static final Color URGENCY_VEHICLE_COLOR = Color.YELLOW;

	/** Color for crash location.
	 */
	public static final Color CRASH_COLOR = Color.PINK;

	/** Colors for traffic lights.
	 */
	public static final Color[] TRAFFIC_LIGHT_COLORS = new Color[] {
		Color.GREEN,
		Color.ORANGE,
		Color.RED,
		Color.MAGENTA,
	};

	/** Size of a cell in pixels.
	 */
	public static final int CELL_SIZE = 5;

	private Color[][] grid = null;
	private int width = 0;
	private int height = 0;
	private float currentTime = 0f;
	
	private WeakReference<EnvironmentArea> environmentArea;
	
	/**
	 */
	public TrafficPanel() {
		setBackground(GRACE_COLOR);
	}
	
	private void setEnvironmentArea(EnvironmentArea area) {
		if (area == null) {
			this.environmentArea = null;
		} else {
			this.environmentArea = new WeakReference<>(area);
		}
	}
	
	/** Replies the environment area.
	 * 
	 * @return the environment area.
	 */
	protected EnvironmentArea getEnvironmentArea() {
		return (this.environmentArea == null) ? null : this.environmentArea.get();
	}

	@Override
	public synchronized void environmentStateChanged(JaakEvent event) {
		EnvironmentArea environment = event.getEnvironment();
		if (environment != null) {
			this.currentTime = event.getCurrentTime();
			Color[][] grid = this.grid;
			if (grid == null) {
				this.width = environment.getWidth();
				this.height = environment.getHeight();
				Dimension dim = new Dimension(
						this.width*CELL_SIZE + 10,
						this.height*CELL_SIZE + 10);
				setPreferredSize(dim);
				revalidate();
				grid = new Color[this.width][this.height];
				this.grid = grid;
			}
			EnvironmentalObject obj;
			Serializable semantic;
			Color c;
			for (int x = 0; x < this.width; ++x) {
				for (int y = 0; y < this.height; ++y) {
					c = null;
					semantic = environment.getTurtleSemantic(x, y);
					if (UrgencyDriver.class.equals(semantic)) {
						c = URGENCY_VEHICLE_COLOR;
					} else if (StandardDriver.class.equals(semantic)) {
						c = STANDARD_VEHICLE_COLOR;
					} else {
						Iterable<EnvironmentalObject> objects = environment.getEnvironmentalObjects(x, y);
						if (objects != null) {
							Iterator<EnvironmentalObject> objectIterator = objects.iterator();
							while ((c == null || c == ROAD_COLOR) && objectIterator.hasNext()) {
								obj = objectIterator.next();
								semantic = obj.getSemantic();
								if (TrafficLight.class.equals(semantic)) {
									TrafficLight light = (TrafficLight) obj;
									c = TRAFFIC_LIGHT_COLORS[light.getState().ordinal()];
								} else if (CrashedVehicle.class.equals(semantic)) {
									c = CRASH_COLOR;
								}
							}
						}
						if (c == null) {
							semantic = environment.getGroundType(x, y);
							if (semantic instanceof GroundType) {
								GroundType type = (GroundType) semantic;
								switch(type) {
								case ROAD:
								case URGENCY_LOCATION:
								case AGENT_DESTROYER:
									c = ROAD_COLOR;
									break;
								default:
								}
							}
							if (c == null) {
								c = GRACE_COLOR;
							}
						}
					}
					grid[x][y] = c;
				}
			}
			repaint();
		}
	}

	@Override
	public void simulationStarted(JaakEvent event) {
		setEnvironmentArea(event.getEnvironment());
		repaint();
	}

	@Override
	public void simulationStopped(JaakEvent event) {
		this.grid = null;
		this.width = this.height = 0;
		setEnvironmentArea(null);
		repaint();
	}
	
	/** Convert a x coordinate from the simulation coordinate system to the screen.
	 *
	 * @param x the coordinate in the simulation.
	 * @return the coordinate on the screen.
	 */
	protected static int simu2screen_x(int x) {
		return x * CELL_SIZE + 5;
	}
	
	/** Convert a y coordinate from the simulation coordinate system to the screen.
	 *
	 * @param y the coordinate in the simulation.
	 * @return the coordinate on the screen.
	 */
	protected static int simu2screen_y(int y) {
		return y * CELL_SIZE + 5;
	}

	/** Convert a x coordinate from the screen to the simulation coordinate system.
	 *
	 * @param x the coordinate on the screen.
	 * @return the coordinate in the simulation.
	 */
	protected static int screen2simu_x(int x) {
		return ((x - 5) / CELL_SIZE); 
	}
	
	/** Convert a y coordinate from the screen to the simulation coordinate system.
	 *
	 * @param y the coordinate on the screen.
	 * @return the coordinate in the simulation.
	 */
	protected static int screen2simu_y(int y) {
		return ((y - 5) / CELL_SIZE); 
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void paint(Graphics g) {
		super.paint(g);
		if (this.grid != null) {
			for(int x=0; x<this.width; ++x) {
				for(int y=0; y<this.height; ++y) {
					if (this.grid[x][y]!=null) {
						g.setColor(this.grid[x][y]);
						g.fillRect(simu2screen_x(x), simu2screen_y(y), CELL_SIZE, CELL_SIZE);
					}
				}
			}
		}

		g.setColor(Color.WHITE);
		g.drawRect(simu2screen_x(0), simu2screen_y(0), this.width*CELL_SIZE, this.height*CELL_SIZE);
		
		g.drawString(new DecimalFormat("####0.0").format(this.currentTime), 5, 15);
	}

}
