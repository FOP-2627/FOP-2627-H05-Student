package h05.worlds.racing;

import h05.core.BaseRegisseur;
import h05.core.BaseRobot;
import h05.drawingManagers.BoosterDrawing;
import h05.drawingManagers.TrackDrawing;
import h05.worlds.racing.booster.Booster;
import h05.worlds.racing.booster.BoosterFamily;
import h05.worlds.racing.track.Track;
import h05.worlds.racing.track.TrackFamily;
import fopbot.*;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentCreationRequired;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.Comparator;
import java.util.Map;

/**
 * Sets up a world where robots compete in a race. The world contains boosters for the robots.
 */
public class RacingRegisseur extends BaseRegisseur {

    // Constants describing the wall distance for each track ring
    private static final int MAIN_TRACK = 0;
    private static final int FIRST_TRACK = 1;
    private static final int SECOND_TRACK = 2;

    // How many turns the booster stays active
    private static final int FREEZE_EFFECT_TIME = 3;
    private static final int SHOOT_EFFECT_TIME = 5;

    // Robots participating in the race
    private RacingRobot mainRobot;
    private RacingRobot firstEnemy;
    private RacingRobot secondEnemy;

    // Variable that determines the winner
    private RacingRobot winner;


    /**
     * Spawns the participating robots at their starting positions.
     */
    @StudentImplementationRequired
    @Override
    protected void spawnRobots() {

        int initialY = DEFAULT_WORLD_HEIGHT / 2;

        mainRobot = new RacingRobot(0, initialY, Direction.UP, RobotFamily.TRIANGLE_BLUE);
        firstEnemy = new RacingRobot(1, initialY, Direction.UP, RobotFamily.SQUARE_ORANGE);
        secondEnemy = new RacingRobot(2, initialY, Direction.UP, RobotFamily.SQUARE_PURPLE);
    }

    /**
     * Makes all participating robots run in circles.
     */
    @DoNotTouch
    @Override
    protected void run() {
        do {
            doStep(mainRobot, MAIN_TRACK);
            doStep(firstEnemy, FIRST_TRACK);
            doStep(secondEnemy, SECOND_TRACK);
        }
        while (!isFinished());
    }

    /**
     * Checks whether the robot is under a booster effect.
     * Checks whether the robot is standing on a booster it can pick up.
     * Moves the robot along its path if nothing is blocking it.
     *
     * @param robot  the participating robot
     * @param offset the track the robot is moving along
     */
    @DoNotTouch
    private void doStep(RacingRobot robot, int offset) {
        robot.tickStatus();

        if (robot.isFrozen() || robot.isTurnedOff()) return;

        moveAlongTrack(robot, offset);
        if(robot.isOnItem())
            robot.collect();
    }

    /**
     * Applies a booster effect to the robot and removes the booster from the track.
     *
     * @param robot   the robot that found the booster
     * @param booster the booster found on the field
     * @param offset  the track the robot is moving along
     */
    @DoNotTouch
    private void applyBooster(@NotNull RacingRobot robot, @NotNull Booster booster, int offset) {
        World.getGlobalWorld().removeFieldEntity(booster);
        switch (booster.getFamily()) {

            case SPEED -> {
                doStep(robot, offset);
                doStep(robot, offset);
            }

            case FREEZE -> {
                firstEnemy.freeze(FREEZE_EFFECT_TIME);
                secondEnemy.freeze(FREEZE_EFFECT_TIME);
            }

            case SHOOT -> secondEnemy.freeze(SHOOT_EFFECT_TIME);
        }
    }

    /**
     * Moves the robot forward along its track.
     *
     * @param robot  a race participant
     * @param offset the track number
     */
    @DoNotTouch
    private void moveAlongTrack(BaseRobot robot, int offset) {
        if (shouldTurn(robot, offset)) {
            robot.faceInGivenDirection(clockwise(robot.getDirection()));
        } else {
            robot.move();
        }
    }

    /**
     * Decides whether the robot should turn or move straight.
     *
     * @param robot  the robot about to take a step
     * @param offset the track number
     * @return true if the robot should turn, false if it should move forward
     */
    @DoNotTouch
    private boolean shouldTurn(BaseRobot robot, int offset) {
        int x = robot.getX();
        int y = robot.getY();
        int maxX = DEFAULT_WORLD_WIDTH - 1 - offset;
        int maxY = DEFAULT_WORLD_HEIGHT - 1 - offset;

        return switch (robot.getDirection()) {
            case UP -> y == maxY;
            case RIGHT -> x == maxX;
            case DOWN -> y == offset;
            case LEFT -> x == offset;
        };
    }

    /**
     * Returns the direction after a clockwise (right) turn.
     *
     * @param dir the robot's current facing direction
     * @return the direction the robot faces after turning right
     */
    @DoNotTouch
    private Direction clockwise(Direction dir) {
        return switch (dir) {
            case UP -> Direction.RIGHT;
            case RIGHT -> Direction.DOWN;
            case DOWN -> Direction.LEFT;
            case LEFT -> Direction.UP;
        };
    }

