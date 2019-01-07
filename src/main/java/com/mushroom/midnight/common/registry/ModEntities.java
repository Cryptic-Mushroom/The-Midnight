package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.entity.EntityBladeshroomCap;
import com.mushroom.midnight.common.entity.EntityRift;
import com.mushroom.midnight.common.entity.creature.EntityCrystalBug;
import com.mushroom.midnight.common.entity.creature.EntityHunter;
import com.mushroom.midnight.common.entity.creature.EntityNova;
import com.mushroom.midnight.common.entity.creature.EntityRifter;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class ModEntities {
    private static int currentEntityId;

    @SubscribeEvent
    public static void onRegisterEntities(RegistryEvent.Register<EntityEntry> event) {
        event.getRegistry().registerAll(
                EntityEntryBuilder.create()
                        .entity(EntityRift.class)
                        .factory(EntityRift::new)
                        .id(new ResourceLocation(Midnight.MODID, "rift"), currentEntityId++)
                        .name(Midnight.MODID + ".rift")
                        .tracker(1024, 20, false)
                        .build(),
                EntityEntryBuilder.create()
                        .entity(EntityRifter.class)
                        .factory(EntityRifter::new)
                        .id(new ResourceLocation(Midnight.MODID, "rifter"), currentEntityId++)
                        .name(Midnight.MODID + ".rifter")
                        .tracker(80, 3, true)
                        .egg(0x384740, 0x5E8C6C)
                        .build(),
                EntityEntryBuilder.create()
                        .entity(EntityHunter.class)
                        .factory(EntityHunter::new)
                        .id(new ResourceLocation(Midnight.MODID, "hunter"), currentEntityId++)
                        .name(Midnight.MODID + ".hunter")
                        .tracker(80, 2, true)
                        .egg(0x2C3964, 0x19203A)
                        .build(),
                EntityEntryBuilder.create()
                        .entity(EntityBladeshroomCap.class)
                        .factory(EntityBladeshroomCap::new)
                        .id(new ResourceLocation(Midnight.MODID, "bladeshroom_cap"), currentEntityId++)
                        .name(Midnight.MODID + ".bladeshroom_cap")
                        .tracker(64, 1, true)
                        .build(),
                EntityEntryBuilder.create()
                        .entity(EntityNova.class)
                        .factory(EntityNova::new)
                        .id(new ResourceLocation(Midnight.MODID, "nova"), currentEntityId++)
                        .name(Midnight.MODID + ".nova")
                        .tracker(80, 2, true)
                        /*.egg(0x932C3B, 0x47415E)*/
                        .build(),
                EntityEntryBuilder.create()
                        .entity(EntityCrystalBug.class)
                        .factory(EntityCrystalBug::new)
                        .id(new ResourceLocation(Midnight.MODID, "crystal_bug"), currentEntityId++)
                        .name(Midnight.MODID + ".crystal_bug")
                        .tracker(80, 2, true)
                        /*.egg(0x932C3B, 0x47415E)*/
                        .build()
        );

        EntitySpawnPlacementRegistry.setPlacementType(EntityHunter.class, EntityLiving.SpawnPlacementType.IN_AIR);
    }
}
