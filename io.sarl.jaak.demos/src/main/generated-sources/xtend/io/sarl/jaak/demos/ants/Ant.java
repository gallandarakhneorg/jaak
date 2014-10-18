package io.sarl.jaak.demos.ants;

import io.sarl.core.Initialize;
import io.sarl.jaak.demos.ants.behaviors.FoodSelectionCapacity;
import io.sarl.jaak.demos.ants.behaviors.FoodSelectionSkill;
import io.sarl.jaak.demos.ants.behaviors.Forager;
import io.sarl.jaak.demos.ants.behaviors.Patroller;
import io.sarl.jaak.demos.ants.behaviors.PheromoneFollowingCapacity;
import io.sarl.jaak.demos.ants.behaviors.PheromoneFollowingSkill;
import io.sarl.jaak.environment.external.PhysicBody;
import io.sarl.jaak.environment.external.PhysicBodySkill;
import io.sarl.jaak.environment.external.SimulationStopped;
import io.sarl.lang.annotation.Generated;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AgentContext;
import io.sarl.lang.core.Behavior;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.EventListener;
import io.sarl.lang.core.Percept;
import java.io.Serializable;
import java.util.UUID;

/**
 * This class defines an ant.
 * <p>
 * The most important characteristic of an ant in this context
 * is related to its individual and unpredictable tendency
 * to choose a certain route among the many available. Each instance of
 * the class Ant must represent an individual agent with singular
 * characteristics. This can be implemented by using a mathematical
 * function. As described above the pheromone level over a route is
 * measured by an integer number. The agent will use a method that
 * evaluates its tendency of choosing a route based on the
 * pheromone intensity. A good variability of the behavior of
 * the agents can be expressed as a sinusoidal function
 * with at least three coefficients: T(PL) = Alpha * sin(Beta * PL + Gamma).
 * <p>
 * The input PL is the pheromone level over a route. Alfa, Beta and
 * Gamma will be properties of the Ant class initialized as random
 * float numbers within the interval [-5..5]. These properties will
 * make possible to have different individuals in the population.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public class Ant extends Agent {
  /**
   * Construct an agent.
   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.
   */
  @Generated
  public Ant(final UUID parentID) {
    super(parentID, null);
  }
  
  /**
   * Construct an agent.
   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.
   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.
   */
  @Generated
  public Ant(final UUID parentID, final UUID agentID) {
    super(parentID, agentID);
  }
  
  @Generated
  protected EventListener asEventListener() {
    return getSkill(io.sarl.core.Behaviors.class).asEventListener();
  }
  
  @Generated
  protected Behavior registerBehavior(final Behavior attitude) {
    return getSkill(io.sarl.core.Behaviors.class).registerBehavior(attitude);
  }
  
  @Generated
  protected Behavior unregisterBehavior(final Behavior attitude) {
    return getSkill(io.sarl.core.Behaviors.class).unregisterBehavior(attitude);
  }
  
  @Generated
  protected void wake(final Event evt) {
    getSkill(io.sarl.core.Behaviors.class).wake(evt);
  }
  
  @Generated
  protected void killMe() {
    getSkill(io.sarl.core.Lifecycle.class).killMe();
  }
  
  @Generated
  protected UUID spawnInContext(final Class<? extends Agent> agentClass, final AgentContext context, final Object... params) {
    return getSkill(io.sarl.core.Lifecycle.class).spawnInContext(agentClass, context, params);
  }
  
  @Generated
  protected UUID spawnInContextWithID(final Class<? extends Agent> agentClass, final UUID agentID, final AgentContext context, final Object... params) {
    return getSkill(io.sarl.core.Lifecycle.class).spawnInContextWithID(agentClass, agentID, context, params);
  }
  
  @Percept
  public void _handle_Initialize_1(final Initialize occurrence) {
    PhysicBodySkill body = new PhysicBodySkill();
    this.<PhysicBodySkill>setSkill(PhysicBody.class, body);
    PheromoneFollowingSkill _pheromoneFollowingSkill = new PheromoneFollowingSkill();
    this.<PheromoneFollowingSkill>setSkill(PheromoneFollowingCapacity.class, _pheromoneFollowingSkill);
    Behavior beh = null;
    Serializable _semantic = body.getSemantic();
    if ((_semantic instanceof Patroller)) {
      Patroller _patroller = new Patroller(this);
      beh = _patroller;
    } else {
      Forager _forager = new Forager(this);
      beh = _forager;
      FoodSelectionSkill _foodSelectionSkill = new FoodSelectionSkill();
      this.<FoodSelectionSkill>setSkill(FoodSelectionCapacity.class, _foodSelectionSkill);
    }
    this.registerBehavior(beh);
  }
  
  @Percept
  public void _handle_SimulationStopped_2(final SimulationStopped occurrence) {
    this.killMe();
  }
}
