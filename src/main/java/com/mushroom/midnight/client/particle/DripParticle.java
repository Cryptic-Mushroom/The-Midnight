package com.mushroom.midnight.client.particle;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class DripParticle extends Particle {
    private int bobTimer;

    public DripParticle(World world, double x, double y, double z, float r, float g, float b) {
        super(world, x, y, z, 0d, 0d, 0d);
        setParticleTextureIndex(113);
        this.particleRed = r;
        this.particleGreen = g;
        this.particleBlue = b;
        setSize(0.01f, 0.01f);
        this.particleGravity = 0.06f;
        this.bobTimer = 40;
        this.particleMaxAge = (int) (64d / (Math.random() * 0.8d + 0.2d));
        this.motionX = this.motionY = this.motionZ = 0d;
    }

    public void onUpdate() {
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
        if (particleMaxAge-- <= 0) {
            setExpired();
        }
        if (onGround) {
            setExpired();
            motionX *= 0.699999988079071d;
            motionZ *= 0.699999988079071d;
        }
    }

    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory {
        @Nullable
        @Override
        public Particle createParticle(int particleID, World world, double x, double y, double z, double motionX, double motionY, double motionZ, int... params) {
            return new DripParticle(world, x, y, z, params.length > 0 ? params[0] / 255f : 0f, params.length > 1 ? params[1] / 255f : 0f, params.length > 2 ? params[2] / 255f : 0f);
        }
    }
}
