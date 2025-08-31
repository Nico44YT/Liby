package nazario.liby.mixin.world.v1;

import nazario.liby.api.world.v1.entity.LibyBoat;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.vehicle.BoatEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin {
    @Shadow @Final private static TrackedData<Integer> BOAT_TYPE;

    @Redirect(method = "initDataTracker", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/data/DataTracker;startTracking(Lnet/minecraft/entity/data/TrackedData;Ljava/lang/Object;)V"))
    public <T> void liby$initDataTracker(DataTracker instance, TrackedData<T> key, T initialValue) {
        BoatEntity entity = (BoatEntity)(Object)this;

        if(entity instanceof LibyBoat) {
            if(!key.equals(BOAT_TYPE)) instance.startTracking(key, initialValue);
            return;
        }

        instance.startTracking(key, initialValue);
    }
}
