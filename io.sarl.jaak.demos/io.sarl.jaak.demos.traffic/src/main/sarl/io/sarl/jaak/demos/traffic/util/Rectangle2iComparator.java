package io.sarl.jaak.demos.traffic.util;

import java.util.Comparator;

import org.arakhne.afc.math.discrete.object2d.Rectangle2i;

/** Comparator of rectangles.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class Rectangle2iComparator implements Comparator<Rectangle2i> {

	/**
	 */
	public Rectangle2iComparator() {
		//
	}

	@Override
	public int compare(Rectangle2i o1, Rectangle2i o2) {
		if (o1 == o2) {
			return 0;
		}
		if (o1 == null) {
			return Integer.MIN_VALUE;
		}
		if (o2 == null) {
			return Integer.MAX_VALUE;
		}
		int cmp = Double.compare(o1.getMinX(), o2.getMinX());
		if (cmp != 0) {
			return cmp;
		}
		cmp = Double.compare(o1.getMinY(), o2.getMinY());
		if (cmp != 0) {
			return cmp;
		}
		cmp = Double.compare(o1.getMaxX(), o2.getMaxX());
		if (cmp != 0) {
			return cmp;
		}
		return Double.compare(o1.getMaxY(), o2.getMaxY());
	}

}