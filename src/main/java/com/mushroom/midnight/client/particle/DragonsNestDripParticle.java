package com.mushroom.midnight.client.particle;

import com.mushroom.midnight.common.particle.ParticleDataOneInt;
import com.mushroom.midnight.common.util.MidnightUtil;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DragonsNestDripParticle extends SpriteTexturedParticle {
    private final IAnimatedSprite spriteSet;
    private int bobTimer;

    private DragonsNestDripParticle(IAnimatedSprite spriteSet, World world, double x, double y, double z, int color) {
        super(world, x, y, z, 0d, 0d, 0d);
        float[] rgbF = MidnightUtil.getRGBColorF(color);
        setColor(rgbF[0], rgbF[1], rgbF[2]);
        setSize(0.01f, 0.01f);
        this.particleGravity = 0.04f;
        this.maxAge = (int) (64d / (Math.random() * 0.8d + 0.2d));
        this.bobTimer = this.maxAge * 3 / 4;
        this.motionX = this.motionY = this.motionZ = 0d;
        this.particleScale *= 2f;
        selectSpriteWithAge(this.spriteSet = spriteSet);
    }

    @Override
    public void tick() {
        selectSpriteWithAge(this.spriteSet);
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        motionY -= (double) particleGravity;
        if (bobTimer-- > 0) {
            motionX *= 0.02d;
            motionY *= 0.02d;
            motionZ *= 0.02d;
        }
        move(motionX, motionY, motionZ);
        motionX *= 0.9800000190734863d;
        motionY *= 0.9800000190734863d;
        motionZ *= 0.9800000190734863d;
        if (maxAge-- <= 0) {
            setExpired();
        }
        if (onGround) {
            setExpired();
            motionX *= 0.699999988079071d;
            motionZ *= 0.699999988079071d;
        }
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public int getBrightnessForRender(float partialTick) {
        return bobTimer <= 0 ? 0xe000e0 : 0xe000e0 - (0x010001 * bobTimer);
    }

    public static class Factory implements IParticleFactory<ParticleDataOneInt> {
        private IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle makeParticle(ParticleDataOneInt particleType, World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new DragonsNestDripParticle(this.spriteSet, world, x, y, z, particleType.oneInt);
        }
    }
}
