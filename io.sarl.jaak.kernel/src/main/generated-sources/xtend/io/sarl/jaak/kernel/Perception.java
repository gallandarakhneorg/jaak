package io.sarl.jaak.kernel;

import io.sarl.jaak.envinterface.influence.MotionInfluenceStatus;
import io.sarl.jaak.envinterface.perception.Perceivable;
import io.sarl.jaak.kernel.StampedEvent;
import io.sarl.lang.annotation.Generated;
import java.util.Collection;

/**
 * The perception for an agent.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public class Perception extends StampedEvent {
  public final MotionInfluenceStatus lastMotionStatus;
  
  public final Collection<? extends Perceivable> perceivedObjects;
  
  public Perception(final float ct, final float lsd, final MotionInfluenceStatus lms, final Collection<? extends Perceivable> po) {
    super(ct, lsd);
    this.lastMotionStatus = lms;
    this.perceivedObjects = po;
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
    Perception other = (Perception) obj;
    if (this.lastMotionStatus == null) {
      if (other.lastMotionStatus != null)
        return false;
    } else if (!this.lastMotionStatus.equals(other.lastMotionStatus))
      return false;
    if (this.perceivedObjects == null) {
      if (other.perceivedObjects != null)
        return false;
    } else if (!this.perceivedObjects.equals(other.perceivedObjects))
      return false;
    return true;
  }
  
  @Override
  @Generated
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.lastMotionStatus== null) ? 0 : this.lastMotionStatus.hashCode());
    result = prime * result + ((this.perceivedObjects== null) ? 0 : this.perceivedObjects.hashCode());
    return result;
  }
  
  /**
   * Returns a String representation of the Perception event's attributes only.
   */
  @Generated
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("lastMotionStatus  = ").append(this.lastMotionStatus);
    result.append("perceivedObjects  = ").append(this.perceivedObjects);
    return result.toString();
  }
  
  @Generated
  private final static long serialVersionUID = 7177832L;
}