    /**
     * Places boosters on the track.
     */
    @DoNotTouch
    @Override
    protected void setupWorld() {
        spawnBoosterOnField(new Booster(0, 2, BoosterFamily.FREEZE));
        spawnBoosterOnField(new Booster(2, DEFAULT_WORLD_HEIGHT - 2, BoosterFamily.FREEZE));
        spawnBoosterOnField(new Booster(DEFAULT_WORLD_WIDTH - 1, 4, BoosterFamily.FREEZE));

        spawnBoosterOnField(new Booster(5, 0, BoosterFamily.SPEED));
        spawnBoosterOnField(new Booster(0, 6, BoosterFamily.SPEED));

        spawnBoosterOnField(new Booster(5, DEFAULT_WORLD_HEIGHT - 1, BoosterFamily.SHOOT));
        spawnBoosterOnField(new Booster(3, 0, BoosterFamily.SHOOT));

        buildRoad();
    }

    /**
     * Checks whether any participant has crossed the finish line.
     *
     * @return true if any robot has finished the race, false otherwise
     */
    @DoNotTouch
    @Override
    protected boolean isFinished() {

        if (mainRobot.getX() == MAIN_TRACK && mainRobot.getY() == DEFAULT_WORLD_HEIGHT / 2) {
            winner = mainRobot;
        } else if (firstEnemy.getX() == FIRST_TRACK && firstEnemy.getY() == DEFAULT_WORLD_HEIGHT / 2) {
            winner = firstEnemy;
        } else if (secondEnemy.getX() == SECOND_TRACK && secondEnemy.getY() == DEFAULT_WORLD_HEIGHT / 2) {
            winner = secondEnemy;
        }

        return winner != null;
    }

    /**
     * Called when the race ends. Prints the result to the console.
     */
    @DoNotTouch
    @Override
    protected void onFinish() {
        System.out.println("The race is over!");
        if (winner == null) return;

        if (winner.equals(mainRobot)) {
            System.out.println("You won!!!");
        } else {
            System.out.println("Oh no, you lost :(");
        }
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

    @DoNotTouch
    @Override
    protected void registerSprites() {
        World.getGlobalWorld().setDrawingRegistry(
            DrawingRegistry.builder(DrawingRegistry.DEFAULT)
                .addAll(
                    Map.ofEntries(
                        Map.entry(Robot.class, new RobotDrawing()),
                        Map.entry(Booster.class, new BoosterDrawing()),
                        Map.entry(Track.class, new TrackDrawing())
                    )
                ).build(Comparator.comparingInt(this::getDrawingPriority)
                ));
    }

    @DoNotTouch
    private int getDrawingPriority(@NotNull FieldEntity entity) {
        return switch (entity) {
            case Track ignored -> 0;
            case Booster ignored -> 1;
            case Robot ignored -> 2;
            default -> -1;
        };
    }

    @DoNotTouch
    private void buildTrackRing(int offset) {
        int maxX = DEFAULT_WORLD_WIDTH - 1 - offset;
        int maxY = DEFAULT_WORLD_HEIGHT - 1 - offset;

        World.getGlobalWorld().placeFieldEntity(new Track(offset, offset, TrackFamily.TURN_EN));
        World.getGlobalWorld().placeFieldEntity(new Track(offset, maxY, TrackFamily.TURN_SE));
        World.getGlobalWorld().placeFieldEntity(new Track(maxX, offset, TrackFamily.TURN_NW));
        World.getGlobalWorld().placeFieldEntity(new Track(maxX, maxY, TrackFamily.TURN_WS));

        for (int i = offset + 1; i < maxX; i++) {
            World.getGlobalWorld().placeFieldEntity(new Track(offset, i, TrackFamily.STRAIGHT_V));
            World.getGlobalWorld().placeFieldEntity(new Track(maxX, i, TrackFamily.STRAIGHT_V));
        }

        for (int i = offset + 1; i < maxY; i++) {
            World.getGlobalWorld().placeFieldEntity(new Track(i, offset, TrackFamily.STRAIGHT_H));
            World.getGlobalWorld().placeFieldEntity(new Track(i, maxY, TrackFamily.STRAIGHT_H));
        }
    }

    /**
     * Builds three track rings for the three participating robots.
     */
    @DoNotTouch
    private void buildRoad() {
        buildTrackRing(MAIN_TRACK);
        buildTrackRing(FIRST_TRACK);
        buildTrackRing(SECOND_TRACK);

        int finishLineY = DEFAULT_WORLD_HEIGHT / 2;
        World.getGlobalWorld().placeFieldEntity(new Track(MAIN_TRACK, finishLineY, TrackFamily.FINISH));
        World.getGlobalWorld().placeFieldEntity(new Track(FIRST_TRACK, finishLineY, TrackFamily.FINISH));
        World.getGlobalWorld().placeFieldEntity(new Track(SECOND_TRACK, finishLineY, TrackFamily.FINISH));
    }

    @DoNotTouch
    private int getOffsetForRobot(RacingRobot robot) {
        if (robot == mainRobot) return MAIN_TRACK;
        if (robot == firstEnemy) return FIRST_TRACK;
        if (robot == secondEnemy) return SECOND_TRACK;
        throw new IllegalArgumentException("Unknown robot");
    }

    /**
     * Spawns a given booster object onto the game field.
     * <p>
     * This method serves as an intermediary abstraction layer.
     * It wraps the internal FOP World API to shield students from unnecessary framework complexity,
     * allowing them to focus entirely on the core logic of the assignment.
     *
     * @param object The booster entity to be placed in the world.
     */
    @DoNotTouch
    private void spawnBoosterOnField(Booster object) {
        World.getGlobalWorld().placeFieldEntity(object);
    }

    // </editor-fold>
}
