package h05.drawingManagers;

import h05.worlds.racing.track.Track;
import h05.worlds.racing.track.TrackFamily;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public class TrackDrawing extends FamilyBasedDrawing<Track, TrackFamily> {

    public TrackDrawing() {
        super(TrackFamily.values());
    }

    @Override
    protected TrackFamily getFamily(Track entity) {
        return entity.getFamily();
    }

    @Override
    protected String getPath(TrackFamily family) {
        return switch (family) {
            case STRAIGHT_H, STRAIGHT_V   -> "/track/straight_road_optimized.svg";
            case TURN_SE, TURN_WS, TURN_NW, TURN_EN  -> "/track/turn_road_optimized.svg";
            case FINISH -> "/track/finish_line.svg";
        };
    }

    @Override
    protected int getRotation(TrackFamily family) {
        return family.getRotationDegrees();
    }
}
