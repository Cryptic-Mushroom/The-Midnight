/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 24
 */

package midnight.data.models.modelgen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

/**
 * A basic model generator that inherits from another model, which is often either a vanilla model or a non-generated
 * model resource of the Midnight.
 */
public class InheritingModelGen implements ModelGen {
    private final Identifier parent;
    private final List<Pair<String, String>> textureRef = new ArrayList<>();

    /**
     * Creates an inheriting model generator
     *
     * @param parent The parent model
     */
    public InheritingModelGen(Identifier parent) {
        this.parent = parent;
    }

    /**
     * Creates an inheriting model generator
     *
     * @param parent The parent model
     */
    public InheritingModelGen(String parent) {
        this.parent = new Identifier(parent);
    }

    /**
     * Adds a texture override
     *
     * @param reference    The texture name in the parent model
     * @param newReference A texture, or a redirect to another texture starting with a '#'
     */
    public InheritingModelGen texture(String reference, String newReference) {
        textureRef.add(Pair.of(reference, newReference));
        return this;
    }

    /**
     * Adds a texture override
     *
     * @param reference The texture name in the parent model
     * @param id        A texture ID
     */
    public InheritingModelGen texture(String reference, Identifier id) {
        textureRef.add(Pair.of(reference, id.toString()));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JsonElement makeJson(Identifier name) {
        JsonObject root = new JsonObject();
        root.addProperty("parent", parent.toString().replace("{{name}}", name.toString()));
        if (!textureRef.isEmpty()) {
            JsonObject textures = new JsonObject();
            for (Pair<String, String> ref : textureRef) {
                textures.addProperty(
                    ref.getFirst().replace("{{name}}", name.toString()),
                    ref.getSecond().replace("{{name}}", name.toString())
                );
            }
            root.add("textures", textures);
        }
        return root;
    }

    /**
     * Creates a raw inheriting model generator with the given parent model
     */
    public static InheritingModelGen inherit(String parent) {
        return new InheritingModelGen(parent);
    }

    /**
     * Creates a {@code block/cube_all} model with the given texture
     */
    public static InheritingModelGen cubeAll(String texture) {
        return new InheritingModelGen("block/cube_all")
                   .texture("all", texture);
    }

    /**
     * Creates a {@code block/slab} model with the given texture
     */
    public static InheritingModelGen slab(String texture) {
        return new InheritingModelGen("block/slab")
                   .texture("bottom", texture)
                   .texture("top", texture)
                   .texture("side", texture);
    }

    /**
     * Creates a {@code block/slab_top} model with the given texture
     */
    public static InheritingModelGen slabTop(String texture) {
        return new InheritingModelGen("block/slab_top")
                   .texture("bottom", texture)
                   .texture("top", texture)
                   .texture("side", texture);
    }

    /**
     * Creates a {@code block/stairs} model with the given texture
     */
    public static InheritingModelGen stairs(String texture) {
        return new InheritingModelGen("block/stairs")
                   .texture("bottom", texture)
                   .texture("top", texture)
                   .texture("side", texture);
    }

    /**
     * Creates a {@code block/inner_stairs} model with the given texture
     */
    public static InheritingModelGen stairsInner(String texture) {
        return new InheritingModelGen("block/inner_stairs")
                   .texture("bottom", texture)
                   .texture("top", texture)
                   .texture("side", texture);
    }

    /**
     * Creates a {@code block/outer_stairs} model with the given texture
     */
    public static InheritingModelGen stairsOuter(String texture) {
        return new InheritingModelGen("block/outer_stairs")
                   .texture("bottom", texture)
                   .texture("top", texture)
                   .texture("side", texture);
    }

    /**
     * Creates a {@code block/cube_mirrored_all} model with the given texture
     */
    public static InheritingModelGen cubeMirroredAll(String texture) {
        return new InheritingModelGen("block/cube_mirrored_all")
                   .texture("all", texture);
    }

    /**
     * Creates a {@code block/cube_column} model with the given textures
     */
    public static InheritingModelGen cubeColumn(String end, String side) {
        return new InheritingModelGen("block/cube_column")
                   .texture("end", end)
                   .texture("side", side);
    }

    /**
     * Creates a {@code block/cube_column_horizontal} model with the given textures
     */
    public static InheritingModelGen cubeColumnHoriz(String end, String side) {
        return new InheritingModelGen("block/cube_column_horizontal")
                   .texture("end", end)
                   .texture("side", side);
    }

    /**
     * Creates a {@code block/cube_bottom_top} model with the given textures
     */
    public static InheritingModelGen cubeBottomTop(String bottom, String top, String side) {
        return new InheritingModelGen("block/cube_bottom_top")
                   .texture("bottom", bottom)
                   .texture("top", top)
                   .texture("side", side);
    }

    /**
     * Creates a {@code block/cube} model with the given textures
     */
    public static InheritingModelGen cube(String north, String east, String south, String west, String up, String down) {
        return new InheritingModelGen("block/cube")
                   .texture("north", north)
                   .texture("east", east)
                   .texture("south", south)
                   .texture("west", west)
                   .texture("up", up)
                   .texture("down", down);
    }

    /**
     * Creates a {@code block/cube_mirrored} model with the given textures
     */
    public static InheritingModelGen cubeMirrored(String north, String east, String south, String west, String up, String down) {
        return new InheritingModelGen("block/cube_mirrored")
                   .texture("north", north)
                   .texture("east", east)
                   .texture("south", south)
                   .texture("west", west)
                   .texture("up", up)
                   .texture("down", down);
    }

    /**
     * Creates a {@code block/cube} model with the textures:
     * <ul>
     * <li>{@code north: front}</li>
     * <li>{@code east: side}</li>
     * <li>{@code south: side}</li>
     * <li>{@code west: side}</li>
     * <li>{@code up: top}</li>
     * <li>{@code down: bottom}</li>
     * </ul>
     */
    public static InheritingModelGen cubeFrontSided(String front, String side, String top, String bottom) {
        return new InheritingModelGen("block/cube")
                   .texture("north", front)
                   .texture("east", side)
                   .texture("south", side)
                   .texture("west", side)
                   .texture("up", top)
                   .texture("down", bottom);
    }

    /**
     * Creates a {@code block/cube} model with the textures:
     * <ul>
     * <li>{@code north: front}</li>
     * <li>{@code east: side}</li>
     * <li>{@code south: back}</li>
     * <li>{@code west: side}</li>
     * <li>{@code up: top}</li>
     * <li>{@code down: bottom}</li>
     * </ul>
     */
    public static InheritingModelGen cubeFrontBackSided(String front, String back, String side, String top, String bottom) {
        return new InheritingModelGen("block/cube")
                   .texture("north", front)
                   .texture("east", side)
                   .texture("south", back)
                   .texture("west", side)
                   .texture("up", top)
                   .texture("down", bottom);
    }

    /**
     * Creates a {@code midnight:block/grass_block} model with the given textures
     */
    public static InheritingModelGen grassBlock(String top, String side, String bottom, String overlay) {
        return new InheritingModelGen("midnight:block/grass_block")
                   .texture("top", top)
                   .texture("bottom", bottom)
                   .texture("side", side)
                   .texture("overlay", overlay);
    }

    /**
     * Creates a {@code item/generated} model with the given textures as {@code layer0}, {@code layer1}, ...
     */
    public static InheritingModelGen generated(String... layers) {
        InheritingModelGen gen = new InheritingModelGen("item/generated");
        for (int i = 0, l = layers.length; i < l; i++) {
            gen.texture("layer" + i, layers[i]);
        }
        return gen;
    }

    /**
     * Creates a {@code block/cross} model with the given texture
     */
    public static InheritingModelGen cross(String texture) {
        return new InheritingModelGen("block/cross")
                   .texture("cross", texture);
    }

    /**
     * Creates a {@code block/tinted_cross} model with the given texture
     */
    public static InheritingModelGen tintedCross(String texture) {
        return new InheritingModelGen("block/tinted_cross")
                   .texture("cross", texture);
    }

    /**
     * Creates a {@code block/fence_side} model with the given texture
     */
    public static InheritingModelGen fenceSide(String texture) {
        return new InheritingModelGen("block/fence_side")
                   .texture("texture", texture);
    }

    /**
     * Creates a {@code block/fence_post} model with the given texture
     */
    public static InheritingModelGen fencePost(String texture) {
        return new InheritingModelGen("block/fence_post")
                   .texture("texture", texture);
    }

    /**
     * Creates a {@code block/fence_inventory} model with the given texture
     */
    public static InheritingModelGen fenceInventory(String texture) {
        return new InheritingModelGen("block/fence_inventory")
                   .texture("texture", texture);
    }

    /**
     * Creates a {@code block/template_wall_side} model with the given texture
     */
    public static InheritingModelGen wallSide(String texture) {
        return new InheritingModelGen("block/template_wall_side")
                   .texture("wall", texture);
    }

    /**
     * Creates a {@code block/template_wall_side_tall} model with the given texture
     */
    public static InheritingModelGen wallSideTall(String texture) {
        return new InheritingModelGen("block/template_wall_side_tall")
                   .texture("wall", texture);
    }

    /**
     * Creates a {@code block/template_wall_post} model with the given texture
     */
    public static InheritingModelGen wallPost(String texture) {
        return new InheritingModelGen("block/template_wall_post")
                   .texture("wall", texture);
    }

    /**
     * Creates a {@code block/template_wall_inventory} model with the given texture
     */
    public static InheritingModelGen wallInventory(String texture) {
        return new InheritingModelGen("block/wall_inventory")
                   .texture("wall", texture);
    }
}
