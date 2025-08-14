package nazario.liby.internal.assetgen.v1.client.format.deserliazier;

import com.google.gson.*;
import nazario.liby.internal.assetgen.v1.client.format.LibyFreeFormRotation;
import net.minecraft.client.render.model.json.ModelElement;
import net.minecraft.client.render.model.json.ModelElementFace;
import net.minecraft.client.render.model.json.ModelRotation;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.Map;

public class LibyModelElementDeserializer extends ModelElement.Deserializer {
    public LibyModelElementDeserializer() {
        super();
    }

    @Override
    public ModelElement deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject elementObject = jsonElement.getAsJsonObject();
        Vec3f from = this.deserializeVec3f(elementObject, "from");
        Vec3f to = this.deserializeVec3f(elementObject, "to");
        Map<Direction, ModelElementFace> facesMap = this.deserializeFacesValidating(jsonDeserializationContext, elementObject);

        ModelRotation modelRotation = this.liby$deserializeRotation(elementObject);

        boolean shade = !elementObject.has("shade") || elementObject.get("shade").getAsBoolean();

        return new ModelElement(from, to, facesMap, modelRotation, shade);
    }

    @Nullable
    public ModelRotation liby$deserializeRotation(JsonObject object) {
        ModelRotation modelRotation = new ModelRotation(new Vec3f(), null, 0, false);
        if (object.has("rotation")) {
            LibyFreeFormRotation libyFreeFormRotation = LibyFreeFormRotation.deserializeRotation(object.getAsJsonObject());

            libyFreeFormRotation.getOrigin().scale(1/16f);

            modelRotation = new ModelRotation(new Vec3f(), null, 0, false);
            modelRotation.libyAssets$setFreeFormRotation(libyFreeFormRotation);
        }

        return modelRotation;
    }

    private Vec3f deserializeVec3f(JsonObject object, String name) {
        JsonArray jsonArray = JsonHelper.getArray(object, name);
        if (jsonArray.size() != 3) {
            throw new JsonParseException("Expected 3 " + name + " values, found: " + jsonArray.size());
        } else {
            float[] fs = new float[3];

            for(int i = 0; i < fs.length; ++i) {
                fs[i] = JsonHelper.asFloat(jsonArray.get(i), name + "[" + i + "]");
            }

            return new Vec3f(fs[0], fs[1], fs[2]);
        }
    }

    private Map<Direction, ModelElementFace> deserializeFacesValidating(JsonDeserializationContext context, JsonObject object) {
        Map<Direction, ModelElementFace> map = this.deserializeFaces(context, object);
        if (map.isEmpty()) {
            throw new JsonParseException("Expected between 1 and 6 unique faces, got 0");
        } else {
            return map;
        }
    }

}
