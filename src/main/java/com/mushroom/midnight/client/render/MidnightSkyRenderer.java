package com.mushroom.midnight.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.IRenderHandler;
import org.lwjgl.opengl.GL11;

public final class MidnightSkyRenderer implements IRenderHandler {
    public static final MidnightSkyRenderer INSTANCE = new MidnightSkyRenderer();

    private static final float DARKNESS_RED = 14.5F / 255.0F;
    private static final float DARKNESS_GREEN = 8.0F / 255.0F;
    private static final float DARKNESS_BLUE = 20.0F / 255.0F;

    private static final VertexFormat FORMAT = DefaultVertexFormats.POSITION_COLOR;

    private static final int RESOLUTION = 8;
    private static final int HALF_RESOLUTION = RESOLUTION / 2;

    private static final double RADIUS = 100.0;
    private static final double UNIT = (RADIUS * 2.0) / RESOLUTION;

    private VertexBuffer gradientVbo;

    private MidnightSkyRenderer() {
    }

    @Override
    public void render(int ticks, float partialTicks, ClientWorld world, Minecraft client) {
        this.generateVbos();

        GlStateManager.depthMask(false);
        GlStateManager.disableTexture();
        RenderHelper.disableStandardItemLighting();

        GlStateManager.enableBlend();
        GlStateManager.disableAlphaTest();
        GlStateManager.disableFog();

        GlStateManager.shadeModel(GL11.GL_SMOOTH);

        this.gradientVbo.bindBuffer();

        int stride = FORMAT.getSize();

        GlStateManager.enableClientState(GL11.GL_VERTEX_ARRAY);
        GlStateManager.vertexPointer(3, GL11.GL_FLOAT, stride, 0);

        GlStateManager.enableClientState(GL11.GL_COLOR_ARRAY);
        GlStateManager.colorPointer(4, GL11.GL_UNSIGNED_BYTE, stride, 12);

        this.gradientVbo.drawArrays(GL11.GL_QUADS);

        VertexBuffer.unbindBuffer();

        GlStateManager.disableClientState(GL11.GL_VERTEX_ARRAY);
        GlStateManager.disableClientState(GL11.GL_COLOR_ARRAY);

        GlStateManager.shadeModel(GL11.GL_FLAT);

        GlStateManager.enableAlphaTest();
        GlStateManager.enableTexture();

        GlStateManager.depthMask(true);
    }

    private void generateVbos() {
        if (this.gradientVbo == null) {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder builder = tessellator.getBuffer();

            this.gradientVbo = new VertexBuffer(FORMAT);

            this.buildGradientVbo(builder);

            builder.finishDrawing();
            builder.reset();

            this.gradientVbo.bufferData(builder.getByteBuffer());
        }
    }

    private void buildGradientVbo(BufferBuilder builder) {
        builder.begin(GL11.GL_QUADS, FORMAT);

        buildGradientFaceX(builder, -HALF_RESOLUTION, false);
        buildGradientFaceX(builder, HALF_RESOLUTION, true);

        buildGradientFaceY(builder, -HALF_RESOLUTION, true);
        buildGradientFaceY(builder, HALF_RESOLUTION, false);

        buildGradientFaceZ(builder, -HALF_RESOLUTION, false);
        buildGradientFaceZ(builder, HALF_RESOLUTION, true);
    }

    private static void buildGradientFaceX(BufferBuilder builder, int ux, boolean reverse) {
        for (int uy = -HALF_RESOLUTION; uy < HALF_RESOLUTION; uy++) {
            for (int uz = -HALF_RESOLUTION; uz < HALF_RESOLUTION; uz++) {
                addGradientVertices(builder, reverse, new int[] {
                        ux, uy, uz,
                        ux, uy + 1, uz,
                        ux, uy + 1, uz + 1,
                        ux, uy, uz + 1
                });
            }
        }
    }

    private static void buildGradientFaceY(BufferBuilder builder, int uy, boolean reverse) {
        for (int ux = -HALF_RESOLUTION; ux < HALF_RESOLUTION; ux++) {
            for (int uz = -HALF_RESOLUTION; uz < HALF_RESOLUTION; uz++) {
                addGradientVertices(builder, reverse, new int[] {
                        ux, uy, uz,
                        ux + 1, uy, uz,
                        ux + 1, uy, uz + 1,
                        ux, uy, uz + 1
                });
            }
        }
    }

    private static void buildGradientFaceZ(BufferBuilder builder, int uz, boolean reverse) {
        for (int ux = -HALF_RESOLUTION; ux < HALF_RESOLUTION; ux++) {
            for (int uy = -HALF_RESOLUTION; uy < HALF_RESOLUTION; uy++) {
                addGradientVertices(builder, reverse, new int[] {
                        ux, uy, uz,
                        ux + 1, uy, uz,
                        ux + 1, uy + 1, uz,
                        ux, uy + 1, uz
                });
            }
        }
    }

    private static void addGradientVertices(BufferBuilder builder, boolean reverse, int[] vertices) {
        if (reverse) {
            for (int i = vertices.length - 3; i >= 0; i -= 3) {
                addGradientVertex(builder, vertices[i], vertices[i + 1], vertices[i + 2]);
            }
        } else {
            for (int i = 0; i < vertices.length; i += 3) {
                addGradientVertex(builder, vertices[i], vertices[i + 1], vertices[i + 2]);
            }
        }
    }

    private static void addGradientVertex(BufferBuilder builder, int ux, int uy, int uz) {
        double x = ux * UNIT;
        double y = uy * UNIT;
        double z = uz * UNIT;
        float alpha = sampleGradient(x, y, z);

        builder.pos(x, y, z).color(DARKNESS_RED, DARKNESS_GREEN, DARKNESS_BLUE, alpha).endVertex();
    }

    private static float sampleGradient(double x, double y, double z) {
        double length = Math.sqrt(x * x + y * y + z * z);
        y /= length;

        return MathHelper.clamp((float) y * 1.6F, 0.0F, 1.0F);
    }
}
