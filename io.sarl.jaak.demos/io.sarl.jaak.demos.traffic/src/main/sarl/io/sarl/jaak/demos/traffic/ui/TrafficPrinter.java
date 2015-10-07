package io.sarl.jaak.demos.traffic.ui;

import java.awt.Graphics;


/**
 * Graphic User Interface for the traffic demo.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public interface TrafficPrinter {

	/** Paint the traffic environment with the given graphic.
	 *
	 * @param g the graphic tool.
	 */
	void paint(Graphics g);
	
}
