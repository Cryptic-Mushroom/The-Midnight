package midnight.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import midnight.common.entity.projectile.BladeshroomCapEntity;
import midnight.common.item.MnItems;

public class BladeshroomCapRenderer extends EntityRenderer<BladeshroomCapEntity> {
    private static final Minecraft MC = Minecraft.getInstance();
    private static final ItemStack STACK = new ItemStack(MnItems.BLADESHROOM_CAP);

    public BladeshroomCapRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public void render(BladeshroomCapEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.push();
        //matrixStackIn.translated(x, y, z);

        float rotationYaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks;
        float rotationPitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
        float spin = entity.prevSpin + (entity.spin - entity.prevSpin) * partialTicks;
        //matrixStackIn.enableRescaleNormal();
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(rotationYaw - 90.0F));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(rotationPitch));


        matrixStackIn.scale(1.5F, 1.5F, 1.5F);

        this.getEntityTexture(entity);
        MC.getItemRenderer().renderItem(STACK, ItemCameraTransforms.TransformType.GROUND, packedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);

        //matrixStackIn.disableRescaleNormal();
        matrixStackIn.pop();
    }

    @Override
    public ResourceLocation getEntityTexture(BladeshroomCapEntity entity) {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }
}
