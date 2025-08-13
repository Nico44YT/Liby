package nazario.liby.api.animation.v2;

import net.minecraft.util.StringIdentifiable;

public enum LibyAnimationPlayState implements StringIdentifiable {
    PLAYING("PLAYING"),
    PAUSED("PAUSED"),
    STOPPED("STOPPED"),
    RESTARTING("RESTARTING"),
    NO_ANIMATION("NOTHING");

    private final String name;

    LibyAnimationPlayState(String name) {
        this.name = name.toUpperCase();
    }

    @Override
    public String toString() {
        return this.asString();
    }

    @Override
    public String asString() {
        return this.name;
    }
}
