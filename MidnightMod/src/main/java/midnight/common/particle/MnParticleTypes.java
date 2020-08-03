package midnight.common.particle;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.client.Minecraft;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import midnight.MidnightInfo;
import midnight.client.particle.*;
import midnight.common.registry.RegistryManager;

public class MnParticleTypes {
    public static final BasicParticleType AMBIENT_SPORE = register("ambient_spore", new BasicParticleType(false));
    //public static final ParticleType<ParticleDataOneInt> BOMB_EXPLOSION = new ParticleType<>(false, ParticleDataOneInt.DESERIALIZER);
    public static final BasicParticleType SPORE = register("spore", new BasicParticleType(false));
    //public static final ParticleType<ParticleDataOneInt> FADING_SPORE = new ParticleType<>(false, ParticleDataOneInt.DESERIALIZER);
    //public static final ParticleType<ParticleDataOneInt> DRAGONS_NEST_DRIP = new ParticleType<>(false, ParticleDataOneInt.DESERIALIZER);
    public static final BasicParticleType FURNACE_FLAME = register("furnace_flame", new BasicParticleType(false));
    public static final BasicParticleType BOGSHROOM_SPORCH = register("bogshroom_sporch", new BasicParticleType(false));
    public static final BasicParticleType DEWSHROOM_SPORCH = register("dewshroom_sporch", new BasicParticleType(false));
    public static final BasicParticleType NIGHTSHROOM_SPORCH = register("nightshroom_sporch", new BasicParticleType(false));
    public static final BasicParticleType VIRIDSHROOM_SPORCH = register("viridshroom_sporch", new BasicParticleType(false));
    public static final BasicParticleType BLUE_UNSTABLE_BUSH = register("blue_unstable_bush", new BasicParticleType(false));
    public static final BasicParticleType LIME_UNSTABLE_BUSH = register("lime_unstable_bush", new BasicParticleType(false));
    public static final BasicParticleType GREEN_UNSTABLE_BUSH = register("green_unstable_bush", new BasicParticleType(false));


    @Mod.EventBusSubscriber(modid = MidnightInfo.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Client {
        @SubscribeEvent
        public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
            Minecraft.getInstance().particles.registerFactory(MnParticleTypes.AMBIENT_SPORE, AmbientSporeParticle.Factory::new);
            //Minecraft.getInstance().particles.registerFactory(MidnightParticleTypes.BOMB_EXPLOSION, BombExplosionParticle.Factory::new);
            //Minecraft.getInstance().particles.registerFactory(MidnightParticleTypes.DRAGONS_NEST_DRIP, DragonsNestDripParticle.Factory::new);
            //Minecraft.getInstance().particles.registerFactory(MidnightParticleTypes.FADING_SPORE, FadingSporeParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(MnParticleTypes.FURNACE_FLAME, FurnaceFlameParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(MnParticleTypes.BOGSHROOM_SPORCH, SporchParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(MnParticleTypes.DEWSHROOM_SPORCH, SporchParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(MnParticleTypes.NIGHTSHROOM_SPORCH, SporchParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(MnParticleTypes.VIRIDSHROOM_SPORCH, SporchParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(MnParticleTypes.SPORE, SporeParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(MnParticleTypes.BLUE_UNSTABLE_BUSH, UnstableBushParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(MnParticleTypes.LIME_UNSTABLE_BUSH, UnstableBushParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(MnParticleTypes.GREEN_UNSTABLE_BUSH, UnstableBushParticle.Factory::new);
        }
    }

    private static <P extends ParticleType<?>> P register(String id, P particle) {
        RegistryManager.PARTICLE_TYPES.register(id, particle);
        return particle;
    }
}
