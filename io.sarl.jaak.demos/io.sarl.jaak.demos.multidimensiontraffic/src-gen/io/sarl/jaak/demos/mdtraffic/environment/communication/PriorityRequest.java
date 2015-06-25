package io.sarl.jaak.demos.mdtraffic.environment.communication;

import io.sarl.jaak.demos.mdtraffic.environment.communication.CommunicationEvent;
import io.sarl.lang.annotation.Generated;
import io.sarl.lang.core.Address;

/**
 * A request for obtaining the priority at traffic lights.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public class PriorityRequest extends CommunicationEvent {
  /**
   * Construct an event. The source of the event is unknown.
   */
  @Generated
  public PriorityRequest() {
    super();
  }
  
  /**
   * Construct an event.
   * @param source - address of the agent that is emitting this event.
   */
  @Generated
  public PriorityRequest(final Address source) {
    super(source);
  }
  
  @Generated
  private final static long serialVersionUID = 1220805837L;
}
