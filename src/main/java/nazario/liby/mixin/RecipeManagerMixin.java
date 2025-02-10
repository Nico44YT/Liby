package nazario.liby.mixin;

import com.google.gson.JsonElement;
import nazario.liby.api.registry.runtime.recipe.LibyRecipeRegistry;
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
        LibyRecipeRegistry.get().getResourceMap().forEach((identifier, element) -> {
            if(element != null) map.put(identifier, element);
        });
        return map;
    }
}
