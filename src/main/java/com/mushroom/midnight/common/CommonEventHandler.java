package com.mushroom.midnight.common;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.capability.CavernousBiomeStore;
import com.mushroom.midnight.common.capability.MidnightWorldSpawners;
import com.mushroom.midnight.common.capability.RiftTraveller;
import com.mushroom.midnight.common.capability.RifterCapturable;
import com.mushroom.midnight.common.config.MidnightConfig;
import com.mushroom.midnight.common.entity.RiftEntity;
import com.mushroom.midnight.common.event.RifterCaptureEvent;
import com.mushroom.midnight.common.event.RifterReleaseEvent;
import com.mushroom.midnight.common.util.MidnightUtil;
import com.mushroom.midnight.common.registry.MidnightDimensions;
import com.mushroom.midnight.common.registry.MidnightEffects;
import com.mushroom.midnight.common.world.GlobalBridgeManager;
import com.mushroom.midnight.common.world.RiftSpawnHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class CommonEventHandler {
    public static final float SOUND_TRAVEL_DISTANCE_MULTIPLIER = 2.0F;
    private static final ThreadLocal<DimensionType> TICKING_DIMENSION = ThreadLocal.withInitial(() -> null);

    @SubscribeEvent
    public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof LivingEntity) {
            event.addCapability(new ResourceLocation(Midnight.MODID, "rift_traveller"), new RiftTraveller());

            if (!(event.getObject() instanceof PlayerEntity)) {
                ResourceLocation rl = EntityType.getKey(event.getObject().getType());
                if (rl == null) {
                    return;
                }
                if (event.getObject() instanceof AnimalEntity) {
                    if (MidnightConfig.general.notCapturableAnimals.get().stream().anyMatch(p -> p.contains(":") ? rl.toString().equals(p) : rl.getNamespace().equals(p))) {
                        return;
                    }
                } else if (!MidnightConfig.general.capturableEntities.get().contains(rl.toString())) {
                    return;
                }
            }
            event.addCapability(new ResourceLocation(Midnight.MODID, "rifter_captured"), new RifterCapturable());
        }
    }

    @SubscribeEvent
    public static void onAttachChunkCapabilities(AttachCapabilitiesEvent<Chunk> event) {
        if (MidnightUtil.isMidnightDimension(event.getObject().getWorld())) {
            event.addCapability(new ResourceLocation(Midnight.MODID, "cavernous_biomes"), new CavernousBiomeStore());
        }
    }

    @SubscribeEvent
    public static void onAttachWorldCapabilities(AttachCapabilitiesEvent<World> event) {
        World world = event.getObject();

        if (MidnightUtil.isMidnightDimension(world)) {
            if (!world.isRemote && world instanceof ServerWorld) {
                MidnightWorldSpawners spawners = new MidnightWorldSpawners.SurfaceAndCave((ServerWorld) world);
                event.addCapability(new ResourceLocation(Midnight.MODID, "world_spawners"), spawners);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityTick(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        entity.getCapability(Midnight.RIFT_TRAVELLER_CAP).ifPresent(traveller -> traveller.update(entity));
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            World world = event.world;
            if (!world.isRemote) {
                GlobalBridgeManager.getServer().update();
            }

            TICKING_DIMENSION.set(world.dimension.getType());
        } else {
            TICKING_DIMENSION.set(null);
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (MidnightConfig.general.riftSpawnRarity.get() > 0 && event.phase == TickEvent.Phase.END) {
            RiftSpawnHandler.update();
        }
    }

    @SubscribeEvent
    public static void onEntityAttack(LivingHurtEvent event) {
        DamageSource source = event.getSource();
        Entity trueSource = source.getTrueSource();
        if (trueSource instanceof LivingEntity && ((LivingEntity) trueSource).isPotionActive(MidnightEffects.STUNNED)) {
            event.setAmount(0.0F);
        }
    }

    @SubscribeEvent
    public static void onRifterCapture(RifterCaptureEvent event) {
        LivingEntity captured = event.getCaptured();
        if (captured instanceof PlayerEntity) {
            captured.recalculateSize();
        }
    }

    @SubscribeEvent
    public static void onRifterRelease(RifterReleaseEvent event) {
        LivingEntity captured = event.getCaptured();
        if (captured instanceof PlayerEntity) {
            captured.recalculateSize();
        }
    }

    @SubscribeEvent
    public static void setEyeHeight(EntityEvent.EyeHeight event) {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            if (RifterCapturable.isCaptured(player)) {
                event.setNewHeight(player.getWidth() / 2f);
            }
        }
    }

    @SubscribeEvent
    public static void onSleep(PlayerSleepInBedEvent event) {
        if (event.getResultStatus() == null) {
            PlayerEntity player = event.getEntityPlayer();
            BlockPos bedPos = player.getPosition(); // event.getPos() is nullable in the forge event

            List<RiftEntity> rifts = player.world.getEntitiesWithinAABB(RiftEntity.class, new AxisAlignedBB(bedPos).grow(6.0));
            if (!rifts.isEmpty()) {
                event.setResult(PlayerEntity.SleepResult.OTHER_PROBLEM);
                player.sendStatusMessage(new TranslationTextComponent("status.midnight.rift_nearby"), true);
            }
        }
    }

    @SubscribeEvent
    public static void onPlaySound(PlaySoundAtEntityEvent event) {
        if (TICKING_DIMENSION.get() == MidnightDimensions.midnight()) {
            event.setVolume(event.getVolume() * SOUND_TRAVEL_DISTANCE_MULTIPLIER);
        }
    }
}
