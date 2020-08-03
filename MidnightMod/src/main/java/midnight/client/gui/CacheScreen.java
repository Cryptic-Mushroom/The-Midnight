package midnight.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import midnight.common.inventory.CacheContainer;

import static midnight.MidnightInfo.MODID;

@OnlyIn(Dist.CLIENT)
public class CacheScreen extends ContainerScreen<CacheContainer> implements IHasContainer<CacheContainer> {
    private static final ResourceLocation CACHE_BG_TEXTURE = new ResourceLocation(MODID, "textures/gui/gui_cache.png");

    public CacheScreen(CacheContainer container, PlayerInventory playerInventory, ITextComponent iTextComponent) {
        super(container, playerInventory, iTextComponent);
        this.xSize = 174;
        this.ySize = 127;
        this.passEvents = false;
    }

    @Override
    public void render(MatrixStack matrixStackIn, int mouseXIn, int mouseYIn, float partialTicksIn) {
        renderBackground(matrixStackIn);
        super.render(matrixStackIn, mouseXIn, mouseYIn, partialTicksIn);
        this.func_230459_a_(matrixStackIn, mouseXIn, mouseYIn);
    }

  /*  @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String titleString = this.title.getFormattedText();
        float startPos = (this.xSize - this.font.getStringWidth(titleString)) / 2f;
        this.font.drawString(titleString, startPos, 8f, 4210752);
    }*/

    protected void func_230450_a_(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        assert this.minecraft != null;
        this.minecraft.getTextureManager().bindTexture(CACHE_BG_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.blit(p_230450_1_, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }
}
