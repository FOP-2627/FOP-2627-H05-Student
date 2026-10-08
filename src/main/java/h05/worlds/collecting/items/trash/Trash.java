package h05.worlds.collecting.items.trash;

import h05.core.Item;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public abstract class Trash extends Item{

    /**
     * Constructs a new {@code FieldEntity} if type Trash at the specified (x, y) coordinates.
     *
     * @param x the initial x-coordinate of this entity
     * @param y the initial y-coordinate of this entity
     */
    public Trash(int x, int y) {
        super(x, y);
    }
}
