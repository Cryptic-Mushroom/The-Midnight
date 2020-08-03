package midnight.common.handler;

import com.google.common.reflect.Reflection;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.particles.ParticleType;
import net.minecraft.tileentity.TileEntityType;
import midnight.common.block.MnBlocks;
import midnight.common.entity.MnEntities;
import midnight.common.fluid.MnFluids;
import midnight.common.item.MnItems;
import midnight.common.particle.MnParticleTypes;
import midnight.common.recipe.MnRecipeSerializers;
import midnight.common.registry.RegistryManager;
import midnight.common.tileentity.MnTileEntities;

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
                MnEntities.class,
                MnRecipeSerializers.class,
                MnTileEntities.class,
                MnParticleTypes.class
        );
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        RegistryManager.BLOCKS.fillRegistry(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        RegistryManager.ITEMS.fillRegistry(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerFluids(RegistryEvent.Register<Fluid> event) {
        RegistryManager.FLUIDS.fillRegistry(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerEntityType(RegistryEvent.Register<EntityType<?>> event) {
        RegistryManager.ENTITY_TYPES.fillRegistry(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        RegistryManager.RECIPE_SERIALIZERS.fillRegistry(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerTileEntityType(RegistryEvent.Register<TileEntityType<?>> event) {
        RegistryManager.TILE_ENTITIES.fillRegistry(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerParticleType(RegistryEvent.Register<ParticleType<?>> event) {
        RegistryManager.PARTICLE_TYPES.fillRegistry(event.getRegistry());
    }
}
