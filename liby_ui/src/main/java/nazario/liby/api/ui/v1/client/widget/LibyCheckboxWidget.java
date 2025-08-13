package nazario.liby.api.ui.v1.client.widget;

import nazario.liby.api.ui.v1.client.LibyScreen;
import nazario.liby.api.ui.v1.client.WidgetSyncType;
import nazario.liby.api.ui.v1.state.LibyCheckboxWidgetState;
import nazario.liby.internal.ui.v1.LibyWidgetState;
import nazario.liby.api.util.LibyIdentifier;
import nazario.liby.internal.ui.v1.client.LibyWidget;
import nazario.liby.internal.ui.v1.client.LibyWidgetBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class LibyCheckboxWidget extends CheckboxWidget implements LibyWidget {

    protected LibyIdentifier identifier;
    protected WidgetSyncType syncType;

    protected LibyCheckboxWidget(LibyIdentifier identifier, int x, int y, int width, int height, Text message, boolean defaultState, WidgetSyncType syncType) {
        super(x, y, width, height, message, defaultState);

        this.identifier = identifier;
        this.syncType = syncType;

        this.setChecked(defaultState);
    }

    public LibyCheckboxWidget setChecked(boolean checked) {
        if(checked != this.isChecked()) super.onPress();
        return this;
    }

    @Override
    public void onPress() {
        super.onPress();

        if(this.syncType == WidgetSyncType.ON_UPDATE) {
            this.send();
        }
    }

    @Override
    public WidgetSyncType getSyncType() {
        return this.syncType;
    }

    @Override
    public LibyWidgetState getState() {
        return new LibyCheckboxWidgetState(this.isChecked());
    }

    @Override
    public LibyScreen getParentScreen() {
        return null;
    }

    @Override
    public LibyIdentifier liby$getId() {
        return this.identifier;
    }

    public static class Builder implements LibyWidgetBuilder {
        protected LibyIdentifier identifier;
        protected ButtonWidget.PressAction onPress;

        protected Text message;
        protected ButtonWidget.NarrationSupplier narrationSupplier;
        protected Tooltip tooltip;

        protected int x;
        protected int y;
        protected int width;
        protected int height;

        protected WidgetSyncType syncType;

        protected boolean defaultState;

        public Builder(Identifier identifier, ButtonWidget.PressAction onPress) {
            this.identifier = new LibyIdentifier(identifier);
            this.width = 150;
            this.height = 20;
            this.onPress = onPress;
            this.syncType = WidgetSyncType.PASSIVE;
        }

        public Builder message(Text message) {
            this.message = message;
            return this;
        }


        public Builder narrationSupplier(ButtonWidget.NarrationSupplier narrationSupplier) {
            this.narrationSupplier = narrationSupplier;
            return this;
        }


        public Builder tooltip(@Nullable Tooltip tooltip) {
            this.tooltip = tooltip;
            return this;
        }


        public Builder dimensions(int x, int y, int width, int height) {
            this.position(x, y);
            this.size(width, height);
            return this;
        }


        public Builder size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }


        public Builder width(int width) {
            this.width = width;
            return this;
        }


        public Builder position(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Builder defaultState(boolean defaultState) {
            this.defaultState = defaultState;
            return this;
        }

        @Override
        public LibyCheckboxWidget.Builder syncType(WidgetSyncType syncType) {
            this.syncType = syncType;
            return this;
        }

        @Override
        public LibyCheckboxWidget build() {
            return new LibyCheckboxWidget(identifier, x, y, width, height, message, defaultState, syncType);
        }
    }
}
