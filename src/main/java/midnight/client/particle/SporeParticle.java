/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
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

        float shade = random.nextFloat() * 0.1f + 0.9f;
        rCol = shade;
        gCol = shade;
        bCol = shade;
        alpha = 1;

        xd = xv;
        yd = yv;
        zd = zv;

        setSize(0.2f, 0.2f);
        quadSize *= (random.nextFloat() * 0.6f + 1) * 0.7f;
        lifetime = 60;
        hasPhysics = true;
        setSpriteFromAge(spriteSet = sprites);
    }

    @Override
    public void tick() {
        if (age++ < lifetime) {
            setSpriteFromAge(spriteSet);
        }
        xo = x;
        yo = y;
        zo = z;

        xd *= 0.98;
        yd *= 0.98;
        zd *= 0.98;

        yd -= 0.04;

        move(xd, yd, zd);

        if (age++ >= lifetime || onGround) {
            remove();
        }
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public int getLightColor(float partialTicks) {
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
        public Particle createParticle(BasicParticleType particleType, ClientWorld world, double x, double y, double z, double xv, double yv, double zv) {
            return new SporeParticle(world, x, y, z, xv, yv, zv, spriteSet);
        }
    }
}
