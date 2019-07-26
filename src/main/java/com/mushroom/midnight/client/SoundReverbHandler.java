package com.mushroom.midnight.client;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.util.MidnightUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.openal.EXTEfx;

public class SoundReverbHandler {
    private static final Minecraft MC = Minecraft.getInstance();

    private static boolean available;
    private static boolean setup;

    private static int auxEffectSlot;
    private static int reverbEffectSlot;

    private static boolean reverbApplied;

    public static void onPlaySound(int soundId) {
        if (!setup) {
            setupEffects();
            setup = true;
        }

        if (available) {
            applyEffect(soundId);
        }
    }

    private static void setupEffects() {
        available = AL.getCapabilities().ALC_EXT_EFX;
        if (!available) {
            Midnight.LOGGER.warn("Unable to setup reverb effects, AL EFX not supported!");
            return;
        }

        auxEffectSlot = EXTEfx.alGenAuxiliaryEffectSlots();
        EXTEfx.alAuxiliaryEffectSloti(auxEffectSlot, EXTEfx.AL_EFFECTSLOT_AUXILIARY_SEND_AUTO, AL10.AL_TRUE);

        reverbEffectSlot = EXTEfx.alGenEffects();
    }

    private static void applyEffect(int soundId) {
        if (shouldEcho(MC.world)) {
            applyReverbEffect();
        } else {
            applyDefaultEffect();
        }

        AL11.alSource3i(soundId, EXTEfx.AL_AUXILIARY_SEND_FILTER, auxEffectSlot, 0, EXTEfx.AL_FILTER_NULL);
    }

    private static void applyReverbEffect() {
        if (reverbApplied) {
            return;
        }

        reverbApplied = true;

        EXTEfx.alEffecti(reverbEffectSlot, EXTEfx.AL_EFFECT_TYPE, EXTEfx.AL_EFFECT_EAXREVERB);

        EXTEfx.alEffectf(reverbEffectSlot, EXTEfx.AL_EAXREVERB_DECAY_TIME, 6.0F);

        EXTEfx.alAuxiliaryEffectSloti(auxEffectSlot, EXTEfx.AL_EFFECTSLOT_EFFECT, reverbEffectSlot);
    }

    private static void applyDefaultEffect() {
        if (!reverbApplied) {
            return;
        }

        reverbApplied = false;

        EXTEfx.alEffecti(reverbEffectSlot, EXTEfx.AL_EFFECT_TYPE, EXTEfx.AL_EFFECT_NULL);
        EXTEfx.alAuxiliaryEffectSloti(auxEffectSlot, EXTEfx.AL_EFFECTSLOT_EFFECT, reverbEffectSlot);
    }

    private static boolean shouldEcho(World world) {
        return MidnightUtil.isMidnightDimension(world);
    }
}
