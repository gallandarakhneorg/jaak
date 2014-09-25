package io.sarl.jaak.kernel;

import io.sarl.lang.annotation.Generated;
import io.sarl.lang.core.Event;

@SuppressWarnings("all")
public class StampedEvent extends Event {
  public final float currentTime;
  
  public final float lastStepDuration;
  
  public StampedEvent(final float ct, final float lsd) {
    this.currentTime = ct;
    this.lastStepDuration = lsd;
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
    StampedEvent other = (StampedEvent) obj;
    if (Float.floatToIntBits(other.currentTime) != Float.floatToIntBits(this.currentTime))
      return false;
    if (Float.floatToIntBits(other.lastStepDuration) != Float.floatToIntBits(this.lastStepDuration))
      return false;
    return true;
  }
  
  @Override
  @Generated
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + Float.floatToIntBits(this.currentTime);
    result = prime * result + Float.floatToIntBits(this.lastStepDuration);
    return result;
  }
  
  /**
   * Returns a String representation of the Event StampedEvent attributes only.
   */
  @Generated
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("currentTime  = ").append(this.currentTime);
    result.append("lastStepDuration  = ").append(this.lastStepDuration);
    return result.toString();
  }
  
  @Generated
  private final static long serialVersionUID = 602296355L;
}
