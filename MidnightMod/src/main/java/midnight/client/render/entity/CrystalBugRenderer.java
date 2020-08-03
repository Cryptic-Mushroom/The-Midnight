package midnight.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import midnight.MidnightInfo;
import midnight.client.model.CrystalBugModel;
import midnight.common.entity.creature.CrystalBugEntity;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class CrystalBugRenderer extends MobRenderer<CrystalBugEntity, CrystalBugModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MidnightInfo.MODID, "textures/entities/crystal_bug.png");

    public CrystalBugRenderer(EntityRendererManager manager) {
        super(manager, new CrystalBugModel(), 0.2f);
    }

    @Nullable
    @Override
    public ResourceLocation getEntityTexture(CrystalBugEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void preRenderCallback(CrystalBugEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        if (entitylivingbaseIn.isStanding()) {
            matrixStackIn.translate(0f, 0.3f, 0f);
        } else {
            matrixStackIn.translate(0f, 0.4f, 0f);
        }
        matrixStackIn.scale(0.3f, 0.3f, 0.3f);
    }


    @Override
    protected float getDeathMaxRotation(CrystalBugEntity entity) {
        return 180f;
    }

    /*@Override
    protected int getBlockLight(CrystalBugEntity entityIn, float partialTicks) {
        RenderSystem.glMultiTexCoord2f(33986, 240f, 240f);
        return super.getBlockLight(entityIn, partialTicks);
    }
    */
}
