package io.sarl.jaak.demos.mdtraffic.behaviors;

import io.sarl.core.AgentKilled;
import io.sarl.core.AgentSpawned;
import io.sarl.core.Destroy;
import io.sarl.core.Lifecycle;
import io.sarl.jaak.demos.mdtraffic.environment.communication.CommunicationEvent;
import io.sarl.jaak.demos.mdtraffic.environment.communication.EmergencyDetected;
import io.sarl.jaak.demos.mdtraffic.environment.communication.EmergencyResolved;
import io.sarl.jaak.demos.mdtraffic.environment.communication.EmergencyVehicleActivated;
import io.sarl.jaak.demos.mdtraffic.environment.communication.PriorityRequest;
import io.sarl.jaak.demos.traffic.behaviors.DrivingCapacity;
import io.sarl.jaak.demos.traffic.behaviors.GPSCapacity;
import io.sarl.jaak.demos.traffic.environment.GroundType;
import io.sarl.jaak.demos.traffic.environment.Path;
import io.sarl.jaak.environment.Perception;
import io.sarl.jaak.environment.PhysicBody;
import io.sarl.jaak.environment.body.TurtleObject;
import io.sarl.jaak.environment.perception.EnvironmentalObject;
import io.sarl.jaak.environment.perception.Perceivable;
import io.sarl.lang.annotation.EarlyExit;
import io.sarl.lang.annotation.FiredEvent;
import io.sarl.lang.annotation.Generated;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AgentContext;
import io.sarl.lang.core.Behavior;
import io.sarl.lang.core.Percept;
import io.sarl.util.OpenEventSpace;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import org.arakhne.afc.math.continous.object2d.Vector2f;
import org.arakhne.afc.math.discrete.object2d.Point2i;
import org.arakhne.afc.math.discrete.object2d.Vector2i;

