package nazario.liby.api.ui.v1;

import nazario.liby.api.util.LibyIdentifier;
import nazario.liby.internal.injections.LibyIdentifierResolvable;
import nazario.liby.internal.ui.v1.LibyWidgetState;

import java.util.List;

public class LibyScreenState implements LibyIdentifierResolvable {

    protected LibyIdentifier screenId;
    protected List<LibyWidgetState> states;

    public LibyScreenState(LibyIdentifier screenId, List<LibyWidgetState> states) {
        this.screenId = screenId;
        this.states = states;
    }

    @Override
    public LibyIdentifier liby$getId() {
        return this.screenId;
    }
}
