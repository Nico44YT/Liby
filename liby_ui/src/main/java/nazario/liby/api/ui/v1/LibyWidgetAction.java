package nazario.liby.api.ui.v1;

import nazario.liby.internal.ui.v1.LibyWidgetState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

@FunctionalInterface
public interface LibyWidgetAction {
    <T extends LibyWidgetState> void apply(ServerWorld world, PlayerEntity player, T state);
}
