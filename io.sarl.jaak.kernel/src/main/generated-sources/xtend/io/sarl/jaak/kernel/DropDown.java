package io.sarl.jaak.kernel;

import io.sarl.jaak.envinterface.perception.EnvironmentalObject;
import io.sarl.jaak.kernel.StampedEvent;
import io.sarl.lang.annotation.Generated;

/**
 * An agent wants to drop down.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public class DropDown extends StampedEvent {
  public final EnvironmentalObject object;
  
  public DropDown(final float ct, final float lsd, final EnvironmentalObject o) {
    super(ct, lsd);
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
    DropDown other = (DropDown) obj;
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
    result = prime * result + ((this.object== null) ? 0 : this.object.hashCode());
    return result;
  }
  
  /**
   * Returns a String representation of the Event DropDown attributes only.
   */
  @Generated
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("object  = ").append(this.object);
    return result.toString();
  }
  
  @Generated
  private final static long serialVersionUID = -3384055060L;
}
