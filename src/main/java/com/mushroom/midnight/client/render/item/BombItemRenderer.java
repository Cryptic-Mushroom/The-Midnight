package com.mushroom.midnight.client.render.item;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mushroom.midnight.client.model.BombModel;
import com.mushroom.midnight.common.item.SporeBombItem;
import com.mushroom.midnight.common.item.SporeBombItem.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.mushroom.midnight.Midnight.MODID;

@OnlyIn(Dist.CLIENT)
public class BombItemRenderer extends ItemStackTileEntityRenderer {
    private final static ResourceLocation TEXTURE_NIGHT = new ResourceLocation(MODID, "textures/items/nightshroom_spore_bomb.png");
    private final static ResourceLocation TEXTURE_DEW = new ResourceLocation(MODID, "textures/items/dewshroom_spore_bomb.png");
    private final static ResourceLocation TEXTURE_VIRID = new ResourceLocation(MODID, "textures/items/viridshroom_spore_bomb.png");
    private final static ResourceLocation TEXTURE_BOG = new ResourceLocation(MODID, "textures/items/bogshroom_spore_bomb.png");
    private final BombModel modelBomb = new BombModel();

    @Override
    public void renderByItem(ItemStack stack) {
        if (stack.getItem() instanceof SporeBombItem) {
            ClientWorld world = Minecraft.getInstance().world;
            SporeBombItem item = (SporeBombItem) stack.getItem();
            float fuseTime = world != null ? item.getFuseTime(world, stack) / (float) item.maxFuseTime : 1f;
            // TODO fusing animation on client
            if (fuseTime < 1f) {
                float ratio = fuseTime * 10f % 1;
                GlStateManager.color4f(1f, 1f, 1f, ratio < 0.5f ? 0.5f : 1f);
            }
            bindTexture(item.getBombType());
            GlStateManager.pushMatrix();
            GlStateManager.enableCull();
            GlStateManager.translatef(0.5f, 0.45f, 0.5f);
            GlStateManager.rotatef(180f, -1f, 0f, 0f);
            this.modelBomb.render();
            GlStateManager.disableCull();
            GlStateManager.popMatrix();
        }
    }

    private void bindTexture(Type bombType) {
        TextureManager textureManager = Minecraft.getInstance().getTextureManager();
        switch (bombType) {
            case NIGHTSHROOM:
                textureManager.bindTexture(TEXTURE_NIGHT);
                break;
            case DEWSHROOM:
                textureManager.bindTexture(TEXTURE_DEW);
                break;
            case VIRIDSHROOM:
                textureManager.bindTexture(TEXTURE_VIRID);
                break;
            case BOGSHROOM:
                textureManager.bindTexture(TEXTURE_BOG);
                break;
        }
    }
}
