package nazario.liby.mixin;

import nazario.liby.api.registry.itemgroup.LibyItemGroupRegistry;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Registries.class)
public abstract class RegistriesMixin {
    @Inject(method = "freezeRegistries", at = @At("HEAD"))
    private static void liby$freezeRegistries(CallbackInfo ci) {
        LibyItemGroupRegistry.registerAll();
    }
}
