package nazario.liby.mixin.client;


import javassist.bytecode.Opcode;
import nazario.liby.api.LibyModelLoaderEntrypoint;
import nazario.liby.api.registry.rendering.LibyItemSpecialModelRegistry;
import nazario.liby.api.registry.runtime.models.LibyJsonModel;
import nazario.liby.api.registry.runtime.models.LibyModelRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Environment(EnvType.CLIENT)
@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {

    @Shadow
    @Final
    private Map<ModelIdentifier, UnbakedModel> modelsToBake;

    @Shadow
    @Final
    private Map<ModelIdentifier, UnbakedModel> unbakedModels;

    @Shadow protected abstract void loadItemModel(ModelIdentifier id);


    @ModifyVariable(method = "<init>", at = @At(value = "HEAD"), ordinal = 0, argsOnly = true)
    private static Map<Identifier, JsonUnbakedModel> liby$jsonUnbakedModels(Map<Identifier, JsonUnbakedModel> value) {
        FabricLoader.getInstance().getEntrypoints("liby_model_loader", LibyModelLoaderEntrypoint.class).forEach(LibyModelLoaderEntrypoint::onLibyModelLoaderInitialize);

        HashMap<Identifier, JsonUnbakedModel> models = new HashMap<>(value);

        LibyModelRegistry.getModelList().forEach(model -> {
            models.put(model.getBasicId().withPrefixedPath("models/").withSuffixedPath(".json"), (JsonUnbakedModel) model.bake());
        });

        return models;
    }

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;push(Ljava/lang/String;)V"))
    public void liby$initHead(BlockColors blockColors, Profiler profiler, Map jsonUnbakedModels, Map blockStates, CallbackInfo ci) {
        LibyModelRegistry.getModelList().forEach(model -> {
            unbakedModels.put(model.getModelId(), model.bake());
            this.unbakedModels.put(model.getModelId(), model.bake());
        });

    }

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;loadItemModel(Lnet/minecraft/client/util/ModelIdentifier;)V"))
    public void liby$setupSpecialModels(BlockColors blockColors, Profiler profiler, Map jsonUnbakedModels, Map blockStates, CallbackInfo ci) {
        LibyItemSpecialModelRegistry._getModelList().forEach(this::loadItemModel);
    }

    @Inject(method = "loadModelFromJson", at = @At("HEAD"), cancellable = true)
    public void liby$loadModelFromJson(Identifier id, CallbackInfoReturnable<JsonUnbakedModel> cir) {
        LibyModelRegistry.getModelList().forEach(model -> {
            if (model.getModelId().id().equals(id)) {
                if(model instanceof LibyJsonModel jsonModel) {
                    cir.setReturnValue(jsonModel.bake());
                    return;
                }
            }
        });
    }
}
