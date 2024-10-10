package nazario.liby;

import nazario.liby.networking.NetworkingRegistry;
import net.fabricmc.api.ModInitializer;

public class Liby implements ModInitializer {
    @Override
    public void onInitialize() {
        NetworkingRegistry.register();
    }
}
