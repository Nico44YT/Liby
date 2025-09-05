package nazario.liby.api.world.v1.block.sign;

import nazario.liby.api.util.LibyIdentifier;
import net.minecraft.block.SignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.util.Identifier;

public class LibySignBlock extends SignBlock implements LibySign {

    public LibySignBlock(Settings settings, WoodType woodType) {
        super(settings, woodType);
    }

    @Override
    public Identifier getTexture() {
        LibyIdentifier id = LibyIdentifier.tryParseOrDefault(getWoodType().name(), "minecraft");
        return Identifier.of(id.getNamespace(), "entity/sign/" + id.getPath());
    }
}
