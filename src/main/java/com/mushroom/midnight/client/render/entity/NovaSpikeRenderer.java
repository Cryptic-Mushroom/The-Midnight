package com.mushroom.midnight.client.render.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.NovaSpikeModel;
import com.mushroom.midnight.common.entity.projectile.NovaSpikeEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NovaSpikeRenderer extends EntityRenderer<NovaSpikeEntity> {
    private static final ResourceLocation NOVA_SPIKE_TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/nova_spike.png");
    private final NovaSpikeModel<NovaSpikeEntity> model = new NovaSpikeModel<>();

    public NovaSpikeRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public void doRender(NovaSpikeEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translatef((float) x, (float) y + 0.15F, (float) z);
        GlStateManager.rotatef(MathHelper.lerp(partialTicks, entity.prevRotationYaw, entity.rotationYaw) - 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotatef(MathHelper.lerp(partialTicks, entity.prevRotationPitch, entity.rotationPitch) - 90.0F, 0.0F, 0.0F, 1.0F);
        this.bindEntityTexture(entity);
        this.model.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(NovaSpikeEntity entity) {
        return NOVA_SPIKE_TEXTURE;
    }
}
