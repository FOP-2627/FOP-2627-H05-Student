package h05.worlds.sorting.container;

import h05.worlds.collecting.items.trash.Trash;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * A specialized container designed specifically for hazardous or dangerous waste materials.
 */
public class HazardContainer extends TrashStorage {

    /**
     * Constructs a new {@code FieldEntity} at the specified (x, y) coordinates.
     *
     * @param x the initial x-coordinate of this entity
     * @param y the initial y-coordinate of this entity
     */
    @DoNotTouch
    public HazardContainer(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean accepts(Trash trash) {
        // TODO: H5.4.1
        return org.tudalgo.algoutils.student.Student.crash("H5.4.1 - Remove if implemented");
    }

    /**
     * Returns the specific family type for this container.
     *
     * @return {@link StorageFamily#HAZARD_CONTAINER}
     */
    @DoNotTouch
    @Override
    public StorageFamily getFamily() {
        return StorageFamily.HAZARD_CONTAINER;
    }
}
