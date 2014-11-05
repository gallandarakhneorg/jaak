package io.sarl.jaak.environment.external;

import com.google.common.base.Objects;
import io.sarl.jaak.environment.external.PhysicBody;
import io.sarl.jaak.environment.external.body.TurtleBody;
import io.sarl.jaak.environment.external.influence.DropDownInfluence;
import io.sarl.jaak.environment.external.influence.MotionInfluence;
import io.sarl.jaak.environment.external.influence.PickUpInfluence;
import io.sarl.jaak.environment.external.influence.SemanticChangeInfluence;
import io.sarl.jaak.environment.external.perception.EnvironmentalObject;
import io.sarl.jaak.environment.external.perception.Perceivable;
import io.sarl.jaak.kernel.external.JaakPhysicSpace;
import io.sarl.jaak.kernel.external.JaakPhysicSpaceConstants;
import io.sarl.jaak.kernel.internal.JaakPhysicSpaceSpecification;
import io.sarl.jaak.kernel.internal.SkillBinder;
import io.sarl.lang.annotation.DefaultValueUse;
import io.sarl.lang.annotation.Generated;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AgentContext;
import io.sarl.lang.core.Behavior;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.EventListener;
import io.sarl.lang.core.EventSpace;
import io.sarl.lang.core.Scope;
import io.sarl.lang.core.Skill;
import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;
import org.arakhne.afc.math.continous.object2d.Vector2f;
import org.arakhne.afc.math.discrete.object2d.Point2i;

@SuppressWarnings("all")
public class PhysicBodySkill extends Skill implements PhysicBody {
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
  
  protected JaakPhysicSpace physicSpace;
  
  protected SkillBinder binder;
  
  public void install() {
    AgentContext dc = this.getDefaultContext();
    UUID spaceId = JaakPhysicSpaceConstants.getSpaceIDInContext(dc);
    JaakPhysicSpace _orCreateSpace = dc.<JaakPhysicSpace>getOrCreateSpace(JaakPhysicSpaceSpecification.class, spaceId);
    this.physicSpace = _orCreateSpace;
    boolean _tripleEquals = (this.physicSpace == null);
    if (_tripleEquals) {
      throw new IllegalStateException("No physic space found");
    }
    EventListener _asEventListener = this.asEventListener();
    SkillBinder _skillBinder = new SkillBinder(_asEventListener);
    this.binder = _skillBinder;
    this.physicSpace.spawnBody(this.binder);
  }
  
  public void uninstall() {
    if (this.physicSpace!=null) {
      this.physicSpace.killBody(this.binder);
    }
    this.physicSpace = null;
    this.binder = null;
  }
  
  public void move(final Vector2f direction, final boolean changeHeading) {
    float _currentTime = this.binder.getCurrentTime();
    TurtleBody _body = this.binder.getBody();
    MotionInfluence _motionInfluence = new MotionInfluence(_body, direction);
    this.physicSpace.influence(_currentTime, _motionInfluence);
    if (changeHeading) {
      this.setHeading(direction);
    }
  }
  
  public void moveForward(final int cells) {
    TurtleBody body = this.binder.getBody();
    Vector2f _headingVector = body.getHeadingVector();
    Vector2f view = _headingVector.clone();
    view.normalize();
    view.scale(cells);
    float _currentTime = this.binder.getCurrentTime();
    MotionInfluence _motionInfluence = new MotionInfluence(body, view);
    this.physicSpace.influence(_currentTime, _motionInfluence);
  }
  
  public void moveBackward(final int cells) {
    TurtleBody body = this.binder.getBody();
    Vector2f _headingVector = body.getHeadingVector();
    Vector2f view = _headingVector.clone();
    view.normalize();
    view.scale((-cells));
    float _currentTime = this.binder.getCurrentTime();
    MotionInfluence _motionInfluence = new MotionInfluence(body, view);
    this.physicSpace.influence(_currentTime, _motionInfluence);
  }
  
  public void turnLeft(final float radians) {
    float _currentTime = this.binder.getCurrentTime();
    TurtleBody _body = this.binder.getBody();
    MotionInfluence _motionInfluence = new MotionInfluence(_body, (-radians));
    this.physicSpace.influence(_currentTime, _motionInfluence);
  }
  
  public void turnRight(final float radians) {
    float _currentTime = this.binder.getCurrentTime();
    TurtleBody _body = this.binder.getBody();
    MotionInfluence _motionInfluence = new MotionInfluence(_body, radians);
    this.physicSpace.influence(_currentTime, _motionInfluence);
  }
  
