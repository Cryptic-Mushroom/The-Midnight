/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 19
 */

package midnight.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SporeParticle extends SpriteTexturedParticle {
    private final IAnimatedSprite spriteSet;

    private SporeParticle(ClientWorld world, double x, double y, double z, double xv, double yv, double zv, IAnimatedSprite sprites) {
        super(world, x, y, z, xv, yv, zv);

        float shade = rand.nextFloat() * 0.1f + 0.9f;
        particleRed = shade;
        particleGreen = shade;
        particleBlue = shade;
        particleAlpha = 1;

        motionX = xv;
        motionY = yv;
        motionZ = zv;

        setSize(0.2f, 0.2f);
        particleScale *= (rand.nextFloat() * 0.6f + 1) * 0.7f;
        maxAge = 60;
        canCollide = true;
        selectSpriteWithAge(spriteSet = sprites);
    }

    @Override
    public void tick() {
        if (age++ < maxAge) {
            selectSpriteWithAge(spriteSet);
        }
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;

        motionX *= 0.98;
        motionY *= 0.98;
        motionZ *= 0.98;

        motionY -= 0.04;

        move(motionX, motionY, motionZ);

        if (age++ >= maxAge || onGround) {
            setExpired();
        }
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public int getBrightnessForRender(float partialTicks) {
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
        public Particle makeParticle(BasicParticleType particleType, ClientWorld world, double x, double y, double z, double xv, double yv, double zv) {
            return new SporeParticle(world, x, y, z, xv, yv, zv, spriteSet);
        }
    }
}
