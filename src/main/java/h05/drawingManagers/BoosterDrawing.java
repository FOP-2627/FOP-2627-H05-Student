package h05.drawingManagers;

import h05.worlds.racing.booster.Booster;
import h05.worlds.racing.booster.BoosterFamily;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public class BoosterDrawing extends FamilyBasedDrawing<Booster, BoosterFamily> {

    public BoosterDrawing(){
        super(BoosterFamily.values());
    }

    @Override
    protected BoosterFamily getFamily(Booster booster){
        return booster.getFamily();
    }

    @Override
    protected String getPath(BoosterFamily family) {
        return switch (family) {
            case SPEED   -> "/boosters/lightning.svg";
            case FREEZE  -> "/boosters/snowflake_booster.svg";
            case SHOOT  -> "/trash/banana.svg";
        };
    }
}
