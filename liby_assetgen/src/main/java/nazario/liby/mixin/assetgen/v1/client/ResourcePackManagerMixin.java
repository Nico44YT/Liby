package nazario.liby.mixin.assetgen.v1.client;

import com.google.common.collect.ImmutableMap;
import nazario.liby.internal.assetgen.v1.client.LibyResourcePack;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProfile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.HashMap;
import java.util.Map;

@Mixin(ResourcePackManager.class)
public abstract class ResourcePackManagerMixin {
    @Redirect(method = "providePackProfiles", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableMap;copyOf(Ljava/util/Map;)Lcom/google/common/collect/ImmutableMap;"), remap = false)
    public <T extends String, U extends ResourcePackProfile> ImmutableMap<T, U> liby$injectLibyPackProfile(Map<T, U> kvMap) {
        HashMap<String, ResourcePackProfile> profiles = new HashMap<>(Map.copyOf(kvMap));
        profiles.put(LibyResourcePack.get().getName(), LibyResourcePack.get().profile);
        return (ImmutableMap<T, U>)ImmutableMap.copyOf(profiles);
    }
}
