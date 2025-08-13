package nazario.liby.internal.animation.v2.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import nazario.liby.api.animation.v2.LibyEntityAnimation;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

public class CheckAnimationCommand {
    public static ArgumentBuilder<ServerCommandSource, ?> create() {
        return CommandManager.literal("check")
                .then(CommandManager.argument("target", EntityArgumentType.entity())
                        .executes(CheckAnimationCommand::execute));
    }

    public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

        if(context.getArgument("target", EntitySelector.class).getEntity(context.getSource()) instanceof LivingEntity target) {
            if(target.getLibyAnimation().isEmpty()) {
                MutableText text = Text.literal(String.format("There is no animation playing for %s (%s)", target.getName().getString(), target.getUuid().toString()));
                context.getSource().sendFeedback(() -> text, false);
                return 0;
            }

            MutableText text = getMutableText(target);

            context.getSource().sendFeedback(() -> text, false);

            return 0;
        }

        return 1;
    }

    private static @NotNull MutableText getMutableText(LivingEntity target) {
        LibyEntityAnimation<LivingEntity> animation = (LibyEntityAnimation) target.getLibyAnimation().get();

        return Text.literal
                (String.format(
                        "Animation Info:\n" +
                                "Target: §7%s (%s)§r\n" +
                                "Ticks-Left: §7%s§r\n" +
                                "Play-State: §7%s§r",
                        target.getName().getString(),
                        target.getUuid().toString(),
                        String.format("%dt, %ds, %smin", animation.getTicksLeft(), animation.getTicksLeft()/20, animation.getTicksLeft()/20/60), animation.getPlayState().name()
                ));
    }
}
