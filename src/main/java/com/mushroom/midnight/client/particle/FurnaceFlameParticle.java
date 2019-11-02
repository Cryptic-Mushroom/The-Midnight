package com.mushroom.midnight.client.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FurnaceFlameParticle extends SpriteTexturedParticle {
    private final IAnimatedSprite spriteSet;

    public FurnaceFlameParticle(IAnimatedSprite spriteSet, World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ) {
        super(world, posX, posY, posZ, motionX, motionY, motionZ);
        this.motionX = this.motionX * 0.009999999776482582d + motionX;
        this.motionY = this.motionY * 0.009999999776482582d + motionY;
        this.motionZ = this.motionZ * 0.009999999776482582d + motionZ;
        this.posX += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        this.posY += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        this.posZ += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        this.maxAge = (int)(8.0d / (Math.random() * 0.8d + 0.2d)) + 4;
        selectSpriteWithAge(this.spriteSet = spriteSet);
    }

    @Override
    public void move(double x, double y, double z) {
        setBoundingBox(this.getBoundingBox().offset(x, y, z));
        resetPositionToBB();
    }

    @Override
    public float getScale(float partialTicks) {
        float ratio = ((float)this.age + partialTicks) / (float)this.maxAge;
        return this.particleScale * (1f - ratio * ratio * 0.5f);
    }

    @Override
    public int getBrightnessForRender(float partialTicks) {
        float ratio = ((float)this.age + partialTicks) / (float)this.maxAge;
        ratio = MathHelper.clamp(ratio, 0f, 1f);
        int i = super.getBrightnessForRender(partialTicks);
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int)(ratio * 15f * 16f);
        if (j > 240) {
            j = 240;
        }
        return j | k << 16;
    }

    @Override
    public void tick() {
        selectSpriteWithAge(this.spriteSet);
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ >= this.maxAge) {
            setExpired();
        } else {
            move(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9599999785423279d;
            this.motionY *= 0.9599999785423279d;
            this.motionZ *= 0.9599999785423279d;
            if (this.onGround) {
                this.motionX *= 0.699999988079071d;
                this.motionZ *= 0.699999988079071d;
            }

        }
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Factory implements IParticleFactory<BasicParticleType> {
        private IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle makeParticle(BasicParticleType particleType, World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new FurnaceFlameParticle(this.spriteSet, world, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }
}
