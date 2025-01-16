package nazario.liby.mixin.client;

import nazario.liby.api.registry.runtime.models.LibyModelRegistry;
import net.minecraft.client.render.model.BlockStatesLoader;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;

@Mixin(BlockStatesLoader.class)
public class BlockStateLoaderMixin {

    @Redirect(method = "loadBlockStates", at = @At(value = "INVOKE", target = "Ljava/util/Map;getOrDefault(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    public <K, V> V liby$loadBlockStates(Map<K, V> blockStatesMap, K id, V defaultValue) {
        V object = blockStatesMap.getOrDefault(id, defaultValue);

        Identifier cleanId = Identifier.of(((Identifier)id).getNamespace(), ((Identifier)id).getPath().replace(".json","").replace("blockstates/",""));

        if(LibyModelRegistry.getBlockStateMap().get(cleanId) != null) {
            return (V)LibyModelRegistry.getBlockStateMap().get(cleanId).createTrackedData();
        }

        return object;
    }
}
