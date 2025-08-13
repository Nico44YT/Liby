package nazario.liby.mixin.manager;

import com.google.gson.JsonElement;
import nazario.liby.internal.registry.LibyInternalRecipeRegistry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Map;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin {
    @ModifyVariable(method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V", at = @At("HEAD"), order = 1, argsOnly = true)
    private Map<Identifier, JsonElement> liby$apply(Map<Identifier, JsonElement> map) {
        LibyInternalRecipeRegistry.get().resourceMap().forEach(libyJsonObject -> {
            if (libyJsonObject != null) map.put(libyJsonObject.getId(), libyJsonObject.createJson());
        });

        return map;
    }
}
