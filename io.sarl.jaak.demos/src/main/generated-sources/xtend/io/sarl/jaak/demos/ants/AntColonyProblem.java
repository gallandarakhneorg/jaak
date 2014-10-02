package io.sarl.jaak.demos.ants;

import io.sarl.core.Initialize;
import io.sarl.jaak.demos.ants.Ant;
import io.sarl.jaak.demos.ants.AntColonyConstants;
import io.sarl.jaak.demos.ants.environment.AntColony;
import io.sarl.jaak.demos.ants.environment.Food;
import io.sarl.jaak.demos.ants.spawn.AntColonySpawner;
import io.sarl.jaak.demos.ants.ui.AntFrame;
import io.sarl.jaak.demos.ants.ui.AntPanel;
import io.sarl.jaak.environment.model.JaakEnvironment;
import io.sarl.jaak.environment.solver.ActionApplier;
import io.sarl.jaak.kernel.JaakKernelAgent;
import io.sarl.jaak.spawner.JaakSpawner;
import io.sarl.jaak.util.RandomNumber;
import io.sarl.lang.annotation.Generated;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.Percept;
import java.util.Comparator;
import java.util.Set;
import java.util.UUID;
import org.arakhne.afc.math.discrete.object2d.Point2i;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;

/**
 * This class defines the simulation environment for the ant colony problem.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public class AntColonyProblem extends JaakKernelAgent {
  /**
   * Construct an agent.
   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.
   */
  @Generated
  public AntColonyProblem(final UUID parentID) {
    super(parentID, null);
  }
  
  /**
   * Construct an agent.
   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.
   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.
   */
  @Generated
  public AntColonyProblem(final UUID parentID, final UUID agentID) {
    super(parentID, agentID);
  }
  
  protected boolean isWrappedEnvironment = true;
  
  protected final Set<Point2i> positions = CollectionLiterals.<Point2i>newTreeSet(
    new Comparator<Point2i>() {
      public int compare(final Point2i o1, final Point2i o2) {
        boolean _tripleEquals = (o1 == o2);
        if (_tripleEquals) {
          return 0;
        }
        boolean _tripleEquals_1 = (o1 == null);
        if (_tripleEquals_1) {
          return Integer.MIN_VALUE;
        }
        boolean _tripleEquals_2 = (o2 == null);
        if (_tripleEquals_2) {
          return Integer.MAX_VALUE;
        }
        int _x = o1.x();
        int _x_1 = o2.x();
        int cmp = (_x - _x_1);
        if ((cmp != 0)) {
          return cmp;
        }
        int _y = o1.y();
        int _y_1 = o2.y();
        return (_y - _y_1);
      }
    });
  
  @Percept
  public void _handle_Initialize_1(final Initialize occurrence) {
    super._handle_Initialize_1(occurrence);
    AntPanel uiPanel = new AntPanel();
    this.addJaakListener(uiPanel);
    AntFrame uiFrame = new AntFrame(uiPanel, AntColonyConstants.WIDTH, AntColonyConstants.HEIGHT, this.controller);
    this.addJaakListener(uiFrame);
    uiFrame.setVisible(true);
    this.controller.startSimulation();
  }
  
  /**
   * Create an ant colony and the associated spawner.
   * 
   * @param colonyId is the identifier of the colony to create.
   * @return an instance of the spawner.
   */
  public JaakSpawner createColony(final int colonyId) {
    ActionApplier actionApplier = this.physicEnvironment.getActionApplier();
    int _width = this.physicEnvironment.getWidth();
    int _nextInt = RandomNumber.nextInt(_width);
    int _height = this.physicEnvironment.getHeight();
    int _nextInt_1 = RandomNumber.nextInt(_height);
    Point2i position = new Point2i(_nextInt, _nextInt_1);
    while (this.positions.contains(position)) {
      int _width_1 = this.physicEnvironment.getWidth();
      int _nextInt_2 = RandomNumber.nextInt(_width_1);
      int _height_1 = this.physicEnvironment.getHeight();
      int _nextInt_3 = RandomNumber.nextInt(_height_1);
      position.set(_nextInt_2, _nextInt_3);
    }
    this.positions.add(position);
    AntColony antColonyObject = new AntColony(colonyId);
    int _x = position.x();
    int _y = position.y();
    actionApplier.putObject(_x, _y, antColonyObject);
    int _x_1 = position.x();
    int _y_1 = position.y();
    return new AntColonySpawner(
      AntColonyConstants.ANT_COLONY_PATROLLER_POPULATION, 
      AntColonyConstants.ANT_COLONY_FORAGER_POPULATION, _x_1, _y_1);
  }
  
  /**
   * Create the spawners to put on the environment
   * at the start up of the simulation.
   * 
   * @return the start-up spawners.
   */
  public JaakSpawner[] createSpawners() {
    JaakSpawner[] spawners = new JaakSpawner[AntColonyConstants.ANT_COLONY_COUNT];
    for (int i = 0; (i < spawners.length); i++) {
      JaakSpawner _createColony = this.createColony((i + 1));
      spawners[i] = _createColony;
    }
    return spawners;
  }
  
  /**
   * Create an instance of the environment that must
   * be used by the Jaak kernel.
   * 
   * @return the instance of time manager.
   */
  public JaakEnvironment createEnvironment() {
    JaakEnvironment environment = new JaakEnvironment(AntColonyConstants.WIDTH, AntColonyConstants.HEIGHT);
    environment.setWrapped(this.isWrappedEnvironment);
    ActionApplier actionApplier = environment.getActionApplier();
    for (int i = 0; (i < AntColonyConstants.FOOD_SOURCES); i++) {
      {
        int _width = environment.getWidth();
        int _nextInt = RandomNumber.nextInt(_width);
        int _height = environment.getHeight();
        int _nextInt_1 = RandomNumber.nextInt(_height);
        Point2i p = new Point2i(_nextInt, _nextInt_1);
        while (this.positions.contains(p)) {
          int _width_1 = environment.getWidth();
          int _nextInt_2 = RandomNumber.nextInt(_width_1);
          int _height_1 = environment.getHeight();
          int _nextInt_3 = RandomNumber.nextInt(_height_1);
          p.set(_nextInt_2, _nextInt_3);
        }
        this.positions.add(p);
        int _nextInt_2 = RandomNumber.nextInt(AntColonyConstants.MAX_FOOD_PER_SOURCE);
        int _max = Math.max(10, _nextInt_2);
        Food food = new Food(_max);
        int _x = p.x();
        int _y = p.y();
        actionApplier.putObject(_x, _y, food);
      }
    }
    return environment;
  }
  
  /**
   * Replies the type of the agents to spawn.
   * 
   * @param spawner - the spawner that will create the agent.
   */
  public Class<? extends Agent> getSpawnableAgentType(final JaakSpawner spawner) {
    return Ant.class;
  }
}
