package h05.worlds.racing.booster;
import h05.core.Item;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public class Booster extends Item {
    private final BoosterFamily family;

    public Booster(int x, int y, BoosterFamily family){
        super(x, y);
        this.family = family;
    }
    public BoosterFamily getFamily() {
        return family;
    }
}
