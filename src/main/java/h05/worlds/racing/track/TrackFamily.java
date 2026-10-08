package h05.worlds.racing.track;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public enum TrackFamily {
    STRAIGHT_H  ( 90),
    STRAIGHT_V  (0),
    TURN_SE     (0),
    TURN_WS     (90),
    TURN_NW     (180),
    TURN_EN     (270),
    FINISH      (0);

    private final int rotationDegrees;

    TrackFamily(int rotationDegrees) {
        this.rotationDegrees = rotationDegrees;
    }

    public int getRotationDegrees() { return rotationDegrees; }
}
