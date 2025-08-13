package nazario.liby.api.ui.v1.state;

import nazario.liby.api.util.nbt.LibyNbtCompound;
import nazario.liby.internal.ui.v1.LibyWidgetState;
import net.minecraft.nbt.NbtCompound;

public class LibyCheckboxWidgetState extends LibyWidgetState {

    private boolean checked;

    public LibyCheckboxWidgetState(LibyNbtCompound nbtCompound) {
        super(nbtCompound);
    }

    public LibyCheckboxWidgetState(boolean checked) {
        super(new LibyNbtCompound());

        this.checked = checked;
    }

    public boolean isChecked() {
        return this.checked;
    }

    @Override
    public void readFromNbt(LibyNbtCompound tag) {
        this.checked = tag.getBoolean("checked");
    }

    @Override
    public void writeToNbt(LibyNbtCompound tag) {
        tag.putString("class", getClass().getCanonicalName());
        tag.putBoolean("checked", this.checked);
    }
}
