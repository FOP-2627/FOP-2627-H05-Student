package h05.worlds.collecting;

import fopbot.Direction;
import fopbot.World;
import h05.core.BaseRobot;
import h05.core.Item;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

public class CollectingRobot extends BaseRobot {

    /**
     * Constructs a new CollectingRobt at the specified position facing the given direction.
     * It has additional functionality: Collecting and storing.
     *
     * @param x         the initial x-coordinate of the robot
     * @param y         the initial y-coordinate of the robot
     * @param direction the initial direction the robot is facing
     */
    public CollectingRobot(int x, int y, Direction direction) {
        super(x, y, direction);
    }

    /**
     * Extends the backpack array by the exact number of provided items.
     * The additional slots are appended to the end of the array.
     *
     * @param length used to determine how many additional slots to add to the backpack
     */
    @DoNotTouch
    private Item[] extendBackpack(Item[] backpack, int length) {
        Item[] tmp = new Item[backpack.length + length];

        //noinspection ManualArrayCopy we want to keep template code easy to understand for students
        for (int i = 0; i < backpack.length; i++) {
            tmp[i] = backpack[i];
        }

        return tmp;
    }


    public void collect() {
        // TODO: H5.3.4
        org.tudalgo.algoutils.student.Student.crash("H5.3.4 - Remove if implemented");
    }

    /**
     * removes an item from the world
     *
     * @param item the item to remove
     */
    @DoNotTouch
    private void removeItemFromWorld(Item item) {
        World.getGlobalWorld().removeFieldEntity(item);
    }
}
