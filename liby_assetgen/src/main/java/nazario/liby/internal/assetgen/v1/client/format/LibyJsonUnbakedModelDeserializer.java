package nazario.liby.internal.assetgen.v1.client.format;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import nazario.liby.internal.assetgen.v1.client.format.deserliazier.*;
import net.minecraft.client.render.model.json.*;
import net.minecraft.util.JsonHelper;

import java.io.Reader;
import java.io.StringReader;

public class LibyJsonUnbakedModelDeserializer extends JsonUnbakedModel.Deserializer {

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter((JsonUnbakedModel.class), new LibyJsonUnbakedModelDeserializer())
            .registerTypeAdapter((ModelElement.class), new LibyModelElementDeserializer())
            .registerTypeAdapter((ModelElementFace.class), new LibyModelElementFaceDeserializer())
            .registerTypeAdapter((ModelElementTexture.class), new LibyModelElementTextureDeserializer())
            .registerTypeAdapter((Transformation.class), new LibyTransformationDeserializer())
            .registerTypeAdapter((ModelTransformation.class), new LibyModelTransformationDeserializer())
            .registerTypeAdapter((ModelOverride.class), new LibyModelOverrideDeserializer())
            .create();

    public static JsonUnbakedModel deserialize(Reader input) {
        return JsonHelper.deserialize(GSON, input, JsonUnbakedModel.class);
    }

    public static JsonUnbakedModel deserialize(String json) {
        return LibyJsonUnbakedModelDeserializer.deserialize(new StringReader(json));
    }

    public LibyJsonUnbakedModelDeserializer() {
        super();
    }
}
