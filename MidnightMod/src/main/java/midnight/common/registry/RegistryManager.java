package midnight.common.registry;

import com.mojang.datafixers.util.Pair;
import midnight.MidnightInfo;
import midnight.common.Midnight;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A pre-registry that registry objects may be added to, before Forge triggers a certain registry event.
 */
public class RegistryManager<E extends IForgeRegistryEntry<E>> implements Iterable<E> {
    public static final RegistryManager<Block> BLOCKS = new RegistryManager<>();
    public static final RegistryManager<Item> ITEMS = new RegistryManager<>();
    public static final BlockItemRegistryManager BLOCKS_ITEMS = new BlockItemRegistryManager(BLOCKS, ITEMS);

    public static final RegistryManager<EntityType<?>> ENTITY_TYPES = new RegistryManager<>();
    public static final RegistryManager<Fluid> FLUIDS = new RegistryManager<>();
    public static final RegistryManager<ModDimension> DIMENSIONS = new RegistryManager<>();
    public static final RegistryManager<Biome> BIOMES = new RegistryManager<>();
    public static final RegistryManager<SoundEvent> SOUND_EVENTS = new RegistryManager<>();

    private final List<Entry> entries = new ArrayList<>();

    /**
     * Register an entry under a specific ID.
     *
     * @param id    The ID to register on, generating a {@link ResourceLocation} as specified in {@link
     *              Midnight#resLoc}.
     * @param entry The entry to register.
     * @return The registered entry.
     */
    public E register(String id, E entry) {
        entries.add(new Entry(Midnight.resLoc(id), entry));
        return entry;
    }

    /**
     * Register an entry under a specific ID.
     *
     * @param id    The ID to register on.
     * @param entry The entry to register.
     * @return The registered entry.
     */
    public E register(ResourceLocation id, E entry) {
        entries.add(new Entry(id, entry));
        return entry;
    }

    /**
     * Register an entry under the registry ID of the object.
     *
     * @param entry The entry to register. Make sure to call {@link IForgeRegistryEntry#setRegistryName setRegistryName}
     *              before registring.
     * @return The registered entry.
     */
    public E register(E entry) {
        if (entry.getRegistryName() == null) throw new NullPointerException("Registry name is null");
        entries.add(new Entry(entry.getRegistryName(), entry));
        return entry;
    }

    /**
     * Fills the given {@link IForgeRegistry} with the entries registered in this {@link RegistryManager}.
     *
     * @param registry The registry to register to.
     */
    public void fillRegistry(IForgeRegistry<E> registry) {
        for (Entry entry : entries) {
            if (entry.entry.getRegistryName() == null) {
                entry.entry.setRegistryName(entry.id);
            }
            registry.register(entry.entry);
        }
    }

    /**
     * Obtain a {@link RegistryFiller} to fill this registry with, using no configuration.
     */
    public RegistryFiller<E, Object> fill() {
        return new RegistryFiller<>(this, () -> null);
    }

    /**
     * Obtain a {@link RegistryFiller} to fill this registry with, using the specified configuration supplier.
     */
    public <C> RegistryFiller<E, C> fill(Supplier<C> config) {
        return new RegistryFiller<>(this, config);
    }

    /**
     * Obtain a {@link RegistryFiller} to fill this registry with, using the specified configuration.
     */
    public <C> RegistryFiller<E, C> fill(C config) {
        return new RegistryFiller<>(this, () -> config);
    }

    @Override
    public Iterator<E> iterator() {
        return entries.stream().map(en -> en.entry).iterator();
    }


    private class Entry {
        private final ResourceLocation id;
        private final E entry;

        private Entry(ResourceLocation id, E entry) {
            this.id = id;
            this.entry = entry;
        }
    }

    /**
     * A filler for conveniently filling a {@link RegistryManager}.
     */
    public static class RegistryFiller<E extends IForgeRegistryEntry<E>, C> {
        private final RegistryManager<E> manager;
        private final Supplier<? extends C> config;

        private RegistryFiller(RegistryManager<E> manager, Supplier<? extends C> config) {
            this.manager = manager;
            this.config = config;
        }

        /**
         * Bulk-register a collection of objects under the given IDs, created by the given factory.
         *
         * @param factory The factory to create the objects with, given the specified configuration.
         * @param ids     The IDs to register the objects under. A new object is created for each ID. The ID is
         *                generated as specified by {@link Midnight#resLoc}.
         * @return This instance for chaining.
         */
        public RegistryFiller<E, C> register(Function<? super C, ? extends E> factory, String... ids) {
            for (String id : ids) {
                manager.register(id, factory.apply(config.get()));
            }
            return this;
        }

        /**
         * Bulk-register a collection of objects under the given IDs, created by the given factory.
         *
         * @param factory The factory to create the objects with, given the specified configuration and ID.
         * @param ids     The IDs to register the objects under. A new object is created for each ID. The ID is
         *                generated as specified by {@link Midnight#resLoc}.
         * @return This instance for chaining.
         */
        public RegistryFiller<E, C> register(BiFunction<? super C, ? super ResourceLocation, ? extends E> factory, String... ids) {
            for (String id : ids) {
                ResourceLocation resID = Midnight.resLoc(id);
                manager.register(resID, factory.apply(config.get(), resID));
            }
            return this;
        }
    }

