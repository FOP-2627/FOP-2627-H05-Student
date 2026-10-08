package h05.worlds.racing.track;

import h05.core.Item;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public class Track extends Item {
    private final TrackFamily family;

    /**
     * Constructs a new {@code FieldEntity} at the specified (x, y) coordinates.
     *
     * @param x the initial x-coordinate of this entity
     * @param y the initial y-coordinate of this entity
     */
    public Track(int x, int y, TrackFamily family) {
        super(x, y);

        this.family = family;
    }

    public TrackFamily getFamily() {
        return family;
    }
}
