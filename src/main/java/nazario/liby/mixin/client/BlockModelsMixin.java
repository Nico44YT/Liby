package nazario.liby.mixin.client;

import nazario.liby.api.registry.runtime.models.LibyBlockState;
import nazario.liby.api.registry.runtime.models.LibyModelRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.block.BlockModels;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockModels.class)
public class BlockModelsMixin {

    @Shadow
    @Final
    private BakedModelManager modelManager;

    @Inject(method = "getModel", at = @At("HEAD"), cancellable = true)
    public void liby$getModel(BlockState state, CallbackInfoReturnable<BakedModel> cir) {
        LibyBlockState libyBlockState = LibyModelRegistry.getModel(state);
        if (libyBlockState != null) {
            cir.setReturnValue(modelManager.getModel(libyBlockState.getModelIdentifier()));
        }
    }
}