    /**
     * A filler for conveniently filling a {@link BlockItemRegistryManager}.
     */
    public static class BlockItemRegistryFiller<C> {
        private final RegistryManager<Block> blockManager;
        private final RegistryManager<Item> itemManager;
        private final Supplier<? extends C> config;

        private BlockItemRegistryFiller(RegistryManager<Block> blockManager, RegistryManager<Item> itemManager, Supplier<? extends C> config) {
            this.blockManager = blockManager;
            this.itemManager = itemManager;
            this.config = config;
        }

        /**
         * Register a collection of blocks and their block-items to the block and the item registry respectively, using
         * the given factory.
         *
         * @param factory The factory, that generates both the block and the item in a pair, given the specified
         *                configuration.
         * @param ids     The IDs to register the objects under. A new object is created for each ID. The ID is
         *                generated as specified by {@link Midnight#resLoc}.
         * @return This instance for chaining.
         */
        public BlockItemRegistryFiller<C> blockItem(Function<? super C, ? extends Pair<? extends Block, BlockItem>> factory, String... ids) {
            for (String id : ids) {
                Pair<? extends Block, BlockItem> pair = factory.apply(config.get());
                blockManager.register(id, pair.getFirst());
                itemManager.register(id, pair.getSecond());
            }
            return this;
        }

        /**
         * Register a collection of blocks and their block-items to the block and the item registry respectively, using
         * the given factory.
         *
         * @param factory The factory, that generates both the block and the item in a pair, given the specified
         *                configuration and ID.
         * @param ids     The IDs to register the objects under. A new object is created for each ID. The ID is
         *                generated as specified by {@link Midnight#resLoc}.
         * @return This instance for chaining.
         */
        public BlockItemRegistryFiller<C> blockItem(BiFunction<? super C, ? super ResourceLocation, ? extends Pair<? extends Block, BlockItem>> factory, String... ids) {
            for (String id : ids) {
                ResourceLocation resID = Midnight.resLoc(id);
                Pair<? extends Block, BlockItem> pair = factory.apply(config.get(), resID);
                blockManager.register(resID, pair.getFirst());
                itemManager.register(resID, pair.getSecond());
            }
            return this;
        }

        /**
         * Register a collection of blocks to the block registry, using the given factory.
         *
         * @param factory The factory, given the specified configuration.
         * @param ids     The IDs to register the objects under. A new object is created for each ID. The ID is
         *                generated as specified by {@link Midnight#resLoc}.
         * @return This instance for chaining.
         */
        public BlockItemRegistryFiller<C> blockOnly(Function<? super C, ? extends Block> factory, String... ids) {
            for (String id : ids) {
                blockManager.register(id, factory.apply(config.get()));
            }
            return this;
        }

        /**
         * Register a collection of blocks to the block registry, using the given factory.
         *
         * @param factory The factory, given the specified configuration and ID.
         * @param ids     The IDs to register the objects under. A new object is created for each ID. The ID is
         *                generated as specified by {@link Midnight#resLoc}.
         * @return This instance for chaining.
         */
        public BlockItemRegistryFiller<C> blockOnly(BiFunction<? super C, ? super ResourceLocation, ? extends Block> factory, String... ids) {
            for (String id : ids) {
                ResourceLocation resID = new ResourceLocation(MidnightInfo.MODID, id);
                blockManager.register(resID, factory.apply(config.get(), resID));
            }
            return this;
        }
    }

    /**
     * A wrapper around the block and item registry manager, to act like a regular registry manager but used to register
     * blocks and their respective items simultaneously.
     */
    public static class BlockItemRegistryManager {
        private final RegistryManager<Block> blockManager;
        private final RegistryManager<Item> itemManager;

        private BlockItemRegistryManager(RegistryManager<Block> blockManager, RegistryManager<Item> itemManager) {
            this.blockManager = blockManager;
            this.itemManager = itemManager;
        }


        /**
         * Obtain a {@link BlockItemRegistryFiller} to fill this registry with, using no configuration.
         */
        public BlockItemRegistryFiller<Object> fill() {
            return new BlockItemRegistryFiller<>(blockManager, itemManager, () -> null);
        }

        /**
         * Obtain a {@link BlockItemRegistryFiller} to fill this registry with, using the specified configuration
         * supplier.
         */
        public <C> BlockItemRegistryFiller<C> fill(Supplier<C> config) {
            return new BlockItemRegistryFiller<>(blockManager, itemManager, config);
        }

        /**
         * Obtain a {@link BlockItemRegistryFiller} to fill this registry with, using the specified configuration.
         */
        public <C> BlockItemRegistryFiller<C> fill(C config) {
            return new BlockItemRegistryFiller<>(blockManager, itemManager, () -> config);
        }
    }
}
