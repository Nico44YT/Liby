package nazario.liby.mixin.assetgen.v1.client.format;

import com.google.gson.*;
import nazario.liby.LibyAssetGenMain;
import nazario.liby.internal.assetgen.v1.client.blockstate.format.LibyModelVariantDeserializer;
import net.minecraft.client.render.model.MultipartUnbakedModel;
import net.minecraft.client.render.model.json.ModelVariant;
import net.minecraft.client.render.model.json.ModelVariantMap;
import net.minecraft.client.render.model.json.MultipartModelComponent;
import net.minecraft.client.render.model.json.WeightedUnbakedModel;
import net.minecraft.util.JsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.function.Function;

@Mixin(ModelVariantMap.class)
public abstract class ModelVariantMapFormatMixin {

    @Unique
    private static final Function<ModelVariantMap.DeserializationContext, Gson> liby$gsonFunction = (context) -> new GsonBuilder()
            .registerTypeAdapter(ModelVariantMap.class, new ModelVariantMap.Deserializer())
            .registerTypeAdapter(ModelVariant.class, new LibyModelVariantDeserializer())
            .registerTypeAdapter(WeightedUnbakedModel.class, new WeightedUnbakedModel.Deserializer())
            .registerTypeAdapter(MultipartUnbakedModel.class, new MultipartUnbakedModel.Deserializer(context))
            .registerTypeAdapter(MultipartModelComponent.class, new MultipartModelComponent.Deserializer())
            .create();

    @Unique
    private static Gson liby$gsonInstance;

    @Unique
    private static ModelVariantMap.DeserializationContext liby$context;

    @Inject(method = "fromJson", at = @At("HEAD"))
    private static void liby$getContext(ModelVariantMap.DeserializationContext context, Reader reader, CallbackInfoReturnable<ModelVariantMap> cir) {
        liby$context = context;
    }

    @Redirect(method = "fromJson", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/JsonHelper;deserialize(Lcom/google/gson/Gson;Ljava/io/Reader;Ljava/lang/Class;)Ljava/lang/Object;"))
    private static Object liby$fromJson(Gson gson, Reader reader, Class<ModelVariantMap> clazz) {
        if(liby$gsonInstance == null) liby$gsonInstance = liby$gsonFunction.apply(liby$context);

        BufferedReader bufferedReader = new BufferedReader(reader);
        JsonElement json = JsonParser.parseReader(bufferedReader);


        if(json instanceof JsonObject jsonObject && jsonObject.has("format")) {
            LibyAssetGenMain.libyModelRotationUsers.add(liby$context.getStateFactory().getOwner().liby$getId().prependPath("block/"));
            String format = jsonObject.get("format").getAsString();

            if(format.equals(LibyAssetGenMain.FORMAT)) {
                ModelVariantMap map = liby$gsonInstance.fromJson(json, ModelVariantMap.class);
                return map;
            }
        }

        return (ModelVariantMap) JsonHelper.deserialize(gson, new StringReader(json.toString()), ModelVariantMap.class);
    }
}
