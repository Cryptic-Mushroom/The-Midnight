package com.mushroom.midnight.client.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal")
public class SporchParticle extends SpriteTexturedParticle {
    private final IAnimatedSprite spriteSet;
    private final float flameScale;
    private final int MAX_FRAME_ID = 2;
    private int currentFrame = 0;
    private boolean directionRight = true;
    private int lastTick = 0;

    private SporchParticle(IAnimatedSprite spriteSet, World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ) {
        super(world, posX, posY, posZ, motionX, motionY, motionZ);
        this.motionX = this.motionX * 0.009999999776482582d + motionX;
        this.motionY = this.motionY * 0.009999999776482582d + motionY;
        this.motionZ = this.motionZ * 0.009999999776482582d + motionZ;
        this.posX += (double) ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        this.posY += (double) ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        this.posZ += (double) ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        this.flameScale = this.particleScale = 0.2f;
        this.particleRed = 1f;
        this.particleGreen = 1f;
        this.particleBlue = 1f;
        this.maxAge = (int) (8d / (Math.random() * 0.8d + 0.2d)) + 4;
        selectSpriteWithAge(this.spriteSet = spriteSet);
    }

    @Override
    public void tick() {
        super.tick();
        if (isAlive()) {
            selectSpriteWithAge(this.spriteSet);
        }
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void move(double x, double y, double z) {
        setBoundingBox(this.getBoundingBox().offset(x, y, z));
        resetPositionToBB();
    }

    @Override
    public void renderParticle(BufferBuilder buffer, ActiveRenderInfo activeInfo, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        Entity entity = activeInfo.getRenderViewEntity();
        if (entity.ticksExisted >= this.lastTick + 5) {
            if (this.currentFrame == MAX_FRAME_ID) {
                this.directionRight = false;
            } else if (currentFrame == 0) {
                this.directionRight = true;
            }
            this.currentFrame = this.currentFrame + (directionRight ? 1 : -1);
            this.lastTick = entity.ticksExisted;
        }
        float f = ((float) this.age + partialTicks) / (float) this.maxAge;
        this.particleScale = this.flameScale * (1f - f * f * 0.5f);
        super.renderParticle(buffer, activeInfo, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
    }

    @Override
    public int getBrightnessForRender(float partialTick) {
        float f = ((float) this.age + partialTick) / (float) this.maxAge;
        f = MathHelper.clamp(f, 0f, 1f);
        int i = super.getBrightnessForRender(partialTick);
        int j = i & 255;
        int k = i >> 16 & 255;
        j = j + (int) (f * 15f * 16f);
        if (j > 240) {
            j = 240;
        }
        return j | k << 16;
    }

    public static class Factory implements IParticleFactory<BasicParticleType> {
        private IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle makeParticle(BasicParticleType particleType, World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new SporchParticle(spriteSet, world, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }
}
