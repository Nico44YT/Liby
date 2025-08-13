package nazario.liby.api.assetgen.v1.client.blockstate;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nazario.liby.LibyAssetGenMain;
import nazario.liby.internal.assetgen.v1.client.blockstate.LibyBlockState;
import net.minecraft.resource.ResourcePack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class LibyMultipartBlockState extends LibyBlockState {

    protected final List<JsonObject> variants = new ArrayList<>();

    public LibyMultipartBlockState(Identifier blockStateId) {
        this.id = blockStateId;
    }

    /**
     * Adds a state with default rotation, UV lock, and weight.
     *
     * @see #addState(String, Identifier, Vector3d, boolean, int)
     */
    public LibyMultipartBlockState addState(@Nullable String when, Identifier modelIdentifier) {
        return addState(when, modelIdentifier, null, false);
    }

    /**
     * Adds a state with specified rotation and default UV lock and weight.
     *
     * @see #addState(String, Identifier, Vector3d, boolean, int)
     */
    public LibyMultipartBlockState addState(@Nullable String when, Identifier modelIdentifier, Vector3d rotation) {
        return addState(when, modelIdentifier, rotation, false);
    }

    /**
     * Adds a state with specified rotation and UV lock, and default weight.
     *
     * @see #addState(String, Identifier, Vector3d, boolean, int)
     */
    public LibyMultipartBlockState addState(@Nullable String when, Identifier modelIdentifier, Vector3d rotation, boolean uv_lock) {
        return addState(when, modelIdentifier, rotation, uv_lock, 1);
    }

    /**
     * <a href="https://minecraft.wiki/w/Blockstates_definition">For a more detailed explanation check out the Minecraft wiki</a>
     *
     * @param modelIdentifier {@linkplain Identifier Identifier} of the model, e.g: "minecraft:block/cobblestone"
     * @param rotation      Rotation of the model
     * @param uv_lock         Locks the rotation of the texture.
     * @param weight          Probability of the model being used; default is 1.
     * @param when            Conditions for when this model should be applied (e.g., "power=true,facing=west").
     * @return The finished {@linkplain LibyMultipartBlockState LibyBlockState}.
     */
    public LibyMultipartBlockState addState(@Nullable String when, Identifier modelIdentifier, Vector3d rotation, boolean uv_lock, int weight) {

        JsonObject applyObject = new JsonObject();
        applyObject.addProperty("model", modelIdentifier.toString());
        if(rotation != null) {
            JsonObject rotationObject = new JsonObject();
            rotationObject.addProperty("x", rotation.x());
            rotationObject.addProperty("y", rotation.y());
            rotationObject.addProperty("z", rotation.z());

            applyObject.add("rotation", rotationObject);
        }
        applyObject.addProperty("uvlock", uv_lock);
        applyObject.addProperty("weight", weight);

        JsonObject mainObject = new JsonObject();
        mainObject.add("apply", applyObject);

        if (when != null && !when.isBlank()) {
            JsonObject whenObject = new JsonObject();
            for (String part : when.split(",")) {
                String[] split = part.split("=", 2);
                if (split.length == 2) {
                    whenObject.addProperty(split[0].trim(), split[1].trim());
                }
            }
            if (whenObject.size() > 0) {
                mainObject.add("when", whenObject);
            }
        }

        variants.add(mainObject);
        return this;
    }

    @Override
    public void accept(Identifier resourceId, ResourcePack.ResultConsumer consumer) {
        JsonObject mainJson = new JsonObject();

        JsonArray multipartArray = new JsonArray();

        for (JsonObject variant : variants) {
            multipartArray.add(variant);
        }

        mainJson.addProperty("format", LibyAssetGenMain.FORMAT);
        mainJson.add("multipart", multipartArray);

        consumer.accept(resourceId, () -> new ByteArrayInputStream(mainJson.toString().getBytes()));
    }
}
