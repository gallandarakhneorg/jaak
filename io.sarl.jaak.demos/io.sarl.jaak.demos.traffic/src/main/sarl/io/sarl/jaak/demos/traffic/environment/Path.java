package io.sarl.jaak.demos.traffic.environment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import org.arakhne.afc.math.discrete.object2d.Point2i;

/** Definition of a path.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class Path extends ArrayList<Point2i> implements Cloneable, Serializable {

	private static final long serialVersionUID = 3927921866951405752L;

	private final UUID id;
	
	public Path() {
		this(UUID.randomUUID());
	}
	
	public Path(UUID id) {
		this.id = id;
	}

	@Override
	public Path clone() {
		Path p = new Path(this.id); 
		for (Point2i pts : this) {
			p.add(pts.clone());
		}
		return p;
	}
	
	/** Replies the identifier of the path.
	 *
	 * @return the id.
	 */
	public UUID getID() {
		return this.id;
	}
	
	/** Remove the elements of the path until the given point.
	 * 
	 * @param position the position.
	 * @return <code>true</code> if the path has changed; <code>false</code> otherwise.
	 */
	public boolean removePosition(Point2i position) {
		int index = indexOf(position);
		if (index >= 0) {
			removeRange(0, index + 1);
			return true;
		}
		return false;
	}

}