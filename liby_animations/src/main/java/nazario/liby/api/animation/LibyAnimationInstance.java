package nazario.liby.api.animation;

import nazario.liby.api.util.nbt.LibyNbtCompound;
import nazario.liby.api.util.nbt.NbtConvertible;
import nazario.liby.api.util.rendering.LibyRenderingContext;
import net.minecraft.client.render.Frustum;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class LibyAnimationInstance implements NbtConvertible {
    private PlayState playState;
    private Identifier animationIdentifier;
    private LibyEntityAnimation animation;
    private long ticksLeft;

    private LivingEntity entity;

    public LibyAnimationInstance(NbtCompound nbtCompound) {
        readFromNbt(new LibyNbtCompound(nbtCompound));
    }

    public LibyAnimationInstance(LivingEntity entity, PlayState playState, Identifier animationIdentifier, LibyEntityAnimation animation, long ticksLeft) {
        this.playState = playState;
        this.animationIdentifier = animationIdentifier;
        this.animation = animation;
        this.ticksLeft = ticksLeft;
        this.entity = entity;
    }

    public Identifier getAnimationIdentifier() {
        return this.animationIdentifier;
    }

    public void renderTick(LivingEntity entity, LibyRenderingContext renderingContext) {
        this.animation.onRenderTick(this, entity, renderingContext);
    }

    public void logicTick(LivingEntity entity) {
        this.animation.onLogicTick(this, entity);

        if(playState == PlayState.PLAYING) {
            this.decrementTicks();

            if(this.getAnimation().shouldEnd(this)) {
                this.stop();
            }
        }
    }

    public void stop() {
        this.setPlayState(PlayState.STOPPED);
        this.animation.onStop(this);
    }

    public LibyAnimationInstance setPlayState(PlayState playState) {
        this.playState = playState;
        return this;
    }

    public PlayState getPlayState() {
        return this.playState;
    }

    public long getTicksPassed() {
        return animation.getMaxDuration() - this.getTicksLeft();
    }

    public long getTicksLeft() {
        return this.ticksLeft;
    }

    public LibyAnimationInstance setTicksLeft(long ticksLeft) {
        this.ticksLeft = ticksLeft;
        return this;
    }

    public void decrementTicks() {
        this.ticksLeft--;
    }

    public LibyEntityAnimation getAnimation() {
        return this.animation;
    }

    public int shouldEntityRender(Frustum frustum, double x, double y, double z) {
        return animation.shouldEntityRender(this, frustum, x, y, z);
    }

    public LivingEntity getEntity() {
        return entity;
    }

    @Override
    public void readFromNbt(LibyNbtCompound tag) {
        this.animationIdentifier = tag.getIdentifier("identifier");

        LibyAnimationRegistry.get(this.animationIdentifier).ifPresentOrElse(animation1 -> this.animation = (LibyEntityAnimation) animation1, () -> {
            throw new RuntimeException("How does this happen?\nCouldn't find animation " + this.animationIdentifier + ", even though it should be there?!\n" + "This is probably caused by an incorrect packet.");
        });

        this.playState = PlayState.valueOf(tag.getString("playstate"));
        this.ticksLeft = tag.getLong("ticks_left");

        this.animation.readCustomData(this, tag.getCompound("animation_custom_data"));
    }

    @Override
    public void writeToNbt(LibyNbtCompound tag) {
        tag.putIdentifier("identifier", this.animationIdentifier);
        tag.putString("playstate", this.playState.toString());
        tag.putLong("ticks_left", this.ticksLeft);

        tag.put("animation_custom_data", this.animation.writeCustomData(this));
    }
}
