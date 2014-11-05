package io.sarl.jaak.kernel.internal;

import com.google.common.base.Objects;
import io.sarl.core.AgentTask;
import io.sarl.core.Destroy;
import io.sarl.core.Initialize;
import io.sarl.jaak.environment.external.BodyCreated;
import io.sarl.jaak.environment.external.Perception;
import io.sarl.jaak.environment.external.SimulationStarted;
import io.sarl.jaak.environment.external.SimulationStopped;
import io.sarl.jaak.environment.external.body.BodySpawner;
import io.sarl.jaak.environment.external.body.TurtleBody;
import io.sarl.jaak.environment.external.body.TurtleBodyFactory;
import io.sarl.jaak.environment.external.time.TimeManager;
import io.sarl.jaak.environment.internal.model.JaakEnvironment;
import io.sarl.jaak.environment.internal.spawner.JaakSpawner;
import io.sarl.jaak.environment.internal.spawner.JaakWorldSpawner;
import io.sarl.jaak.kernel.external.JaakController;
import io.sarl.jaak.kernel.external.JaakEvent;
import io.sarl.jaak.kernel.external.JaakListener;
import io.sarl.jaak.kernel.external.JaakPhysicSpace;
import io.sarl.jaak.kernel.external.JaakPhysicSpaceConstants;
import io.sarl.jaak.kernel.internal.AbstractStampedEvent;
import io.sarl.jaak.kernel.internal.AgentBodyCreator;
import io.sarl.jaak.kernel.internal.AgentInfluence;
import io.sarl.jaak.kernel.internal.DefaultJaakTimeManager;
import io.sarl.jaak.kernel.internal.ExecuteSimulationStep;
import io.sarl.jaak.kernel.internal.JaakKernelController;
import io.sarl.jaak.kernel.internal.JaakPhysicSpaceSpecification;
import io.sarl.jaak.kernel.internal.TurtleCreated;
import io.sarl.jaak.kernel.internal.TurtleDestroyed;
import io.sarl.jaak.util.RandomNumber;
import io.sarl.lang.annotation.Generated;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AgentContext;
import io.sarl.lang.core.Behavior;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.EventListener;
import io.sarl.lang.core.EventSpace;
import io.sarl.lang.core.Percept;
import io.sarl.lang.core.Scope;
import io.sarl.lang.core.SpaceID;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

