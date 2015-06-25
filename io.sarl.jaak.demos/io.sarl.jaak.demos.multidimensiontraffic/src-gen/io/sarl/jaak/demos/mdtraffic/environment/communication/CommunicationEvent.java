package io.sarl.jaak.demos.mdtraffic.environment.communication;

import io.sarl.lang.annotation.Generated;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Event;

/**
 * Abstract communication event.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public class CommunicationEvent extends Event {
  /**
   * Construct an event. The source of the event is unknown.
   */
  @Generated
  public CommunicationEvent() {
    super();
  }
  
  /**
   * Construct an event.
   * @param source - address of the agent that is emitting this event.
   */
  @Generated
  public CommunicationEvent(final Address source) {
    super(source);
  }
  
  @Generated
  private final static long serialVersionUID = 588368462L;
}
