package io.sarl.jaak.demos.traffic.ui;

import io.sarl.jaak.demos.traffic.TrafficConstants;
import io.sarl.jaak.demos.traffic.behaviors.EmergencyDriver;
import io.sarl.jaak.demos.traffic.behaviors.StandardDriver;
import io.sarl.jaak.demos.traffic.environment.CrashedVehicle;
import io.sarl.jaak.demos.traffic.environment.GroundType;
import io.sarl.jaak.demos.traffic.environment.Siren;
import io.sarl.jaak.demos.traffic.environment.Sound;
import io.sarl.jaak.demos.traffic.environment.TrafficLight;
import io.sarl.jaak.environment.EnvironmentArea;
import io.sarl.jaak.environment.perception.EnvironmentalObject;
import io.sarl.jaak.kernel.JaakEvent;
import io.sarl.jaak.kernel.JaakListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import org.arakhne.afc.math.MathUtil;
import org.arakhne.afc.math.discrete.object2d.Point2i;

/**
 * Graphic User Interface for the traffic demo.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class TrafficPanel extends JPanel implements TrafficPrinter, JaakListener, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 4513244867249144515L;
	
	/** Color for grace
	 */
	public static final Color GRACE_COLOR = new Color(63, 127, 63);

	/** Color for roads.
	 */
	public static final Color ROAD_COLOR = Color.BLACK;

	/** Color for roads.
	 */
	public static final Color CROSS_ROAD_COLOR = Color.DARK_GRAY;

	/** Color for standard vehicles.
	 */
	public static final Color STANDARD_VEHICLE_COLOR = Color.BLUE;

	/** Color for emergency vehicles.
	 */
	public static final Color EMERGENCY_VEHICLE_COLOR = Color.YELLOW;

	/** Color for crash location.
	 */
	public static final Color CRASH_COLOR = Color.PINK;

	private static final int CRASH_CIRCLE_RADIUS = 50;
	private static final int CRASH_CIRCLE_DIAMETER = CRASH_CIRCLE_RADIUS * 2;
	
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

	private final DecimalFormat floatFormat = new DecimalFormat("####0.0");
	
	private Color[][] grid = null;
	private int width = 0;
	private int height = 0;
	private float currentTime = 0f;
	private final List<Point2i> crashes = new ArrayList<>();
	
	private WeakReference<EnvironmentArea> environmentArea;
	
	private int cursorX = 0;
	private int cursorY = 0;
	
	/**
	 */
	public TrafficPanel() {
		setBackground(GRACE_COLOR);
	}
	
	private void setEnvironmentArea(EnvironmentArea area) {
		if (area == null) {
			this.environmentArea = null;
			removeMouseListener(this);
			removeMouseMotionListener(this);
		} else {
			this.environmentArea = new WeakReference<>(area);
			addMouseListener(this);
			addMouseMotionListener(this);
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
	public void environmentStateChanged(JaakEvent event) {
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
			
			this.crashes.clear();
			
			EnvironmentalObject obj;
			Serializable semantic;
			Color c;
			Color ac;
			for (int x = 0; x < this.width; ++x) {
				for (int y = 0; y < this.height; ++y) {
					ac = c = null;
					semantic = environment.getTurtleSemantic(x, y);
					if (EmergencyDriver.class.equals(semantic)) {
						c = EMERGENCY_VEHICLE_COLOR;
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
								} else if (Siren.class.equals(semantic)) {
									float v = ((Sound) obj).floatValue();
									if (v > 0f) {
										ac = getSoundColor(v);
									}
								}
							}
						}
						if (c == null) {
							semantic = environment.getGroundType(x, y);
							if (semantic instanceof GroundType) {
								GroundType type = (GroundType) semantic;
								switch(type) {
								case ROAD:
								case AGENT_DESTROYER:
									c = ROAD_COLOR;
									break;
								case EMERGENCY_LOCATION:
									this.crashes.add(new Point2i(x, y));
									c = ROAD_COLOR;
									break;
								case CROSS_ROAD:
									c = CROSS_ROAD_COLOR;
									break;
								default:
								}
							}
							if (c == null) {
								c = GRACE_COLOR;
							}
						}
					}
					if (ac != null) {
						c = composeColor(c, ac);
					}
					grid[x][y] = c;
				}
			}
		}
		repaint();
	}
	
	private static int composeAlpha(int a1, int a2) {
	    return 255 - ((255 - a2) * (255 - a1)) / 255;
	}


	private static int composeColorComponent(int c1, int a1, int c2, int a2, int a) {
	    if (a == 0) {
	        return 0x00;
	    }
	    return (((255 * c2 * a2) + (c1 * a1 * (255 - a2))) / a) / 255;
	}

	private static Color composeColor(Color argb1, Color argb2) {
		int a1 = argb1.getAlpha();
		int a2 = argb2.getAlpha();
	    int a = composeAlpha(a1, a2);
	    int r = composeColorComponent(argb1.getRed(), a1, argb2.getRed(), a2, a);
	    int g = composeColorComponent(argb1.getGreen(), a1, argb2.getGreen(), a2, a);
	    int b = composeColorComponent(argb1.getBlue(), a1, argb2.getBlue(), a2, a);
	    return new Color(r, g, b, a);
	}
	
	private static Color getSoundColor(float amount) {
		int v = (int) MathUtil.clamp((amount * 255) / TrafficConstants.SIREN_RADIUS, 0, 255);
		return new Color(v, v, v, 64);
	}

	@Override
	public void simulationStarted(JaakEvent event) {
		setEnvironmentArea(event.getEnvironment());
		repaint();
	}

	@Override
	public void simulationStopped(JaakEvent event) {
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
		Graphics2D g2d = (Graphics2D) g;

		if (this.grid != null) {
			for(int x=0; x<this.width; ++x) {
				for(int y=0; y<this.height; ++y) {
					if (this.grid[x][y]!=null) {
						g2d.setColor(this.grid[x][y]);
						g2d.fillRect(simu2screen_x(x), simu2screen_y(y), CELL_SIZE, CELL_SIZE);
					}
				}
			}
		}

		g2d.setColor(Color.WHITE);
		g2d.drawRect(simu2screen_x(0), simu2screen_y(0), this.width*CELL_SIZE, this.height*CELL_SIZE);
		
		StringBuilder txt = new StringBuilder();
		txt.append("t = ").append(this.floatFormat.format(this.currentTime));
		txt.append(", x = ").append(this.cursorX);
		txt.append(", y = ").append(this.cursorY);
		
		g2d.drawString(txt.toString(), simu2screen_x(0), CELL_SIZE * this.height + g.getFont().getSize() + 5);
		
		g2d.setColor(CRASH_COLOR);
		for (Point2i crashPosition : this.crashes) {
			g2d.drawOval(
					simu2screen_x(crashPosition.x()) - CRASH_CIRCLE_RADIUS,
					simu2screen_y(crashPosition.y()) - CRASH_CIRCLE_RADIUS,
					CRASH_CIRCLE_DIAMETER, CRASH_CIRCLE_DIAMETER);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//
	}

	@Override
	public synchronized void mouseEntered(MouseEvent e) {
		this.cursorX = screen2simu_x(e.getX());
		this.cursorY = screen2simu_x(e.getY());
		repaint();
	}

	@Override
	public synchronized void mouseExited(MouseEvent e) {
		this.cursorX = this.cursorY = -1;
		repaint();
	}

	@Override
	public synchronized void mouseDragged(MouseEvent e) {
		this.cursorX = screen2simu_x(e.getX());
		this.cursorY = screen2simu_x(e.getY());
		repaint();
	}

	@Override
	public synchronized void mouseMoved(MouseEvent e) {
		this.cursorX = screen2simu_x(e.getX());
		this.cursorY = screen2simu_x(e.getY());
		repaint();
	}

}
