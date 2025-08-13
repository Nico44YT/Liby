package nazario.liby.internal.command.animation;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import nazario.liby.api.animation.LibyAnimation;
import nazario.liby.api.animation.LibyAnimationHelper;
import nazario.liby.api.animation.LibyAnimationRegistry;
import nazario.liby.api.animation.LibyEntityAnimation;
import net.minecraft.command.CommandSource;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class StartAnimationCommand {
    public static ArgumentBuilder<ServerCommandSource, ?> create() {
        return CommandManager.literal("start")
                .then(CommandManager.argument("target", EntityArgumentType.entity())
                        .then(CommandManager.argument("animation_identifier", IdentifierArgumentType.identifier()).suggests(StartAnimationCommand::suggests)
                                .executes(StartAnimationCommand::execute)));
    }

    public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if(context.getArgument("target", EntitySelector.class).getEntity(context.getSource()) instanceof LivingEntity target) {
            Optional<LibyAnimation> optional =  LibyAnimationRegistry.get(context.getArgument("animation_identifier", Identifier.class));

            if(optional.isPresent()) {
                LibyEntityAnimation animation = (LibyEntityAnimation)optional.get();

                if(!LibyAnimationHelper.canStart(target, animation)) {
                    context.getSource().sendFeedback(() -> Text.translatable("liby.animation.fail", animation.getId(), animation.getClass()), false);
                    return 1;
                }

                LibyAnimationHelper.start(target, animation);
                context.getSource().sendFeedback(() -> Text.literal(String.format("Started animation (%s)", optional.get().getId())), true);
                return 0;
            }
        }

        return 1;
    }

    private static CompletableFuture<Suggestions> suggests(CommandContext<ServerCommandSource> context, SuggestionsBuilder suggestionsBuilder) {
        CommandSource commandSource = (CommandSource)context.getSource();
        List<Suggestion> suggestions = new ArrayList<>();

        StringReader stringReader = new StringReader(suggestionsBuilder.getInput());
        stringReader.setCursor(suggestionsBuilder.getStart());

        LibyAnimationRegistry.getAll().entrySet().stream().filter(entry -> entry.getValue() instanceof LibyEntityAnimation).forEach(entry -> {
            suggestions.add(new Suggestion(StringRange.between(stringReader.getCursor(), stringReader.getCursor()+entry.getKey().toString().length()), entry.getKey().toString()));
        });

        Collections.sort(suggestions);

        return CompletableFuture.supplyAsync(() -> new Suggestions(StringRange.at(stringReader.getCursor()), suggestions));
    }
}
