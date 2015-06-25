package io.sarl.jaak.demos.mdtraffic.environment.communication;

import io.sarl.jaak.demos.mdtraffic.environment.communication.CommunicationEvent;
import io.sarl.jaak.demos.traffic.environment.Path;
import io.sarl.lang.annotation.Generated;

/**
 * An emergency vehicle was activated.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public class EmergencyVehicleActivated extends CommunicationEvent {
  /**
   * Path followed by the emergency vehicle.
   */
  public final Path path;
  
  public EmergencyVehicleActivated(final Path path) {
    Path _clone = path.clone();
    this.path = _clone;
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
    EmergencyVehicleActivated other = (EmergencyVehicleActivated) obj;
    if (this.path == null) {
      if (other.path != null)
        return false;
    } else if (!this.path.equals(other.path))
      return false;
    return true;
  }
  
  @Override
  @Generated
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.path== null) ? 0 : this.path.hashCode());
    return result;
  }
  
  /**
   * Returns a String representation of the EmergencyVehicleActivated event's attributes only.
   */
  @Generated
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("path  = ").append(this.path);
    return result.toString();
  }
  
  @Generated
  private final static long serialVersionUID = 362471569L;
}
