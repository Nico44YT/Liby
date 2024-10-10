package nazario.liby.client;

import nazario.liby.networking.NetworkingRegistry;
import net.fabricmc.api.ClientModInitializer;

public class LibyClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        NetworkingRegistry.clientRegister();
    }
}

























