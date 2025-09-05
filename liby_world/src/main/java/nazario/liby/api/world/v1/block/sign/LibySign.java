package nazario.liby.api.world.v1.block.sign;

import net.minecraft.block.WoodType;
import net.minecraft.util.Identifier;

public interface LibySign {
    Identifier getTexture();
    WoodType getWoodType();
}
