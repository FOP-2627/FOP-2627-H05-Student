package h05.worlds.sorting;

import h05.core.BaseRegisseur;
import h05.drawingManagers.ContainerDrawing;
import h05.worlds.collecting.items.trash.*;
import h05.worlds.collecting.items.trash.*;
import h05.worlds.sorting.container.BrownContainer;
import h05.worlds.sorting.container.GreenContainer;
import h05.worlds.sorting.container.HazardContainer;
import h05.worlds.sorting.container.TrashStorage;
import fopbot.*;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.Comparator;
import java.util.Map;

public class SortingRegisseur extends BaseRegisseur {

    // The robot performing the sorting task
    public SortingRobot robot;

    private TrashStorage[] containers;

    /**
     * Drives the robot to each container in sequence and attempts to deposit matching backpack items into it.
     */
    @DoNotTouch
    @Override
    protected void run() {
        for (TrashStorage container : containers) {
            robot.goToContainer(container);
            robot.sortInto();
        }
    }

    /**
     * Spawns the SortingRobot at position (0, 0) and pre-loads its backpack
     * with a fixed set of mixed trash items that represent the output of the collection phase from World 1.
     */
    @StudentImplementationRequired
    @Override
    protected void spawnRobots() {
        robot = new SortingRobot(0, 0, Direction.RIGHT); // Do not touch

        // TODO: H5.4.4
        org.tudalgo.algoutils.student.Student.crash("H5.4.4 - Remove if implemented");
    }

    @DoNotTouch
    @Override
    protected boolean isFinished() {
        return robot.isBackpackEmpty();
    }

    /**
     * Called when all items have been sorted.
     * Prints a completion message to the console.
     */
    @DoNotTouch
    @Override
    protected void onFinish() {
        System.out.println("The sorting process is over!");
    }


    // <editor-fold defaultstate="collapsed" desc="========== [ SYSTEM INTERNAL: DO NOT MODIFY BEYOND THIS POINT ] ==========">

    // ............................................................................
    // .                                                                          .
    // .    /\_/\  USER-LAND ENDS HERE                                            .
    // .   ( o.o ) Below lies the Abyss of Implementation Details.                .
    // .    > ^ <  No changes needed beyond this point.                           .
    // .                                                                          .
    // .                           ≽^•⩊•^≼                                        .
    // ............................................................................


    /**
     * Registers sprite renderers for all entity types that appear in this world.
     */
    @DoNotTouch
    @Override
    protected void registerSprites() {
        world.setDrawingRegistry(
            DrawingRegistry.builder(DrawingRegistry.DEFAULT)
                .addAll(
                    Map.ofEntries(
                        Map.entry(Robot.class, new RobotDrawing()),
                        Map.entry(TrashStorage.class, new ContainerDrawing())
                    )
                ).build(Comparator.comparingInt(this::getDrawingPriority)
                ));
    }

    /**
     * Returns the drawing priority for a given field entity.
     * Higher values are rendered on top of lower ones.
     *
     * @param entity the entity whose priority is requested
     * @return the integer draw priority
     */
    @DoNotTouch
    private int getDrawingPriority(@NotNull FieldEntity entity) {
        return switch (entity) {
            case TrashStorage ignored -> 0;
            case Robot ignored -> 2;
            default -> -1;
        };
    }

    /**
     * Places five recycling containers in a row.
     */
    @Override
    protected void setupWorld() {
        containers = new TrashStorage[3];
        containers[0] = new BrownContainer(3, 2);
        containers[1] = new GreenContainer(4, 2);
        containers[2] = new HazardContainer(6, 2);

        World.getGlobalWorld().placeFieldEntity(containers[0]);
        World.getGlobalWorld().placeFieldEntity(containers[1]);
        World.getGlobalWorld().placeFieldEntity(containers[2]);
    }

    /**
     * Initializes the world dimensions and enables the visual display.
     * The world is 10 fields wide and 4 fields tall, giving the robot enough room to navigate to all containers from its starting position.
     */
    @Override
    protected void initWorld() {
        World.setSize(10, 4);
        World.setVisible(true);
    }

    // </editor-fold>
}
