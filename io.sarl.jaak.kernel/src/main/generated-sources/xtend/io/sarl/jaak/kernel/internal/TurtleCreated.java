package io.sarl.jaak.kernel.internal;

import io.sarl.jaak.kernel.internal.AbstractStampedEvent;
import io.sarl.lang.annotation.Generated;

/**
 * An agent is appearing.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public class TurtleCreated extends AbstractStampedEvent {
  public int x;
  
  public int y;
  
  public String frustumType;
  
  public int frustumLength;
  
  public TurtleCreated(final float ct, final float lsd) {
    super(ct, lsd);
    this.x = (-1);
    this.y = (-1);
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
    TurtleCreated other = (TurtleCreated) obj;
    if (other.x != this.x)
      return false;
    if (other.y != this.y)
      return false;
    if (this.frustumType == null) {
      if (other.frustumType != null)
        return false;
    } else if (!this.frustumType.equals(other.frustumType))
      return false;
    if (other.frustumLength != this.frustumLength)
      return false;
    return true;
  }
  
  @Override
  @Generated
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + this.x;
    result = prime * result + this.y;
    result = prime * result + ((this.frustumType== null) ? 0 : this.frustumType.hashCode());
    result = prime * result + this.frustumLength;
    return result;
  }
  
  /**
   * Returns a String representation of the TurtleCreated event's attributes only.
   */
  @Generated
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("x  = ").append(this.x);
    result.append("y  = ").append(this.y);
    result.append("frustumType  = ").append(this.frustumType);
    result.append("frustumLength  = ").append(this.frustumLength);
    return result.toString();
  }
  
  @Generated
  private final static long serialVersionUID = -273028179L;
}
