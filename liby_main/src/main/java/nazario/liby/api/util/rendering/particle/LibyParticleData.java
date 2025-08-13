package nazario.liby.api.util.rendering.particle;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LibyParticleData {
    public ParticleEffect particleEffect;
    public Vec3d velocity;
    public World world;
    public boolean important;
    public boolean alwaysSpawns;

    public LibyParticleData(World world, ParticleEffect particleEffect, Vec3d velocity, boolean important, boolean alwaysSpawns) {
        this.particleEffect = particleEffect;
        this.velocity = velocity;
        this.world = world;
        this.important = important;
        this.alwaysSpawns = alwaysSpawns;
    }
}
