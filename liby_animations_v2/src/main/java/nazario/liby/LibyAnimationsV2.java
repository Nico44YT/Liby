package nazario.liby;

import com.mojang.brigadier.builder.ArgumentBuilder;
import nazario.liby.internal.animation.v2.command.*;
import nazario.liby.internal.command.LibyMainCommand;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;

public class LibyAnimationsV2 implements ModInitializer {

    public static final String MOD_ID = "liby_animations";

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

    public static Identifier id(String name) {
        return Identifier.of(MOD_ID, name);
    }
}
