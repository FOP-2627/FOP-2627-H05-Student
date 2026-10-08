package h05.drawingManagers;

import h05.worlds.sorting.container.StorageFamily;
import h05.worlds.sorting.container.TrashStorage;

public class ContainerDrawing extends FamilyBasedDrawing<TrashStorage, StorageFamily> {

    public ContainerDrawing() {
        super(StorageFamily.values());
    }

    @Override
    protected StorageFamily getFamily(TrashStorage entity) {
        return entity.getFamily();
    }

    @Override
    protected String getPath(StorageFamily family) {
        return switch (family) {
            case BROWN_TRASH_CONTAINER  -> "/containers/brown_container_optimized.svg";
            case GREEN_TRASH_CONTAINER  -> "/containers/green_container.svg";
            case HAZARD_CONTAINER -> "/containers/nuclear_waste_optimized.svg";
        };
    }
}
