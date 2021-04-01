/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.client.particle;

import midnight.core.util.ColorUtil;
import midnight.core.util.MnMath;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.ReuseableStream;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.stream.Stream;

/**
 * Spore particle that can be given a custom gravity factor. This particle is used for generating spores from shroom cap
 * blocks and for the pollen of tendrilweed.
 *
 * @author Shadew
 * @since 0.6.0
 */
@OnlyIn(Dist.CLIENT)
public class RisingSporeParticle extends SpriteTexturedParticle {
    private final IAnimatedSprite sprite;
    private final double verticalAcceleration;

    /**
     * Creates a rising spore particle.
     *
     * @param world            The world to create it in
     * @param x                The x coordinate of the particle
     * @param y                The y coordinate of the particle
     * @param z                The z coordinate of the particle
     * @param xvMul            Multiplier for random x velocity
     * @param yvMul            Multiplier for random y velocity
     * @param zvMul            Multiplier for random z velocity
     * @param xv               Extra x velocity
     * @param yv               Extra y velocity
     * @param zv               Extra z velocity
     * @param scaling          The particle scale
     * @param particleSprite   The sprite for the particle
     * @param randomLighting   The factor of random lighting differences in color
     * @param r                The red channel of the color
     * @param g                The green channel of the color
     * @param b                The blue channel of the color
     * @param averageAge       The average ageing of the particle
     * @param vertAcceleration The vertical acceleration the particle will have (for average gravity speed around
     *                         -0.04)
     * @param collideable      Whether this particle can collide with blocks
     */
    public RisingSporeParticle(ClientWorld world, double x, double y, double z, float xvMul, float yvMul, float zvMul, double xv, double yv, double zv, float scaling, IAnimatedSprite particleSprite, double randomLighting, double r, double g, double b, int averageAge, double vertAcceleration, boolean collideable) {
        super(world, x, y, z, 0, 0, 0);
        verticalAcceleration = vertAcceleration;
        sprite = particleSprite;
        xd *= xvMul;
        yd *= yvMul;
        zd *= zvMul;
        xd += xv;
        yd += yv;
        zd += zv;

        float shade = (world.random.nextFloat() * 2 - 1) * (float) randomLighting;
        rCol = MathHelper.clamp((float) r + shade, 0, 1);
        gCol = MathHelper.clamp((float) g + shade, 0, 1);
        bCol = MathHelper.clamp((float) b + shade, 0, 1);

        quadSize *= 0.75 * scaling;

        lifetime = (int) (averageAge / (world.random.nextDouble() * 0.8 + 0.2));
        lifetime *= scaling;
        lifetime = Math.max(lifetime, 1);

        hasPhysics = collideable;

        setSpriteFromAge(particleSprite);
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @Override
    public float getQuadSize(float partialTicks) {
        return quadSize * MnMath.clamp(0, 1, (age + partialTicks) / lifetime * 32);
    }

    // This override prevents this particle from stopping moving when it collides (which causes the particle to hang
    // randomly in the air)
    @Override
    public void move(double xv, double yv, double zv) {
        double fxv = xv;
        double fyv = yv;
        double fzv = zv;
        if (hasPhysics && (xv != 0.0D || yv != 0.0D || zv != 0.0D)) {
            Vector3d collisionResult = Entity.collideBoundingBoxHeuristically(
                null,
                new Vector3d(xv, yv, zv),
                getBoundingBox(),
                level,
                ISelectionContext.empty(),
                new ReuseableStream<>(Stream.empty())
            );
            xv = collisionResult.x;
            yv = collisionResult.y;
            zv = collisionResult.z;
        }

        if (xv != 0 || yv != 0 || zv != 0) {
            setBoundingBox(getBoundingBox().move(xv, yv, zv));
            setLocationFromBoundingbox();
        }

        onGround = fyv != yv && fyv < 0;

        if (fxv != xv) {
            xd = 0;
        }

        if (fyv != yv) {
            yd = 0;
        }

        if (fzv != zv) {
            zd = 0;
        }
    }

    @Override
    public void tick() {
        xo = x;
        yo = y;
        zo = z;
        if (age++ >= lifetime) {
            remove();
        } else {
            setSpriteFromAge(sprite);
            yd += verticalAcceleration;
            move(xd, yd, zd);
            if (y == yo) {
                xd *= 1.1;
                zd *= 1.1;
            }

            xd *= 0.96;
            yd *= 0.96;
            zd *= 0.96;

            if (onGround) { // Ground friction
                xd *= 0.7;
                zd *= 0.7;
            }
        }
    }

    @Override
    public int getLightColor(float partialTicks) {
        int skylight = 10;
        int blocklight = 5;
        return skylight << 20 | blocklight << 4;
    }

    @OnlyIn(Dist.CLIENT)
    public static class ShroomFactory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteProvider;

        public ShroomFactory(IAnimatedSprite sprite) {
            spriteProvider = sprite;
        }

        @Override
        public Particle createParticle(BasicParticleType type, ClientWorld world, double x, double y, double z, double r, double g, double b) {
            return new RisingSporeParticle(world, x, y, z, 0.5f, 0.5f, 0.5f, 0, 0, 0, 0.4f, spriteProvider, 0.1, r, g, b, 90, -0.01, true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class TendrilweedFactory implements IParticleFactory<BasicParticleType> {
        private static final int COLOR = 0xAB5E67;
        private static final double R = ColorUtil.redd(COLOR);
        private static final double G = ColorUtil.greend(COLOR);
        private static final double B = ColorUtil.blued(COLOR);

        private final IAnimatedSprite spriteProvider;

        public TendrilweedFactory(IAnimatedSprite sprite) {
            spriteProvider = sprite;
        }

        @Override
        public Particle createParticle(BasicParticleType type, ClientWorld world, double x, double y, double z, double xv, double yv, double zv) {
            return new RisingSporeParticle(world, x, y, z, 0.1f, 0.1f, 0.1f, xv, yv, zv, 0.9f, spriteProvider, 0.1, R, G, B, 30, -0.002, true);
        }
    }
}
