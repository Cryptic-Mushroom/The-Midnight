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
public class BombExplosionParticle extends SpriteTexturedParticle {
    private final IAnimatedSprite spriteSet;

    private BombExplosionParticle(IAnimatedSprite spriteSet, World world, double x, double y, double z, int color) {
        super(world, x, y + 0.5d, z, 0d, 0d, 0d);
        this.maxAge = 6 + this.rand.nextInt(4);
        this.particleScale = 1f;
        float[] rgbF = MidnightUtil.getRGBColorF(color);
        setColor(rgbF[0], rgbF[1], rgbF[2]);
        selectSpriteWithAge(this.spriteSet = spriteSet);
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public int getBrightnessForRender(float partialTick) {
        return 15728880;
    }

    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ >= this.maxAge) {
            setExpired();
        } else {
            selectSpriteWithAge(this.spriteSet);
        }
    }

    public static class Factory implements IParticleFactory<ParticleDataOneInt> {
        private IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle makeParticle(ParticleDataOneInt particleType, World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
            return new BombExplosionParticle(this.spriteSet, world, x, y, z, particleType.oneInt);
        }
    }
}
