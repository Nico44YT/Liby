package nazario.liby.client;

import nazario.liby.api.networking.v1.wrapper.client.LibyClientNetworking;
import nazario.liby.internal.animation.v2.networking.SyncAnimationS2C;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class LibyAnimationsV2Client implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        LibyClientNetworking.registerGlobalReceiver(SyncAnimationS2C.PACKET_TYPE, (p, c) -> p.receive(p, c));
    }
}
