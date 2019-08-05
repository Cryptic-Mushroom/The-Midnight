package com.mushroom.midnight.client.render.entity;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.shader.ShaderHandle;
import com.mushroom.midnight.client.shader.WorldShader;
import com.mushroom.midnight.common.config.MidnightConfig;
import com.mushroom.midnight.common.entity.RiftBridge;
import com.mushroom.midnight.common.entity.RiftEntity;
import com.mushroom.midnight.common.entity.RiftGeometry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import javax.vecmath.Point2f;

public class RiftRenderer extends EntityRenderer<RiftEntity> {
    private static final double QUAD_WIDTH = 1.8;
    private static final double QUAD_HEIGHT = 2.6;

    private static final ResourceLocation RIFT_NOISE = new ResourceLocation(Midnight.MODID, "textures/effects/rift_noise.png");
    private static final ResourceLocation RIFT_TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/rift.png");

    private final WorldShader shader = new WorldShader(new ResourceLocation(Midnight.MODID, "rift"))
            .withTextureSampler("NoiseSampler", RIFT_NOISE);

    public RiftRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public void doRender(RiftEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        RiftBridge bridge = entity.getBridge();
        if (bridge == null) {
            return;
        }

        float openProgress = bridge.getOpenAnimation(partialTicks);
        float unstableTime = bridge.getUnstableAnimation(partialTicks);

        if (openProgress > 0.0F) {
            GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, 240, 240);

            GlStateManager.pushMatrix();
            GlStateManager.disableCull();
            GlStateManager.disableLighting();

            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

            GlStateManager.translated(x, y + entity.getHeight() / 2.0F, z);
            GlStateManager.rotatef(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);

            float time = entity.ticksExisted + partialTicks;

            float openAnimation = this.computeOpenAnimation(openProgress);
            float unstableAnimation = this.computeUnstableAnimation(unstableTime);

            if (this.shader.isAvailable() && MidnightConfig.client.riftShaders.get()) {
                this.renderShader(entity, time, openAnimation, unstableAnimation);
            } else {
                this.renderTexture(time, openAnimation, unstableAnimation);
            }

            GlStateManager.enableLighting();
            GlStateManager.enableCull();
            GlStateManager.popMatrix();

            int brightness = entity.getBrightnessForRender();
            GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, brightness % 65536, brightness / 65536);
        }
    }

    private void renderShader(RiftEntity entity, float time, float openAnimation, float unstableAnimation) {
        RiftGeometry geometry = entity.getGeometry();
        Point2f[] ring = geometry.computePath(openAnimation, unstableAnimation, time);

        GlStateManager.enableBlend();

        try (ShaderHandle ignored = this.shader.use(shader -> {
            for (int i = 0; i < ring.length; i++) {
                Point2f point = ring[i];
                shader.getUniform("vertices[" + i + "]").set(point.x, point.y);
            }
            shader.getUniform("Time").set(time);
        })) {
            GlStateManager.disableCull();

            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder builder = tessellator.getBuffer();

            builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
            builder.pos(-QUAD_WIDTH, QUAD_HEIGHT, 0.0).endVertex();
            builder.pos(QUAD_WIDTH, QUAD_HEIGHT, 0.0).endVertex();
            builder.pos(QUAD_WIDTH, -QUAD_HEIGHT, 0.0).endVertex();
            builder.pos(-QUAD_WIDTH, -QUAD_HEIGHT, 0.0).endVertex();
            tessellator.draw();

            GlStateManager.enableCull();
        }

        GlStateManager.disableBlend();
    }

    private void renderTexture(float time, float openAnimation, float unstableAnimation) {
        Minecraft client = Minecraft.getInstance();

        client.getTextureManager().bindTexture(RIFT_TEXTURE);

        GlStateManager.enableAlphaTest();
        GlStateManager.enableBlend();

        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();

        double width = (64.0 * 0.0625) / 2.0;
        double height = (128.0 * 0.0625) / 2.0;

        float idleSpeed = 0.08F;
        float idleIntensity = 0.1F * openAnimation * (unstableAnimation * 5.0F + 1.0F);
        float idleAnimation = (MathHelper.sin(time * idleSpeed) + 1.0F) * 0.4F * idleIntensity;

        GlStateManager.scalef(openAnimation, openAnimation, 1.0F);
        GlStateManager.scalef(1.0F + idleAnimation, 1.0F + (idleAnimation * 0.5F), 1.0F);

        builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        builder.pos(-width, height, 0.0).tex(0.0, 1.0).endVertex();
        builder.pos(width, height, 0.0).tex(1.0, 1.0).endVertex();
        builder.pos(width, -height, 0.0).tex(1.0, 0.0).endVertex();
        builder.pos(-width, -height, 0.0).tex(0.0, 0.0).endVertex();
        tessellator.draw();

        GlStateManager.disableBlend();
    }

    private float computeOpenAnimation(float openProgress) {
        float openAnimation = openProgress / RiftEntity.OPEN_TIME;
        openAnimation = (float) (1.0F - Math.pow(1.0 - openAnimation, 3.0));
        openAnimation = MathHelper.clamp(openAnimation, 0.0F, 1.0F);
        return openAnimation;
    }

    private float computeUnstableAnimation(float unstableTime) {
        float unstableAnimation = unstableTime / RiftEntity.UNSTABLE_TIME;
        unstableAnimation = (float) (1.0F - Math.pow(1.0 - unstableAnimation, 2.0));
        unstableAnimation = MathHelper.clamp(unstableAnimation, 0.0F, 1.0F);
        return unstableAnimation;
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(RiftEntity entity) {
        return null;
    }
}
