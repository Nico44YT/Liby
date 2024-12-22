package nazario.liby.mixin;

import nazario.liby.api.item.LibyItemExtendedMethods;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(Item.class)
public abstract class ItemMixin implements LibyItemExtendedMethods {
    @Unique
    private Item.Settings itemSettings;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void liby$init(Item.Settings settings, CallbackInfo ci) {
        this.itemSettings = settings;
    }

    @Override
    public Identifier liby$getId() {
        return ((Item) (Object) this).getRegistryEntry().getKey().get().getValue();
    }

    @Override
    public Optional<Item.Settings> liby$getItemSettings() {
        return Optional.of(itemSettings);
    }
}
