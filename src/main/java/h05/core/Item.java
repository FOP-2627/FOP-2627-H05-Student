package h05.core;

import fopbot.FieldEntity;

public class Item extends FieldEntity {

    /**
     * Constructs a new {@code FieldEntity} at the specified (x, y) coordinates.
     *
     * @param x the initial x-coordinate of this entity
     * @param y the initial y-coordinate of this entity
     */
    public Item(int x, int y) {
        super(x, y);
    }

    /**
     * returns the type of the item
     * @return type as String
     */
    public String getTypeName(){
        return this.getClass().getSimpleName();
    }
}
