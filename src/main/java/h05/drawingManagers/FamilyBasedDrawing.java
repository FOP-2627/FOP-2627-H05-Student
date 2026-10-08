package h05.drawingManagers;

import fopbot.*;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;


public abstract class FamilyBasedDrawing<T extends FieldEntity, R extends Enum<R>> extends SvgBasedDrawing<T> {

    private final R[] families;


    public FamilyBasedDrawing(R[] families) {
        super(families.length);
        this.families = families;
    }

    @Override
    protected Image getCurrentDrawingImage(T entity) {
        return getImage(getFamily(entity).ordinal());
    }

    @Override
    protected void loadImages(int targetSize, @NotNull DrawingContext<? extends T> context){
        for(R family : families){
            Image image = PaintUtils.loadFieldImage(
                Objects.requireNonNull(
                    getClass().getResourceAsStream(getPath(family)),
                    "Resource not found: " + getPath(family)
                ),
                getRotation(family),
                targetSize
            );
            setImage(family.ordinal(), image);
        }
    }

    protected abstract R getFamily(T entity);
    protected abstract String getPath(R family);

    protected int getRotation(R family){
        return 0;
    }
}
