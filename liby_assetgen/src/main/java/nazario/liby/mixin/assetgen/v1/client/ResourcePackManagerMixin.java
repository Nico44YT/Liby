package nazario.liby.mixin.assetgen.v1.client;

import com.google.common.collect.ImmutableSet;
import nazario.liby.internal.assetgen.v1.client.LibyResourcePack;
import net.minecraft.resource.ResourcePackManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ResourcePackManager.class)
public abstract class ResourcePackManagerMixin {

    @Redirect(method = "<init>(Lnet/minecraft/resource/ResourcePackProfile$Factory;[Lnet/minecraft/resource/ResourcePackProvider;)V", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableSet;copyOf([Ljava/lang/Object;)Lcom/google/common/collect/ImmutableSet;"))
    public ImmutableSet<?> liby$injectPackProvider(Object[] elements) {
        Object[] newElements = new Object[elements.length+1];
        System.arraycopy(elements, 0, newElements, 0, elements.length);
        newElements[elements.length] = LibyResourcePack.get();
        return ImmutableSet.copyOf(newElements);
    }
}