  public void setHeading(final float radians) {
    TurtleBody body = this.binder.getBody();
    float _headingAngle = body.getHeadingAngle();
    float v = (radians - _headingAngle);
    float _currentTime = this.binder.getCurrentTime();
    MotionInfluence _motionInfluence = new MotionInfluence(body, v);
    this.physicSpace.influence(_currentTime, _motionInfluence);
  }
  
  public void setHeading(final Vector2f direction) {
    float _orientationAngle = direction.getOrientationAngle();
    this.setHeading(_orientationAngle);
  }
  
  public void dropOff(final EnvironmentalObject object) {
    float _currentTime = this.binder.getCurrentTime();
    TurtleBody _body = this.binder.getBody();
    DropDownInfluence _dropDownInfluence = new DropDownInfluence(_body, object);
    this.physicSpace.influence(_currentTime, _dropDownInfluence);
  }
  
  public Perceivable pickUp(final Class<? extends Perceivable> type) {
    TurtleBody body = this.binder.getBody();
    Collection<EnvironmentalObject> _perceivedObjects = body.getPerceivedObjects();
    for (final EnvironmentalObject obj : _perceivedObjects) {
      boolean _and = false;
      boolean _isInstance = type.isInstance(obj);
      if (!_isInstance) {
        _and = false;
      } else {
        Point2i _position = body.getPosition();
        Point2i _position_1 = obj.getPosition();
        boolean _equals = Objects.equal(_position, _position_1);
        _and = _equals;
      }
      if (_and) {
        float _currentTime = this.binder.getCurrentTime();
        PickUpInfluence _pickUpInfluence = new PickUpInfluence(body, obj);
        this.physicSpace.influence(_currentTime, _pickUpInfluence);
        return type.cast(obj);
      }
    }
    return null;
  }
  
  public void pickUp(final EnvironmentalObject object) {
    float _currentTime = this.binder.getCurrentTime();
    TurtleBody _body = this.binder.getBody();
    PickUpInfluence _pickUpInfluence = new PickUpInfluence(_body, object);
    this.physicSpace.influence(_currentTime, _pickUpInfluence);
  }
  
  public EnvironmentalObject touchUp(final Class<? extends EnvironmentalObject> type) {
    TurtleBody _body = this.binder.getBody();
    Collection<EnvironmentalObject> _perceivedObjects = _body.getPerceivedObjects();
    for (final EnvironmentalObject obj : _perceivedObjects) {
      boolean _and = false;
      boolean _isInstance = type.isInstance(obj);
      if (!_isInstance) {
        _and = false;
      } else {
        Point2i _position = this.getPosition();
        Point2i _position_1 = obj.getPosition();
        boolean _equals = Objects.equal(_position, _position_1);
        _and = _equals;
      }
      if (_and) {
        return type.cast(obj);
      }
    }
    return null;
  }
  
  public void setSemantic(final Serializable semantic) {
    float _currentTime = this.binder.getCurrentTime();
    SemanticChangeInfluence _semanticChangeInfluence = new SemanticChangeInfluence(semantic);
    this.physicSpace.influence(_currentTime, _semanticChangeInfluence);
  }
  
  public Point2i getPosition() {
    TurtleBody _body = this.binder.getBody();
    return _body.getPosition();
  }
  
  public float getHeadingAngle() {
    TurtleBody _body = this.binder.getBody();
    return _body.getHeadingAngle();
  }
  
  public Vector2f getHeadingVector() {
    TurtleBody _body = this.binder.getBody();
    return _body.getHeadingVector();
  }
  
  public Serializable getSemantic() {
    TurtleBody _body = this.binder.getBody();
    return _body.getSemantic();
  }
  
  public float getSpeed() {
    TurtleBody _body = this.binder.getBody();
    return _body.getSpeed();
  }
  
  public int getX() {
    Point2i _position = this.getPosition();
    return _position.x();
  }
  
  public int getY() {
    Point2i _position = this.getPosition();
    return _position.y();
  }
  
  @DefaultValueUse("org.arakhne.afc.math.continous.object2d.Vector2f,boolean")
  public final void move(final Vector2f direction) {
    move(direction, io.sarl.jaak.environment.external.PhysicBody.___FORMAL_PARAMETER_DEFAULT_VALUE_0_1);
  }
  
  /**
   * Construct a skill.
   * @param owner - agent that is owning this skill.
   */
  @Generated
  public PhysicBodySkill(final Agent owner) {
    super(owner);
  }
  
  /**
   * Construct a skill. The owning agent is unknown.
   */
  @Generated
  public PhysicBodySkill() {
    super();
  }
}
