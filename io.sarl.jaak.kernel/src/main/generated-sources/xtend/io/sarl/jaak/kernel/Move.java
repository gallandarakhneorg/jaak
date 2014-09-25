package io.sarl.jaak.kernel;

import io.sarl.jaak.kernel.StampedEvent;
import io.sarl.lang.annotation.Generated;
import java.util.UUID;

/**
 * An agent wants to move itself or something.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public class Move extends StampedEvent {
  public float linearMotionX;
  
  public float linearMotionY;
  
  public float angularMotion;
  
  public UUID movedObject;
  
  public Move(final float ct, final float lsd) {
    super(ct, lsd);
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
    Move other = (Move) obj;
    if (Float.floatToIntBits(other.linearMotionX) != Float.floatToIntBits(this.linearMotionX))
      return false;
    if (Float.floatToIntBits(other.linearMotionY) != Float.floatToIntBits(this.linearMotionY))
      return false;
    if (Float.floatToIntBits(other.angularMotion) != Float.floatToIntBits(this.angularMotion))
      return false;
    if (this.movedObject == null) {
      if (other.movedObject != null)
        return false;
    } else if (!this.movedObject.equals(other.movedObject))
      return false;
    return true;
  }
  
  @Override
  @Generated
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + Float.floatToIntBits(this.linearMotionX);
    result = prime * result + Float.floatToIntBits(this.linearMotionY);
    result = prime * result + Float.floatToIntBits(this.angularMotion);
    result = prime * result + ((this.movedObject== null) ? 0 : this.movedObject.hashCode());
    return result;
  }
  
  /**
   * Returns a String representation of the Event Move attributes only.
   */
  @Generated
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("linearMotionX  = ").append(this.linearMotionX);
    result.append("linearMotionY  = ").append(this.linearMotionY);
    result.append("angularMotion  = ").append(this.angularMotion);
    result.append("movedObject  = ").append(this.movedObject);
    return result.toString();
  }
  
  @Generated
  private final static long serialVersionUID = 553179218L;
}
