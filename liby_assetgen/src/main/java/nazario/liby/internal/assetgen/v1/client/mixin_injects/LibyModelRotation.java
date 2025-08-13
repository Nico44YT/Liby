package nazario.liby.internal.assetgen.v1.client.mixin_injects;

import nazario.liby.internal.assetgen.v1.client.format.LibyFreeFormRotation;

public interface LibyModelRotation {
    default LibyFreeFormRotation libyAssets$getFreeFormRotation() {
        return null;
    }

    default void libyAssets$setFreeFormRotation(LibyFreeFormRotation libyFreeFormRotation) {

    }

    default boolean libyAssets$isLibyFreeFormSet() {
        return false;
    }
}
