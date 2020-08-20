package midnight.client.render;

import midnight.MidnightInfo;
import midnight.client.model.SkulkModel;
import midnight.common.entity.SkulkEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class SkulkRenderer extends MobRenderer<SkulkEntity, SkulkModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MidnightInfo.MODID, "textures/entities/skulk.png");

    public SkulkRenderer(EntityRendererManager manager) {
        super(manager, new SkulkModel(), 0.4f);
    }

    @Override
    protected RenderType func_230042_a_(SkulkEntity entity, boolean p_230042_2_, boolean p_230042_3_) { // p_230042_2_ and p_230042_3_ unmapped as of mappings 20200326
        ResourceLocation resourcelocation = this.getEntityTexture(entity);
        if(entity.isStealth()) {
            return RenderType.getEntityTranslucent(resourcelocation);
        } else if(p_230042_2_) {
            return this.entityModel.getRenderType(resourcelocation);
        } else {
            return entity.isGlowing() ? RenderType.getOutline(resourcelocation) : null;
        }
    }

    @Override
    protected boolean isVisible(SkulkEntity livingEntityIn) {
        return !livingEntityIn.isStealth() && !livingEntityIn.isInvisible();
    }

    @Nullable
    @Override
    public ResourceLocation getEntityTexture(SkulkEntity entity) {
        return TEXTURE;
    }
}
