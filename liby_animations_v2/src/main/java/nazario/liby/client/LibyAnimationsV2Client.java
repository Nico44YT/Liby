package nazario.liby.client;

import nazario.liby.internal.animation.v2.networking.SyncAnimationS2C;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class LibyAnimationsV2Client implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(SyncAnimationS2C.ID, new SyncAnimationS2C(null, null));
    }
}
