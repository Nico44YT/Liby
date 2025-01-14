package nazario.liby.mixin.client;


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
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {

    @Shadow
    protected abstract void addModel(ModelIdentifier modelId);

    @Shadow
    @Final
    private Map<Identifier, UnbakedModel> modelsToBake;

    @Shadow
    @Final
    private Map<Identifier, UnbakedModel> unbakedModels;

    @Shadow @Final private ResourceManager resourceManager;

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;push(Ljava/lang/String;)V"))
    private void liby$initHead(ResourceManager resourceManager, BlockColors blockColors, Profiler profiler, int mipmapLevel, CallbackInfo ci) {
        FabricLoader.getInstance().getEntrypoints("liby_model_loader", LibyModelLoaderEntrypoint.class).forEach(LibyModelLoaderEntrypoint::onLibyModelLoaderInitialize);

        LibyModelRegistry.getModelList().forEach(model -> {
            this.unbakedModels.put(model.getId(), model.bake());
            this.modelsToBake.put(model.getId(), model.bake());
        });
    }

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;addModel(Lnet/minecraft/client/util/ModelIdentifier;)V", ordinal = 3, shift = At.Shift.AFTER))
    public void liby$setupSpecialModels(ResourceManager resourceManager, BlockColors blockColors, Profiler profiler, int mipmapLevel, CallbackInfo ci) {
        LibyItemSpecialModelRegistry._getModelList().forEach(this::addModel);
    }

    @Redirect(method = "loadModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourceManager;getAllResources(Lnet/minecraft/util/Identifier;)Ljava/util/List;"))
    public List<Resource> liby$loadBlockStates(ResourceManager instance, Identifier identifier) {
        Identifier cleanId = new Identifier(identifier.getNamespace(), identifier.getPath().replace(".json","").replace("blockstates/",""));

        if(LibyModelRegistry.getBlockStateMap().get(cleanId) != null) {
            return LibyModelRegistry.getBlockStateMap().get(cleanId).createResource();
        }

        return instance.getAllResources(identifier);
    }

    @Inject(method = "loadModelFromJson", at = @At("HEAD"), cancellable = true)
    public void liby$loadModelFromJson(Identifier id, CallbackInfoReturnable<JsonUnbakedModel> cir) {
        LibyModelRegistry.getModelList().forEach(model -> {
            if (model.getId().equals(id)) {
                if(model instanceof LibyJsonModel jsonModel) {
                    if(jsonModel.bake() instanceof JsonUnbakedModel jsonUnbakedModel) {
                        cir.setReturnValue(jsonUnbakedModel);
                        return;
                    }
                }
            }
        });
    }
}
