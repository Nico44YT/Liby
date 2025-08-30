package nazario.liby.mixin.assetgen.v1.client;

import com.google.common.collect.ImmutableSet;
import nazario.liby.internal.assetgen.v1.client.LibyResourcePack;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(ResourcePackManager.class)
public abstract class ResourcePackManagerMixin {
    @Shadow @Final private Set<ResourcePackProvider> providers;

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableSet;copyOf([Ljava/lang/Object;)Lcom/google/common/collect/ImmutableSet;"), remap = false)
    public <E> ImmutableSet<E> liby$injectPackProvider(E[] elements) {
        Object[] newElements = new Object[elements.length+1];
        System.arraycopy(elements, 0, newElements, 0, elements.length);
        newElements[elements.length] = LibyResourcePack.get();
        return (ImmutableSet<E>)ImmutableSet.copyOf(newElements);
    }
}
