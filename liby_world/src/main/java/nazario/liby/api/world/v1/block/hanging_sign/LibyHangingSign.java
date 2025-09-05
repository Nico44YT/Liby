package nazario.liby.api.world.v1.block.hanging_sign;

import net.minecraft.block.WoodType;
import net.minecraft.util.Identifier;

public interface LibyHangingSign {
    Identifier getTexture();
    WoodType getWoodType();
}
