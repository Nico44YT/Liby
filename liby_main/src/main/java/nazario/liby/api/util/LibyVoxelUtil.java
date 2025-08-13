package nazario.liby.api.util;

import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LibyVoxelUtil {
    private static final Map<CacheKey, VoxelShape> cachedVoxelShapes = new HashMap<>();

    public static VoxelShape rotate(VoxelShape shape, Direction direction) {
        return rotate(shape, direction.getHorizontal());
    }

    /**
     * Rotates a VoxelShape by the given degrees (must be 0, 90, 180, or 270).
     *
     * @param shape    The VoxelShape to rotate.
     * @param rotation The direction of rotation (can be 0, 90, 180, 270).
     * @return The rotated VoxelShape.
     */
    public static VoxelShape rotate(VoxelShape shape, int rotation) {
        if (rotation % 90 != 0) {
            throw new IllegalArgumentException("Rotation must be a multiple of 90");
        }

        if (rotation == 0) {
            return shape; // No rotation needed
        }

        CacheKey cacheKey = new CacheKey(rotation, shape);
        if (cachedVoxelShapes.containsKey(cacheKey)) {
            return cachedVoxelShapes.get(cacheKey);
        }

        VoxelShape rotatedShape = VoxelShapes.empty();

        // Rotate each bounding box in the VoxelShape
        for (Box box : shape.getBoundingBoxes()) {
            rotatedShape = VoxelShapes.union(rotatedShape, rotateBox(box, rotation));
        }

        cachedVoxelShapes.put(cacheKey, rotatedShape);
        return rotatedShape;
    }

    /**
     * Rotates a Box by the given rotation (90-degree increments).
     *
     * @param box      The box to rotate.
     * @param rotation The amount of rotation (90, 180, 270).
     * @return The rotated Box.
     */
    private static VoxelShape rotateBox(Box box, int rotation) {
        double minX = box.minX;
        double minY = box.minY;
        double minZ = box.minZ;
        double maxX = box.maxX;
        double maxY = box.maxY;
        double maxZ = box.maxZ;

        switch (rotation) {
            case 90:
                return VoxelShapes.cuboid(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX);
            case 180:
                return VoxelShapes.cuboid(1 - maxX, minY, 1 - maxZ, 1 - minX, maxY, 1 - minZ);
            case 270:
                return VoxelShapes.cuboid(minZ, minY, 1 - maxX, maxZ, maxY, 1 - minX);
            default:
                throw new IllegalArgumentException("Rotation must be 0, 90, 180, or 270 degrees");
        }
    }

    public static VoxelShape move(VoxelShape shape, Vec3i position) {
        return shape.offset(position.getX(), position.getY(), position.getZ());
    }

    /**
     * Represents a composite key for caching VoxelShape rotations.
     */
    private static class CacheKey {
        final int rotation;
        final VoxelShape shape;

        CacheKey(int rotation, VoxelShape shape) {
            this.rotation = rotation;
            this.shape = shape;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            CacheKey cacheKey = (CacheKey) obj;
            return rotation == cacheKey.rotation && Objects.equals(shape, cacheKey.shape);
        }

        @Override
        public int hashCode() {
            return Objects.hash(rotation, shape);
        }
    }
}