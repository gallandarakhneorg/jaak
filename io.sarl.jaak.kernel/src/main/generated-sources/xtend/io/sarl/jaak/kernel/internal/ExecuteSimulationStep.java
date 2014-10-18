package io.sarl.jaak.kernel.internal;

import io.sarl.lang.annotation.Generated;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Event;

/**
 * Notify the simulation engine that a
 * simulation step must be executed.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public class ExecuteSimulationStep extends Event {
  /**
   * Construct an event. The source of the event is unknown.
   */
  @Generated
  public ExecuteSimulationStep() {
    super();
  }
  
  /**
   * Construct an event.
   * @param source - address of the agent that is emitting this event.
   */
  @Generated
  public ExecuteSimulationStep(final Address source) {
    super(source);
  }
  
  @Generated
  private final static long serialVersionUID = 588368462L;
}
