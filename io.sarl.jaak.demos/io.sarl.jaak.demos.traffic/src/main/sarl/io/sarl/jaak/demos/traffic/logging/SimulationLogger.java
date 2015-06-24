package io.sarl.jaak.demos.traffic.logging;

import io.sarl.jaak.demos.traffic.environment.physic.GroundType;
import io.sarl.jaak.demos.traffic.util.Rectangle2iComparator;
import io.sarl.jaak.environment.EnvironmentArea;
import io.sarl.jaak.kernel.JaakEvent;
import io.sarl.jaak.kernel.JaakListener;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.nio.channels.IllegalSelectorException;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.arakhne.afc.math.discrete.object2d.Rectangle2i;
import org.arakhne.afc.vmutil.FileSystem;

/**
 * Log information from the jaak environment.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class SimulationLogger implements JaakListener {

	private static SimulationLogger LOGGER;
	
	/** Replies the simulation logger.
	 *
	 * @return the simulation logger.
	 */
	public static SimulationLogger getLogger() {
		synchronized(SimulationLogger.class) {
			if (LOGGER == null) {
				throw new IllegalSelectorException();
			}
			return LOGGER;
		}
	}
	
	/** Create the simulation logger.
	 *
	 * @param printer the UI printer linked to this simulation logger.
	 * @return the simulation logger.
	 */
	public static SimulationLogger createLogger(JPanel printer) {
		synchronized(SimulationLogger.class) {
			if (LOGGER == null) {
				LOGGER = new SimulationLogger(printer);
			}
			return LOGGER;
		}
	}

	private final File logFolder;
	private final File vehicleMapFolder;
	private final File countMapFolder;
	private final File densityMapFolder;
	private final File zoneFolder;

	private WeakReference<EnvironmentArea> environmentArea;

	private Zone[][] zones = null;

	private final JPanel printer;

	/**
	 * @param printer the object that is able to output an image of the environment.
	 */
	private SimulationLogger(JPanel printer) {
		try {
			this.logFolder = FileSystem.createTempDirectory("trafficSimulation", null);
			System.out.println("Logging into: " + this.logFolder);
		} catch (IOException e) {
			throw new IOError(e);
		}
		this.printer = printer;
		this.vehicleMapFolder = new File(this.logFolder, "vehicleMap");
		this.vehicleMapFolder.mkdirs();
		this.countMapFolder = new File(this.logFolder, "countMap");
		this.countMapFolder.mkdirs();
		this.densityMapFolder = new File(this.logFolder, "densityMap");
		this.densityMapFolder.mkdirs();
		this.zoneFolder = new File(this.logFolder, "zones");
		this.zoneFolder.mkdirs();
	}
	
	/** Replies the folder in which the logging data are written.
	 *
	 * @return the logging folder.
	 */
	public File getLoggingFolder() {
		return this.logFolder;
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
	public void environmentStateChanged(JaakEvent event) {
		EnvironmentArea environment = event.getEnvironment();
		if (environment != null) {
			try {
				float time = event.getCurrentTime();
				int width = environment.getWidth();
				int height = environment.getHeight();
	
				if (this.printer != null) {
					BufferedImage img = new BufferedImage(width * 5 + 10, height * 5 + 10, BufferedImage.TYPE_INT_ARGB);
					Graphics2D g = (Graphics2D) img.getGraphics();
					this.printer.paint(g);
					g.dispose();
					ImageIO.write(img, "png", new File(this.vehicleMapFolder, "t" + time + ".png"));
				}
	
				if (this.zones == null) {
					this.zones = new Zone[width][height];
					for (int x = 0; x < width; ++x) {
						for (int y = 0; y < height; ++y) {
							if (this.zones[x][y] == null) {
								Serializable t = environment.getGroundType(x, y);
								if (t instanceof GroundType) {
									GroundType type = (GroundType) t;
									Rectangle2i r = null;
									switch (type) {
									case ROAD:
									case AGENT_DESTROYER:
									case URGENCY_LOCATION:
										r = searchFor(environment, x, y, GroundType.ROAD,
												GroundType.AGENT_DESTROYER,
												GroundType.URGENCY_LOCATION);
										break;
									case CROSS_ROAD:
										r = searchFor(environment, x, y, GroundType.CROSS_ROAD);
										break;
									}
									if (r != null) {
										Zone z = new Zone(type, r);
										for (int i = r.getMinX(); i <= r.getMaxX(); ++i) {
											for (int j = r.getMinY(); j <= r.getMaxY(); ++j) {
												this.zones[i][j] = z;
											}
										}
									}
								}
							}
						}
					}
				}
	
				int maxDensity = 0;
				Map<Rectangle2i, ZoneData> densities = new TreeMap<>(new Rectangle2iComparator());
				for (int x = 0; x < width; ++x) {
					for (int y = 0; y < height; ++y) {
						if (this.zones[x][y] != null) {
							Rectangle2i r = this.zones[x][y].getPosition();
							ZoneData data = densities.get(r);
							if (data == null) {
								data = new ZoneData(this.zones[x][y]);
								densities.put(r, data);
							}
							int d = data.addDensity(environment.hasTurtle(x, y) ? 1 : 0);
							if (d > maxDensity) {
								maxDensity = d;
							}
						}
					}
				}
		
				BufferedImage vehicleCount = new BufferedImage(width * 5 + 10, height * 5 + 10, BufferedImage.TYPE_INT_ARGB);
				Graphics2D vehicleCountGraphics = (Graphics2D) vehicleCount.getGraphics();
				vehicleCountGraphics.scale(5, 5);
				vehicleCountGraphics.translate(1, 1);
				vehicleCountGraphics.setColor(Color.WHITE);
				vehicleCountGraphics.fillRect(-1, -1, width + 2, height + 2);
				
				BufferedImage vehicleDensity = new BufferedImage(width * 5 + 10, height * 5 + 10, BufferedImage.TYPE_INT_ARGB);
				Graphics2D vehicleDensityGraphics = (Graphics2D) vehicleDensity.getGraphics();
				vehicleDensityGraphics.scale(5, 5);
				vehicleDensityGraphics.translate(1, 1);
				vehicleDensityGraphics.setColor(Color.WHITE);
				vehicleDensityGraphics.fillRect(-1, -1, width + 2, height + 2);
				
				try (PrintWriter printWriter = new PrintWriter(new File(this.zoneFolder, "zones_" + time + ".csv"))) {
					printWriter.append("MIN_X\tMIN_Y\tMAX_X\tMAX_Y\tType\t# vehicles\tdensity\n");
					for (Entry<Rectangle2i, ZoneData> data : densities.entrySet()) {
						Rectangle2i r = data.getKey();
						ZoneData zd = data.getValue();
						Color c;
						if (maxDensity > 0) {
							c = new Color((zd.getDensity() * 255) / maxDensity, 0, 0);
						} else {
							c = Color.BLACK;
						}
						vehicleCountGraphics.setColor(c);
						vehicleCountGraphics.fillRect(r.getMinX(), r.getMinY(), r.getWidth() + 1, r.getHeight() + 1);
						//
						int cells = (r.getWidth() + 1) * (r.getHeight() + 1);
						float density = (float) zd.getDensity() / cells;
						vehicleDensityGraphics.setColor(new Color(Math.min(density, 1), 0, 0));
						vehicleDensityGraphics.fillRect(r.getMinX(), r.getMinY(), r.getWidth() + 1, r.getHeight() + 1);
						//
						printWriter.append(Float.toString(r.getMinX())).append("\t");
						printWriter.append(Float.toString(r.getMinY())).append("\t");
						printWriter.append(Float.toString(r.getMaxX())).append("\t");
						printWriter.append(Float.toString(r.getMaxY())).append("\t");
						printWriter.append(zd.getZone().getType().name()).append("\t");
						printWriter.append(Integer.toString(zd.getDensity())).append("\t");
						printWriter.append(Float.toString(density)).append("\n");
					}
				}
				
				vehicleCountGraphics.dispose();
				vehicleDensityGraphics.dispose();
				
				ImageIO.write(vehicleCount, "png", new File(this.countMapFolder, "t" + time + ".png"));
				ImageIO.write(vehicleDensity, "png", new File(this.densityMapFolder, "t" + time + ".png"));
			} catch (IOException e) {
				throw new Error(e);
			}
		}
	}

	private Rectangle2i searchFor(EnvironmentArea environment, int x, int y, GroundType... types) {
		Set<GroundType> s = new TreeSet<>(Arrays.asList(types));
		if (!s.contains(environment.getGroundType(x, y))) {
			return null;
		}
		int minx, maxx, miny, maxy;
		minx = maxx = x;
		miny = maxy = y;
		while (environment.getGroundType(x, miny - 1) != null && s.contains(environment.getGroundType(x, miny - 1))) {
			--miny;
		}
		while (environment.getGroundType(x, maxy + 1) != null && s.contains(environment.getGroundType(x, maxy + 1))) {
			++maxy;
		}
		while (environment.getGroundType(minx - 1, y) != null && s.contains(environment.getGroundType(minx - 1, y))) {
			--minx;
		}
		while (environment.getGroundType(maxx + 1, y) != null && s.contains(environment.getGroundType(maxx + 1, y))) {
			++maxx;
		}
		Rectangle2i r = new Rectangle2i();
		r.setFromCorners(minx,  miny, maxx, maxy);
		return r;
	}

	@Override
	public void simulationStarted(JaakEvent event) {
		setEnvironmentArea(event.getEnvironment());
	}

	@Override
	public void simulationStopped(JaakEvent event) {
		//
	}

	/**
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	private static class Zone {

		private Rectangle2i position;
		private final GroundType type;

		/**
		 * @param groundType
		 * @param position
		 */
		public Zone(Serializable groundType, Rectangle2i position) {
			this.position = position;
			if (groundType instanceof GroundType) {
				this.type = (GroundType) groundType;
			} else {
				this.type = null;
			}
		}

		public GroundType getType() {
			return this.type;
		}

		public Rectangle2i getPosition() {
			return this.position;
		}

	}

	/**
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	private static class ZoneData {

		private final Zone zone;
		private int density = 0;

		/**
		 * @param z
		 */
		public ZoneData(Zone z) {
			this.zone = z;
		}
		
		public Zone getZone() {
			return this.zone;
		}

		public int getDensity() {
			return this.density;
		}

		public int addDensity(int value) {
			this.density += Math.max(0, value);
			return this.density;
		}
	}

}
