package h05.drawingManagers;

import h05.core.Item;
import h05.worlds.collecting.items.nonTrash.Snake;
import fopbot.DrawingContext;
import fopbot.PaintUtils;
import fopbot.SvgBasedDrawing;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

public class OtherDrawing extends SvgBasedDrawing<Item> {
    private enum OtherRenderType {
        Snake("/other/snake.svg");

        final String path;

        OtherRenderType(String path) {
            this.path = path;
        }
    }

    public OtherDrawing() {
        super(OtherDrawing.OtherRenderType.values().length);
    }

    @Override
    protected Image getCurrentDrawingImage(Item entity) {
        if (entity instanceof Snake)    return getImage(0);
        throw new IllegalArgumentException("Unknown: " + entity.getClass());
    }

    @Override
    protected void loadImages(int targetSize, @NotNull DrawingContext<? extends Item> context) {
        for (OtherDrawing.OtherRenderType renderType : OtherDrawing.OtherRenderType.values()) {
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
