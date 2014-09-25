package io.sarl.jaak.kernel;

import io.sarl.jaak.kernel.StampedEvent;
import io.sarl.lang.annotation.Generated;

/**
 * An agent is desappearing
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public class TurtleDestroyed extends StampedEvent {
  public TurtleDestroyed(final float ct, final float lsd) {
    super(ct, lsd);
  }
  
  @Generated
  private final static long serialVersionUID = 298206073L;
}
