package nazario.liby.api.assetgen.v1.client.model;

import com.google.gson.JsonObject;
import nazario.liby.internal.assetgen.v1.client.model.LibyModel;
import net.minecraft.resource.InputSupplier;
import net.minecraft.util.Identifier;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class LibyJsonModel implements LibyModel<InputSupplier<InputStream>> {
    public final Identifier identifier;
    public final JsonObject jsonModel;

    public LibyJsonModel(Identifier id, JsonObject jsonModel) {
        this.identifier = id;
        this.jsonModel = jsonModel;
    }

    @Override
    public Identifier getId() {
        return this.identifier;
    }

    public JsonObject getJson() {
        return this.jsonModel;
    }

    @Override
    public InputSupplier<InputStream> bake() {
        return () -> new ByteArrayInputStream(
                this.jsonModel.toString().getBytes(StandardCharsets.UTF_8)
        );
    }

    @Override
    public String toString() {
        return "LibyJsonModel{\n" +
                identifier.toString() + "\n" +
                jsonModel.toString() + "\n" +
                "}";
    }
}
