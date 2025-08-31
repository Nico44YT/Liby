package nazario.liby.mixin.assetgen.v1.client;

import nazario.liby.internal.assetgen.v1.client.LibyInternalAssetRegistry;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
    @Shadow protected abstract JsonUnbakedModel loadModelFromJson(Identifier id) throws IOException;

    //@Inject(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/model/ModelLoader;jsonUnbakedModels:Ljava/util/Map;", opcode = Opcodes.PUTFIELD))
    //public void liby$init(BlockColors blockColors, Profiler profiler, Map<Identifier, JsonUnbakedModel> jsonUnbakedModels, Map blockStates, CallbackInfo ci) {
    //    LibyInternalAssetRegistry.itemModelRules.forEach(rule -> {
    //        try {
    //            ModelIdentifier id = new ModelIdentifier(rule.modelIdentifier().liby$getId().prependPath("item/"), rule.modelIdentifier().getVariant());
    //            jsonUnbakedModels.put(id, loadModelFromJson(id));
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //    });
    //}

    @Mutable @Shadow @Final
    public Map<Identifier, JsonUnbakedModel> jsonUnbakedModels;

    @Redirect(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/model/ModelLoader;jsonUnbakedModels:Ljava/util/Map;", opcode = Opcodes.PUTFIELD))
    private void liby$init(ModelLoader modelLoader, Map<Identifier, JsonUnbakedModel> map) {
        this.jsonUnbakedModels = new HashMap<>(map);

        LibyInternalAssetRegistry.itemModelRules.forEach(rule -> {
            try{
                ModelIdentifier id = new ModelIdentifier(rule.modelIdentifier().liby$getId().prependPath("item/"), rule.modelIdentifier().getVariant());
                this.jsonUnbakedModels.put(id, loadModelFromJson(id));
            }catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
