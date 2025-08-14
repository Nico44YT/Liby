package nazario.liby.mixin.assetgen.v1.client.format;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import nazario.liby.LibyAssetGenMain;
import nazario.liby.api.util.LibyGenericUtils;
import nazario.liby.internal.assetgen.v1.client.format.LibyJsonUnbakedModelDeserializer;
import nazario.liby.internal.assetgen.v1.client.format.LibyModelFormat;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.util.JsonHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.BufferedReader;
import java.io.Reader;

@Mixin(JsonUnbakedModel.class)
public abstract class JsonUnbakedModelMixin {

    @Shadow @Final static Gson GSON;

    @Shadow public String id;

    @Inject(method = "deserialize(Ljava/io/Reader;)Lnet/minecraft/client/render/model/json/JsonUnbakedModel;", at = @At("HEAD"), cancellable = true)
    private static void liby$deserializeModel(Reader reader, CallbackInfoReturnable<JsonUnbakedModel> cir) {
        BufferedReader bufferedReader = new BufferedReader(reader);
        JsonElement jsonElement = JsonParser.parseString(LibyGenericUtils.streamToString(bufferedReader.lines(), ""));

        LibyModelFormat format = liby$checkFormat(jsonElement);

        if(format == LibyModelFormat.LIBY) {
            cir.setReturnValue(LibyJsonUnbakedModelDeserializer.deserialize(jsonElement.toString()));
            return;
        }

        if(format == LibyModelFormat.LIBY_OBJ) {
            //cir.setReturnValue(LibyObjModelFormatDeserializer.deserialize(jsonElement.toString()));
            return;
        }

        cir.setReturnValue(JsonHelper.deserialize(GSON, jsonElement.toString(), JsonUnbakedModel.class));
    }

    @Inject(method = "deserialize(Ljava/lang/String;)Lnet/minecraft/client/render/model/json/JsonUnbakedModel;", at = @At("HEAD"), cancellable = true)
    private static void liby$deserializeModel(String json, CallbackInfoReturnable<JsonUnbakedModel> cir) {
        JsonElement jsonElement = JsonParser.parseString(json);

        LibyModelFormat format = liby$checkFormat(jsonElement);

        if (format == LibyModelFormat.LIBY) {
            cir.setReturnValue(LibyJsonUnbakedModelDeserializer.deserialize(jsonElement.toString()));
            return;
        }

        if (format == LibyModelFormat.LIBY_OBJ) {
            //cir.setReturnValue(LibyObjModelFormatDeserializer.deserialize(jsonElement.toString()));
            return;
        }
    }

    @Unique
    private static LibyModelFormat liby$checkFormat(JsonElement jsonElement) {
        if(jsonElement instanceof JsonObject jsonObject) {
            if(jsonObject.has("format") && jsonObject.get("format").isJsonPrimitive() && jsonObject.get("format").getAsJsonPrimitive().isString()) {
                String format = jsonObject.get("format").getAsString();

                if(format.equals(LibyAssetGenMain.FORMAT)) return LibyModelFormat.LIBY;
                if(format.equals("liby_obj")) return LibyModelFormat.LIBY_OBJ;
                if(format != null) return LibyModelFormat.OTHER;
                return LibyModelFormat.VANILLA;
            }
        }

        return LibyModelFormat.NONE;
    }
}
