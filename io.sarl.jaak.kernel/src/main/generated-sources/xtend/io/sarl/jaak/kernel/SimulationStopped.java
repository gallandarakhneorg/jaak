package io.sarl.jaak.kernel;

import io.sarl.jaak.kernel.StampedEvent;
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
public class SimulationStopped extends StampedEvent {
  public SimulationStopped(final float ct, final float lsd) {
    super(ct, lsd);
  }
  
  @Generated
  private final static long serialVersionUID = -996382462L;
}
