package nazario.liby.api.assetgen.v1.client.blockstate;

import com.google.gson.JsonObject;
import nazario.liby.LibyAssetGenMain;
import nazario.liby.internal.assetgen.v1.client.blockstate.LibyBlockState;
import net.minecraft.block.Block;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class LibyVariantBlockState extends LibyBlockState {

    public HashMap<ModelIdentifier, JsonObject> modelIdentifierMap;

    public LibyVariantBlockState(Block block) {
        this(block.liby$getId());
    }

    public LibyVariantBlockState(Identifier id) {
        this(id, new HashMap<>());
    }

    public LibyVariantBlockState(Identifier id, HashMap<ModelIdentifier, JsonObject> jsonIdentifiers) {
        this.id = id;
        this.modelIdentifierMap = jsonIdentifiers;
    }

    /**
     *
     * @see #addState(String, Identifier, Vector3d, Boolean, Integer)
     */
    public LibyVariantBlockState addState(String variant, Identifier modelIdentifier) {
        return this.addState(variant, modelIdentifier, null, null, null);
    }

    /**
     *
     * @see #addState(String, Identifier, Vector3d, Boolean, Integer)
     */
    public LibyVariantBlockState addState(String variant, Identifier modelIdentifier, Vector3d rotation) {
        return this.addState(variant, modelIdentifier, rotation, null);
    }

    /**
     *
     * @see #addState(String, Identifier, Vector3d, Boolean, Integer)
     */
    public LibyVariantBlockState addState(String variant, Identifier modelIdentifier, Vector3d rotation, Boolean uv_lock) {
        return this.addState(variant, modelIdentifier, rotation, uv_lock, null);
    }

    /**
     * <a href="https://minecraft.wiki/w/Blockstates_definition">For a more detailed explanation check out the Minecraft wiki</a>
     *
     * @param variant Name of the variant along with its value, (e.g., "power=true,facing=west").
     * @param modelIdentifier {@linkplain Identifier Identifier} of the model, e.g: "minecraft:block/cobblestone"
     * @param rotation        Rotation of the model
     * @param uv_lock         Locks the rotation of the texture.
     * @param weight          Probability of the model being used; default is 1.
     * @return The {@linkplain LibyVariantBlockState LibyBlockState} with the state added.
     */
    public LibyVariantBlockState addState(@NotNull String variant, @NotNull Identifier modelIdentifier, @Nullable Vector3d rotation, @Nullable  Boolean uv_lock, @Nullable Integer weight) {
        JsonObject applyObject = new JsonObject();

        applyObject.addProperty("model", modelIdentifier.toString());
        if(rotation != null) {
            JsonObject rotationObject = new JsonObject();
            rotationObject.addProperty("x", rotation.x);
            rotationObject.addProperty("y", rotation.y);
            rotationObject.addProperty("z", rotation.z);

            applyObject.add("rotation", rotationObject);
        }
        if(uv_lock != null) applyObject.addProperty("uvlock", uv_lock);
        if(weight!= null) applyObject.addProperty("weight", weight);

        this.modelIdentifierMap.put(new ModelIdentifier(id, variant), applyObject);

        return this;
    }

    @Override
    public void accept(Identifier resourceId, Map<Identifier, Supplier<InputStream>> map) {
        JsonObject mainJson = new JsonObject();
        JsonObject variants = new JsonObject();

        for(ModelIdentifier modelId : modelIdentifierMap.keySet()) {
            JsonObject jsonObject = modelIdentifierMap.get(modelId);

            variants.add(modelId.getVariant(), jsonObject);
        }

        mainJson.addProperty("format", LibyAssetGenMain.FORMAT);
        mainJson.add("variants", variants);

        map.put(resourceId, () -> new ByteArrayInputStream(mainJson.toString().getBytes()));
    }

    public JsonObject getModel(ModelIdentifier modelIdentifier) {
        return modelIdentifierMap.get(modelIdentifier);
    }
}
