package com.mushroom.midnight.client.render.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mushroom.midnight.common.entity.projectile.BladeshroomCapEntity;
import com.mushroom.midnight.common.registry.MidnightItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class BladeshroomCapRenderer extends EntityRenderer<BladeshroomCapEntity> {
    private static final Minecraft MC = Minecraft.getInstance();
    private static final ItemStack STACK = new ItemStack(MidnightItems.BLADESHROOM_CAP);

    public BladeshroomCapRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public void doRender(BladeshroomCapEntity entity, double x, double y, double z, float yaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translated(x, y, z);

        float rotationYaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks;
        float rotationPitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
        float spin = entity.prevSpin + (entity.spin - entity.prevSpin) * partialTicks;

        GlStateManager.rotatef(rotationYaw - 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotatef(rotationPitch, 0.0F, 0.0F, 1.0F);

        GlStateManager.rotatef(spin, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotatef(90.0F, 1.0F, 0.0F, 0.0F);

        GlStateManager.enableRescaleNormal();

        GlStateManager.scalef(1.5F, 1.5F, 1.5F);

        this.bindEntityTexture(entity);
        MC.getItemRenderer().renderItem(STACK, ItemCameraTransforms.TransformType.GROUND);

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(BladeshroomCapEntity entity) {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }
}
