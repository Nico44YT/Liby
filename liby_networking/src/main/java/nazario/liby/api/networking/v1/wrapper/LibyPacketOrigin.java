package nazario.liby.api.networking.v1.wrapper;

import net.minecraft.network.NetworkSide;

public enum LibyPacketOrigin {
    SERVER_TO_CLIENT(NetworkSide.CLIENTBOUND),
    CLIENT_TO_SERVER(NetworkSide.SERVERBOUND);

    private final NetworkSide side;

    LibyPacketOrigin(NetworkSide side) {
        this.side = side;
    }

    public NetworkSide getNetworkSide() {
        return side;
    }
}
