package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.client.particle.AmbientSporeParticle;
import com.mushroom.midnight.client.particle.BombExplosionParticle;
import com.mushroom.midnight.client.particle.DragonsNestDripParticle;
import com.mushroom.midnight.client.particle.FadingSporeParticle;
import com.mushroom.midnight.client.particle.FurnaceFlameParticle;
import com.mushroom.midnight.client.particle.SporchParticle;
import com.mushroom.midnight.client.particle.SporeParticle;
import com.mushroom.midnight.client.particle.UnstableBushParticle;
import com.mushroom.midnight.common.particle.ParticleDataOneInt;
import net.minecraft.client.Minecraft;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.mushroom.midnight.Midnight.MODID;

public class MidnightParticleTypes {
    public static final BasicParticleType AMBIENT_SPORE = new BasicParticleType(false);
    public static final ParticleType<ParticleDataOneInt> BOMB_EXPLOSION = new ParticleType<>(false, ParticleDataOneInt.DESERIALIZER);
    public static final BasicParticleType SPORE = new BasicParticleType(false);
    public static final ParticleType<ParticleDataOneInt> FADING_SPORE = new ParticleType<>(false, ParticleDataOneInt.DESERIALIZER);
    public static final ParticleType<ParticleDataOneInt> DRAGONS_NEST_DRIP = new ParticleType<>(false, ParticleDataOneInt.DESERIALIZER);
    public static final BasicParticleType FURNACE_FLAME = new BasicParticleType(false);
    public static final BasicParticleType BOGSHROOM_SPORCH = new BasicParticleType(false);
    public static final BasicParticleType DEWSHROOM_SPORCH = new BasicParticleType(false);
    public static final BasicParticleType NIGHTSHROOM_SPORCH = new BasicParticleType(false);
    public static final BasicParticleType VIRIDSHROOM_SPORCH = new BasicParticleType(false);
    public static final BasicParticleType BLUE_UNSTABLE_BUSH = new BasicParticleType(false);
    public static final BasicParticleType LIME_UNSTABLE_BUSH = new BasicParticleType(false);
    public static final BasicParticleType GREEN_UNSTABLE_BUSH = new BasicParticleType(false);

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Common {
        @SubscribeEvent
        public static void registerParticleTypes(RegistryEvent.Register<ParticleType<?>> event) {
            RegUtil.generic(event.getRegistry())
                    .add("ambient_spore", AMBIENT_SPORE)
                    .add("bomb_explosion", BOMB_EXPLOSION)
                    .add("dragons_nest_drip", DRAGONS_NEST_DRIP)
                    .add("fading_spore", FADING_SPORE)
                    .add("furnace_flame", FURNACE_FLAME)
                    .add("bogshroom_sporch", BOGSHROOM_SPORCH)
                    .add("dewshroom_sporch", DEWSHROOM_SPORCH)
                    .add("nightshroom_sporch", NIGHTSHROOM_SPORCH)
                    .add("viridshroom_sporch", VIRIDSHROOM_SPORCH)
                    .add("spore", SPORE)
                    .add("blue_unstable_bush", BLUE_UNSTABLE_BUSH)
                    .add("lime_unstable_bush", LIME_UNSTABLE_BUSH)
                    .add("green_unstable_bush", GREEN_UNSTABLE_BUSH);
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Client {
        @SubscribeEvent
        public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
            Minecraft.getInstance().particles.registerFactory(MidnightParticleTypes.AMBIENT_SPORE, AmbientSporeParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(MidnightParticleTypes.BOMB_EXPLOSION, BombExplosionParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(MidnightParticleTypes.DRAGONS_NEST_DRIP, DragonsNestDripParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(MidnightParticleTypes.FADING_SPORE, FadingSporeParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(MidnightParticleTypes.FURNACE_FLAME, FurnaceFlameParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(MidnightParticleTypes.BOGSHROOM_SPORCH, SporchParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(MidnightParticleTypes.DEWSHROOM_SPORCH, SporchParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(MidnightParticleTypes.NIGHTSHROOM_SPORCH, SporchParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(MidnightParticleTypes.VIRIDSHROOM_SPORCH, SporchParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(MidnightParticleTypes.SPORE, SporeParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(MidnightParticleTypes.BLUE_UNSTABLE_BUSH, UnstableBushParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(MidnightParticleTypes.LIME_UNSTABLE_BUSH, UnstableBushParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(MidnightParticleTypes.GREEN_UNSTABLE_BUSH, UnstableBushParticle.Factory::new);
        }
    }
}
