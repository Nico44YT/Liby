package nazario.liby.client;

import nazario.liby.LibyMain;
import net.fabricmc.api.ClientModInitializer;

public class LibyClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        LibyMain.LOGGER.info("Liby Client Initialized");
    }
}
