package nazario.liby.internal.animation;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import nazario.liby.api.animation.LibyAnimationInstance;
import nazario.liby.api.animation.LibyEntityAnimation;
import nazario.liby.api.animation.PlayState;
import nazario.liby.api.util.nbt.LibyNbtCompound;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class EntityAnimationComponent implements AutoSyncedComponent {
    public static final Identifier ID = Identifier.of("liby","entity_animation");

    private final LivingEntity owner;
    private LibyAnimationInstance animationInstance;

    public EntityAnimationComponent(LivingEntity owner) {
        this.owner = owner;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        LibyNbtCompound nbtCompound = new LibyNbtCompound(tag);

        this.animationInstance = nbtCompound.getNbtConvertible("instance", LibyAnimationInstance::new);
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        LibyNbtCompound nbtCompound = new LibyNbtCompound(tag);

        if(this.animationInstance != null) nbtCompound.putNbtConvertible("instance", this.animationInstance);

        tag.copyFrom(nbtCompound);
    }

    public LibyAnimationInstance getAnimationInstance() {
        return this.animationInstance;
    }

    public PlayState getPlayingState() {
        return this.animationInstance.getPlayState();
    }

    public void start(LibyEntityAnimation animation) {
        this.animationInstance = new LibyAnimationInstance(this.owner, PlayState.PLAYING, animation.getId(), animation, animation.getMaxDuration());
        this.animationInstance.getAnimation().onStart(this.animationInstance);
    }

    public void pause() {
        if(this.animationInstance != null) {
            this.animationInstance.setPlayState(PlayState.PAUSED);
            this.animationInstance.getAnimation().onPause(this.animationInstance);
        }
    }

    public void resume() {
        if(this.animationInstance != null) {
            this.animationInstance.setPlayState(PlayState.PLAYING);
            this.animationInstance.getAnimation().onResume(this.animationInstance);
        }
    }

    public void stop() {
        if(this.animationInstance != null) {
            this.animationInstance.getAnimation().onStop(this.animationInstance);
            this.animationInstance.setPlayState(PlayState.STOPPED);
        }
    }

    @Override
    public boolean shouldSyncWith(ServerPlayerEntity player) {
        return true;
    }

    public void clear() {
        this.animationInstance = null;
    }

    public void sync() {
        AnimationEntrypoint.ENTITY_ANIMATION_COMPONENT_KEY.sync(owner);
    }
}
