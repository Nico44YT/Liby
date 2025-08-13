package nazario.liby.internal.ui.v1;

import nazario.liby.api.util.LibyIdentifier;
import nazario.liby.api.util.nbt.LibyNbtCompound;
import nazario.liby.api.util.nbt.NbtConvertible;
import nazario.liby.internal.injections.LibyIdentifierResolvable;

public abstract class LibyWidgetState implements NbtConvertible, LibyIdentifierResolvable {

    protected LibyIdentifier widgetId;

    public LibyWidgetState(LibyNbtCompound nbtCompound) {
        this.widgetId = new LibyIdentifier(nbtCompound.getIdentifier("widget_id"));
    }

    @Override
    public LibyIdentifier liby$getId() {
        return this.widgetId;
    }
}
