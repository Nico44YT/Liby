package nazario.liby.registry.helper;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class LibyBlockEntityRegister extends LibyRegister {
    public LibyBlockEntityRegister(String namespace) {
        super(namespace);
    }

    public <T extends BlockEntityType<?>> T registerType(String name, T blockEntityType) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, Identifier.of(namespace, name), blockEntityType);
    }
}
