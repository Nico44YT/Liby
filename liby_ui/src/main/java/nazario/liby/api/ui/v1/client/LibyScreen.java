package nazario.liby.api.ui.v1.client;

import nazario.liby.api.util.LibyIdentifier;
import nazario.liby.internal.injections.LibyIdentifierResolvable;
import nazario.liby.internal.ui.v1.client.LibyWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class LibyScreen extends Screen implements LibyIdentifierResolvable {

    protected final LibyIdentifier identifier;

    public LibyScreen(Identifier identifier, Text title) {
        super(title);
        this.identifier = new LibyIdentifier(identifier);
    }

    public void sendScreenStateToServer() {
        this.children().forEach(child -> {
            if(child instanceof LibyWidget widget) {
                widget.send();
            }
        });
    }

    @Override
    public LibyIdentifier liby$getId() {
        return this.identifier;
    }
}
