package io.sarl.jaak.demos.ants.ui;

import io.sarl.jaak.demos.ants.AntColonyConstants;
import io.sarl.jaak.demos.ants.environment.Food;
import io.sarl.jaak.demos.ants.environment.Pheromone;
import io.sarl.jaak.environment.EnvironmentArea;
import io.sarl.jaak.environment.body.BodySpawner;
import io.sarl.jaak.environment.perception.EnvironmentalObject;
import io.sarl.jaak.kernel.JaakEvent;
import io.sarl.jaak.kernel.JaakListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JPanel;

import org.arakhne.afc.math.discrete.object2d.Point2i;

/**
 * Graphic User Interface for the ant demo.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class AntPanel extends JPanel implements JaakListener, MouseListener {

	private static final long serialVersionUID = 4513244867249144515L;
	
	/** Color for mobile ants.
	 */
	public static final Color MOBILE_ANT_COLOR = Color.GREEN;

	/** Color for mobile ants.
	 */
	public static final Color IMMOBILE_ANT_COLOR = Color.YELLOW;

	/** Color for colonies.
	 */
	public static final Color COLONY_COLOR = Color.WHITE;

	/** Size of a cell in pixels.
	 */
	public static final int CELL_SIZE = 4;

	private Color[][] grid = null;
	private int[] bases = null;
	private int radarLength = 0;
	private int width = 0;
	private int height = 0;
	
	private int mx = -1;
	private int my = -1;
	private float speed = Float.NaN;
	
	private WeakReference<EnvironmentArea> environmentArea;
	
	/**
	 */
	public AntPanel() {
		setBackground(Color.BLACK);
		addMouseListener(this);
	}
	
	private void setEnvironmentArea(EnvironmentArea area) {
		if (area == null) {
			this.environmentArea = null;
		} else {
			this.environmentArea = new WeakReference<>(area);
		}
	}
	
	private EnvironmentArea getEnvironmentArea() {
		return (this.environmentArea == null) ? null : this.environmentArea.get();
	}

	/** Replies the color which is corresponding to the given amount
	 * of food.
	 * 
	 * @param amount is the amount of food
	 * @return the color for food
	 */
	public static Color makeFoodColor(int amount) {
		if (amount<=0) return null;
		int n = 55 + (amount * 200) / AntColonyConstants.MAX_FOOD_PER_SOURCE;
		return new Color(n, 0, 0);
	}

	/** Replies the color which is corresponding to the given amount
	 * of pheromones.
	 * 
	 * @param pheromoneColor is the color of pheromones
	 * @param foodAmount is the amount of food
	 * @return the color for pheromone
	 */
	public static Color makePheromoneFoodColor(Color pheromoneColor, int foodAmount) {
		int red = 0;
		if (foodAmount>0) {
			red = 55 + (foodAmount * 200) / AntColonyConstants.MAX_FOOD_PER_SOURCE;
		}
		
		red += pheromoneColor.getRed();
		if (red<0) red = 0;
		else if (red>255) red = 255;

		return new Color(red, pheromoneColor.getGreen(), pheromoneColor.getRed());
	}

	@Override
	public synchronized void environmentStateChanged(JaakEvent event) {
		EnvironmentArea environment = event.getEnvironment();
		assert(environment != null);
		if (this.grid==null) {
			this.width = environment.getWidth();
			this.height = environment.getHeight();
			Dimension dim = new Dimension(
					this.width*CELL_SIZE + 10,
					this.height*CELL_SIZE + 10);
			setPreferredSize(dim);
			revalidate();
			this.grid = new Color[this.width][this.height];
		}
		Iterable<EnvironmentalObject> iterable;
		Iterator<EnvironmentalObject> iterator;
		EnvironmentalObject obj;
		Food food;
		Color c;
		float speed;
		for(int x=0; x<this.width; ++x) {
			for(int y=0; y<this.height; ++y) {
				speed = environment.getTurtleSpeed(x,y);
				if (!Float.isNaN(speed)) {
					if (speed>0f) {
						c = MOBILE_ANT_COLOR;
					}
					else {
						c = IMMOBILE_ANT_COLOR;
					}
				}
				else {
					iterable = environment.getEnvironmentalObjects(x, y);
					c = null;
					if (iterable!=null) {
						iterator = iterable.iterator();
						int nbElts = 0;
						Color pc;
						food = null;
						int cr = 0;
						int cg = 0;
						int cb = 0;
						while (iterator.hasNext()) {
							obj = iterator.next();
							if (obj instanceof Pheromone) {
								pc = ((Pheromone)obj).getColor();
								cr = Math.max(pc.getRed(), cr);
								cg = Math.max(pc.getGreen(), cg);
								cb = Math.max(pc.getBlue(), cb);
								++nbElts;
							}
							else if (obj instanceof Food && food==null) {
								food = (Food)obj;
							}
						}
						if (nbElts>0) {
							c = new Color(cr, cg, cb);
						}
						if (c!=null && food!=null) {
							c = makePheromoneFoodColor(c, food.intValue());
						}
						else if (food!=null) {
							c = makeFoodColor(food.intValue());
						}
					}
				}
				if (c==null) c = getBackground();
				this.grid[x][y] = c;
			}
		}
		if (this.bases==null) {
			Collection<BodySpawner> spawners = event.getBodySpawners();
			this.bases = new int[spawners.size()*2];
			Iterator<BodySpawner> ptIterator = spawners.iterator();
			for(int i=0; i<this.bases.length; i+=2) {
				BodySpawner s = ptIterator.next();
				Point2i p = s.getReferenceSpawningPosition();
				this.bases[i] = p.x();
				this.bases[i+1] = p.y();
			}
		}
		repaint();
	}

	@Override
	public void simulationStarted(JaakEvent event) {
		setEnvironmentArea(event.getEnvironment());
		repaint();
	}

	@Override
	public void simulationStopped(JaakEvent event) {
		this.bases = null;
		this.grid = null;
		this.width = this.height = 0;
		setEnvironmentArea(null);
		repaint();
	}
	
	private static int simu2screen_x(int x) {
		return x * CELL_SIZE + 5;
	}
	
	private static int simu2screen_y(int y) {
		return y * CELL_SIZE + 5;
	}

	private static int screen2simu_x(int x) {
		return ((x - 5) / CELL_SIZE); 
	}
	
	private static int screen2simu_y(int y) {
		return ((y - 5) / CELL_SIZE); 
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void paint(Graphics g) {
		super.paint(g);
		
		if (this.grid!=null) {
			for(int x=0; x<this.width; ++x) {
				for(int y=0; y<this.height; ++y) {
					if (this.grid[x][y]!=null) {
						g.setColor(this.grid[x][y]);
						g.fillRect(simu2screen_x(x), simu2screen_y(y), CELL_SIZE, CELL_SIZE);
					}
				}
			}
		}
		if (this.bases!=null) {
			g.setColor(COLONY_COLOR);
			int x, y;
			int s = this.radarLength*2 + 1;
			for(int i=0; i<this.bases.length-1; i+=2) {
				x = this.bases[i];
				y = this.bases[i+1];
				g.fillRect(simu2screen_x(x), simu2screen_y(y), CELL_SIZE, CELL_SIZE);
				if (this.radarLength>0) {
					g.drawOval(
							simu2screen_x(x-this.radarLength),
							simu2screen_y(y-this.radarLength),
							s*CELL_SIZE, s*CELL_SIZE);
				}
			}
			this.radarLength = (this.radarLength + 1) % 15;
		}

		g.setColor(Color.WHITE);
		g.drawRect(simu2screen_x(0), simu2screen_y(0), this.width*CELL_SIZE, this.height*CELL_SIZE);
		
		int mouseX = this.mx;
		int mouseY = this.my;
		float s = this.speed;
		if (!Float.isNaN(s)) {
			String speedTxt = Float.toString(s)+"c/s"; //$NON-NLS-1$
			g.drawString(speedTxt, mouseX, mouseY);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		this.mx = e.getX();
		this.my = e.getY();
		
		EnvironmentArea environment = getEnvironmentArea();
		if (environment != null) {
			int sx = screen2simu_x(this.mx);
			int sy = screen2simu_y(this.my);
	
			try {
				this.speed = environment.getTurtleSpeed(sx, sy);
			}
			catch(Throwable exception) {
				this.speed = Float.NaN;
			}
		} else {
			this.speed = Float.NaN;
		}
		repaint();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		this.speed = Float.NaN;
		repaint();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		this.speed = Float.NaN;
		repaint();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		//
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		//
	}

}
