package nazario.liby.api.animation;

import net.minecraft.util.StringIdentifiable;

public enum PlayState implements StringIdentifiable {
    PLAYING("PLAYING"),
    PAUSED("PAUSED"),
    STOPPED("STOPPED");

    private final String name;

    private PlayState(String name) {
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
