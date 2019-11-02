package com.mushroom.midnight.client.particle;

import com.mushroom.midnight.common.particle.ParticleDataOneInt;
import com.mushroom.midnight.common.util.MidnightUtil;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FadingSporeParticle extends SpriteTexturedParticle {
    private final IAnimatedSprite spriteSet;
    private final float scaleMax;

    private FadingSporeParticle(IAnimatedSprite spriteSet, World world, double x, double y, double z, double motionX, double motionY, double motionZ, int color) {
        super(world, x, y, z, motionX, motionY, motionZ);
        this.motionX = motionX;
        this.motionY = motionY + 0.01d;
        this.motionZ = motionZ;
        float[] rgbF = MidnightUtil.getRGBColorF(color);
        setColor(MathHelper.clamp(rgbF[0] + (this.rand.nextFloat() * 0.2f - 0.1f), 0f, 1f), MathHelper.clamp(rgbF[1] + (this.rand.nextFloat() * 0.2f - 0.1f), 0f, 1f), MathHelper.clamp(rgbF[2] + (this.rand.nextFloat() * 0.2f - 0.1f), 0f, 1f));
        this.particleAlpha = 1f;
        this.particleScale = 0f;
        this.scaleMax = world.rand.nextFloat() * 0.5f + 0.5f;
        this.maxAge = (int) (8d / (Math.random() * 0.8d + 0.2d)) + 4;
        this.canCollide = false;
        this.particleGravity = 0f;
        selectSpriteWithAge(this.spriteSet = spriteSet);
    }

    @Override
    public void tick() {
        selectSpriteRandomly(this.spriteSet);
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (++this.age >= this.maxAge) {
            setExpired();
        } else {
            move(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9d;
            this.motionZ *= 0.9d;
            float ratio = this.age / (float) this.maxAge;
            if (ratio <= 0.25f) {
                this.particleScale = this.scaleMax * ratio * 0.2f;
            } else {
                float ratio2 = ratio / 0.75f;
                this.particleAlpha = ratio2;
                this.particleScale = this.scaleMax * (1f - ratio2) * 0.05f;
            }
        }
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public int getBrightnessForRender(float partialTick) {
        int skylight = 10;
        int blocklight = 5;
        return skylight << 20 | blocklight << 4;
    }

    public static class Factory implements IParticleFactory<ParticleDataOneInt> {
        private IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle makeParticle(ParticleDataOneInt particleType, World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new FadingSporeParticle(this.spriteSet, world, x, y, z, xSpeed, ySpeed, zSpeed, particleType.oneInt);
        }
    }
}
