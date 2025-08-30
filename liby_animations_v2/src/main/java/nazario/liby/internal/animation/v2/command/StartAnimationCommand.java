package nazario.liby.internal.animation.v2.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import nazario.liby.api.animation.v2.LibyAnimatable;
import nazario.liby.api.animation.v2.LibyAnimation;
import nazario.liby.api.animation.v2.LibyEntityAnimation;
import nazario.liby.internal.animation.v2.LibyInternalAnimationRegistry;
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
import java.util.function.Supplier;

public class StartAnimationCommand {
    public static ArgumentBuilder<ServerCommandSource, ?> create() {
        return CommandManager.literal("start")
                .then(CommandManager.argument("target", EntityArgumentType.entity())
                        .then(CommandManager.argument("animation_identifier", IdentifierArgumentType.identifier()).suggests(StartAnimationCommand::suggests)
                                .executes(StartAnimationCommand::execute)));
    }

    public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if(context.getArgument("target", EntitySelector.class).getEntity(context.getSource()) instanceof LivingEntity target) {
            Optional<Supplier<LibyAnimation<LibyAnimatable>>> optional = LibyInternalAnimationRegistry.getAnimationSupplier(context.getArgument("animation_identifier", Identifier.class));

            if(optional.isPresent()) {
                LibyAnimation<LibyAnimatable> animation = optional.get().get();

                if(!animation.checkPredicate(target)) {
                    context.getSource().sendFeedback(() -> Text.translatable("liby.animation.fail", animation.liby$getId(), animation.getClass()), false);
                    return 1;
                }

                target.startLibyAnimation(animation.liby$getId());
                context.getSource().sendFeedback(() -> Text.literal(String.format("Started animation (%s)", animation.liby$getId())), true);
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

        LibyInternalAnimationRegistry.getAnimations().entrySet().stream().filter(entry -> entry.getValue().factory() instanceof LibyEntityAnimation.EntityFactory<?,?>).forEach(entry -> {
            suggestions.add(new Suggestion(StringRange.between(stringReader.getCursor(), stringReader.getCursor()+entry.getKey().toString().length()), entry.getKey().toString()));
        });

        Collections.sort(suggestions);

        return CompletableFuture.supplyAsync(() -> new Suggestions(StringRange.at(stringReader.getCursor()), suggestions));
    }
}
