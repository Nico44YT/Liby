package nazario.liby.internal.ui.v1;

import nazario.liby.api.util.nbt.LibyNbtCompound;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class LibyWidgetStateRegistry {
    public static final Map<Identifier, Function<LibyNbtCompound, LibyWidgetState>> map = new ConcurrentHashMap<>();

    public static void register(Identifier id, Function<LibyNbtCompound, LibyWidgetState> factory) {
        map.put(id, factory);
    }

    public static <T extends LibyWidgetState> T createState(Identifier id, LibyNbtCompound nbt) {
        return (T) map.get(id).apply(nbt);
    }
}
