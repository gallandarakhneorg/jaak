package io.sarl.jaak.demos.mdtraffic.environment.communication;

import io.sarl.jaak.demos.mdtraffic.environment.communication.CommunicationEvent;
import io.sarl.lang.annotation.Generated;
import org.arakhne.afc.math.discrete.object2d.Point2i;
import org.arakhne.afc.math.discrete.object2d.UnmodifiablePoint2i;

/**
 * An emergency situation was resolved.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public class EmergencyResolved extends CommunicationEvent {
  /**
   * Position of the emergency situation.
   */
  public final Point2i position;
  
  public EmergencyResolved(final Point2i position) {
    int _x = position.x();
    int _y = position.y();
    UnmodifiablePoint2i _unmodifiablePoint2i = new UnmodifiablePoint2i(_x, _y);
    this.position = _unmodifiablePoint2i;
  }
  
  @Override
  @Generated
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    if (!super.equals(obj))
      return false;
    EmergencyResolved other = (EmergencyResolved) obj;
    if (this.position == null) {
      if (other.position != null)
        return false;
    } else if (!this.position.equals(other.position))
      return false;
    return true;
  }
  
  @Override
  @Generated
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.position== null) ? 0 : this.position.hashCode());
    return result;
  }
  
  /**
   * Returns a String representation of the EmergencyResolved event's attributes only.
   */
  @Generated
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("position  = ").append(this.position);
    return result.toString();
  }
  
  @Generated
  private final static long serialVersionUID = -147306872L;
}
