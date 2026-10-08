package h05.worlds.racing;

import h05.core.BaseRobot;
import fopbot.Direction;
import fopbot.RobotFamily;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

public class RacingRobot extends BaseRobot {

    private int frozenTurns = 0;


    public RacingRobot(int x, int y, Direction direction, RobotFamily color) {
        super(x, y, direction, color);
    }

    public void collect() {
        // TODO: H5.5.2
        org.tudalgo.algoutils.student.Student.crash("H5.5.2 - Remove if implemented");
    }

    @DoNotTouch
    public boolean isFrozen() {
        return frozenTurns > 0;
    }

    @DoNotTouch
    public void freeze(int turns) {
        this.frozenTurns = turns;
    }

    @DoNotTouch
    public void tickStatus() {
        if (frozenTurns > 0) frozenTurns--;
    }
}
