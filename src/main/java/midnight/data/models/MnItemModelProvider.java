/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 7 - 7
 */

package midnight.data.models;

import midnight.MnInfo;
import midnight.common.block.MnBlocks;
import midnight.common.item.MnItems;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Function;

import static midnight.data.models.MnBlockStateProvider.*;

public class MnItemModelProvider extends ItemModelProvider {
    public MnItemModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    @Override
    public String getName() {
        return String.format("%s - Item Models", MnInfo.NAME);
    }

    @Override
    protected void registerModels() {
        this.inherit(MnBlocks.NIGHTSTONE);
        this.inherit(MnBlocks.NIGHT_BEDROCK);
        this.inherit(MnBlocks.TRENCHSTONE);

        this.inherit(MnBlocks.NIGHT_DIRT);
        this.inherit(MnBlocks.NIGHT_MYCELIUM);
        this.inherit(MnBlocks.NIGHT_GRASS_BLOCK);
        this.inherit(MnBlocks.COARSE_NIGHT_DIRT);
        this.inherit(MnBlocks.DECEITFUL_MUD);
        this.inherit(MnBlocks.DECEITFUL_PEAT);
        this.inherit(MnBlocks.STRANGE_SAND);

        this.inherit(MnBlocks.NIGHTSTONE_BRICKS);
        this.inherit(MnBlocks.TRENCHSTONE_BRICKS);
        this.inherit(MnBlocks.SHROOMBRICKS);

        this.generated(MnBlocks.NIGHT_GRASS);
        this.generated(MnBlocks.TALL_NIGHT_GRASS, block -> this.blockTexture(block, "upper"));

        this.generated(MnBlocks.GHOST_PLANT);
        this.inherit(MnBlocks.GHOST_PLANT_LEAF);
        this.inherit(MnBlocks.GHOST_PLANT_STEM);

        this.inherit(MnBlocks.DEAD_WOOD_LOG);
        this.inherit(MnBlocks.DEAD_WOOD);
        this.inherit(MnBlocks.STRIPPED_DEAD_WOOD_LOG);
        this.inherit(MnBlocks.STRIPPED_DEAD_WOOD);
        this.inherit(MnBlocks.DEAD_WOOD_PLANKS);
        this.generated(MnBlocks.DEAD_SAPLING);

        this.inherit(MnBlocks.SHADOWROOT_LOG);
        this.inherit(MnBlocks.SHADOWROOT_WOOD);
        this.inherit(MnBlocks.STRIPPED_SHADOWROOT_LOG);
        this.inherit(MnBlocks.STRIPPED_SHADOWROOT_WOOD);
        this.inherit(MnBlocks.SHADOWROOT_LEAVES);
        this.inherit(MnBlocks.SHADOWROOT_PLANKS);
        this.generated(MnBlocks.SHADOWROOT_SAPLING);
        this.inherit(MnBlocks.SHADOWROOT_BOOKSHELF);

        this.inherit(MnBlocks.DARK_WILLOW_LOG);
        this.inherit(MnBlocks.DARK_WILLOW_WOOD);
        this.inherit(MnBlocks.STRIPPED_DARK_WILLOW_LOG);
        this.inherit(MnBlocks.STRIPPED_DARK_WILLOW_WOOD);
        this.inherit(MnBlocks.DARK_WILLOW_LEAVES);
        this.generated(MnBlocks.HANGING_DARK_WILLOW_LEAVES, block -> this.blockTexture(block, "item"));
        this.inherit(MnBlocks.DARK_WILLOW_PLANKS);
        this.generated(MnBlocks.DARK_WILLOW_SAPLING);
        this.inherit(MnBlocks.DARK_WILLOW_BOOKSHELF);

        this.inherit(MnBlocks.NIGHTSHROOM_CAP);
        this.inherit(MnBlocks.NIGHTSHROOM_STEM);
        this.inherit(MnBlocks.NIGHTSHROOM_PLANKS);
        this.generated(MnBlocks.NIGHTSHROOM);
        this.generated(MnBlocks.NIGHTSHROOM_FIBRE);
        this.generated(MnBlocks.NIGHTSHROOM_SHELF);
        this.generated(MnBlocks.NIGHTSHROOM_ROOTS);
        this.generated(MnBlocks.FLOWERING_NIGHTSHROOM_ROOTS);
        this.generated(MnBlocks.TALL_NIGHTSHROOM, block -> this.blockTexture(block, "upper"));
        this.generated(MnItems.NIGHTSHROOM_POWDER);

        this.inherit(MnBlocks.DEWSHROOM_CAP);
        this.inherit(MnBlocks.DEWSHROOM_STEM);
        this.inherit(MnBlocks.DEWSHROOM_PLANKS);
        this.generated(MnBlocks.DEWSHROOM);
        this.generated(MnBlocks.DEWSHROOM_FIBRE);
        this.generated(MnBlocks.DEWSHROOM_SHELF);
        this.generated(MnBlocks.DEWSHROOM_ROOTS);
        this.generated(MnBlocks.FLOWERING_DEWSHROOM_ROOTS);
        this.generated(MnBlocks.TALL_DEWSHROOM, block -> this.blockTexture(block, "upper"));
        this.generated(MnItems.DEWSHROOM_POWDER);

        this.inherit(MnBlocks.VIRIDSHROOM_CAP);
        this.inherit(MnBlocks.VIRIDSHROOM_STEM);
        this.inherit(MnBlocks.VIRIDSHROOM_PLANKS);
        this.generated(MnBlocks.VIRIDSHROOM);
        this.generated(MnBlocks.VIRIDSHROOM_FIBRE);
        this.generated(MnBlocks.VIRIDSHROOM_SHELF);
        this.generated(MnBlocks.VIRIDSHROOM_ROOTS);
        this.generated(MnBlocks.FLOWERING_VIRIDSHROOM_ROOTS);
        this.generated(MnBlocks.TALL_VIRIDSHROOM, block -> this.blockTexture(block, "upper"));
        this.generated(MnItems.VIRIDSHROOM_POWDER);

        this.inherit(MnBlocks.MOONSHROOM_CAP);
        this.inherit(MnBlocks.MOONSHROOM_STEM);
        this.inherit(MnBlocks.MOONSHROOM_PLANKS);
        this.generated(MnBlocks.MOONSHROOM);
        this.generated(MnBlocks.MOONSHROOM_FIBRE);
        this.generated(MnBlocks.MOONSHROOM_SHELF);
        this.generated(MnBlocks.MOONSHROOM_ROOTS);
        this.generated(MnBlocks.FLOWERING_MOONSHROOM_ROOTS);
        this.generated(MnBlocks.TALL_MOONSHROOM, block -> this.blockTexture(block, "upper"));
        this.generated(MnItems.MOONSHROOM_POWDER);

        this.inherit(MnBlocks.BOGSHROOM_CAP);
        this.inherit(MnBlocks.BOGSHROOM_STEM);
        this.inherit(MnBlocks.BOGSHROOM_PLANKS);
        this.generated(MnBlocks.BOGSHROOM);
        this.generated(MnBlocks.BOGSHROOM_FIBRE);
        this.generated(MnBlocks.BOGSHROOM_SHELF);
        this.generated(MnBlocks.TALL_BOGSHROOM, block -> this.blockTexture(block, "upper"));
        this.generated(MnItems.BOGSHROOM_POWDER);

        this.generated(MnBlocks.MISTSHROOM);
        this.generated(MnBlocks.TALL_MISTSHROOM, block -> this.blockTexture(block, "upper"));
        this.generated(MnBlocks.BRISTLY_GRASS);
        this.generated(MnBlocks.LUMEN_BUD);
        this.generated(MnBlocks.TALL_LUMEN_BUD, block -> this.blockTexture(block, "upper"));
        this.generated(MnBlocks.RUNEBUSH);
        this.generated(MnBlocks.BOGWEED);
        this.generated(MnBlocks.CRYSTALOTUS, this::itemTexture);
        this.inherit(MnBlocks.SUAVIS, block -> this.blockTexture(block, "stage_3"));
        this.generated(MnItems.RAW_SUAVIS);
        this.generated(MnItems.COOKED_SUAVIS);
        this.generated(MnBlocks.VIOLEAF, this::itemTexture);
        this.generated(MnBlocks.TENDRILWEED);
        this.generated(MnBlocks.NIGHT_REED, this::itemTexture);
        this.generated(MnBlocks.DECEITFUL_MOSS);
        this.generated(MnBlocks.DECEITFUL_ALGAE);

        this.inherit(MnBlocks.GLOB_FUNGUS_CAP);
        this.inherit(MnBlocks.GLOB_FUNGUS_THATCH);
        this.inherit(MnBlocks.GLOB_FUNGUS_STEM);
        this.inherit(MnBlocks.INFESTED_GLOB_FUNGUS_STEM);
        this.inherit(MnBlocks.GLOB_FUNGUS_HYPHAE);
        this.generated(MnBlocks.GLOB_FUNGUS);
        this.generated(MnItems.GLOB_FUNGUS_HAND);

        this.inherit(MnBlocks.REED_THATCH);
        this.inherit(MnBlocks.CUT_REED_THATCH);

        this.inherit(MnBlocks.DARK_PEARL_ORE);
        this.inherit(MnBlocks.DARK_PEARL_BLOCK);
        this.generated(MnItems.DARK_PEARL);
        this.generated(MnItems.GEODE);

        this.inherit(MnBlocks.TENEBRUM_ORE);
        this.inherit(MnBlocks.TENEBRUM_BLOCK);
        this.generated(MnItems.TENEBRUM_INGOT);
        this.generated(MnItems.TENEBRUM_NUGGET);

        this.inherit(MnBlocks.NAGRILITE_ORE);
        this.inherit(MnBlocks.NAGRILITE_BLOCK);
        this.generated(MnItems.NAGRILITE_INGOT);
        this.generated(MnItems.NAGRILITE_NUGGET);

        this.inherit(MnBlocks.EBONITE_ORE);
        this.inherit(MnBlocks.EBONITE_BLOCK);
        this.generated(MnItems.EBONITE);

        this.inherit(MnBlocks.VIRILUX_ORE);
        this.inherit(MnBlocks.VIRILUX_BLOCK);
        this.generated(MnItems.VIRILUX);

        this.inherit(MnBlocks.ROCKSHROOM);
        this.generated(MnItems.ROCKSHROOM_CLUMP);

        this.inherit(MnBlocks.ROUXE_ROCK);
        this.inherit(MnBlocks.BLOOMCRYSTAL_ROCK);
        this.generated(MnBlocks.ROUXE);
        this.generated(MnBlocks.BLOOMCRYSTAL);
        this.generated(MnBlocks.CRYSTAL_FLOWER);

        this.generated(MnItems.DARK_STICK);

        this.inherit(MnBlocks.STRANGE_GLASS);
        this.generated(MnBlocks.STRANGE_GLASS_PANE, block -> this.blockTexture(block, null, "pane"));

        this.generated(MnItems.ARCHAIC_SHARD);
        this.inherit(MnBlocks.ARCHAIC_ORE);
        this.inherit(MnBlocks.ARCHAIC_GLASS);
        this.generated(MnBlocks.ARCHAIC_GLASS_PANE, block -> this.blockTexture(block, null, "pane"));
    }

