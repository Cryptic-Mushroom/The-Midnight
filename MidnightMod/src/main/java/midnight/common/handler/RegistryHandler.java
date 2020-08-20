package midnight.common.handler;

import com.google.common.reflect.Reflection;
import midnight.common.block.MnBlocks;
import midnight.common.block.fluid.MnFluids;
import midnight.common.item.MnItems;
import midnight.common.registry.RegistryManager;
import midnight.common.sound.MnSoundEvents;
import midnight.common.world.biome.MnBiomes;
import midnight.common.world.dimension.MnDimensions;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Event handler responsible for handling all registry events and passing them to their respective object holders.
 */
@Mod.EventBusSubscriber(modid = "midnight", bus = Mod.EventBusSubscriber.Bus.MOD)
public final class RegistryHandler {
    private RegistryHandler() {
    }

    static {
        Reflection.initialize(
            MnBlocks.class,
            MnItems.class,
            MnFluids.class,
            MnDimensions.class,
            MnBiomes.class,
            MnSoundEvents.class
        );
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        RegistryManager.BLOCKS.fillRegistry(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerDimensions(RegistryEvent.Register<ModDimension> event) {
        RegistryManager.DIMENSIONS.fillRegistry(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        RegistryManager.ITEMS.fillRegistry(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerEntity(RegistryEvent.Register<EntityType<?>> event) {
        RegistryManager.ENTITY_TYPES.fillRegistry(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerFluids(RegistryEvent.Register<Fluid> event) {
        RegistryManager.FLUIDS.fillRegistry(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
        RegistryManager.SOUND_EVENTS.fillRegistry(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerBiomes(RegistryEvent.Register<Biome> event) {
        RegistryManager.BIOMES.fillRegistry(event.getRegistry());
    }
}
