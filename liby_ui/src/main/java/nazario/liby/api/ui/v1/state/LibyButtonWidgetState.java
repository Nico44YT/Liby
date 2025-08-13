package nazario.liby.api.ui.v1.state;

import nazario.liby.api.util.nbt.LibyNbtCompound;
import nazario.liby.internal.ui.v1.LibyWidgetState;

public class LibyButtonWidgetState extends LibyWidgetState {

    public LibyButtonWidgetState(LibyNbtCompound nbtCompound) {
        super(nbtCompound);
    }

    @Override
    public void readFromNbt(LibyNbtCompound tag) {

    }

    @Override
    public void writeToNbt(LibyNbtCompound tag) {
        tag.putString("class", getClass().getCanonicalName());
    }
}
