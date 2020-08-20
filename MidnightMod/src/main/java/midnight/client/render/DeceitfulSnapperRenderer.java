package midnight.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import midnight.MidnightInfo;
import midnight.client.model.DeceitfulSnapperModel;
import midnight.common.entity.DeceitfulSnapperEntity;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;


public class DeceitfulSnapperRenderer extends MobRenderer<DeceitfulSnapperEntity, DeceitfulSnapperModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MidnightInfo.MODID, "textures/entities/deceitful_snapper.png");

    public DeceitfulSnapperRenderer(EntityRendererManager manager) {
        super(manager, new DeceitfulSnapperModel(), 0f);
    }

    @Nullable
    @Override
    public ResourceLocation getEntityTexture(DeceitfulSnapperEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void preRenderCallback(DeceitfulSnapperEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        super.preRenderCallback(entitylivingbaseIn, matrixStackIn, partialTickTime);
        matrixStackIn.translate(0f, 1.4f, -0.05f);
        matrixStackIn.scale(1.1f, 1.1f, 1.1f);
    }


    @Override
    protected void applyRotations(DeceitfulSnapperEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        if(!entityLiving.isInWater()) {
            matrixStackIn.rotate(Vector3f.ZP.rotation(90.0F));
        }
    }

    @Override
    protected float getDeathMaxRotation(DeceitfulSnapperEntity entity) {
        return 180f;
    }
}