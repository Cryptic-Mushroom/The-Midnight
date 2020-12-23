/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.core.util;

import com.google.common.collect.Maps;
import midnight.MnInfo;
import midnight.api.util.IItemCategory;
import midnight.api.util.IMidnightObjects;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.particles.ParticleType;
import net.minecraft.tags.ITag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

public class MnObjects implements IMidnightObjects {
    public static final MnObjects INSTANCE = new MnObjects();

    private static final Map<ResourceLocation, Material> MATERIALS = Maps.newHashMap();

    public static void addMaterial(String id, Material material) {
        MATERIALS.put(resLoc(id), material);
    }

    private static final Map<ResourceLocation, SoundType> SOUND_TYPES = Maps.newHashMap();

    public static void addSoundType(String id, SoundType soundType) {
        SOUND_TYPES.put(resLoc(id), soundType);
    }

    private static final Map<ResourceLocation, Food> FOODS = Maps.newHashMap();

    public static void addFood(String id, Food food) {
        FOODS.put(resLoc(id), food);
    }

    private static final Map<ResourceLocation, ItemGroup> ITEM_GROUPS = Maps.newHashMap();

    public static void addItemGroup(String id, ItemGroup group) {
        ITEM_GROUPS.put(resLoc(id), group);
    }

    private static final Map<ResourceLocation, IItemCategory> ITEM_CATEGORIES = Maps.newHashMap();

    public static void addItemCategory(String id, IItemCategory cat) { // Meow
        ITEM_CATEGORIES.put(resLoc(id), cat); // Meow
    }

    private static final Map<ResourceLocation, ITag.INamedTag<Block>> BLOCK_TAGS = Maps.newHashMap();

    public static void addBlockTag(String id, ITag.INamedTag<Block> tag) {
        BLOCK_TAGS.put(resLoc(id), tag);
    }

    private static final Map<ResourceLocation, ITag.INamedTag<Item>> ITEM_TAGS = Maps.newHashMap();

    public static void addItemTag(String id, ITag.INamedTag<Item> tag) {
        ITEM_TAGS.put(resLoc(id), tag);
    }

    private static final Map<ResourceLocation, ITag.INamedTag<Fluid>> FLUID_TAGS = Maps.newHashMap();

    public static void addFluidTag(String id, ITag.INamedTag<Fluid> tag) {
        FLUID_TAGS.put(resLoc(id), tag);
    }

    private static final Map<ResourceLocation, ITag.INamedTag<EntityType<?>>> ENTITY_TYPE_TAGS = Maps.newHashMap();

    public static void addEntityTypeTag(String id, ITag.INamedTag<EntityType<?>> tag) {
        ENTITY_TYPE_TAGS.put(resLoc(id), tag);
    }

    private static final Map<ResourceLocation, DamageSource> DAMAGE_SOURCES = Maps.newHashMap();

    public static void addDamageSource(String id, DamageSource src) {
        DAMAGE_SOURCES.put(resLoc(id), src);
    }

    private static final Map<ResourceLocation, LootParameterSet> LOOT_PARAMETER_SETS = Maps.newHashMap();

    public static void addLootParameterSet(String id, LootParameterSet set) {
        LOOT_PARAMETER_SETS.put(resLoc(id), set);
    }

    private static final Map<ResourceLocation, RegistryKey<Biome>> BIOMES = Maps.newHashMap();

    public static void addBiome(String id, RegistryKey<Biome> biome) {
        BIOMES.put(resLoc(id), biome);
    }

    private static final Map<ResourceLocation, DimensionType> DIMENSIONS = Maps.newHashMap();

    public static void addDimension(String id, DimensionType set) {
        DIMENSIONS.put(resLoc(id), set);
    }

    private MnObjects() {
    }

    @Override
    public Block block(String name) {
        return ForgeRegistries.BLOCKS.getValue(resLoc(name));
    }

    @Override
    public Material material(String name) {
        return MATERIALS.get(resLoc(name));
    }

    @Override
    public PlantType plantType(String name) {
        return PlantType.get(name);
    }

    @Override
    public SoundType soundType(String name) {
        return SOUND_TYPES.get(resLoc(name));
    }

    @Override
    public Item item(String name) {
        return ForgeRegistries.ITEMS.getValue(resLoc(name));
    }

    @Override
    public Food food(String name) {
        return FOODS.get(resLoc(name));
    }

    @Override
    public ItemGroup itemGroup(String name) {
        return ITEM_GROUPS.get(resLoc(name));
    }

    @Override
    public IItemCategory itemCategory(String name) {
        return ITEM_CATEGORIES.get(resLoc(name));
    }

    @Override
    public Fluid fluid(String name) {
        return ForgeRegistries.FLUIDS.getValue(resLoc(name));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Entity> EntityType<T> entityType(String name) {
        return (EntityType<T>) ForgeRegistries.ENTITIES.getValue(resLoc(name));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends TileEntity> TileEntityType<T> tileEntityType(String name) {
        return (TileEntityType<T>) ForgeRegistries.TILE_ENTITIES.getValue(resLoc(name));
    }

    @Override
    public ITag.INamedTag<Block> blockTag(String name) {
        return BLOCK_TAGS.get(resLoc(name));
    }

    @Override
    public ITag.INamedTag<Item> itemTag(String name) {
        return ITEM_TAGS.get(resLoc(name));
    }

    @Override
    public ITag.INamedTag<Fluid> fluidTag(String name) {
        return FLUID_TAGS.get(resLoc(name));
    }

    @Override
    public ITag.INamedTag<EntityType<?>> entityTypeTag(String name) {
        return ENTITY_TYPE_TAGS.get(resLoc(name));
    }

    @Override
    public DamageSource damageSource(String name) {
        return DAMAGE_SOURCES.get(resLoc(name));
    }

    @Override
    public ResourceLocation lootTable(String name) {
        return resLoc(name);
    }

    @Override
    public LootParameterSet lootParameterSet(String name) {
        return LOOT_PARAMETER_SETS.get(resLoc(name));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ParticleType<?>> T particleType(String name) {
        return (T) ForgeRegistries.PARTICLE_TYPES.getValue(resLoc(name));
    }

    @Override
    public SoundEvent soundEvent(String name) {
        return ForgeRegistries.SOUND_EVENTS.getValue(resLoc(name));
    }

    @Override
    public RegistryKey<Biome> biome(String name) {
        return BIOMES.get(resLoc(name));
    }

    @Override
    public DimensionType dimensionType(String name) {
        return DIMENSIONS.get(resLoc(name));
    }

    private static ResourceLocation resLoc(String path) {
        int colon = path.indexOf(':');
        if (colon >= 0) {
            return new ResourceLocation(path.substring(0, colon), path.substring(colon + 1));
        }
        return new ResourceLocation(MnInfo.MODID, path);
    }
}
