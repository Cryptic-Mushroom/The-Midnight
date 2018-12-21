package com.mushroom.midnight.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;

public enum MidnightParticles {
    AMBIENT_SPORE(new AmbientSporeParticle.Factory()),
    SPORE(new SporeParticle.Factory()),
    DRIP(new DripParticle.Factory());

    IParticleFactory factory;

    MidnightParticles(IParticleFactory factory) {
        this.factory = factory;
    }

    public Particle create(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int... parameters) {
        return this.factory.createParticle(-1, world, x, y, z, velocityX, velocityY, velocityZ, parameters);
    }

    public void spawn(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int... parameters) {
        if (!world.isRemote) {
            return;
        }
        Particle particle = this.create(world, x, y, z, velocityX, velocityY, velocityZ, parameters);
        this.spawn(particle);
    }

    private void spawn(Particle particle) {
        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }
}
