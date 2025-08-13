package nazario.liby.internal.command.animation;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import nazario.liby.api.animation.LibyAnimationHelper;
import nazario.liby.api.animation.LibyAnimationInstance;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class CheckAnimationCommand {
    public static ArgumentBuilder<ServerCommandSource, ?> create() {
        return CommandManager.literal("check")
                .then(CommandManager.argument("target", EntityArgumentType.entity())
                        .executes(CheckAnimationCommand::execute));
    }

    public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if(context.getArgument("target", EntitySelector.class).getEntity(context.getSource()) instanceof LivingEntity target) {
            LibyAnimationInstance instance = LibyAnimationHelper.getAnimationInstance(target);

            if(instance == null) {
                MutableText text = Text.literal(String.format("There is no animation playing for %s (%s)", target.getName().getString(), target.getUuid().toString()));
                context.getSource().sendFeedback(() -> text, false);
                return 0;
            }

            MutableText text = Text.literal
                    (String.format(
                    "Animation Info:\n" +
                    "Target: §7%s (%s)§r\n" +
                    "Ticks-Left: §7%s§r\n" +
                    "Play-State: §7%s§r",
                    target.getName().getString(),
                    target.getUuid().toString(),
                    String.format("%dt, %ds, %smin", instance.getTicksLeft(), instance.getTicksLeft()/20, instance.getTicksLeft()/20/60),
                    instance.getPlayState().name()
            ));

            context.getSource().sendFeedback(() -> text, false);

            return 0;
        }

        return 1;
    }
}
