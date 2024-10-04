package nazario.liby.registry.helper;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class LibyBlockEntityRegister extends LibyRegister {
    public LibyBlockEntityRegister(String namespace) {
        super(namespace);
    }

    public <T extends BlockEntityType<?>> T registerType(String name, T blockEntityType) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(namespace, name), blockEntityType);
    }
}
