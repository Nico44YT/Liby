package nazario.liby.api.animation.v2;

import nazario.liby.api.util.LibyIdentifier;
import nazario.liby.api.util.nbt.LibyNbtCompound;
import nazario.liby.api.util.nbt.NbtConvertible;
import nazario.liby.internal.injections.LibyIdentifierResolvable;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Predicate;

public abstract class LibyAnimation<T extends LibyAnimatable> implements LibyIdentifierResolvable, NbtConvertible {

    protected LibyIdentifier identifier;
    protected Predicate<T> predicate;

    protected long ticksLeft;
    protected LibyAnimationPlayState playState;

    private final long MAX_DURATION;

    public LibyAnimation(Identifier identifier, Predicate<T> predicate) {
        this.identifier = new LibyIdentifier(identifier);
        this.predicate = predicate;
        this.playState = LibyAnimationPlayState.STOPPED;

        MAX_DURATION = getMaxDuration();
        this.ticksLeft = MAX_DURATION;
    }

    abstract public long getMaxDuration();
    abstract public void onStart(World world, T animatable);
    abstract public void onPause(World world, T animatable);
    abstract public void onResume(World world, T animatable);
    abstract public void onStop(World world, T animatable);
    abstract public void onRestart(World world, T animatable, LibyAnimation<T> newAnimation);
    abstract public void onTick(World world, T animatable);

    public boolean shouldStop(World world, T animatable) {
        return this.ticksLeft < 0;
    }

    abstract public void syncAnimation(T animatable);

    @ApiStatus.Internal
    public void tick(World world, T animatable) {
        if(this.playState == LibyAnimationPlayState.PLAYING) {
            this.ticksLeft--;

            this.onTick(world, animatable);

            if(shouldStop(world, animatable)) this.stop(world, animatable);
        }
    }

    @ApiStatus.Internal
    public void start(World world, T libyAnimatable) {
        this.playState = LibyAnimationPlayState.PLAYING;

        this.onStart(world, libyAnimatable);
    }

    @ApiStatus.Internal
    public void pause(World world, T libyAnimatable) {
        this.playState = LibyAnimationPlayState.PAUSED;

        this.onPause(world, libyAnimatable);
    }

    @ApiStatus.Internal
    public void resume(World world, T libyAnimatable) {
        this.playState = LibyAnimationPlayState.PLAYING;

        this.onResume(world, libyAnimatable);
    }

    @ApiStatus.Internal
    public void stop(World world, T libyAnimatable) {
        this.playState = LibyAnimationPlayState.STOPPED;

        this.onStop(world, libyAnimatable);
    }

    @ApiStatus.Internal
    public void restart(World world, T libyAnimatable, LibyAnimation<T> newAnimation) {
        this.onRestart(world, libyAnimatable, newAnimation);
    }

    public void readCustomData(LibyNbtCompound tag) {

    }

    public void writeCustomData(LibyNbtCompound tag) {

    }

    @Override
    @ApiStatus.Internal
    public void readFromNbt(LibyNbtCompound tag) {
        this.identifier = new LibyIdentifier(tag.getIdentifier("identifier"));
        this.ticksLeft = tag.getLong("ticks_left");
        this.playState = tag.getEnum("play_state", LibyAnimationPlayState.class);

        this.readCustomData(tag.getLibyCompound("custom_data"));
    }

    @Override
    @ApiStatus.Internal
    public void writeToNbt(LibyNbtCompound tag) {
        tag.putIdentifier("identifier", this.identifier);
        tag.putLong("ticks_left", this.ticksLeft);
        tag.putEnum("play_state", this.playState);

        LibyNbtCompound customData = new LibyNbtCompound();
        this.writeCustomData(customData);
        tag.put("custom_data", customData);
    }

    @Override
    public LibyIdentifier liby$getId() {
        return identifier;
    }

    public boolean checkPredicate(T animatable) {
        return this.predicate.test(animatable);
    }

    public LibyAnimationPlayState getPlayState() {
        return this.playState;
    }

    public long getTicksLeft() {
        return this.ticksLeft;
    }

    public long getTicksPassed() {
        return MAX_DURATION - this.ticksLeft;
    }

    public <U extends LibyAnimatable> boolean matches(LibyAnimation<U> otherAnimation) {
        return this.liby$getId().equals(otherAnimation.liby$getId());
    }

    @ApiStatus.Internal
    public void setPlayState(LibyAnimationPlayState playState) {
        this.playState = playState;
    }

    @FunctionalInterface
    public interface Factory {
        LibyAnimation<LibyAnimatable> apply(LibyIdentifier identifier, Predicate<LibyAnimatable> predicate);
    }
}
