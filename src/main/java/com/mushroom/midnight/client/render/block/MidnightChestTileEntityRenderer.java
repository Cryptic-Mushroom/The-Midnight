package com.mushroom.midnight.client.render.block;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mushroom.midnight.common.block.MidnightChestBlock;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.tile.base.MidnightChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.model.ChestModel;
import net.minecraft.client.renderer.tileentity.model.LargeChestModel;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.mushroom.midnight.Midnight.MODID;

@OnlyIn(Dist.CLIENT)
public class MidnightChestTileEntityRenderer extends TileEntityRenderer<MidnightChestTileEntity> {
    private static final ResourceLocation TEXTURE_SHADOWROOT_DOUBLE = new ResourceLocation(MODID, "textures/entities/chest/shadowroot_chest_double.png");
    private static final ResourceLocation TEXTURE_SHADOWROOT_NORMAL = new ResourceLocation(MODID, "textures/entities/chest/shadowroot_chest.png");
    private static final ResourceLocation TEXTURE_DARK_WILLOW_DOUBLE = new ResourceLocation(MODID, "textures/entities/chest/dark_willow_chest_double.png");
    private static final ResourceLocation TEXTURE_DARK_WILLOW_NORMAL = new ResourceLocation(MODID, "textures/entities/chest/dark_willow_chest.png");
    private static final ResourceLocation TEXTURE_DEAD_WOOD_DOUBLE = new ResourceLocation(MODID, "textures/entities/chest/dead_wood_chest_double.png");
    private static final ResourceLocation TEXTURE_DEAD_WOOD_NORMAL = new ResourceLocation(MODID, "textures/entities/chest/dead_wood_chest.png");
    private static final ResourceLocation TEXTURE_NIGHTSHROOM_DOUBLE = new ResourceLocation(MODID, "textures/entities/chest/nightshroom_chest_double.png");
    private static final ResourceLocation TEXTURE_NIGHTSHROOM_NORMAL = new ResourceLocation(MODID, "textures/entities/chest/nightshroom_chest.png");
    private static final ResourceLocation TEXTURE_DEWSHROOM_DOUBLE = new ResourceLocation(MODID, "textures/entities/chest/dewshroom_chest_double.png");
    private static final ResourceLocation TEXTURE_DEWSHROOM_NORMAL = new ResourceLocation(MODID, "textures/entities/chest/dewshroom_chest.png");
    private static final ResourceLocation TEXTURE_VIRIDSHROOM_DOUBLE = new ResourceLocation(MODID, "textures/entities/chest/viridshroom_chest_double.png");
    private static final ResourceLocation TEXTURE_VIRIDSHROOM_NORMAL = new ResourceLocation(MODID, "textures/entities/chest/viridshroom_chest.png");
    private final ChestModel simpleChest = new ChestModel();
    private final LargeChestModel largeChest = new LargeChestModel();

    public MidnightChestTileEntityRenderer() {
    }

    private void bindTexture(Block chestModel, int destroyStage, boolean isDouble) {
        ResourceLocation rl = TEXTURE_SHADOWROOT_NORMAL;
        if (destroyStage >= 0) {
            rl = DESTROY_STAGES[destroyStage];
        } else if (chestModel == MidnightBlocks.SHADOWROOT_CHEST) {
            rl = isDouble ? TEXTURE_SHADOWROOT_DOUBLE : TEXTURE_SHADOWROOT_NORMAL;
        } else if (chestModel == MidnightBlocks.DARK_WILLOW_CHEST) {
            rl = isDouble ? TEXTURE_DARK_WILLOW_DOUBLE : TEXTURE_DARK_WILLOW_NORMAL;
        } else if (chestModel == MidnightBlocks.DEAD_WOOD_CHEST) {
            rl = isDouble ? TEXTURE_DEAD_WOOD_DOUBLE : TEXTURE_DEAD_WOOD_NORMAL;
        } else if (chestModel == MidnightBlocks.NIGHTSHROOM_CHEST) {
            rl = isDouble ? TEXTURE_NIGHTSHROOM_DOUBLE : TEXTURE_NIGHTSHROOM_NORMAL;
        } else if (chestModel == MidnightBlocks.DEWSHROOM_CHEST) {
            rl = isDouble ? TEXTURE_DEWSHROOM_DOUBLE : TEXTURE_DEWSHROOM_NORMAL;
        } else if (chestModel == MidnightBlocks.VIRIDSHROOM_CHEST) {
            rl = isDouble ? TEXTURE_VIRIDSHROOM_DOUBLE : TEXTURE_VIRIDSHROOM_NORMAL;
        }
        bindTexture(rl);
    }

    @Override
    public void render(MidnightChestTileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.enableDepthTest();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);

        final Block blockModel;
        final BlockState state;
        if (te.hasWorld() && te.getBlockState().getBlock() instanceof MidnightChestBlock) {
            state = te.getBlockState();
            blockModel = state.getBlock();
        } else {
            blockModel = te.getChestModel();
            state = blockModel.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        }
        ChestType chestType = state.has(ChestBlock.TYPE) ? state.get(ChestBlock.TYPE) : ChestType.SINGLE;

        if (chestType != ChestType.LEFT) {
            boolean flag = chestType != ChestType.SINGLE;
            ChestModel chestmodel = flag ? this.largeChest : this.simpleChest;
            bindTexture(blockModel, destroyStage, flag);
            if (destroyStage >= 0) {
                GlStateManager.matrixMode(5890);
                GlStateManager.pushMatrix();
                GlStateManager.scalef(flag ? 8.0F : 4.0F, 4.0F, 1.0F);
                GlStateManager.translatef(0.0625F, 0.0625F, 0.0625F);
                GlStateManager.matrixMode(5888);
            } else {
                GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            }

            GlStateManager.pushMatrix();
            GlStateManager.enableRescaleNormal();
            GlStateManager.translatef((float)x, (float)y + 1.0F, (float)z + 1.0F);
            GlStateManager.scalef(1.0F, -1.0F, -1.0F);
            float f = state.get(ChestBlock.FACING).getHorizontalAngle();
            if ((double)Math.abs(f) > 1.0E-5D) {
                GlStateManager.translatef(0.5F, 0.5F, 0.5F);
                GlStateManager.rotatef(f, 0.0F, 1.0F, 0.0F);
                GlStateManager.translatef(-0.5F, -0.5F, -0.5F);
            }

            this.applyLidRotation(te, partialTicks, chestmodel);
            chestmodel.renderAll();
            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            if (destroyStage >= 0) {
                GlStateManager.matrixMode(5890);
                GlStateManager.popMatrix();
                GlStateManager.matrixMode(5888);
            }
        }
    }

    private void applyLidRotation(MidnightChestTileEntity p_199346_1_, float p_199346_2_, ChestModel p_199346_3_) {
        float f = ((IChestLid) p_199346_1_).getLidAngle(p_199346_2_);
        f = 1.0F - f;
        f = 1.0F - f * f * f;
        p_199346_3_.getLid().rotateAngleX = -(f * ((float) Math.PI / 2F));
    }
}
