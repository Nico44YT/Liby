package nazario.liby.api.ui.v1.client.widget;

import nazario.liby.api.ui.v1.client.LibyScreen;
import nazario.liby.api.ui.v1.client.WidgetSyncType;
import nazario.liby.api.ui.v1.state.LibyButtonWidgetState;
import nazario.liby.internal.ui.v1.LibyWidgetState;
import nazario.liby.api.util.LibyIdentifier;
import nazario.liby.api.util.nbt.LibyNbtCompound;
import nazario.liby.internal.ui.v1.client.LibyWidget;
import nazario.liby.internal.ui.v1.client.LibyWidgetBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class LibyButtonWidget extends ButtonWidget implements LibyWidget {

    protected LibyIdentifier identifier;
    protected WidgetSyncType syncType;

    protected LibyButtonWidget(LibyIdentifier identifier, int x, int y, int width, int height, Text message, PressAction onPress, NarrationSupplier narrationSupplier, WidgetSyncType syncType) {
        super(x, y, width, height, message, onPress, narrationSupplier);

        this.identifier = identifier;
        this.syncType = syncType;
    }

    @Override
    public void onPress() {
        super.onPress();

        if(this.syncType == WidgetSyncType.ON_UPDATE) {
            this.send();
        }
    }

    @Override
    public LibyIdentifier liby$getId() {
        return this.identifier;
    }

    @Override
    public WidgetSyncType getSyncType() {
        return this.syncType;
    }

    @Override
    public LibyWidgetState getState() {
        return new LibyButtonWidgetState(new LibyNbtCompound());
    }

    @Override
    public LibyScreen getParentScreen() {
        return null;
    }

    public static class Builder extends ButtonWidget.Builder implements LibyWidgetBuilder {
        protected LibyIdentifier identifier;
        protected PressAction onPress;

        protected Text message;
        protected NarrationSupplier narrationSupplier;
        protected Tooltip tooltip;

        protected int x;
        protected int y;
        protected int width;
        protected int height;

        protected WidgetSyncType syncType;

        public Builder(Identifier identifier, PressAction onPress) {
            super(Text.empty(), onPress);

            this.identifier = new LibyIdentifier(identifier);
            this.narrationSupplier = ButtonWidget.DEFAULT_NARRATION_SUPPLIER;
            this.width = 150;
            this.height = 20;
            this.onPress = onPress;

            this.syncType = WidgetSyncType.PASSIVE;
        }

        public Builder message(Text message) {
            this.message = message;
            return this;
        }

        @Override
        public ButtonWidget.Builder narrationSupplier(NarrationSupplier narrationSupplier) {
            this.narrationSupplier = narrationSupplier;
            return this;
        }

        @Override
        public ButtonWidget.Builder tooltip(@Nullable Tooltip tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        @Override
        public ButtonWidget.Builder dimensions(int x, int y, int width, int height) {
            this.position(x, y);
            this.size(width, height);
            return this;
        }

        @Override
        public ButtonWidget.Builder size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        @Override
        public ButtonWidget.Builder width(int width) {
            this.width = width;
            return this;
        }

        @Override
        public ButtonWidget.Builder position(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        @Override
        public LibyWidgetBuilder syncType(WidgetSyncType syncType) {
            this.syncType = syncType;
            return this;
        }

        @Override
        public LibyButtonWidget build() {
            return new LibyButtonWidget(identifier, x, y, width, height, message, onPress, narrationSupplier, syncType);
        }
    }
}
