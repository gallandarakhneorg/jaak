package io.sarl.jaak.kernel.internal;

import io.sarl.jaak.environment.external.influence.Influence;
import io.sarl.jaak.kernel.internal.AbstractStampedEvent;
import io.sarl.lang.annotation.Generated;

/**
 * Notify the simulation engine that an
 * agent has sent an influence.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public class AgentInfluence extends AbstractStampedEvent {
  public final Influence influence;
  
  public AgentInfluence(final float ct, final float lsd, final Influence influence) {
    super(ct, lsd);
    this.influence = influence;
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
    AgentInfluence other = (AgentInfluence) obj;
    if (this.influence == null) {
      if (other.influence != null)
        return false;
    } else if (!this.influence.equals(other.influence))
      return false;
    return true;
  }
  
  @Override
  @Generated
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.influence== null) ? 0 : this.influence.hashCode());
    return result;
  }
  
  /**
   * Returns a String representation of the AgentInfluence event's attributes only.
   */
  @Generated
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("influence  = ").append(this.influence);
    return result.toString();
  }
  
  @Generated
  private final static long serialVersionUID = -1029510479L;
}
