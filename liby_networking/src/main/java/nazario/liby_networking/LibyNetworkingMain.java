package nazario.liby_networking;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class LibyNetworkingMain implements ModInitializer {

    public static final String MOD_ID = "liby_networking";

    @Override
    public void onInitialize() {
    }

    public static Identifier id(String name) {
        return Identifier.of(MOD_ID, name);
    }
}
