package nazario.liby.mixin.animation.v2;

import nazario.liby.api.animation.v2.LibyAnimatable;
import nazario.liby.api.animation.v2.LibyAnimation;
import nazario.liby.api.animation.v2.LibyAnimationPlayState;
import nazario.liby.api.animation.v2.LibyBlockEntityAnimation;
import nazario.liby.api.util.nbt.LibyNbtCompound;
import nazario.liby.internal.animation.v2.LibyInternalAnimationRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(BlockEntity.class)
@SuppressWarnings({"all"})
public abstract class BlockEntityInjectMixin implements LibyAnimatable {

    @Unique
    private LibyBlockEntityAnimation playingAnimation;

    @Override
    public void startLibyAnimation(Identifier identifier) {
        BlockEntity entity = (BlockEntity)(Object)this;
        LibyBlockEntityAnimation newAnimation = (LibyBlockEntityAnimation) LibyInternalAnimationRegistry.createAnimation(identifier, entity);

        if(newAnimation != null) {
            if(this.playingAnimation != null && this.playingAnimation.matches(newAnimation) && this.playingAnimation.getPlayState() != LibyAnimationPlayState.RESTARTING) {
                this.restartLibyAnimation(newAnimation);
                return;
            }

            if(this.playingAnimation != null) this.playingAnimation.onStop(entity.getWorld(), entity);

            this.playingAnimation = newAnimation;
            this.playingAnimation.start(entity.getWorld(), entity);
        }
    }

    @Override
    public void pauseLibyAnimation() {
        BlockEntity entity = (BlockEntity)(Object)this;

        getLibyAnimation().ifPresent(anim -> anim.pause(entity.getWorld(), entity));
    }

    @Override
    public void resumeLibyAnimation() {
        BlockEntity entity = (BlockEntity)(Object)this;

        getLibyAnimation().ifPresent(anim -> anim.resume(entity.getWorld(), entity));
    }

    @Override
    public void stopLibyAnimation() {
        BlockEntity entity = (BlockEntity)(Object)this;

        getLibyAnimation().ifPresent(anim -> anim.stop(entity.getWorld(), entity));
        this.playingAnimation = null;
    }

    @Override
    public void restartLibyAnimation(LibyAnimation newAnimation) {
        BlockEntity entity = (BlockEntity)(Object)this;

        getLibyAnimation().ifPresent(anim -> anim.restart(entity.getWorld(), entity, newAnimation));

        this.playingAnimation.setPlayState(LibyAnimationPlayState.RESTARTING);

        this.startLibyAnimation(newAnimation.liby$getId());
    }

    @Override
    public Optional<LibyBlockEntityAnimation> getLibyAnimation() {
        return (Optional<LibyBlockEntityAnimation>) Optional.ofNullable(this.playingAnimation);
    }

    @Override
    public LibyAnimationPlayState getLibyAnimationState() {
        return this.getLibyAnimation()
                .map(LibyAnimation::getPlayState)
                .orElse(LibyAnimationPlayState.NO_ANIMATION);
    }

    @Inject(method = "tick", at = @At("TAIL"), remap = false)
    public void libyAnimations$tick(CallbackInfo ci) {
        BlockEntity entity = (BlockEntity)(Object)this;
        if(this.playingAnimation != null) this.playingAnimation.tick(entity.getWorld(), entity);
    }

    @Inject(method = "writeNbt", at = @At("TAIL"))
    public void libyAnimations$writeNbt(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        if(this.playingAnimation != null) {
            LibyNbtCompound nbtCompound = new LibyNbtCompound();
            this.playingAnimation.writeToNbt(nbtCompound);
            nbt.put("liby_animation", nbtCompound);
        }
    }

    @Inject(method = "readNbt", at = @At("TAIL"))
    public void libyAnimations$readNbt(NbtCompound nbt, CallbackInfo ci) {
        if(nbt.contains("liby_animation")) {
            BlockEntity entity = (BlockEntity)(Object)this;
            LibyNbtCompound nbtCompound = new LibyNbtCompound(nbt.getCompound("liby_animation"));

            Identifier identifier = nbtCompound.getIdentifier("identifier");

            this.playingAnimation = (LibyBlockEntityAnimation)LibyInternalAnimationRegistry.createAnimation(identifier, entity);
            if(this.playingAnimation != null) this.playingAnimation.readFromNbt(nbtCompound);
        }
    }
}