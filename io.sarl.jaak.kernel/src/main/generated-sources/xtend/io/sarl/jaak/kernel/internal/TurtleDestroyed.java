package io.sarl.jaak.kernel.internal;

import io.sarl.jaak.kernel.internal.AbstractStampedEvent;
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
public class TurtleDestroyed extends AbstractStampedEvent {
  public TurtleDestroyed(final float ct, final float lsd) {
    super(ct, lsd);
  }
  
  @Generated
  private final static long serialVersionUID = 1142490697L;
}
