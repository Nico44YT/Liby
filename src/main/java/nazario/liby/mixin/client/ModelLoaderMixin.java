package nazario.liby.mixin.client;


import nazario.liby.api.LibyModelLoaderEntrypoint;
import nazario.liby.api.registry.rendering.LibyItemSpecialModelRegistry;
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
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;push(Ljava/lang/String;)V"))
    private void liby$initHead(BlockColors blockColors, Profiler profiler, Map jsonUnbakedModels, Map blockStates, CallbackInfo ci) {
        FabricLoader.getInstance().getEntrypoints("liby_model_loader", LibyModelLoaderEntrypoint.class).forEach(LibyModelLoaderEntrypoint::onLibyModelLoaderInitialize);

        LibyModelRegistry.getMap().forEach(((blockState, libyBlockState) -> {
            this.unbakedModels.put(libyBlockState.getModelIdentifier(), libyBlockState.model.bake());
            this.modelsToBake.put(libyBlockState.getModelIdentifier(), libyBlockState.model.bake());
        }));
    }

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;addModel(Lnet/minecraft/client/util/ModelIdentifier;)V", ordinal = 3, shift = At.Shift.AFTER))
    public void liby$setupSpecialModels(BlockColors blockColors, Profiler profiler, Map jsonUnbakedModels, Map blockStates, CallbackInfo ci) {
        LibyItemSpecialModelRegistry._getModelList().forEach(this::addModel);
    }

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;pop()V"))
    public void liby$initTail(BlockColors blockColors, Profiler profiler, Map jsonUnbakedModels, Map blockStates, CallbackInfo ci) {
    }

    @Inject(method = "loadModelFromJson", at = @At("HEAD"), cancellable = true)
    public void liby$loadModelFromJson(Identifier id, CallbackInfoReturnable<JsonUnbakedModel> cir) {
        LibyModelRegistry.getList().forEach(model -> {
            if (model.getId().equals(id)) {
                UnbakedModel model1 = model.bake();
                if(model1 instanceof JsonUnbakedModel jsonUnbakedModel) {
                    cir.setReturnValue(jsonUnbakedModel);
                }
            }
        });
    }
}
