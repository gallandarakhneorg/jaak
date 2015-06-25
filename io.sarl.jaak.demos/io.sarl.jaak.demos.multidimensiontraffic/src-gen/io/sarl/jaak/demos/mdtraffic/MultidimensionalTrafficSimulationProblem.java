package io.sarl.jaak.demos.mdtraffic;

import io.sarl.core.Initialize;
import io.sarl.jaak.demos.mdtraffic.Driver;
import io.sarl.jaak.demos.mdtraffic.MultidimensionalTrafficConstants;
import io.sarl.jaak.demos.mdtraffic.RuleDatabase;
import io.sarl.jaak.demos.mdtraffic.environment.MultiEndogenousEngine;
import io.sarl.jaak.demos.mdtraffic.environment.communication.CommunicationEvent;
import io.sarl.jaak.demos.mdtraffic.environment.communication.EmergencyDetected;
import io.sarl.jaak.demos.mdtraffic.environment.communication.PriorityRequest;
import io.sarl.jaak.demos.mdtraffic.environment.physic.CrashInfluence;
import io.sarl.jaak.demos.mdtraffic.environment.physic.EmergencyGenerator;
import io.sarl.jaak.demos.traffic.TrafficSimulationProblem;
import io.sarl.jaak.demos.traffic.environment.ReactiveTrafficLightGroup;
import io.sarl.jaak.demos.traffic.environment.Siren;
import io.sarl.jaak.demos.traffic.environment.TrafficLightGroup;
import io.sarl.jaak.environment.body.TurtleBody;
import io.sarl.jaak.environment.endogenous.EnvironmentEndogenousEngine;
import io.sarl.jaak.environment.model.JaakEnvironment;
import io.sarl.jaak.environment.spawner.JaakSpawner;
import io.sarl.jaak.environment.time.TimeManager;
import io.sarl.jaak.kernel.AgentInfluence;
import io.sarl.lang.annotation.Generated;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AgentContext;
import io.sarl.lang.core.EventListener;
import io.sarl.lang.core.Percept;
import io.sarl.lang.core.SpaceID;
import io.sarl.util.OpenEventSpace;
import io.sarl.util.OpenEventSpaceSpecification;
import java.util.UUID;
import org.arakhne.afc.math.discrete.object2d.Point2i;
import org.arakhne.afc.math.discrete.object2d.Rectangle2i;
import org.eclipse.xtext.xbase.lib.Functions.Function3;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * This class defines the multidimensional simulation environment for the traffic simulation problem.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public class MultidimensionalTrafficSimulationProblem extends TrafficSimulationProblem {
  protected RuleDatabase rules;
  
  protected OpenEventSpace outputSpace;
  
  protected OpenEventSpace inputSpace;
  
  @Percept
  public void _handle_Initialize_0(final Initialize occurrence) {
    AgentContext _defaultContext = this.getDefaultContext();
    OpenEventSpace _createSpace = _defaultContext.<OpenEventSpace>createSpace(
      OpenEventSpaceSpecification.class, MultidimensionalTrafficConstants.OUTPUT_COMMUNICATION_SPACE_ID);
    this.outputSpace = _createSpace;
    AgentContext _defaultContext_1 = this.getDefaultContext();
    OpenEventSpace _createSpace_1 = _defaultContext_1.<OpenEventSpace>createSpace(
      OpenEventSpaceSpecification.class, MultidimensionalTrafficConstants.INPUT_COMMUNICATION_SPACE_ID);
    this.inputSpace = _createSpace_1;
    EventListener _asEventListener = this.asEventListener();
    this.outputSpace.register(_asEventListener);
    super._handle_Initialize_0(occurrence);
  }
  
  /**
   * Replies the type of the agents to spawn.
   * 
   * @param spawner - the spawner that will create the agent.
   */
  public Class<? extends Agent> getSpawnableAgentType(final JaakSpawner spawner) {
    return Driver.class;
  }
  
  /**
   * Create an instance of the environment that must
   * be used by the Jaak kernel.
   * 
   * @return the instance of time manager.
   */
  public JaakEnvironment createEnvironment(final TimeManager tm) {
    JaakEnvironment env = super.createEnvironment(tm);
    RuleDatabase _ruleDatabase = new RuleDatabase(env, this.inputSpace);
    this.rules = _ruleDatabase;
    this.createRules(this.rules);
    EnvironmentEndogenousEngine oldEndogenousEngine = env.getEndogenousEngine();
    MultiEndogenousEngine newEndogenousEngine = new MultiEndogenousEngine();
    newEndogenousEngine.add(oldEndogenousEngine);
    EmergencyGenerator _emergencyGenerator = new EmergencyGenerator();
    newEndogenousEngine.add(_emergencyGenerator);
    newEndogenousEngine.add(this.rules);
    env.setEndogenousEngine(newEndogenousEngine);
    return env;
  }
  
  @Percept
  public void _handle_CommunicationEvent_1(final CommunicationEvent occurrence) {
    /* this; */
    synchronized (this) {
      boolean _filter = this.rules.filter(occurrence);
      if (_filter) {
        SpaceID _iD = this.inputSpace.getID();
        Address _source = occurrence.getSource();
        UUID _uUID = _source.getUUID();
        Address _address = new Address(_iD, _uUID);
        occurrence.setSource(_address);
        this.inputSpace.emit(occurrence);
      }
    }
  }
  
  @Generated
  private boolean _guard_AgentInfluence_2(final AgentInfluence occurrence) {
    boolean _isValid = this.isValid(occurrence);
    return _isValid;
  }
  
  @Percept
  public void _handle_AgentInfluence_2(final AgentInfluence occurrence) {
    if (_guard_AgentInfluence_2(occurrence)) {
      /* this; */
      synchronized (this) {
        boolean _filter = this.rules.filter(occurrence.influence);
        if (_filter) {
          super._handle_AgentInfluence_4(occurrence);
        }
      }
    }
  }
  
  public TrafficLightGroup newTrafficLightGroup(final Rectangle2i crossRoad) {
    return new ReactiveTrafficLightGroup(crossRoad);
  }
  
  public void createRules(final RuleDatabase it) {
    final Function3<Object, JaakEnvironment, OpenEventSpace, Boolean> _function = new Function3<Object, JaakEnvironment, OpenEventSpace, Boolean>() {
      public Boolean apply(final Object a, final JaakEnvironment b, final OpenEventSpace c) {
        return Boolean.valueOf((a instanceof CrashInfluence));
      }
    };
    final Function3<Object, JaakEnvironment, OpenEventSpace, Boolean> _function_1 = new Function3<Object, JaakEnvironment, OpenEventSpace, Boolean>() {
      public Boolean apply(final Object a, final JaakEnvironment b, final OpenEventSpace c) {
        boolean _xblockexpression = false;
        {
          CrashInfluence i = ((CrashInfluence) a);
          Point2i _position = i.getPosition();
          EmergencyDetected e = new EmergencyDetected(_position);
          SpaceID _iD = c.getID();
          UUID _iD_1 = MultidimensionalTrafficSimulationProblem.this.getID();
          Address _address = new Address(_iD, _iD_1);
          e.setSource(_address);
          c.emit(e);
          _xblockexpression = true;
        }
        return Boolean.valueOf(_xblockexpression);
      }
    };
    Pair<Function3<Object, JaakEnvironment, OpenEventSpace, Boolean>, Function3<Object, JaakEnvironment, OpenEventSpace, Boolean>> _mappedTo = Pair.<Function3<Object, JaakEnvironment, OpenEventSpace, Boolean>, Function3<Object, JaakEnvironment, OpenEventSpace, Boolean>>of(_function, _function_1);
    it.add(_mappedTo);
    final Function3<Object, JaakEnvironment, OpenEventSpace, Boolean> _function_2 = new Function3<Object, JaakEnvironment, OpenEventSpace, Boolean>() {
      public Boolean apply(final Object a, final JaakEnvironment b, final OpenEventSpace c) {
        return Boolean.valueOf((a instanceof PriorityRequest));
      }
    };
    final Function3<Object, JaakEnvironment, OpenEventSpace, Boolean> _function_3 = new Function3<Object, JaakEnvironment, OpenEventSpace, Boolean>() {
      public Boolean apply(final Object a, final JaakEnvironment b, final OpenEventSpace c) {
        boolean _xblockexpression = false;
        {
          PriorityRequest e = ((PriorityRequest) a);
          Address _source = e.getSource();
          UUID _uUID = _source.getUUID();
          TurtleBody _bodyFor = b.getBodyFor(_uUID);
          Siren _siren = new Siren();
          _bodyFor.dropOff(_siren);
          _xblockexpression = true;
        }
        return Boolean.valueOf(_xblockexpression);
      }
    };
    Pair<Function3<Object, JaakEnvironment, OpenEventSpace, Boolean>, Function3<Object, JaakEnvironment, OpenEventSpace, Boolean>> _mappedTo_1 = Pair.<Function3<Object, JaakEnvironment, OpenEventSpace, Boolean>, Function3<Object, JaakEnvironment, OpenEventSpace, Boolean>>of(_function_2, _function_3);
    it.add(_mappedTo_1);
  }
  
  /**
   * Construct an agent.
   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.
   */
  @Generated
  public MultidimensionalTrafficSimulationProblem(final UUID parentID) {
    super(parentID, null);
  }
  
  /**
   * Construct an agent.
   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.
   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.
   */
  @Generated
  public MultidimensionalTrafficSimulationProblem(final UUID parentID, final UUID agentID) {
    super(parentID, agentID);
  }
}
