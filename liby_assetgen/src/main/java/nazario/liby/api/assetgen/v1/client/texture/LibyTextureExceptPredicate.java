package nazario.liby.api.assetgen.v1.client.texture;

import java.awt.*;

@FunctionalInterface
public interface LibyTextureExceptPredicate {
    boolean check(int x, int y, Color existingColor, Color replacingColor);
}
