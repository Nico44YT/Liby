package nazario.liby.api.registry.helper;

import nazario.liby.api.annotations.Unimplemented;
import nazario.liby.api.util.LibyMultiMap;
import nazario.liby.internal.registry.LibyImplementableRegistry;
import nazario.liby.internal.registry.LibyImplementedRegistry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;

public interface LibySoundRegistry extends LibyImplementableRegistry {
    static LibyMultiMap<String, SoundEvent> sounds = new LibyMultiMap<>(HashMap.class, ArrayList.class);

    static LibySoundRegistry of(String name) {
        return LibyImplementedRegistry.ofSounds(name);
    }

    SoundEvent registerSoundEvent(String name);
    SoundEvent registerSoundEvent(Identifier id);

    SoundEvent registerSoundEvent(String name, float distanceToTravel);
    SoundEvent registerSoundEvent(Identifier id, float distanceToTravel);

    @Unimplemented
    void registerAllForFile();
}
