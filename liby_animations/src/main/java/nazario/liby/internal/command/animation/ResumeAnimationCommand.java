package nazario.liby.internal.command.animation;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import nazario.liby.api.animation.LibyAnimationHelper;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ResumeAnimationCommand {
    public static ArgumentBuilder<ServerCommandSource, ?> create() {
        return CommandManager.literal("resume")
                .then(CommandManager.argument("target", EntityArgumentType.entity()).executes(ResumeAnimationCommand::execute));

    }

    public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if(context.getArgument("target", EntitySelector.class).getEntity(context.getSource()) instanceof LivingEntity target) {
            Identifier id = LibyAnimationHelper.getAnimationInstance(target).getAnimationIdentifier();
            LibyAnimationHelper.stop(target);
            context.getSource().sendFeedback(() -> Text.literal(String.format("Resumed animation (%s)", id)), true);
            return 0;
        }

        return 1;
    }

}
