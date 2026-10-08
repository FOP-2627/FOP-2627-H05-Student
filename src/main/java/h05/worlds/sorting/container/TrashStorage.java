package h05.worlds.sorting.container;

import h05.core.Item;
import h05.worlds.collecting.items.trash.Trash;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Represents an abstract base class for all trash storage containers.
 * Defines the core contract for checking accepted waste types and managing container identification.
 */
@DoNotTouch
public abstract class TrashStorage extends Item {

    private Item[] storage = new Item[0];

    /**
     * Constructs a new {@code FieldEntity} at the specified (x, y) coordinates.
     *
     * @param x the initial x-coordinate of this entity
     * @param y the initial y-coordinate of this entity
     */
    public TrashStorage(int x, int y) {
        super(x, y);
    }

    /**
     * Checks whether this storage container accepts the specified trash item.
     *
     * @param trash the trash item to be checked for acceptance
     * @return true if the trash is compatible with this container, false otherwise
     */
    public abstract boolean accepts(Trash trash);

    /**
     * Retrieves the storage family category this container belongs to.
     *
     * @return the StorageFamily type of this container
     */
    public abstract StorageFamily getFamily();

    /**
     * Adds all given items to storage.
     *
     * @param item the item to be added
     */
    public void add(Item item){
        Item[] tmp = new Item[storage.length + 1];
        tmp[storage.length] = item;
        storage = tmp;
    }
}
