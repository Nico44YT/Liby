package nazario.liby.mixin.assetgen.v1.manager.client;

import nazario.liby.internal.assetgen.v1.client.LibyResourcePack;
import net.minecraft.client.gui.screen.pack.PackScreen;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PackScreen.class)
public abstract class PackScreenMixin {
    @Inject(method = "loadPackIcon", at = @At("HEAD"), cancellable = true)
    public void liby$loadPackIcon(TextureManager textureManager, ResourcePackProfile resourcePackProfile, CallbackInfoReturnable<Identifier> cir) {
        if(LibyResourcePack.get().profile.equals(resourcePackProfile)) {
            cir.setReturnValue(Identifier.of("liby", "icon.png"));
        }
    }
}
