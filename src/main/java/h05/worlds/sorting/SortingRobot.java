package h05.worlds.sorting;

import h05.core.BaseRobot;
import h05.core.Item;
import h05.worlds.sorting.container.TrashStorage;
import fopbot.Direction;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * A robot that carries a backpack of pre-collected trash items and sorts them
 * into the correct recycling containers placed in the world.
 */
public class SortingRobot extends BaseRobot {

    // Storage space inside the backpack. Belongs to a specific robot
    private Item[] backpack;

    public SortingRobot(int x, int y, Direction direction) {
        super(x, y, direction);
        backpack = new Item[0];
    }

    /**
     * Determines whether the given item can be disposed in the specified container.
     *
     * @param item      the item being checked for compatibility with the container
     * @param container the target container for disposing of the item
     * @return true if the item can be disposed of in this container, false otherwise
     */
    @StudentImplementationRequired
    private boolean isCorrectContainer(Item item, TrashStorage container) {
        // TODO: H5.4.2
        return org.tudalgo.algoutils.student.Student.crash("H5.4.2 - Remove if implemented");
    }


    /**
     * Removes the specified item from the backpack, shifting subsequent elements
     * to fill the gap, and shrinks the backpack size if successful.
     *
     * @param item the item to be removed
     * @return true if the item was found and removed, false if it wasn't in the backpack
     */
    @DoNotTouch
    public boolean removeItem(Item item) {
        for(int i = 0; i < backpack.length; i++){
            if(backpack[i] == item){
                for(int j = i; j < backpack.length - 1; j++){
                    backpack[j] = backpack[j + 1];
                }

                shrinkBackpack();
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the current state of the backpack.
     *
     * @return an array of items currently held by the robot, including null elements
     */
    @DoNotTouch
    public Item[] getItems() {
        return backpack;
    }


    /**
     * Shrinks the Backpack by one slot. The Last item gets lost.
     */
    @DoNotTouch
    private void shrinkBackpack() {
        Item[] tmp = new Item[backpack.length - 1];

        //noinspection ManualArrayCopy we want to keep template code easy to understand for students
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = backpack[i];
        }

        backpack = tmp;
    }

    /**
     * Returns true if every slot in the backpack is null,
     * indicating that all items have been successfully sorted.
     *
     * @return true if no items remain in the backpack; false otherwise
     */
    @DoNotTouch
    public boolean isBackpackEmpty() {
        for (Item item : backpack) {
            if (item != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * If in front of a container, this method deposits all matching trash into the container.
     * The first Item removed is the last item added.
     */
    @DoNotTouch
    public void sortInto() {
        for (int i = backpack.length - 1; i >= 0; i--) {
            Item item = backpack[i];

            if (isItemInFront() && scanItemInFront() instanceof TrashStorage container) {
                if (isCorrectContainer(item, container)) {
                    container.add(item);
                    removeItem(item);
                    System.out.println("Put " + item.getTypeName() + " into " + container.getClass().getSimpleName());
                }
            }
        }
    }

    /**
     * Navigates the robot to the field directly in front of the given container, then turns to face it.
     *
     * @param storage the target container to navigate to
     */
    @DoNotTouch
    public void goToContainer(TrashStorage storage) {
        int targetX = storage.getX();
        int targetY = storage.getY() - 1;

        if (getX() < targetX) {
            faceInGivenDirection(Direction.RIGHT);
        } else if (getX() > targetX) {
            faceInGivenDirection(Direction.LEFT);
        }

        while (getX() != targetX) {
            move();
        }

        if (getY() < targetY) {
            faceInGivenDirection(Direction.UP);
        } else if (getY() > targetY) {
            faceInGivenDirection(Direction.DOWN);
        }
        while (getY() != targetY) {
            move();
        }

        faceInGivenDirection(Direction.UP);
    }
}
