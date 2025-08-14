package nazario.liby;

import nazario.liby.internal.command.LibyMainCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LibyMain implements ModInitializer {

    public static final String MOD_ID = "liby";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info(String.format("[Liby] Liby started with version %s",
                FabricLoader.getInstance().getModContainer("liby_main").get().getMetadata().getVersion()));

        CommandRegistrationCallback.EVENT.register(LibyMainCommand::new);
    }
}
