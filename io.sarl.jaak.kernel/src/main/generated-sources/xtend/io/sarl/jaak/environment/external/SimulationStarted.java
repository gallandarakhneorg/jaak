package io.sarl.jaak.environment.external;

import io.sarl.jaak.kernel.internal.AbstractStampedEvent;
import io.sarl.lang.annotation.Generated;

/**
 * The Jaak simulation has started.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public class SimulationStarted extends AbstractStampedEvent {
  public SimulationStarted(final float ct, final float lsd) {
    super(ct, lsd);
  }
  
  @Generated
  private final static long serialVersionUID = -2541181042L;
}
