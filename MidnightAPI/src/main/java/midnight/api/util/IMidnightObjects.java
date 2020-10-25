/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 25
 */

package midnight.api.util;

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

/**
 * Provides access to objects of the Midnight, such as blocks, items, but also sound types, foods, and block materials.
 * One can obtain a certain object by calling the applicable method of this interface with the ID of the preferred
 * object. I.e. if one wants to get the tenebrum block, one can use:
 * <pre>
 * IMidnight.get().getObjects().block("tenebrum_block");
 * </pre>
 *
 * @author Shadew
 * @since 0.6.0
 */
public interface IMidnightObjects {
    Block block(String name);
    Material material(String name);
    PlantType plantType(String name);
    SoundType soundType(String name);

    Item item(String name);
    Food food(String name);
    ItemGroup itemGroup(String name);
    IItemCategory itemCategory(String name);

    Fluid fluid(String name);

    <T extends Entity> EntityType<T> entityType(String name);

    <T extends TileEntity> TileEntityType<T> tileEntityType(String name);

    ITag.INamedTag<Block> blockTag(String name);
    ITag.INamedTag<Item> itemTag(String name);
    ITag.INamedTag<Fluid> fluidTag(String name);
    ITag.INamedTag<EntityType<?>> entityTypeTag(String name);

    DamageSource damageSource(String name);

    ResourceLocation lootTable(String name);
    LootParameterSet lootParameterSet(String name);

    <T extends ParticleType<?>> T particleType(String name);

    SoundEvent soundEvent(String name);

    RegistryKey<Biome> biome(String name);

    DimensionType dimensionType(String name);
}
