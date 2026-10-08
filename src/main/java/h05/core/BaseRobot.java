package h05.core;

import fopbot.*;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

public abstract class BaseRobot extends Robot {

    /**
     * Represents the base robot class shared across all three worlds.
     * Provides the ability to turn towards a given direction,
     * to detect whether an Item is present on the robot's current field
     * and to scan Items on his spot and directly in front of him.
     *
     * @param x         the initial x-coordinate of the robot in the world
     * @param y         the initial y-coordinate of the robot in the world
     * @param direction the direction the robot is facing at spawn
     */
    public BaseRobot(int x, int y, Direction direction) {
        super(x, y, direction, 0);
    }

    public BaseRobot(int x, int y, Direction direction, RobotFamily test) {
        super(x, y, direction, 0, test);
    }

    /**
     * Checks whether at least one Item is present on the robot's current field.
     *
     * @return true if the robot shares its field with at least one Item; false otherwise
     */
    @DoNotTouch
    public boolean isOnItem() {
        return World.getGlobalWorld().getField(this.getX(), this.getY()).getEntities().stream()
            .anyMatch(Item.class::isInstance);
    }

    /**
     * Returns the first Item found on the robot's current field.
     *{@link #isOnItem()} should always be called beforehand.
     *
     * @return the first Item present on that field
     */
    @DoNotTouch
    public Item scanItemOnCurrentField() {
        return World.getGlobalWorld().getField(this.getX(), this.getY())
            .getEntities()
            .stream()
            .filter(e -> e instanceof Item)
            .map(e -> (Item) e)
            .toList()
            .getFirst();
    }

    /**
     * Returns the first Item found on the field directly in front of the robot.
     * {@link #isItemInFront()} should always be called beforehand.
     *
     * @return the first Item present directly in front of the robot.
     */
    @DoNotTouch
    public Item scanItemInFront() {
        return getFieldInFront()
            .getEntities()
            .stream()
            .filter(e -> e instanceof Item)
            .map(e -> (Item) e)
            .toList()
            .getFirst();
    }

    /**
     * Rotates the robot left until it faces the specified direction.
     *
     * @param dir the desired direction the robot should face after the call
     */
    @DoNotTouch
    public void faceInGivenDirection(Direction dir) {
        while (!getDirection().equals(dir)) {
            turnLeft();
        }
    }

    /**
     * checks whether at least one Item is present directly in front of the robot
     *
     * @return true if item is found, otherwise false
     */
    @DoNotTouch
    public boolean isItemInFront(){
        return getFieldInFront().getEntities().stream()
            .anyMatch(Item.class::isInstance);
    }

    /**
     * returns the field directly in front of the robot.
     *
     * @return the field
     */
    private Field getFieldInFront(){
        int x = getX();
        int y = getY();

        switch (getDirection()) {
            case UP -> y += 1;
            case RIGHT -> x += 1;
            case DOWN -> y -= 1;
            case LEFT -> x -= 1;
        }

        return World.getGlobalWorld().getField(x, y);
    }



}
