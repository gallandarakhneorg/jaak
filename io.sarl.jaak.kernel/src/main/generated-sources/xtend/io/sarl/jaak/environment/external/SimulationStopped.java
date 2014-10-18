package io.sarl.jaak.environment.external;

import io.sarl.jaak.kernel.internal.AbstractStampedEvent;
import io.sarl.lang.annotation.Generated;

/**
 * The Jaak simulation has stopped.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public class SimulationStopped extends AbstractStampedEvent {
  public SimulationStopped(final float ct, final float lsd) {
    super(ct, lsd);
  }
  
  @Generated
  private final static long serialVersionUID = -2528315174L;
}
