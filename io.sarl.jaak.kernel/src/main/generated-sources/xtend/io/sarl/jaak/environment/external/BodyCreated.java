package io.sarl.jaak.environment.external;

import io.sarl.jaak.environment.external.body.TurtleObject;
import io.sarl.jaak.kernel.internal.AbstractStampedEvent;
import io.sarl.lang.annotation.Generated;

/**
 * The body for an agent is ready.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public class BodyCreated extends AbstractStampedEvent {
  public final TurtleObject body;
  
  public BodyCreated(final float ct, final float lsd, final TurtleObject body) {
    super(ct, lsd);
    this.body = body;
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
    BodyCreated other = (BodyCreated) obj;
    if (this.body == null) {
      if (other.body != null)
        return false;
    } else if (!this.body.equals(other.body))
      return false;
    return true;
  }
  
  @Override
  @Generated
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.body== null) ? 0 : this.body.hashCode());
    return result;
  }
  
  /**
   * Returns a String representation of the BodyCreated event's attributes only.
   */
  @Generated
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("body  = ").append(this.body);
    return result.toString();
  }
  
  @Generated
  private final static long serialVersionUID = 563953564L;
}