/**
 * Provide the core agent which is responsible of the Jaak environment.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public class JaakKernelAgent extends Agent {
  /**
   * Construct an agent.
   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.
   */
  @Generated
  public JaakKernelAgent(final UUID parentID) {
    super(parentID, null);
  }
  
  /**
   * Construct an agent.
   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.
   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.
   */
  @Generated
  public JaakKernelAgent(final UUID parentID, final UUID agentID) {
    super(parentID, agentID);
  }
  
  @Generated
  protected void emit(final Event e) {
    getSkill(io.sarl.core.DefaultContextInteractions.class).emit(e);
  }
  
  @Generated
  protected void emit(final Event e, final Scope<Address> scope) {
    getSkill(io.sarl.core.DefaultContextInteractions.class).emit(e, scope);
  }
  
  @Generated
  protected Address getDefaultAddress() {
    return getSkill(io.sarl.core.DefaultContextInteractions.class).getDefaultAddress();
  }
  
  @Generated
  protected AgentContext getDefaultContext() {
    return getSkill(io.sarl.core.DefaultContextInteractions.class).getDefaultContext();
  }
  
  @Generated
  protected EventSpace getDefaultSpace() {
    return getSkill(io.sarl.core.DefaultContextInteractions.class).getDefaultSpace();
  }
  
  @Generated
  protected void receive(final UUID receiver, final Event e) {
    getSkill(io.sarl.core.DefaultContextInteractions.class).receive(receiver, e);
  }
  
  @Generated
  protected UUID spawn(final Class<? extends Agent> aAgent, final Object... params) {
    return getSkill(io.sarl.core.DefaultContextInteractions.class).spawn(aAgent, params);
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
  
  @Generated
  protected boolean cancel(final AgentTask task) {
    return getSkill(io.sarl.core.Schedules.class).cancel(task);
  }
  
  @Generated
  protected boolean cancel(final AgentTask task, final boolean mayInterruptIfRunning) {
    return getSkill(io.sarl.core.Schedules.class).cancel(task, mayInterruptIfRunning);
  }
  
  @Generated
  protected AgentTask every(final long period, final Procedure1<? super Agent> procedure) {
    return getSkill(io.sarl.core.Schedules.class).every(period, procedure);
  }
  
  @Generated
  protected AgentTask every(final AgentTask task, final long period, final Procedure1<? super Agent> procedure) {
    return getSkill(io.sarl.core.Schedules.class).every(task, period, procedure);
  }
  
  @Generated
  protected AgentTask in(final long delay, final Procedure1<? super Agent> procedure) {
    return getSkill(io.sarl.core.Schedules.class).in(delay, procedure);
  }
  
  @Generated
  protected AgentTask in(final AgentTask task, final long delay, final Procedure1<? super Agent> procedure) {
    return getSkill(io.sarl.core.Schedules.class).in(task, delay, procedure);
  }
  
  @Generated
  protected AgentTask task(final String name) {
    return getSkill(io.sarl.core.Schedules.class).task(name);
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
  
  protected final int waitingDuration = 5000;
  
  protected final List<JaakListener> listeners = CollectionLiterals.<JaakListener>newArrayList();
  
  protected final List<UUID> removedAgents = CollectionLiterals.<UUID>newLinkedList();
  
  protected final Map<UUID, TurtleCreated> addedAgents = CollectionLiterals.<UUID, TurtleCreated>newTreeMap(null);
  
  protected final AtomicBoolean isWaitingInfluences = new AtomicBoolean(false);
  
  protected final JaakController controller = new JaakKernelController();
  
  protected JaakPhysicSpace physicSpace;
  
  protected Address defaultAddressInPhysicSpace;
  
  protected JaakEnvironment physicEnvironment;
  
  protected TimeManager timeManager;
  
  protected JaakSpawner[] spawners;
  
  protected JaakWorldSpawner defaultSpawner;
  
  protected AgentTask waitingTask;
  
  public void addJaakListener(final JaakListener listener) {
    /* this.listeners; */
    synchronized (this.listeners) {
      this.listeners.add(listener);
    }
  }
  
  public void removeJaakListener(final JaakListener listener) {
    /* this.listeners; */
    synchronized (this.listeners) {
      this.listeners.add(listener);
    }
  }
  
  public void fireSimulationStarted() {
    JaakListener[] list = null;
    /* this.listeners; */
    synchronized (this.listeners) {
      {
        int _size = this.listeners.size();
        JaakListener[] _newArrayOfSize = new JaakListener[_size];
        list = _newArrayOfSize;
        this.listeners.<JaakListener>toArray(list);
      }
    }
    Collection<BodySpawner> _unmodifiableCollection = Collections.<BodySpawner>unmodifiableCollection(((Collection<? extends BodySpawner>)Conversions.doWrapArray(this.spawners)));
    float _currentTime = this.timeManager.getCurrentTime();
    float _lastStepDuration = this.timeManager.getLastStepDuration();
    JaakEvent evt = new JaakEvent(this, 
      this.physicEnvironment, _unmodifiableCollection, _currentTime, _lastStepDuration);
    for (final JaakListener listener : list) {
      listener.simulationStarted(evt);
    }
  }
  
  public void fireSimulationStopped() {
    JaakListener[] list = null;
    /* this.listeners; */
    synchronized (this.listeners) {
      {
        int _size = this.listeners.size();
        JaakListener[] _newArrayOfSize = new JaakListener[_size];
        list = _newArrayOfSize;
        this.listeners.<JaakListener>toArray(list);
      }
    }
    Collection<BodySpawner> _unmodifiableCollection = Collections.<BodySpawner>unmodifiableCollection(((Collection<? extends BodySpawner>)Conversions.doWrapArray(this.spawners)));
    float _currentTime = this.timeManager.getCurrentTime();
    float _lastStepDuration = this.timeManager.getLastStepDuration();
    JaakEvent evt = new JaakEvent(this, 
      this.physicEnvironment, _unmodifiableCollection, _currentTime, _lastStepDuration);
    for (final JaakListener listener : list) {
      listener.simulationStopped(evt);
    }
  }
  
  public void fireEnvironmentChange() {
    JaakListener[] list = null;
    /* this.listeners; */
    synchronized (this.listeners) {
      {
        int _size = this.listeners.size();
        JaakListener[] _newArrayOfSize = new JaakListener[_size];
        list = _newArrayOfSize;
        this.listeners.<JaakListener>toArray(list);
      }
    }
    Collection<BodySpawner> _unmodifiableCollection = Collections.<BodySpawner>unmodifiableCollection(((Collection<? extends BodySpawner>)Conversions.doWrapArray(this.spawners)));
    float _currentTime = this.timeManager.getCurrentTime();
    float _lastStepDuration = this.timeManager.getLastStepDuration();
    JaakEvent evt = new JaakEvent(this, 
      this.physicEnvironment, _unmodifiableCollection, _currentTime, _lastStepDuration);
    for (final JaakListener listener : list) {
      listener.environmentStateChanged(evt);
    }
  }
  
  @Percept
  public void _handle_Initialize_1(final Initialize occurrence) {
    AgentContext _defaultContext = this.getDefaultContext();
    UUID spaceId = JaakPhysicSpaceConstants.getSpaceIDInContext(_defaultContext);
    AgentContext _defaultContext_1 = this.getDefaultContext();
    EventListener _asEventListener = this.asEventListener();
    JaakPhysicSpace _orCreateSpace = _defaultContext_1.<JaakPhysicSpace>getOrCreateSpace(JaakPhysicSpaceSpecification.class, spaceId, _asEventListener);
    this.physicSpace = _orCreateSpace;
    boolean _and = false;
    UUID _creatorID = this.physicSpace.getCreatorID();
    boolean _tripleNotEquals = (_creatorID != null);
    if (!_tripleNotEquals) {
      _and = false;
    } else {
      UUID _creatorID_1 = this.physicSpace.getCreatorID();
      UUID _iD = this.getID();
      boolean _notEquals = (!Objects.equal(_creatorID_1, _iD));
      _and = _notEquals;
    }
    if (_and) {
      this.killMe();
    } else {
      SpaceID _iD_1 = this.physicSpace.getID();
      UUID _iD_2 = this.getID();
      Address _address = new Address(_iD_1, _iD_2);
      this.defaultAddressInPhysicSpace = _address;
      TimeManager _createTimeManager = this.createTimeManager();
      this.timeManager = _createTimeManager;
      JaakEnvironment _createEnvironment = this.createEnvironment();
      this.physicEnvironment = _createEnvironment;
      this.physicEnvironment.setTimeManager(this.timeManager);
      JaakWorldSpawner _jaakWorldSpawner = new JaakWorldSpawner(this.physicEnvironment);
      this.defaultSpawner = _jaakWorldSpawner;
      JaakSpawner[] p = this.createSpawners();
      boolean _or = false;
      boolean _tripleEquals = (p == null);
      if (_tripleEquals) {
        _or = true;
      } else {
        final JaakSpawner[] _converted_p = (JaakSpawner[])p;
        boolean _isEmpty = ((List<JaakSpawner>)Conversions.doWrapArray(_converted_p)).isEmpty();
        _or = _isEmpty;
      }
      if (_or) {
        List<JaakSpawner> _singletonList = Collections.<JaakSpawner>singletonList(this.defaultSpawner);
        this.spawners = ((JaakSpawner[])Conversions.unwrapArray(_singletonList, JaakSpawner.class));
      } else {
        this.spawners = p;
      }
      EventSpace _defaultSpace = this.getDefaultSpace();
      Address _defaultAddress = this.getDefaultAddress();
      ((JaakKernelController) this.controller).initialize(_defaultSpace, _defaultAddress, 
        this.timeManager);
    }
  }
  
  /**
   * Ensures that the behavior _handle_Destroy_2 is called only when the guard <XFeatureCallImplCustom> !== <XNullLiteralImplCustom> is valid.
   */
  public boolean _handle_Destroy_2_Guard(final Destroy occurrence) {
    boolean _tripleNotEquals = (this.physicEnvironment != null);
    return _tripleNotEquals;
  }
  
  @Percept
  public void _handle_Destroy_2(final Destroy occurrence) {
    if ( _handle_Destroy_2_Guard(occurrence)) { 
    this.isWaitingInfluences.set(false);
    boolean _tripleNotEquals = (this.waitingTask != null);
    if (_tripleNotEquals) {
      this.cancel(this.waitingTask);
      this.waitingTask = null;
    }
    this.physicSpace.destroy();
    this.physicSpace = null;
    this.physicEnvironment = null;}
  }
  
  /**
   * Ensures that the behavior _handle_TurtleCreated_3 is called only when the guard <XFeatureCallImplCustom>.isValid is valid.
   */
  public boolean _handle_TurtleCreated_3_Guard(final TurtleCreated occurrence) {
    boolean _isValid = this.isValid(occurrence);
    return _isValid;
  }
  
  @Percept
  public void _handle_TurtleCreated_3(final TurtleCreated occurrence) {
    if ( _handle_TurtleCreated_3_Guard(occurrence)) { 
    /* this.addedAgents; */
    synchronized (this.addedAgents) {
      Address _source = occurrence.getSource();
      UUID _uUID = _source.getUUID();
      this.addedAgents.put(_uUID, occurrence);
    }}
  }
  
  /**
   * Ensures that the behavior _handle_TurtleDestroyed_4 is called only when the guard <XFeatureCallImplCustom>.isValid is valid.
   */
  public boolean _handle_TurtleDestroyed_4_Guard(final TurtleDestroyed occurrence) {
    boolean _isValid = this.isValid(occurrence);
    return _isValid;
  }
  
  @Percept
  public void _handle_TurtleDestroyed_4(final TurtleDestroyed occurrence) {
    if ( _handle_TurtleDestroyed_4_Guard(occurrence)) { 
    /* this.removedAgents; */
    synchronized (this.removedAgents) {
      Address _source = occurrence.getSource();
      UUID _uUID = _source.getUUID();
      this.removedAgents.add(_uUID);
    }}
  }
  
  /**
   * Ensures that the behavior _handle_AgentInfluence_5 is called only when the guard <XFeatureCallImplCustom>.isValid is valid.
   */
  public boolean _handle_AgentInfluence_5_Guard(final AgentInfluence occurrence) {
    boolean _isValid = this.isValid(occurrence);
    return _isValid;
  }
  
  @Percept
  public void _handle_AgentInfluence_5(final AgentInfluence occurrence) {
    if ( _handle_AgentInfluence_5_Guard(occurrence)) { 
    TurtleBody _emitter = occurrence.influence.getEmitter();
    UUID _turtleId = _emitter.getTurtleId();
    TurtleBody body = this.physicEnvironment.getBodyFor(_turtleId);
    boolean _tripleNotEquals = (body != null);
    if (_tripleNotEquals) {
      body.influence(occurrence.influence);
    }}
  }
  
  /**
   * Ensures that the behavior _handle_SimulationStarted_6 is called only when the guard <XFeatureCallImplCustom>.fromMe is valid.
   */
  public boolean _handle_SimulationStarted_6_Guard(final SimulationStarted occurrence) {
    boolean _isFromMe = this.isFromMe(occurrence);
    return _isFromMe;
  }
  
  @Percept
  public void _handle_SimulationStarted_6(final SimulationStarted occurrence) {
    if ( _handle_SimulationStarted_6_Guard(occurrence)) { 
    this.runPreAgentExecution();}
  }
  
  /**
   * Ensures that the behavior _handle_ExecuteSimulationStep_7 is called only when the guard <XFeatureCallImplCustom>.fromMe is valid.
   */
  public boolean _handle_ExecuteSimulationStep_7_Guard(final ExecuteSimulationStep occurrence) {
    boolean _isFromMe = this.isFromMe(occurrence);
    return _isFromMe;
  }
  
  @Percept
  public void _handle_ExecuteSimulationStep_7(final ExecuteSimulationStep occurrence) {
    if ( _handle_ExecuteSimulationStep_7_Guard(occurrence)) { 
    this.runPostAgentExecution();
    this.runPreAgentExecution();}
  }
  
  /**
   * Ensures that the behavior _handle_SimulationStopped_8 is called only when the guard <XFeatureCallImplCustom>.fromMe is valid.
   */
  public boolean _handle_SimulationStopped_8_Guard(final SimulationStopped occurrence) {
    boolean _isFromMe = this.isFromMe(occurrence);
    return _isFromMe;
  }
  
  @Percept
  public void _handle_SimulationStopped_8(final SimulationStopped occurrence) {
    if ( _handle_SimulationStopped_8_Guard(occurrence)) { 
    this.killMe();}
  }
  
  public boolean isValid(final AbstractStampedEvent evt) {
    boolean _and = false;
    boolean _and_1 = false;
    boolean _and_2 = false;
    boolean _tripleNotEquals = (this.physicEnvironment != null);
    if (!_tripleNotEquals) {
      _and_2 = false;
    } else {
      Address _source = evt.getSource();
      SpaceID _spaceId = _source.getSpaceId();
      SpaceID _iD = this.physicSpace.getID();
      boolean _equals = Objects.equal(_spaceId, _iD);
      _and_2 = _equals;
    }
    if (!_and_2) {
      _and_1 = false;
    } else {
      boolean _get = this.isWaitingInfluences.get();
      _and_1 = _get;
    }
    if (!_and_1) {
      _and = false;
    } else {
      float _currentTime = this.timeManager.getCurrentTime();
      boolean _lessEqualsThan = (_currentTime <= evt.currentTime);
      _and = _lessEqualsThan;
    }
    return _and;
  }
  
  /**
   * Run the tasks before the agent executions.
   */
  public void runPreAgentExecution() {
    boolean _tripleNotEquals = (this.spawners != null);
    if (_tripleNotEquals) {
      TurtleBodyFactory factory = this.physicEnvironment.getTurtleBodyFactory();
      for (final JaakSpawner spawner : this.spawners) {
        {
          UUID id = UUID.randomUUID();
          UUID _iD = this.getID();
          TurtleBody body = spawner.spawnBodyFor(id, _iD, factory, 
            this.timeManager, 
            null);
          boolean _tripleNotEquals_1 = (body != null);
          if (_tripleNotEquals_1) {
            Class<? extends Agent> _spawnableAgentType = this.getSpawnableAgentType(spawner);
            AgentContext _defaultContext = this.getDefaultContext();
            this.spawnInContextWithID(_spawnableAgentType, id, _defaultContext);
            float _currentTime = this.timeManager.getCurrentTime();
            float _lastStepDuration = this.timeManager.getLastStepDuration();
            BodyCreated _bodyCreated = new BodyCreated(_currentTime, _lastStepDuration, body);
            this.emit(_bodyCreated);
          }
        }
      }
      /* this.removedAgents; */
      synchronized (this.removedAgents) {
        {
          Iterator<UUID> iterator = this.removedAgents.iterator();
          while (iterator.hasNext()) {
            {
              UUID adr = iterator.next();
              iterator.remove();
              this.physicEnvironment.removeBodyFor(adr);
            }
          }
        }
      }
      /* this.addedAgents; */
      synchronized (this.addedAgents) {
        {
          Set<Map.Entry<UUID, TurtleCreated>> _entrySet = this.addedAgents.entrySet();
          Iterator<Map.Entry<UUID, TurtleCreated>> iterator = _entrySet.iterator();
          AgentBodyCreator creator = new AgentBodyCreator();
          while (iterator.hasNext()) {
            {
              Map.Entry<UUID, TurtleCreated> p = iterator.next();
              TurtleCreated _value = p.getValue();
              creator.set(_value);
              iterator.remove();
              boolean _isPositionForced = creator.isPositionForced(this.physicEnvironment);
              if (_isPositionForced) {
                UUID _key = p.getKey();
                UUID _iD = this.getID();
                this.defaultSpawner.spawnBodyFor(_key, _iD, factory, 
                  this.timeManager, creator);
              } else {
                int _length = this.spawners.length;
                int _nextInt = RandomNumber.nextInt(_length);
                JaakSpawner spawner_1 = this.spawners[_nextInt];
                UUID _key_1 = p.getKey();
                UUID _iD_1 = this.getID();
                TurtleBody body = spawner_1.spawnBodyFor(_key_1, _iD_1, factory, 
                  this.timeManager, creator);
                float _currentTime = this.timeManager.getCurrentTime();
                float _lastStepDuration = this.timeManager.getLastStepDuration();
                BodyCreated _bodyCreated = new BodyCreated(_currentTime, _lastStepDuration, body);
                this.emit(_bodyCreated);
              }
            }
          }
        }
      }
    }
    this.physicEnvironment.runPreTurtles();
    boolean _tripleNotEquals_1 = (this.waitingTask != null);
    if (_tripleNotEquals_1) {
      this.cancel(this.waitingTask);
      this.waitingTask = null;
    }
    final Procedure1<Agent> _function = new Procedure1<Agent>() {
      public void apply(final Agent it) {
        ((JaakKernelController) JaakKernelAgent.this.controller).wakeSimulator();
      }
    };
    AgentTask _in = this.in(this.waitingDuration, _function);
    this.waitingTask = _in;
    this.isWaitingInfluences.set(true);
    final JaakEnvironment.Lambda<TurtleBody> _function_1 = new JaakEnvironment.Lambda<TurtleBody>() {
      public void apply(final TurtleBody it) {
        float _currentTime = JaakKernelAgent.this.timeManager.getCurrentTime();
        float _lastStepDuration = JaakKernelAgent.this.timeManager.getLastStepDuration();
        Perception evt = new Perception(_currentTime, _lastStepDuration, it);
        evt.setSource(JaakKernelAgent.this.defaultAddressInPhysicSpace);
        JaakKernelAgent.this.physicSpace.notifyPerception(evt);
      }
    };
    this.physicEnvironment.apply(_function_1);
  }
  
  /**
   * Run the tasks after the agent executions.
   */
  public void runPostAgentExecution() {
    this.isWaitingInfluences.set(false);
    this.physicEnvironment.runPostTurtles();
    this.timeManager.increment();
  }
  
  /**
   * Create an instance of the time manager that must be used by
   * the Jaak kernel.
   * 
   * @return the instance of time manager.
   */
  public TimeManager createTimeManager() {
    return new DefaultJaakTimeManager();
  }
  
  /**
   * Create the spawners to put on the environment
   * at the start up of the simulation.
   * 
   * @return the start-up spawners.
   */
  public JaakSpawner[] createSpawners() {
    return new JaakSpawner[] {};
  }
  
  /**
   * Create an instance of the environment that must
   * be used by the Jaak kernel.
   * 
   * @return the instance of time manager.
   */
  public JaakEnvironment createEnvironment() {
    throw new UnsupportedOperationException("must be overridden");
  }
  
  /**
   * Replies the type of the agents to spawn.
   * 
   * @param spawner - the spawner that will create the agent.
   */
  public Class<? extends Agent> getSpawnableAgentType(final JaakSpawner spawner) {
    throw new UnsupportedOperationException("must be overridden");
  }
}
