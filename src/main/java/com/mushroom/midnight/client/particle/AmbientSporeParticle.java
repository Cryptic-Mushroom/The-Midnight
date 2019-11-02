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
public class AmbientSporeParticle extends SpriteTexturedParticle {
    private final IAnimatedSprite spriteSet;

    private AmbientSporeParticle(IAnimatedSprite spriteSet, World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        float shade = this.rand.nextFloat() * 0.1F + 0.9F;
        this.particleRed = shade;
        this.particleGreen = shade;
        this.particleBlue = shade;
        this.particleAlpha = 0.0F;
        setSize(0.2F, 0.2F);
        this.particleScale *= (this.rand.nextFloat() * 0.6F + 1.0F) * 0.3F;
        this.motionX *= 0.1;
        this.motionY *= 0.1;
        this.motionZ *= 0.1;
        this.maxAge = 150;
        this.canCollide = true;
        selectSpriteWithAge(this.spriteSet = spriteSet);
    }

    @Override
    public void tick() {
        selectSpriteWithAge(this.spriteSet);
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        this.move(this.motionX, this.motionY, this.motionZ);

        this.motionX *= 0.99;
        this.motionY *= 0.99;
        this.motionZ *= 0.99;

        this.maxAge--;

        if (this.maxAge < 20) {
            this.particleAlpha = this.maxAge / 20.0F;
        } else if (this.maxAge >= 180) {
            this.particleAlpha = (200 - this.maxAge) / 20.0F;
        } else {
            this.particleAlpha = 1.0F;
        }

        if (this.maxAge <= 0 || this.onGround) {
            this.setExpired();
        }
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
            return new AmbientSporeParticle(this.spriteSet, world, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }
}
