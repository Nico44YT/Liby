package nazario.liby.api.assetgen.v1.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public abstract class LibySoundProvider implements DataProvider {

    protected final DataOutput.PathResolver soundFilePathResolver;
    protected final JsonObject rootObject;
    protected final String id;

    public LibySoundProvider(String id, DataOutput output) {
        this.id = id;
        this.soundFilePathResolver = output.getResolver(DataOutput.OutputType.RESOURCE_PACK, "");

        this.rootObject = new JsonObject();
    }

    public void registerSound(@NotNull SoundEvent soundEvent, @Nullable String subtitle, @Nullable Boolean replace, @NotNull LibySoundData.Builder... instances) {
        this.registerSound(soundEvent.getId().getPath(), subtitle, replace, instances);
    }

    public void registerSound(@NotNull SoundEvent soundEvent, @Nullable String subtitle, @Nullable Boolean replace, @NotNull LibySoundData... instances) {
        this.registerSound(soundEvent.getId().getPath(), subtitle, replace, instances);
    }

    public void registerSound(@NotNull String soundKey, @Nullable String subtitle, @Nullable Boolean replace, @NotNull LibySoundData.Builder... instances) {
        this.registerSound(soundKey, subtitle, replace, Arrays.stream(instances).map(LibySoundData.Builder::build).toArray(LibySoundData[]::new));
    }

    /**
     * All information about the fields were taken from the <a href="https://minecraft.wiki/w/Sounds.json">Minecraft Wiki</a>
     *
     * @param soundKey The name is usually separated in categories (such as {@code entity.enderman.stare}). (To get a different namespace than {@code minecraft} the file must be under a different namespace; not defining it here.)
     * @param subtitle Translated as the subtitle of the sound if Show Subtitles is enabled ingame. Accepts <a href="https://minecraft.wiki/w/Formatting_codes">formatting codes</a> and displays them properly in-game.
     * @param replace Used only in resource packs. True if the sounds listed in sounds should replace the sounds listed in the default sounds.json for this sound event. False if the sounds listed should be added to the list of default sounds. Optional. If undefined, defaults to {@code false}.
     * @param instances The sound files this sound event uses. One of the listed sounds is randomly selected to play when this sound event is triggered.
     */
    public void registerSound(@NotNull String soundKey, @Nullable String subtitle, @Nullable Boolean replace, @NotNull LibySoundData... instances) {
        JsonObject soundObject = new JsonObject();

        JsonArray soundsArray = new JsonArray();

        for (@NotNull LibySoundProvider.LibySoundData instance : instances) {
            soundsArray.add(instance.getAsJson());
        }

        soundObject.add("sounds", soundsArray);
        if (subtitle != null) soundObject.addProperty("subtitle", subtitle);
        if (replace != null) soundObject.addProperty("replace", replace);

        rootObject.add(soundKey, soundObject);
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return DataProvider.writeToPath(writer, (JsonElement) rootObject, soundFilePathResolver.resolve(Identifier.of(id, "sounds"), "json"));
    }


    @Override
    public String getName() {
        return "liby:sounds";
    }

    public record LibySoundData(String name, Float volume, Float pitch, Integer weight, Boolean stream, Integer attenuationDistance, Boolean preload, Type type) {

        public static LibySoundData create(String name) {
            return LibySoundData.create(name, null, null, null, null, null, null, null);
        }

        public static LibySoundData create(String name, Float volume, Float pitch) {
            return LibySoundData.create(name, volume, pitch, null, null, null, null, null);

        }

        public static LibySoundData create(String name, Float volume, Float pitch, Integer weight) {
            return LibySoundData.create(name, volume, pitch, weight, null, null, null, null);

        }

        public static LibySoundData create(String name, Float volume, Float pitch, Integer weight, Boolean stream) {
            return LibySoundData.create(name, volume, pitch, weight, stream, null, null, null);

        }

        public static LibySoundData create(String name, Float volume, Float pitch, Integer weight, Boolean stream, Integer attenuationDistance, Boolean preload, Type type) {
            return new LibySoundData(name, volume, pitch, weight, stream, attenuationDistance, preload, type);
        }

        public JsonElement getAsJson() {
            if (volume != null || pitch != null || weight != null || stream != null || attenuationDistance != null || preload != null || type != null) {
                JsonObject object = new JsonObject();

                object.addProperty("name", name);
                if (volume != null) object.addProperty("volume", volume);
                if (pitch != null) object.addProperty("pitch", pitch);
                if (weight != null) object.addProperty("weight", weight);
                if (stream != null) object.addProperty("stream", stream);
                if (attenuationDistance != null) object.addProperty("attenuation_distance", attenuationDistance);
                if (preload != null) object.addProperty("preload", preload);
                if (type != null) object.addProperty("type", type.name().toLowerCase());

                return object;
            }

            return new JsonPrimitive(name);
        }

        public enum Type {
            FILE,
            EVENT
        }

        /**
         * All information about the fields were taken from the <a href="https://minecraft.wiki/w/Sounds.json">Minecraft Wiki</a>
         */
        public static class Builder {
            protected String name;
            protected Float volume;
            protected Float pitch;
            protected Integer weight;
            protected Boolean stream;
            protected Integer attenuationDistance;
            protected Boolean preload;
            protected Type type;

            /**
             * The path to this sound file from the {@code namespace/sounds} folder (excluding the .ogg file extension). The namespace defaults to minecraft but it can be changed by prepending a namespace and separating it with a :. Uses forward slashes instead of backslashes. May instead be the name of another sound event (according to value of {@code type}). If the sound file has one channel (mono), it can be played locationally (sound volume decreases the farther you are from the source). If the file has two channels (stereo), the volume does not change (for example music, ambient sounds). Names are also not allowed to contain whitespace characters.
             * @param name
             * @return
             */
            public Builder name(String name) {
                this.name = name;
                return this;
            }

            /**
             * Calls {@link #name(String)} with the string form of the given {@code id}.
             *
             * @param id the identifier to use
             * @return this builder
             */
            public Builder name(Identifier id) {
                return this.name(id.toString());
            }

            /**
             * The volume for playing this sound. Value is a decimal greater than {@code 0.0}. If undefined, defaults to {@code 1.0}.
             * @param volume
             * @return
             */
            public Builder volume(Float volume) {
                this.volume = volume;
                return this;
            }

            /**
             *  Plays the pitch at the specified value. Value is a decimal greater than {@code 0.0}. If undefined, defaults to {@code 1.0}, but higher and lower values can be chosen.
             * @param pitch
             * @return
             */
            public Builder pitch(Float pitch) {
                this.pitch = pitch;
                return this;
            }

            /**
             * The chance that this sound is selected to play when this sound event is triggered. Defaults to {@code 1}. An example: putting {@code 2} in for the value would be like placing in the name twice.
             * @param weight
             * @return
             */
            public Builder weight(Integer weight) {
                this.weight = weight;
                return this;
            }

            /**
             * True if this sound should be streamed from its file. It is recommended that this is set to {@code true} for sounds that have a duration longer than a few seconds to avoid lag. Used for all sounds in the {@code music} and {@code record} categories (except Note Block sounds), as (almost) all the sounds that belong to those categories are over a minute long. Optional. If undefined, defaults to {@code false}. Setting this to false allows many more instances of the sound to be ran at the same time while setting it to true only allows 4 instances (of that type) to be ran at the same time.
             * @param stream
             * @return
             */
            public Builder stream(Boolean stream) {
                this.stream = stream;
                return this;
            }

            /**
             * Modify sound reduction rate based on distance. Used by portals, pistons, beacons, and conduits. Defaults to {@code 16}.
             * @param attenuationDistance
             * @return
             */
            public Builder attenuationDistance(Integer attenuationDistance) {
                this.attenuationDistance = attenuationDistance;
                return this;
            }

            /**
             * True if this sound should be loaded when loading the pack instead of when the sound is played. Used by the underwater ambience. Defaults to {@code false}.
             * @param preload
             * @return
             */
            public Builder preload(Boolean preload) {
                this.preload = preload;
                return this;
            }

            /**
             * Two values are available: {@code Type.FILE} and {@code Type.EVENT}; {@code Type.FILE} causes the value of {@code name} to be interpreted as the name of a file, while {@code Type.EVENT} causes the value of {@code name} to be interpreted as the name of an already defined event. If undefined, defaults to {@code Type.FILE}.
             * @param type
             * @return
             */
            public Builder type(Type type) {
                this.type = type;
                return this;
            }

            public LibySoundData build() {
                return LibySoundData.create(name, volume, pitch, weight, stream, attenuationDistance, preload, type);
            }
        }
    }
}
