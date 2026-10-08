package h05.drawingManagers;

import h05.worlds.collecting.items.trash.*;
import fopbot.DrawingContext;
import fopbot.PaintUtils;
import fopbot.SvgBasedDrawing;
import h05.worlds.collecting.items.trash.*;
import h05.worlds.collecting.items.trash.*;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

public class TrashDrawing extends SvgBasedDrawing<Trash> {

    private enum TrashRenderType {
        APPLE("/trash/apple.svg"),
        BANANA("/trash/banana.svg"),
        NEWSPAPER("/trash/newspaper.svg"),
        OLD_BATTERY("/trash/old_battery.svg"),
        PETROL("/trash/petrol.svg");

        final String path;

        TrashRenderType(String path) {
            this.path = path;
        }
    }

    public TrashDrawing() {
        super(TrashRenderType.values().length);
    }

    @Override
    protected Image getCurrentDrawingImage(Trash entity) {
        if (entity instanceof Apple)            return getImage(0);
        if (entity instanceof Banana)           return getImage(1);
        if (entity instanceof Newspaper)        return getImage(2);
        if (entity instanceof OldBattery)       return getImage(3);
        if (entity instanceof Petrol)           return getImage(4);
        throw new IllegalArgumentException("Unknown: " + entity.getClass());
    }

    @Override
    protected void loadImages(int targetSize, @NotNull DrawingContext<? extends Trash> context) {
        for (TrashRenderType renderType : TrashRenderType.values()) {
            Image image = PaintUtils.loadFieldImage(
                Objects.requireNonNull(
                    getClass().getResourceAsStream(renderType.path),
                    "Resource not found: " + renderType.path
                ),
                0,                     // Direction - UP
                targetSize                        // scale to the actual size of a field
            );
            setImage(renderType.ordinal(), image);
        }
    }
}
