package nazario.liby.api.world.v1.block.hanging_sign;

import nazario.liby.api.util.LibyIdentifier;
import net.minecraft.block.HangingSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.util.Identifier;

public class LibyHangingSignBlock extends HangingSignBlock implements LibyHangingSign {
    public LibyHangingSignBlock(Settings settings, WoodType woodType) {
        super(settings, woodType);
    }

    @Override
    public Identifier getTexture() {
        LibyIdentifier id = LibyIdentifier.tryParseOrDefault(getWoodType().name(), "minecraft");
        return Identifier.of(id.getNamespace(), "entity/sign/" + id.getPath());
    }
}
