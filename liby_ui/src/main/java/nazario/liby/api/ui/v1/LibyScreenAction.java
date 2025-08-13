package nazario.liby.api.ui.v1;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

@FunctionalInterface
public interface LibyScreenAction {
    <T extends LibyScreenState> void apply(ServerWorld world, PlayerEntity player, T state);
}
