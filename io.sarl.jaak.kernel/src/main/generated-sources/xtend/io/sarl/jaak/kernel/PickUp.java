package io.sarl.jaak.kernel;

import io.sarl.jaak.envinterface.perception.EnvironmentalObject;
import io.sarl.jaak.kernel.StampedEvent;
import io.sarl.lang.annotation.Generated;

/**
 * An agent wants to pick up.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public class PickUp extends StampedEvent {
  public final Class<? extends EnvironmentalObject> type;
  
  public final EnvironmentalObject object;
  
  public PickUp(final float ct, final float lsd, final Class<? extends EnvironmentalObject> t) {
    super(ct, lsd);
    this.type = t;
    this.object = null;
  }
  
  public PickUp(final float ct, final float lsd, final EnvironmentalObject o) {
    super(ct, lsd);
    this.type = null;
    this.object = o;
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
    PickUp other = (PickUp) obj;
    if (this.type == null) {
      if (other.type != null)
        return false;
    } else if (!this.type.equals(other.type))
      return false;
    if (this.object == null) {
      if (other.object != null)
        return false;
    } else if (!this.object.equals(other.object))
      return false;
    return true;
  }
  
  @Override
  @Generated
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.type== null) ? 0 : this.type.hashCode());
    result = prime * result + ((this.object== null) ? 0 : this.object.hashCode());
    return result;
  }
  
  /**
   * Returns a String representation of the PickUp event's attributes only.
   */
  @Generated
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("type  = ").append(this.type);
    result.append("object  = ").append(this.object);
    return result.toString();
  }
  
  @Generated
  private final static long serialVersionUID = 491218638L;
}
