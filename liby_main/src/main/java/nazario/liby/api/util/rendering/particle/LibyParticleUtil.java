package nazario.liby.api.util.rendering.particle;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public class LibyParticleUtil {

    public static Vec3d drawCircle(Vec3d startingPos, int point, int maxPoints, double radiusX, double radiusY, double radiusZ) {
        double angle = 2d * Math.PI * point / maxPoints;
        double x = startingPos.x + radiusX * Math.cos(angle);
        double y = startingPos.y + radiusY * Math.sin(angle);
        double z = startingPos.z + radiusZ * Math.sin(angle);

        return new Vec3d(x, y, z);
    }

    public static Vec3d drawCylinder(Vec3d startingPos, int point, int maxPoints, double radiusX, double radiusZ, double height) {
        double angle = 2d * Math.PI * point / maxPoints; // Determine the angle for circular points
        double x = startingPos.x + radiusX * Math.cos(angle); // X position for the circle
        double y = startingPos.y + height;        // Y position based on height and progress
        double z = startingPos.z + radiusZ * Math.sin(angle); // Z position for the circle

        return new Vec3d(x, y, z);
    }

    public static Vec3d drawLine(Vec3d start, Vec3d end, int point, int maxPoints) {
        double dx = (end.x - start.x) / maxPoints;
        double dy = (end.y - start.y) / maxPoints;
        double dz = (end.z - start.z) / maxPoints;

        return new Vec3d(start.x + point * dx, start.y + point * dy, start.z + point * dz);
    }

    public static void drawShape(LibyParticleData data, int maxPoints, Vec3d ankerPoint, Vec3d... points) {
        for (int i = 0; i < points.length; i++) {
            if (i == points.length - 1) break;
            for (int j = 0; j < maxPoints; j++) {
                Vec3d point = drawLine(points[i], points[i + 1], j, maxPoints);
                point = point.add(ankerPoint);

                if (data.important)
                    data.world.addImportantParticle(data.particleEffect, data.alwaysSpawns, point.x, point.y, point.z, data.velocity.x, data.velocity.y, data.velocity.z);
                else
                    data.world.addParticle(data.particleEffect, data.alwaysSpawns, point.x, point.y, point.z, data.velocity.x, data.velocity.y, data.velocity.z);
            }
        }
    }

    public static void drawParticleData(LibyParticleData data, Vec3d position) {
        ((ServerWorld) data.world).spawnParticles(data.particleEffect, position.getX(), position.getY(), position.getZ(), 1, data.velocity.getX(), data.velocity.getY(), data.velocity.getZ(), 0);
    }
}
