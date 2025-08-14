package nazario.liby.mixin.assetgen.v1.client.format;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nazario.liby.LibyAssetGenMain;
import nazario.liby.internal.assetgen.v1.client.blockstate.format.LibyModelVariantDeserializer;
import net.minecraft.client.render.model.MultipartUnbakedModel;
import net.minecraft.client.render.model.json.ModelVariant;
import net.minecraft.client.render.model.json.ModelVariantMap;
import net.minecraft.client.render.model.json.MultipartModelComponent;
import net.minecraft.client.render.model.json.WeightedUnbakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.Reader;
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

    @Inject(method = "fromJson(Lnet/minecraft/client/render/model/json/ModelVariantMap$DeserializationContext;Ljava/io/Reader;)Lnet/minecraft/client/render/model/json/ModelVariantMap;", at = @At("HEAD"), cancellable = true)
    private static void liby$fromJson(ModelVariantMap.DeserializationContext context, Reader reader, CallbackInfoReturnable<ModelVariantMap> cir) {
        if(liby$gsonInstance == null) liby$gsonInstance = liby$gsonFunction.apply(context);
    }

    @Inject(method = "fromJson(Lnet/minecraft/client/render/model/json/ModelVariantMap$DeserializationContext;Lcom/google/gson/JsonElement;)Lnet/minecraft/client/render/model/json/ModelVariantMap;", at = @At("HEAD"), cancellable = true)
    private static void liby$fromJson(ModelVariantMap.DeserializationContext context, JsonElement json, CallbackInfoReturnable<ModelVariantMap> cir) {
        if(liby$gsonInstance == null) liby$gsonInstance = liby$gsonFunction.apply(context);

        if(json instanceof JsonObject jsonObject && jsonObject.has("format")) {
            LibyAssetGenMain.libyModelRotationUsers.add(context.getStateFactory().getOwner().liby$getId().prependPath("block/"));
            String format = jsonObject.get("format").getAsString();

            if(format.equals(LibyAssetGenMain.FORMAT)) {
                ModelVariantMap map = liby$gsonInstance.fromJson(json, ModelVariantMap.class);
                cir.setReturnValue(map);
            }
        }
    }
}
