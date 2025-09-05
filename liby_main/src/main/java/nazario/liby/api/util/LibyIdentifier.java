package nazario.liby.api.util;

import net.minecraft.util.Identifier;

public class LibyIdentifier extends Identifier {
    public LibyIdentifier(String id) {
        super(id);
    }

    public LibyIdentifier(String namespace, String path) {
        super(namespace, path);
    }

    public LibyIdentifier(Identifier id) {
        this(id.getNamespace(), id.getPath());
    }

    public static Identifier of(Identifier id) {
        return new LibyIdentifier(id);
    }

    public static LibyIdentifier of(String namespace, String path) {
        return new LibyIdentifier(namespace, path);
    }

    public static LibyIdentifier ofVanilla(String path) {
        return new LibyIdentifier(Identifier.DEFAULT_NAMESPACE, path);
    }

    public static LibyIdentifier ofRealms(String path) {
        return new LibyIdentifier(Identifier.REALMS_NAMESPACE, path);
    }

    public static LibyIdentifier tryParseOrDefault(String id, String defaultNamespace) {
        String[] parts = id.split(":");
        if(parts.length >= 2) return LibyIdentifier.of(parts[0], parts[1]);
        return LibyIdentifier.of(defaultNamespace, parts[0]);
    }

    public LibyIdentifier append(String namespace, String path) {
        return new LibyIdentifier(this.getNamespace() + namespace, this.getPath() + path);
    }

    public LibyIdentifier appendPath(String path) {
        return new LibyIdentifier(this.getNamespace(), this.getPath() + path);
    }

    public LibyIdentifier appendNamespace(String namespace) {
        return new LibyIdentifier(this.getNamespace() + namespace, this.getPath());
    }

    public LibyIdentifier prepend(String namespace, String path) {
        return new LibyIdentifier(namespace + this.getNamespace(), path + this.getPath());
    }

    public LibyIdentifier prependPath(String path) {
        return new LibyIdentifier(this.getNamespace(), path + this.getPath());
    }

    public LibyIdentifier prependNamespace(String namespace) {
        return new LibyIdentifier(namespace + this.getNamespace(), this.getPath());
    }

    public Identifier toId() {
        return this;
    }
}