/**
 * This class defines an emergency vehicle driver.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public class EmergencyDriver extends Behavior {
  protected Vector2i previousOrientation;
  
  protected Path path;
  
  protected Point2i target;
  
  protected final AtomicBoolean emergencyDetected = new AtomicBoolean(false);
  
  protected final OpenEventSpace outputSpace;
  
  protected final Address address;
  
  public EmergencyDriver(final Agent owner, final OpenEventSpace outputSpace, final Address address) {
    super(owner);
    this.outputSpace = outputSpace;
    this.address = address;
  }
  
  public void emit(final CommunicationEvent evt) {
    evt.setSource(this.address);
    this.outputSpace.emit(evt);
  }
  
  @Generated
  private boolean _guard_EmergencyDetected_0(final EmergencyDetected occurrence) {
    boolean _get = this.emergencyDetected.get();
    boolean _not = (!_get);
    return _not;
  }
  
  @Percept
  public void _handle_EmergencyDetected_0(final EmergencyDetected occurrence) {
    if (_guard_EmergencyDetected_0(occurrence)) {
      this.getOwner();
      synchronized (this.getOwner()) {
        {
          Point2i _clone = occurrence.position.clone();
          this.target = _clone;
          float _headingAngle = this.getHeadingAngle();
          Vector2i direction = Vector2i.toOrientationVector(_headingAngle);
          Point2i _position = this.getPosition();
          Path _updatePath = this.updatePath(this.path, _position, this.target, direction);
          this.path = _updatePath;
          this.emergencyDetected.set(true);
          EmergencyVehicleActivated _emergencyVehicleActivated = new EmergencyVehicleActivated(this.path);
          this.emit(_emergencyVehicleActivated);
        }
      }
    }
  }
  
  @Generated
  private boolean _guard_Perception_1(final Perception occurrence) {
    boolean _get = this.emergencyDetected.get();
    return _get;
  }
  
  @Percept
  public void _handle_Perception_1(final Perception occurrence) {
    if (_guard_Perception_1(occurrence)) {
      this.getOwner();
      synchronized (this.getOwner()) {
        {
          Serializable groundType = occurrence.body.getCurrentGroundType();
          if ((groundType == GroundType.EMERGENCY_LOCATION)) {
            EmergencyResolved _emergencyResolved = new EmergencyResolved(this.target);
            this.emit(_emergencyResolved);
            this.target = null;
            this.path = null;
            this.killMe();
          }
          float _headingAngle = occurrence.body.getHeadingAngle();
          Vector2i direction = Vector2i.toOrientationVector(_headingAngle);
          Point2i _position = occurrence.body.getPosition();
          Path _updatePath = this.updatePath(this.path, _position, this.target, direction);
          this.path = _updatePath;
          Vector2i motion = null;
          boolean _and = false;
          if (!(this.path != null)) {
            _and = false;
          } else {
            boolean _isEmpty = this.path.isEmpty();
            boolean _not = (!_isEmpty);
            _and = _not;
          }
          if (_and) {
            Point2i _position_1 = occurrence.body.getPosition();
            Collection<Perceivable> _perception = occurrence.body.getPerception();
            Map<Vector2i, Serializable> _groundPerception = occurrence.body.getGroundPerception();
            Vector2i _followPath = this.followPath(
              this.path, _position_1, direction, 
              this.previousOrientation, _perception, _groundPerception);
            motion = _followPath;
          }
          boolean _and_1 = false;
          if (!(motion != null)) {
            _and_1 = false;
          } else {
            float _lengthSquared = motion.lengthSquared();
            boolean _greaterThan = (_lengthSquared > 0);
            _and_1 = _greaterThan;
          }
          if (_and_1) {
            this.move(motion, true);
            this.previousOrientation = direction;
          }
          PriorityRequest _priorityRequest = new PriorityRequest();
          this.emit(_priorityRequest);
        }
      }
      this.synchronizeBody();
    }
  }
  
  @Generated
  private boolean _guard_Perception_2(final Perception occurrence) {
    boolean _get = this.emergencyDetected.get();
    boolean _not = (!_get);
    return _not;
  }
  
  @Percept
  public void _handle_Perception_2(final Perception occurrence) {
    if (_guard_Perception_2(occurrence)) {
      this.synchronizeBody();
    }
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.demos.traffic.behaviors.DrivingCapacity#driveRandomly(org.arakhne.afc.math.discrete.object2d.Point2i,org.arakhne.afc.math.discrete.object2d.Vector2i,org.arakhne.afc.math.discrete.object2d.Vector2i,java.lang.Iterable<? extends io.sarl.jaak.environment.perception.Perceivable>,java.util.Map<org.arakhne.afc.math.discrete.object2d.Vector2i, java.io.Serializable>)}.
   * 
   * @see io.sarl.jaak.demos.traffic.behaviors.DrivingCapacity#driveRandomly(org.arakhne.afc.math.discrete.object2d.Point2i,org.arakhne.afc.math.discrete.object2d.Vector2i,org.arakhne.afc.math.discrete.object2d.Vector2i,java.lang.Iterable<? extends io.sarl.jaak.environment.perception.Perceivable>,java.util.Map<org.arakhne.afc.math.discrete.object2d.Vector2i, java.io.Serializable>)
   */
  @Generated
  @ImportedCapacityFeature(DrivingCapacity.class)
  protected Vector2i driveRandomly(final Point2i position, final Vector2i orientation, final Vector2i previousOrientation, final Iterable<? extends Perceivable> perception, final Map<Vector2i, Serializable> ground) {
    return getSkill(io.sarl.jaak.demos.traffic.behaviors.DrivingCapacity.class).driveRandomly(position, orientation, previousOrientation, perception, ground);
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.demos.traffic.behaviors.DrivingCapacity#followPath(io.sarl.jaak.demos.traffic.environment.Path,org.arakhne.afc.math.discrete.object2d.Point2i,org.arakhne.afc.math.discrete.object2d.Vector2i,org.arakhne.afc.math.discrete.object2d.Vector2i,java.lang.Iterable<? extends io.sarl.jaak.environment.perception.Perceivable>,java.util.Map<org.arakhne.afc.math.discrete.object2d.Vector2i, java.io.Serializable>)}.
   * 
   * @see io.sarl.jaak.demos.traffic.behaviors.DrivingCapacity#followPath(io.sarl.jaak.demos.traffic.environment.Path,org.arakhne.afc.math.discrete.object2d.Point2i,org.arakhne.afc.math.discrete.object2d.Vector2i,org.arakhne.afc.math.discrete.object2d.Vector2i,java.lang.Iterable<? extends io.sarl.jaak.environment.perception.Perceivable>,java.util.Map<org.arakhne.afc.math.discrete.object2d.Vector2i, java.io.Serializable>)
   */
  @Generated
  @ImportedCapacityFeature(DrivingCapacity.class)
  protected Vector2i followPath(final Path path, final Point2i position, final Vector2i orientation, final Vector2i previousOrientation, final Iterable<? extends Perceivable> perception, final Map<Vector2i, Serializable> ground) {
    return getSkill(io.sarl.jaak.demos.traffic.behaviors.DrivingCapacity.class).followPath(path, position, orientation, previousOrientation, perception, ground);
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.demos.traffic.behaviors.DrivingCapacity#isVehicleStop(io.sarl.jaak.environment.body.TurtleObject)}.
   * 
   * @see io.sarl.jaak.demos.traffic.behaviors.DrivingCapacity#isVehicleStop(io.sarl.jaak.environment.body.TurtleObject)
   */
  @Generated
  @ImportedCapacityFeature(DrivingCapacity.class)
  protected boolean isVehicleStop(final TurtleObject body) {
    return getSkill(io.sarl.jaak.demos.traffic.behaviors.DrivingCapacity.class).isVehicleStop(body);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Lifecycle#killMe()}.
   * 
   * @see io.sarl.core.Lifecycle#killMe()
   */
  @EarlyExit
  @FiredEvent({ AgentKilled.class, Destroy.class })
  @Generated
  @ImportedCapacityFeature(Lifecycle.class)
  protected void killMe() {
    getSkill(io.sarl.core.Lifecycle.class).killMe();
  }
  
  /**
   * See the capacity {@link io.sarl.core.Lifecycle#spawnInContext(java.lang.Class<? extends io.sarl.lang.core.Agent>,io.sarl.lang.core.AgentContext,java.lang.Object[])}.
   * 
   * @see io.sarl.core.Lifecycle#spawnInContext(java.lang.Class<? extends io.sarl.lang.core.Agent>,io.sarl.lang.core.AgentContext,java.lang.Object[])
   */
  @FiredEvent(AgentSpawned.class)
  @Generated
  @ImportedCapacityFeature(Lifecycle.class)
  protected UUID spawnInContext(final Class<? extends Agent> agentClass, final AgentContext context, final Object... params) {
    return getSkill(io.sarl.core.Lifecycle.class).spawnInContext(agentClass, context, params);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Lifecycle#spawnInContextWithID(java.lang.Class<? extends io.sarl.lang.core.Agent>,java.util.UUID,io.sarl.lang.core.AgentContext,java.lang.Object[])}.
   * 
   * @see io.sarl.core.Lifecycle#spawnInContextWithID(java.lang.Class<? extends io.sarl.lang.core.Agent>,java.util.UUID,io.sarl.lang.core.AgentContext,java.lang.Object[])
   */
  @FiredEvent(AgentSpawned.class)
  @Generated
  @ImportedCapacityFeature(Lifecycle.class)
  protected UUID spawnInContextWithID(final Class<? extends Agent> agentClass, final UUID agentID, final AgentContext context, final Object... params) {
    return getSkill(io.sarl.core.Lifecycle.class).spawnInContextWithID(agentClass, agentID, context, params);
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.environment.PhysicBody#dropOff(io.sarl.jaak.environment.perception.EnvironmentalObject)}.
   * 
   * @see io.sarl.jaak.environment.PhysicBody#dropOff(io.sarl.jaak.environment.perception.EnvironmentalObject)
   */
  @Generated
  @ImportedCapacityFeature(PhysicBody.class)
  protected void dropOff(final EnvironmentalObject object) {
    getSkill(io.sarl.jaak.environment.PhysicBody.class).dropOff(object);
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.environment.PhysicBody#getHeadingAngle()}.
   * 
   * @see io.sarl.jaak.environment.PhysicBody#getHeadingAngle()
   */
  @Generated
  @ImportedCapacityFeature(PhysicBody.class)
  protected float getHeadingAngle() {
    return getSkill(io.sarl.jaak.environment.PhysicBody.class).getHeadingAngle();
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.environment.PhysicBody#getHeadingVector()}.
   * 
   * @see io.sarl.jaak.environment.PhysicBody#getHeadingVector()
   */
  @Generated
  @ImportedCapacityFeature(PhysicBody.class)
  protected Vector2f getHeadingVector() {
    return getSkill(io.sarl.jaak.environment.PhysicBody.class).getHeadingVector();
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.environment.PhysicBody#getPosition()}.
   * 
   * @see io.sarl.jaak.environment.PhysicBody#getPosition()
   */
  @Generated
  @ImportedCapacityFeature(PhysicBody.class)
  protected Point2i getPosition() {
    return getSkill(io.sarl.jaak.environment.PhysicBody.class).getPosition();
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.environment.PhysicBody#getSemantic()}.
   * 
   * @see io.sarl.jaak.environment.PhysicBody#getSemantic()
   */
  @Generated
  @ImportedCapacityFeature(PhysicBody.class)
  protected Serializable getSemantic() {
    return getSkill(io.sarl.jaak.environment.PhysicBody.class).getSemantic();
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.environment.PhysicBody#getSpeed()}.
   * 
   * @see io.sarl.jaak.environment.PhysicBody#getSpeed()
   */
  @Generated
  @ImportedCapacityFeature(PhysicBody.class)
  protected float getSpeed() {
    return getSkill(io.sarl.jaak.environment.PhysicBody.class).getSpeed();
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.environment.PhysicBody#getX()}.
   * 
   * @see io.sarl.jaak.environment.PhysicBody#getX()
   */
  @Generated
  @ImportedCapacityFeature(PhysicBody.class)
  protected int getX() {
    return getSkill(io.sarl.jaak.environment.PhysicBody.class).getX();
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.environment.PhysicBody#getY()}.
   * 
   * @see io.sarl.jaak.environment.PhysicBody#getY()
   */
  @Generated
  @ImportedCapacityFeature(PhysicBody.class)
  protected int getY() {
    return getSkill(io.sarl.jaak.environment.PhysicBody.class).getY();
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.environment.PhysicBody#move(org.arakhne.afc.math.continous.object2d.Vector2f)}.
   * 
   * @see io.sarl.jaak.environment.PhysicBody#move(org.arakhne.afc.math.continous.object2d.Vector2f)
   */
  @Generated
  @ImportedCapacityFeature(PhysicBody.class)
  protected void move(final Vector2f direction) {
    getSkill(io.sarl.jaak.environment.PhysicBody.class).move(direction);
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.environment.PhysicBody#move(org.arakhne.afc.math.discrete.object2d.Vector2i)}.
   * 
   * @see io.sarl.jaak.environment.PhysicBody#move(org.arakhne.afc.math.discrete.object2d.Vector2i)
   */
  @Generated
  @ImportedCapacityFeature(PhysicBody.class)
  protected void move(final Vector2i direction) {
    getSkill(io.sarl.jaak.environment.PhysicBody.class).move(direction);
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.environment.PhysicBody#move(org.arakhne.afc.math.continous.object2d.Vector2f,boolean)}.
   * 
   * @see io.sarl.jaak.environment.PhysicBody#move(org.arakhne.afc.math.continous.object2d.Vector2f,boolean)
   */
  @Generated
  @ImportedCapacityFeature(PhysicBody.class)
  protected void move(final Vector2f direction, final boolean changeHeading) {
    getSkill(io.sarl.jaak.environment.PhysicBody.class).move(direction, changeHeading);
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.environment.PhysicBody#move(org.arakhne.afc.math.discrete.object2d.Vector2i,boolean)}.
   * 
   * @see io.sarl.jaak.environment.PhysicBody#move(org.arakhne.afc.math.discrete.object2d.Vector2i,boolean)
   */
  @Generated
  @ImportedCapacityFeature(PhysicBody.class)
  protected void move(final Vector2i direction, final boolean changeHeading) {
    getSkill(io.sarl.jaak.environment.PhysicBody.class).move(direction, changeHeading);
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.environment.PhysicBody#moveBackward(int)}.
   * 
   * @see io.sarl.jaak.environment.PhysicBody#moveBackward(int)
   */
  @Generated
  @ImportedCapacityFeature(PhysicBody.class)
  protected void moveBackward(final int cells) {
    getSkill(io.sarl.jaak.environment.PhysicBody.class).moveBackward(cells);
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.environment.PhysicBody#moveForward(int)}.
   * 
   * @see io.sarl.jaak.environment.PhysicBody#moveForward(int)
   */
  @Generated
  @ImportedCapacityFeature(PhysicBody.class)
  protected void moveForward(final int cells) {
    getSkill(io.sarl.jaak.environment.PhysicBody.class).moveForward(cells);
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.environment.PhysicBody#pickUp(io.sarl.jaak.environment.perception.EnvironmentalObject)}.
   * 
   * @see io.sarl.jaak.environment.PhysicBody#pickUp(io.sarl.jaak.environment.perception.EnvironmentalObject)
   */
  @Generated
  @ImportedCapacityFeature(PhysicBody.class)
  protected void pickUp(final EnvironmentalObject object) {
    getSkill(io.sarl.jaak.environment.PhysicBody.class).pickUp(object);
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.environment.PhysicBody#pickUp(java.lang.Class<? extends io.sarl.jaak.environment.perception.Perceivable>)}.
   * 
   * @see io.sarl.jaak.environment.PhysicBody#pickUp(java.lang.Class<? extends io.sarl.jaak.environment.perception.Perceivable>)
   */
  @Generated
  @ImportedCapacityFeature(PhysicBody.class)
  protected Perceivable pickUp(final Class<? extends Perceivable> type) {
    return getSkill(io.sarl.jaak.environment.PhysicBody.class).pickUp(type);
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.environment.PhysicBody#setHeading(float)}.
   * 
   * @see io.sarl.jaak.environment.PhysicBody#setHeading(float)
   */
  @Generated
  @ImportedCapacityFeature(PhysicBody.class)
  protected void setHeading(final float radians) {
    getSkill(io.sarl.jaak.environment.PhysicBody.class).setHeading(radians);
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.environment.PhysicBody#setHeading(org.arakhne.afc.math.continous.object2d.Vector2f)}.
   * 
   * @see io.sarl.jaak.environment.PhysicBody#setHeading(org.arakhne.afc.math.continous.object2d.Vector2f)
   */
  @Generated
  @ImportedCapacityFeature(PhysicBody.class)
  protected void setHeading(final Vector2f direction) {
    getSkill(io.sarl.jaak.environment.PhysicBody.class).setHeading(direction);
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.environment.PhysicBody#setHeading(org.arakhne.afc.math.discrete.object2d.Vector2i)}.
   * 
   * @see io.sarl.jaak.environment.PhysicBody#setHeading(org.arakhne.afc.math.discrete.object2d.Vector2i)
   */
  @Generated
  @ImportedCapacityFeature(PhysicBody.class)
  protected void setHeading(final Vector2i direction) {
    getSkill(io.sarl.jaak.environment.PhysicBody.class).setHeading(direction);
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.environment.PhysicBody#setSemantic(java.io.Serializable)}.
   * 
   * @see io.sarl.jaak.environment.PhysicBody#setSemantic(java.io.Serializable)
   */
  @Generated
  @ImportedCapacityFeature(PhysicBody.class)
  protected void setSemantic(final Serializable semantic) {
    getSkill(io.sarl.jaak.environment.PhysicBody.class).setSemantic(semantic);
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.environment.PhysicBody#synchronizeBody()}.
   * 
   * @see io.sarl.jaak.environment.PhysicBody#synchronizeBody()
   */
  @Generated
  @ImportedCapacityFeature(PhysicBody.class)
  protected void synchronizeBody() {
    getSkill(io.sarl.jaak.environment.PhysicBody.class).synchronizeBody();
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.environment.PhysicBody#touchUp(java.lang.Class<? extends io.sarl.jaak.environment.perception.EnvironmentalObject>)}.
   * 
   * @see io.sarl.jaak.environment.PhysicBody#touchUp(java.lang.Class<? extends io.sarl.jaak.environment.perception.EnvironmentalObject>)
   */
  @Generated
  @ImportedCapacityFeature(PhysicBody.class)
  protected EnvironmentalObject touchUp(final Class<? extends EnvironmentalObject> type) {
    return getSkill(io.sarl.jaak.environment.PhysicBody.class).touchUp(type);
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.environment.PhysicBody#turnLeft(float)}.
   * 
   * @see io.sarl.jaak.environment.PhysicBody#turnLeft(float)
   */
  @Generated
  @ImportedCapacityFeature(PhysicBody.class)
  protected void turnLeft(final float radians) {
    getSkill(io.sarl.jaak.environment.PhysicBody.class).turnLeft(radians);
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.environment.PhysicBody#turnRight(float)}.
   * 
   * @see io.sarl.jaak.environment.PhysicBody#turnRight(float)
   */
  @Generated
  @ImportedCapacityFeature(PhysicBody.class)
  protected void turnRight(final float radians) {
    getSkill(io.sarl.jaak.environment.PhysicBody.class).turnRight(radians);
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.demos.traffic.behaviors.GPSCapacity#findPath(org.arakhne.afc.math.discrete.object2d.Point2i,org.arakhne.afc.math.discrete.object2d.Point2i,org.arakhne.afc.math.discrete.object2d.Vector2i)}.
   * 
   * @see io.sarl.jaak.demos.traffic.behaviors.GPSCapacity#findPath(org.arakhne.afc.math.discrete.object2d.Point2i,org.arakhne.afc.math.discrete.object2d.Point2i,org.arakhne.afc.math.discrete.object2d.Vector2i)
   */
  @Generated
  @ImportedCapacityFeature(GPSCapacity.class)
  protected Path findPath(final Point2i from, final Point2i to, final Vector2i vehicleDirection) {
    return getSkill(io.sarl.jaak.demos.traffic.behaviors.GPSCapacity.class).findPath(from, to, vehicleDirection);
  }
  
  /**
   * See the capacity {@link io.sarl.jaak.demos.traffic.behaviors.GPSCapacity#updatePath(io.sarl.jaak.demos.traffic.environment.Path,org.arakhne.afc.math.discrete.object2d.Point2i,org.arakhne.afc.math.discrete.object2d.Point2i,org.arakhne.afc.math.discrete.object2d.Vector2i)}.
   * 
   * @see io.sarl.jaak.demos.traffic.behaviors.GPSCapacity#updatePath(io.sarl.jaak.demos.traffic.environment.Path,org.arakhne.afc.math.discrete.object2d.Point2i,org.arakhne.afc.math.discrete.object2d.Point2i,org.arakhne.afc.math.discrete.object2d.Vector2i)
   */
  @Generated
  @ImportedCapacityFeature(GPSCapacity.class)
  protected Path updatePath(final Path path, final Point2i from, final Point2i to, final Vector2i vehicleDirection) {
    return getSkill(io.sarl.jaak.demos.traffic.behaviors.GPSCapacity.class).updatePath(path, from, to, vehicleDirection);
  }
}
