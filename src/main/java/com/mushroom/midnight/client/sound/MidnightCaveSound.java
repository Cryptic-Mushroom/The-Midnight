package com.mushroom.midnight.client.sound;

import com.mushroom.midnight.client.IdleSoundController;
import com.mushroom.midnight.common.helper.Helper;
import com.mushroom.midnight.common.registry.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MidnightCaveSound extends PositionedSound implements ITickableSound {
    private static final Minecraft MC = Minecraft.getMinecraft();

    public MidnightCaveSound() {
        super(ModSounds.CAVE_IDLE, SoundCategory.AMBIENT);
        this.attenuationType = AttenuationType.NONE;
        this.repeat = true;
    }

    @Override
    public boolean isDonePlaying() {
        return MC.player == null || !Helper.isMidnightDimension(MC.player.world);
    }

    @Override
    public void update() {
        this.volume = Math.max(IdleSoundController.CAVE_ANIMATION.getScale(), 0.01F);
    }
}
