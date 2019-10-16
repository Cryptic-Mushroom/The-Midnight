package com.mushroom.midnight.client.render.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mushroom.midnight.common.entity.projectile.CrystalBulbSpearEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CrystalBulbSpearRenderer extends EntityRenderer<CrystalBulbSpearEntity> {

    private ItemRenderer itemRenderer;

    public CrystalBulbSpearRenderer(EntityRendererManager renderManager) {
        super(renderManager);
        this.itemRenderer = Minecraft.getInstance().getItemRenderer();
    }

    @Override
    public void doRender(CrystalBulbSpearEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);

        GlStateManager.pushMatrix();
        GlStateManager.translated(x, y, z);
        GlStateManager.enableRescaleNormal();

        GlStateManager.translated(0, (entity.getBoundingBox().maxY - entity.getBoundingBox().minY) / 2, 0);
        GlStateManager.rotatef(MathHelper.lerp(partialTicks, entity.prevRotationYaw, entity.rotationYaw) - 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotatef(MathHelper.lerp(partialTicks, entity.prevRotationPitch, entity.rotationPitch) + 180F + 130.0F, 0.0F, 0.0F, 1.0F);

        GlStateManager.scaled(1.2, 1.2, 1.2);

        this.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.setupSolidRenderingTextureCombine(this.getTeamColor(entity));
        }

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        this.itemRenderer.renderItem(entity.getRenderStack(), this.itemRenderer.getModelWithOverrides(entity.getRenderStack()));
        GlStateManager.disableBlend();

        if (this.renderOutlines) {
            GlStateManager.tearDownSolidRenderingTextureCombine();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(CrystalBulbSpearEntity entity) {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }
}