package nazario.liby;

import com.mojang.brigadier.builder.ArgumentBuilder;
import nazario.liby.internal.command.LibyMainCommand;
import nazario.liby.internal.command.animation.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class LibyAnimationsMain implements ModInitializer {
    @Override
    public void onInitialize() {
        LibyMainCommand.registerSubCommand(getSubCommandParts());
    }

    public static ArgumentBuilder<ServerCommandSource, ?> getSubCommandParts() {
        return CommandManager.literal("animation")
                .requires(source -> source.hasPermissionLevel(3))
                .then(ListAnimationsCommand.create())
                .then(StartAnimationCommand.create())
                .then(CheckAnimationCommand.create())
                .then(StopAnimationCommand.create())
                .then(PauseAnimationCommand.create())
                .then(ResumeAnimationCommand.create());
    }
}
