package com.mushroom.midnight.client.particle;

import com.mushroom.midnight.common.entity.RiftEntity;
import com.mushroom.midnight.common.util.MidnightUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.util.math.MathHelper;

import javax.vecmath.Vector2f;
import java.util.Random;

public class RiftParticleSystem implements ParticleSystem<RiftEntity> {
    private static final int ORBITAL_RING_COUNT = 3;
    private static final int ORBITAL_PARTICLE_COUNT = 240;

    private final RiftEntity rift;

    private Ring[] particleRings;
    private int releasedParticleCount;

    public RiftParticleSystem(RiftEntity rift) {
        this.rift = rift;
    }

    @Override
    public void updateParticles(Random random) {
        if (!this.rift.isBridgeValid()) {
            return;
        }

        if (!MidnightUtil.isMidnightDimension(rift.world) && random.nextInt(10) == 0) {
            this.spawnSpore(this.rift, random, 3.0F);
        }

        if (this.rift.isOpen()) {
            this.updateRings(random);
            if (!this.rift.isUnstable()) {
                this.spawnOrbitalParticles(this.rift, random);
            }
        } else if (this.rift.getBridge().open.getTimer() == RiftEntity.CLOSE_SPEED) {
            int sporeCount = random.nextInt(3) + 8;
            for (int i = 0; i < sporeCount; i++) {
                this.spawnSpore(this.rift, random, 1.0F);
            }
        }
    }

    @Override
    public RiftEntity getEntity() {
        return this.rift;
    }

    private void spawnSpore(RiftEntity rift, Random random, float displacementScale) {
        double velocityX = random.nextGaussian() * 0.3F;
        double velocityY = random.nextGaussian() * 0.3F;
        double velocityZ = random.nextGaussian() * 0.3F;
        double particleX = rift.posX + velocityX * displacementScale;
        double particleY = rift.posY + rift.getHeight() / 2.0F + velocityY * displacementScale;
        double particleZ = rift.posZ + velocityZ * displacementScale;
        MidnightParticles.AMBIENT_SPORE.spawn(rift.world, particleX, particleY, particleZ, velocityX, velocityY, velocityZ);
    }

    private void updateRings(Random random) {
        if (this.particleRings == null) {
            this.particleRings = this.generateOrbitalRings(random);
        }
        for (Ring ring : this.particleRings) {
            ring.update();
        }
    }

    private Ring[] generateOrbitalRings(Random random) {
        Ring[] rings = new Ring[ORBITAL_RING_COUNT];
        for (int i = 0; i < rings.length; i++) {
            float tiltX = random.nextFloat() * 360.0F;
            float tiltZ = random.nextFloat() * 360.0F;

            Vector2f tiltSpeed = new Vector2f(random.nextFloat(), random.nextFloat());
            tiltSpeed.normalize();

            rings[i] = new Ring(tiltX, tiltZ, tiltSpeed.x * 0.05F, tiltSpeed.y * 0.05F);
        }

        return rings;
    }

    private void spawnOrbitalParticles(RiftEntity rift, Random random) {
        ParticleManager particleManager = Minecraft.getInstance().particles;

        int count = Math.min(random.nextInt(2) + 1, ORBITAL_PARTICLE_COUNT - this.releasedParticleCount);
        for (int i = 0; i < count; i++) {
            float offsetHorizontal = (random.nextFloat() - 0.5F) * rift.getWidth() * 0.8F;
            float offsetVertical = (random.nextFloat() - 0.5F) * rift.getHeight() * 0.8F;

            float theta = (float) Math.toRadians(rift.rotationYaw);
            double particleX = rift.posX + MathHelper.cos(theta) * offsetHorizontal;
            double particleY = rift.posY + rift.getHeight() / 2.0F + offsetVertical;
            double particleZ = rift.posZ + MathHelper.sin(theta) * offsetHorizontal;

            Ring ring = this.particleRings[random.nextInt(this.particleRings.length)];

            float radius = random.nextFloat() + 2.5F;
            float angleOffset = random.nextFloat() * 360.0F;
            float verticalOffset = (random.nextFloat() - 0.5F) * 0.25F;
            float rotateSpeed = random.nextFloat() * 0.5F + 1.25F;

            RiftParticle particle = new RiftParticle(this, ring, particleX, particleY, particleZ, radius, angleOffset, verticalOffset, rotateSpeed);
            particleManager.addEffect(particle);

            this.releasedParticleCount++;
        }
    }

    public void returnParticle() {
        this.releasedParticleCount--;
    }

    public static class Ring {
        private float tiltX;
        private float tiltZ;

        private float tiltSpeedX;
        private float tiltSpeedZ;

        public Ring(float tiltX, float tiltZ, float tiltSpeedX, float tiltSpeedZ) {
            this.tiltX = tiltX;
            this.tiltZ = tiltZ;
            this.tiltSpeedX = tiltSpeedX;
            this.tiltSpeedZ = tiltSpeedZ;
        }

        public void update() {
            this.tiltX += this.tiltSpeedX;
            this.tiltZ += this.tiltSpeedZ;
        }

        public float getTiltX() {
            return this.tiltX;
        }

        public float getTiltZ() {
            return this.tiltZ;
        }
    }
}
