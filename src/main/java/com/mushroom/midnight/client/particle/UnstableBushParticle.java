package com.mushroom.midnight.client.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UnstableBushParticle extends SpriteTexturedParticle {
    private final IAnimatedSprite spriteSet;

    private UnstableBushParticle(IAnimatedSprite spriteSet, World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ) {
        super(world, posX, posY, posZ);
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.particleAlpha = 1f;
        particleScale = 0.1f;
        this.maxAge = 60;
        this.canCollide = false;
        selectSpriteWithAge(this.spriteSet = spriteSet);
    }

    @Override
    public void tick() {
        selectSpriteWithAge(this.spriteSet);
        this.particleAlpha = age / (float) maxAge;
        super.tick();
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public int getBrightnessForRender(float p_189214_1_) {
        int skylight = 10;
        int blocklight = 5;
        return skylight << 20 | blocklight << 4;
    }

    public static class Factory implements IParticleFactory<BasicParticleType> {
        private IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle makeParticle(BasicParticleType particleType, World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new UnstableBushParticle(this.spriteSet, world, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }
}
