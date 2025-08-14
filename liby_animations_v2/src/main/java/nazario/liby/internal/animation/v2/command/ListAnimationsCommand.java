package nazario.liby.internal.animation.v2.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import nazario.liby.api.animation.v2.LibyAnimatable;
import nazario.liby.api.animation.v2.LibyAnimation;
import nazario.liby.internal.animation.v2.LibyInternalAnimationRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class ListAnimationsCommand {

    public static ArgumentBuilder<ServerCommandSource, ?> create() {
        return CommandManager.literal("list")
                .then(CommandManager.argument("namespace", StringArgumentType.string()).executes(ListAnimationsCommand::execute))
                .executes(ListAnimationsCommand::execute);
    }

    public static int execute(CommandContext<ServerCommandSource> context) {
        Map<Identifier, Supplier<LibyAnimation<LibyAnimatable>>> animationMap = LibyInternalAnimationRegistry.getAnimations();

        try{
            String namespace = context.getArgument("namespace", String.class);
            if(namespace != null) animationMap = LibyInternalAnimationRegistry.getAllFromNamespace(namespace);
        } catch (Exception e){}

        ServerCommandSource source = context.getSource();
        MutableText text = Text.literal(String.format("Animations (%s):" + (animationMap.isEmpty()?"":"\n"), String.valueOf(animationMap.size())));

        AtomicInteger i = new AtomicInteger();
        final int size = animationMap.size();
        animationMap.forEach((identifier, animation) -> {
            text.append(Text.literal(identifier.toString() + (i.getAndIncrement()==size-1?"":"\n")));

        });

        source.sendFeedback(text, false);

        return 0;
    }
}
