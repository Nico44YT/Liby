package nazario.liby.api.animation;

import net.minecraft.client.render.Frustum;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public abstract class LibyAnimation {
    public static final int ALWAYS_RENDER = 0;
    public static final int DEFAULT_RENDER = 1;
    public static final int NEVER_RENDER = 2;

    private Identifier id;

    public abstract long getMaxDuration();

    public abstract void onStart(LibyAnimationInstance instance);
    public abstract void onResume(LibyAnimationInstance instance);
    public abstract void onPause(LibyAnimationInstance instance);
    public abstract void onStop(LibyAnimationInstance instance);

    public boolean shouldEnd(LibyAnimationInstance instance) {
        return instance.getTicksLeft() < 0;
    }

    public void readCustomData(LibyAnimationInstance instance, NbtCompound nbtCompound) {

    };

    public NbtCompound writeCustomData(LibyAnimationInstance instance) {
        return new NbtCompound();
    };

    public Identifier getId() {
        return id;
    }

    LibyAnimation setId(Identifier id) {
        this.id = id;
        return this;
    }
}
