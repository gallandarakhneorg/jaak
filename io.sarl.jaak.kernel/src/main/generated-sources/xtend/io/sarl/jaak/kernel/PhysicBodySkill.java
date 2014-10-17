package io.sarl.jaak.kernel;

import io.sarl.jaak.envinterface.perception.EnvironmentalObject;
import io.sarl.jaak.envinterface.perception.Perceivable;
import io.sarl.jaak.kernel.JaakPhysicSpace;
import io.sarl.jaak.kernel.JaakPhysicSpaceSpecification;
import io.sarl.jaak.kernel.PhysicBody;
import io.sarl.lang.annotation.DefaultValueUse;
import io.sarl.lang.annotation.Generated;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AgentContext;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.EventSpace;
import io.sarl.lang.core.Scope;
import io.sarl.lang.core.Skill;
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
  
  protected JaakPhysicSpace physicSpace;
  
  protected final Point2i position = new Point2i();
  
  public void install() {
    AgentContext _defaultContext = this.getDefaultContext();
    UUID _iD = _defaultContext.getID();
    String _string = _iD.toString();
    String _plus = (_string + "!!!JaakPhysicSpace");
    UUID spaceId = UUID.fromString(_plus);
    AgentContext _defaultContext_1 = this.getDefaultContext();
    Agent _owner = this.getOwner();
    UUID _iD_1 = _owner.getID();
    JaakPhysicSpace _orCreateSpace = _defaultContext_1.<JaakPhysicSpace>getOrCreateSpace(
      JaakPhysicSpaceSpecification.class, spaceId, _iD_1);
    this.physicSpace = _orCreateSpace;
    boolean _tripleEquals = (this.physicSpace == null);
    if (_tripleEquals) {
      throw new IllegalStateException("No physic space found");
    }
    Agent _owner_1 = this.getOwner();
    UUID _iD_2 = _owner_1.getID();
    this.physicSpace.spawnBody(_iD_2, this);
  }
  
  public void uninstall() {
    if (this.physicSpace!=null) {
      Agent _owner = this.getOwner();
      UUID _iD = _owner.getID();
      this.physicSpace.killBody(_iD, this);
    }
    this.physicSpace = null;
  }
  
  public void move(final Vector2f direction, final boolean changeHeading) {
  }
  
  public void moveForward(final int cells) {
  }
  
  public void moveBackward(final int cells) {
  }
  
  public void turnLeft(final float radians) {
  }
  
  public void turnRight(final float radians) {
  }
  
  public void setHeading(final float radians) {
  }
  
  public void setHeading(final Vector2f direction) {
  }
  
  public void dropOff(final EnvironmentalObject object) {
  }
  
  public Perceivable pickUp(final Class<? extends Perceivable> type) {
    return null;
  }
  
  public void pickUp(final EnvironmentalObject object) {
  }
  
  public EnvironmentalObject touchUp(final Class<? extends EnvironmentalObject> type) {
    return null;
  }
  
  public void setSemantic(final Object semantic) {
  }
  
  public Point2i getPosition() {
    return this.position;
  }
  
  public float getHeadingAngle() {
    return 0.0f;
  }
  
  public Vector2f getHeadingVector() {
    return null;
  }
  
  public float getOrientation() {
    return 0.0f;
  }
  
  public Object getSemantic() {
    return null;
  }
  
  public float getSpeed() {
    return 0.0f;
  }
  
  public Vector2f getViewVector() {
    return null;
  }
  
  public int getX() {
    return this.position.x();
  }
  
  public int getY() {
    return this.position.y();
  }
  
  public Vector2f getOrientationVector() {
    return null;
  }
  
  @DefaultValueUse("org.arakhne.afc.math.continous.object2d.Vector2f,boolean")
  public final void move(final Vector2f direction) {
    move(direction, io.sarl.jaak.kernel.PhysicBody.___FORMAL_PARAMETER_DEFAULT_VALUE_0_1);
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
