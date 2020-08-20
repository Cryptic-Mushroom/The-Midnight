package midnight.common.sound;

import midnight.common.Midnight;
import midnight.common.registry.RegistryManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public final class MnSoundEvents {
    public static final SoundEvent BLOCK_MUD_BREAK = make("block.mud.break");
    public static final SoundEvent BLOCK_MUD_STEP = make("block.mud.step");

    public static final SoundEvent SNAPPER_DEATH = make("snapper_death");
    public static final SoundEvent SNAPPER_HURT = make("snapper_hurt");

    private MnSoundEvents() {
    }

    private static SoundEvent make(String type) {
        ResourceLocation id = Midnight.resLoc(type);
        SoundEvent event = new SoundEvent(id).setRegistryName(id);
        RegistryManager.SOUND_EVENTS.register(event);
        return event;
    }
}
