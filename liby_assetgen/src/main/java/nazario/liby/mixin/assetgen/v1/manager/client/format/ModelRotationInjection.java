package nazario.liby.mixin.assetgen.v1.manager.client.format;

import nazario.liby.internal.assetgen.v1.client.format.LibyFreeFormRotation;
import nazario.liby.internal.assetgen.v1.client.mixin_injects.LibyModelRotation;
import net.minecraft.client.render.model.json.ModelRotation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ModelRotation.class)
public abstract class ModelRotationInjection implements LibyModelRotation {

    @Unique
    private boolean isSet;
    @Unique
    private LibyFreeFormRotation libyFreeFormRotation;

    @Override
    public LibyFreeFormRotation libyAssets$getFreeFormRotation() {
        return libyFreeFormRotation;
    }

    @Override
    public void libyAssets$setFreeFormRotation(LibyFreeFormRotation libyFreeFormRotation) {
        this.isSet = true;
        this.libyFreeFormRotation = libyFreeFormRotation;
    }

    @Override
    public boolean libyAssets$isLibyFreeFormSet() {
        return this.isSet;
    }
}