    private ItemModelBuilder inherit(Block block) {
        return this.inherit(block, this::blockTexture);
    }

    private ItemModelBuilder inherit(Block block, Function<Block, ResourceLocation> texture) {
        return this.withExistingParent(this.name(block), texture.apply(block));
    }

    private ItemModelBuilder generated(Block block) {
        return this.generated(block, this::blockTexture);
    }

    private ItemModelBuilder generated(Block block, Function<Block, ResourceLocation> texture) {
        return this.generated(block, texture.apply(block));
    }

    private ItemModelBuilder generated(Item item) {
        return this.generated(item, this::itemTexture);
    }

    private ItemModelBuilder generated(Item item, Function<Item, ResourceLocation> texture) {
        return this.generated(item, texture.apply(item));
    }

    private <T extends ForgeRegistryEntry<T>> ItemModelBuilder generated(T entry, ResourceLocation... layers) {
        ItemModelBuilder builder = this.withExistingParent(this.name(entry), this.itemFolder("generated", false));

        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture(String.format("layer%d", i), layers[i]);
        }

        return builder;
    }

    /**
     * Gets the name of a {@link ForgeRegistryEntry} for use in data generation.
     *
     * @param entry The registry entry to get the name of.
     * @param <T>   The type of the registry entry.
     * @return The name of the {@link ForgeRegistryEntry} to be used.
     */
    private <T extends ForgeRegistryEntry<T>> String name(T entry) {
        return Objects.requireNonNull(entry.getRegistryName(), String.format("Registry name for %s was null!", entry.toString())).getPath();
    }

    /**
     * Gets the name of a {@link ForgeRegistryEntry} for use in data generation and appends a given {@link String} to
     * the end of it.
     *
     * @param entry  The registry entry to get the name of.
     * @param append The string to append to the end of the name.
     * @param <T>    The type of the registry entry.
     * @return The name of the {@link ForgeRegistryEntry} to be used.
     *
     * @see #name(T)
     */
    private <T extends ForgeRegistryEntry<T>> String name(T entry, @Nullable String append) {
        String name = this.name(entry);
        return append(name, append);
    }

    /**
     * Gets the name of a {@link ForgeRegistryEntry} for use in data generation, removed a given suffix, and appends a
     * given {@link String}.
     *
     * @param entry        The registry entry to get the name of.
     * @param append       The string to append to the end of the name.
     * @param removeSuffix The suffix to remove from the original block name.
     * @param <T>          The type of the registry entry.
     * @return The name of the {@link ForgeRegistryEntry} to be used.
     *
     * @see #name(T, String)
     * @see #name(T)
     */
    private <T extends ForgeRegistryEntry<T>> String name(T entry, @Nullable String append, String removeSuffix) {
        String name = this.name(entry);

        removeSuffix = "_" + removeSuffix;
        if (name.endsWith(removeSuffix)) {
            name = name.substring(0, name.length() - removeSuffix.length());
        }

        return append(name, append);
    }

    /**
     * Gets the {@link ResourceLocation} for a {@link Block} to use as a texture.
     *
     * @param block The block to get the {@link ResourceLocation} of.
     * @return The new {@link ResourceLocation} to be used as a texture reference.
     *
     * @see BlockStateProvider#blockTexture(Block)
     */
    private <T extends ForgeRegistryEntry<T>> ResourceLocation blockTexture(T block) {
        ResourceLocation name = Objects.requireNonNull(block.getRegistryName(), String.format("Registry name for %s was null!", block.toString()));
        return new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath());
    }

    /**
     * Gets the {@link ResourceLocation} for a {@link Block} to use as a texture and appends a given {@link String} to
     * the end of it.
     *
     * @param block  The block to get the {@link ResourceLocation} of.
     * @param append The string to append to the end of the new {@link ResourceLocation}.
     * @return The new {@link ResourceLocation} to be used as a texture reference.
     *
     * @see #blockTexture(T)
     */
    private <T extends ForgeRegistryEntry<T>> ResourceLocation blockTexture(T block, @Nullable String append) {
        return new ResourceLocation(append(this.blockTexture(block).toString(), append));
    }

    /**
     * Gets the {@link ResourceLocation} for a {@link Block} to use as a texture, removes a given suffix, and appends a
     * given {@link String}.
     *
     * @param block        The block to get the {@link ResourceLocation} of.
     * @param append       The string to append to the end of the new {@link ResourceLocation}.
     * @param removeSuffix The suffix to remove from the original {@link ResourceLocation} of the block.
     * @return The new {@link ResourceLocation} to be used as a texture reference.
     *
     * @see #blockTexture(T, String)
     * @see BlockStateProvider#blockTexture(Block)
     */
    private <T extends ForgeRegistryEntry<T>> ResourceLocation blockTexture(T block, @Nullable String append, String removeSuffix) {
        String texture = this.blockTexture(block).toString();

        removeSuffix = "_" + removeSuffix;
        if (texture.endsWith(removeSuffix)) {
            texture = texture.substring(0, texture.length() - removeSuffix.length());
        }

        return new ResourceLocation(append(texture, append));
    }

    /**
     * Gets the {@link ResourceLocation} for a {@link Item} to use as a texture.
     *
     * @param item The block to get the {@link ResourceLocation} of.
     * @return The new {@link ResourceLocation} to be used as a texture reference.
     */
    private <T extends ForgeRegistryEntry<T>> ResourceLocation itemTexture(T item) {
        ResourceLocation name = Objects.requireNonNull(item.getRegistryName(), String.format("Registry name for %s was null!", item.toString()));
        return new ResourceLocation(name.getNamespace(), ModelProvider.ITEM_FOLDER + "/" + name.getPath());
    }

    /**
     * Gets the {@link ResourceLocation} for a {@link Item} to use as a texture and appends a given {@link String} to
     * the end of it.
     *
     * @param item   The block to get the {@link ResourceLocation} of.
     * @param append The string to append to the end of the new {@link ResourceLocation}.
     * @return The new {@link ResourceLocation} to be used as a texture reference.
     *
     * @see #itemTexture(T)
     */
    private <T extends ForgeRegistryEntry<T>> ResourceLocation itemTexture(T item, @Nullable String append) {
        return new ResourceLocation(append(this.itemTexture(item).toString(), append));
    }

    /**
     * Gets the {@link ResourceLocation} for a {@link Item} to use as a texture, removes a given suffix, and appends a
     * given {@link String}.
     *
     * @param item         The block to get the {@link ResourceLocation} of.
     * @param append       The string to append to the end of the new {@link ResourceLocation}.
     * @param removeSuffix The suffix to remove from the original {@link ResourceLocation} of the block.
     * @return The new {@link ResourceLocation} to be used as a texture reference.
     *
     * @see #itemTexture(T, String)
     * @see #itemTexture(T)
     */
    private <T extends ForgeRegistryEntry<T>> ResourceLocation itemTexture(T item, @Nullable String append, String removeSuffix) {
        String texture = this.itemTexture(item).toString();

        removeSuffix = "_" + removeSuffix;
        if (texture.endsWith(removeSuffix)) {
            texture = texture.substring(0, texture.length() - removeSuffix.length());
        }

        return new ResourceLocation(append(texture, append));
    }

    private ResourceLocation blockFolder(String name, boolean mod) {
        Function<String, ResourceLocation> locator = mod ? this::modLoc : this::mcLoc;
        return locator.apply(String.format("%s/%s", BLOCK_FOLDER, name));
    }

    private ResourceLocation itemFolder(String name, boolean mod) {
        Function<String, ResourceLocation> locator = mod ? this::modLoc : this::mcLoc;
        return locator.apply(String.format("%s/%s", ITEM_FOLDER, name));
    }
}
