package com.mushroom.midnight.client.sound;

import com.mushroom.midnight.client.IdleSoundController;
import com.mushroom.midnight.common.util.MidnightUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.LocatableSound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.mushroom.midnight.Midnight.MODID;

@OnlyIn(Dist.CLIENT)
public class MidnightIdleSound extends LocatableSound implements ITickableSound {
    private static final Minecraft MC = Minecraft.getInstance();
    private static final ResourceLocation IDLE_RL = new ResourceLocation(MODID, "idle");

    public MidnightIdleSound() {
        super(IDLE_RL, SoundCategory.AMBIENT);
        this.attenuationType = AttenuationType.NONE;
        this.repeat = true;
    }

    @Override
    public boolean isDonePlaying() {
        return MC.player == null || !MidnightUtil.isMidnightDimension(MC.player.world);
    }

    @Override
    public void tick() {
        this.volume = Math.max(1.0F - IdleSoundController.CAVE_ANIMATION.getScale(), 0.01F);
    }
}
