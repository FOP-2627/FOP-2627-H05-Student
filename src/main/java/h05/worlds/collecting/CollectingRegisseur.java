package h05.worlds.collecting;

import h05.drawingManagers.*;
import h05.core.BaseRegisseur;
import h05.drawingManagers.ContainerDrawing;
import h05.drawingManagers.OtherDrawing;
import h05.drawingManagers.TrashDrawing;
import h05.worlds.collecting.items.nonTrash.Snake;
import h05.worlds.collecting.items.trash.*;
import h05.worlds.collecting.items.trash.*;
import h05.worlds.sorting.container.TrashStorage;
import fopbot.*;
import h05.core.Item;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.*;

import java.util.*;
import java.util.function.BiFunction;

/**
 * Sets up a world where a robot collects recyclable trash scattered across the field.
 * The robot must identify safe items and avoid hazardous ones.
 */
public class CollectingRegisseur extends BaseRegisseur {

    public CollectingRobot robot;

    @DoNotTouch
    @Override
    protected void spawnRobots() {
        robot = new CollectingRobot(0,0, Direction.RIGHT);
    }

    /**
     * Drives the robot across the entire world, collecting every safe item it encounters along the way.
     */
    @DoNotTouch
    protected void run(){
        robot.faceInGivenDirection(Direction.RIGHT);

        for (int i = 0; i < DEFAULT_WORLD_HEIGHT; i++) {
            for (int j = 0; j < DEFAULT_WORLD_WIDTH; j++) {
                if (robot.isOnItem()) {
                    robot.collect();
                }

                if (j < DEFAULT_WORLD_WIDTH - 1) {
                    robot.move();
                }
            }

            if (i < DEFAULT_WORLD_HEIGHT - 1) {
                robot.faceInGivenDirection(Direction.UP);
                robot.move();

                if (i % 2 == 0) {
                    robot.faceInGivenDirection(Direction.LEFT);
                } else {
                    robot.faceInGivenDirection(Direction.RIGHT);
                }
            }
        }
    }

    /**
     * Returns true once every Trash entity has been removed from the world, indicating that the collection phase is complete.
     *
     * @return true if no Trash remains on any field; false otherwise
     */
    @DoNotTouch
    @Override
    protected boolean isFinished() {
        return world.getAllFieldEntities().stream().noneMatch(e -> e instanceof Trash);
    }

    /**
     * Called when the last piece of trash has been collected.
     * Prints a completion message to the console.
     */
    @DoNotTouch
    @Override
    protected void onFinish() {
        System.out.println("The collecting process is over!");
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
     * Populates the world with a randomized mix of trash and items.
     * Each field receives at most one entity;
     * the robot's starting position at (0, 0) is always left empty.
     * Items are drawn cyclically from a fixed factory list, so every type appears roughly the same number of times.
     */
    @DoNotTouch
    @Override
    protected void setupWorld() {

        int width = World.getWidth();
        int height = World.getHeight();
        int totalTiles = width * height;

        // collect all the tiles and shuffle they randomly
        List<int[]> positions = new ArrayList<>(totalTiles);
        for(int y = 0; y < width; y++){
            for(int x = 0; x < height; x++){
                positions.add(new int[]{x, y});
            }
        }

        // so the item can't spawn on starting robot position
        positions.removeFirst();

        Collections.shuffle(positions);

        List<BiFunction<Integer, Integer, Item>> factories = List.of(
            Apple::new,
            Banana::new,
            Newspaper::new,
            OldBattery::new,
            Petrol::new,
            Snake::new
        );


        // pick only 1/3 of the positions on the field and spawn item
        for(int i = 0; i < totalTiles / 3; i++){
            int[] ranPos = positions.get(i);

            BiFunction<Integer, Integer, Item> factory = factories.get(i % factories.size());

            World.getGlobalWorld().placeFieldEntity(
                factory.apply(ranPos[0], ranPos[1])
            );
        }
    }

    /**
     * Registers sprite renderers for all entity types that appear in this world.
     */
    @DoNotTouch
    protected void registerSprites(){
        world.setDrawingRegistry(
            DrawingRegistry.builder(DrawingRegistry.DEFAULT)
                .addAll(
                    Map.ofEntries(
                        Map.entry(Robot.class, new RobotDrawing()),
                        Map.entry(TrashStorage.class, new ContainerDrawing()),
                        Map.entry(Trash.class, new TrashDrawing()),
                        Map.entry(Item.class, new OtherDrawing())
                    )
                ).build(Comparator.comparingInt(this::getDrawingPriority))
                );
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
            case Trash ignored -> 0;
            case TrashStorage ignored -> 1;
            case Item ignored -> 3;
            case Robot ignored -> 4;
            default -> -1;
        };
    }

// </editor-fold>
}
