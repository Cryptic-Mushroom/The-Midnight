/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 21
 */

package midnight.data.models.modelgen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class InheritingModelGen implements IModelGen {
    private final ResourceLocation parent;
    private final List<Pair<String, String>> textureRef = new ArrayList<>();

    public InheritingModelGen(ResourceLocation parent) {
        this.parent = parent;
    }

    public InheritingModelGen(String parent) {
        this.parent = new ResourceLocation(parent);
    }

    public InheritingModelGen texture(String reference, String newReference) {
        textureRef.add(Pair.of(reference, newReference));
        return this;
    }

    public InheritingModelGen texture(String reference, ResourceLocation id) {
        textureRef.add(Pair.of(reference, id.toString()));
        return this;
    }

    @Override
    public JsonElement makeJson(ResourceLocation name) {
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
     * Inherits the specified parent model in a child model.
     * <pre>
     * {
     *     "parent": "..."
     * }
     * </pre>
     *
     * @param parent The parent model name
     */
    public static InheritingModelGen inherit(String parent) {
        return new InheritingModelGen(parent);
    }

    /**
     * Generates a full block with the same texture on all sides
     * <pre>
     * {
     *     "parent": "block/cube_all",
     *     "textures": {
     *         "all": "..."
     *     }
     * }
     * </pre>
     *
     * @param texture The texture
     */
    public static InheritingModelGen cubeAll(String texture) {
        return new InheritingModelGen("block/cube_all")
                   .texture("all", texture);
    }

    /**
     * Generates a full block with the same texture on all sides, allowing for foliage coloring in i.e. leaves
     * <pre>
     * {
     *     "parent": "block/leaves",
     *     "textures": {
     *         "all": "..."
     *     }
     * }
     * </pre>
     *
     * @param texture The texture
     */
    public static InheritingModelGen leaves(String texture) {
        return new InheritingModelGen("block/leaves")
                   .texture("all", texture);
    }

    /**
     * Generates a full block with the same texture on all sides, but mirroring the texture
     * <pre>
     * {
     *     "parent": "block/cube_mirrored_all",
     *     "textures": {
     *         "all": "..."
     *     }
     * }
     * </pre>
     *
     * @param texture The texture
     */
    public static InheritingModelGen cubeMirroredAll(String texture) {
        return new InheritingModelGen("block/cube_mirrored_all")
                   .texture("all", texture);
    }

    /**
     * Generates a full block with a texture on top and bottom (end), and another texture on other sides (side).
     * <pre>
     * {
     *     "parent": "block/cube_column",
     *     "textures": {
     *         "end": "...",
     *         "side": "..."
     *     }
     * }
     * </pre>
     *
     * @param end  The end texture
     * @param side The side texture
     */
    public static InheritingModelGen cubeColumn(String end, String side) {
        return new InheritingModelGen("block/cube_column")
                   .texture("end", end)
                   .texture("side", side);
    }

    /**
     * Generates a full block with a texture on north and south (end), and another texture on other sides (side).
     * <pre>
     * {
     *     "parent": "block/cube_column_horizontal",
     *     "textures": {
     *         "end": "...",
     *         "side": "..."
     *     }
     * }
     * </pre>
     *
     * @param end  The end texture
     * @param side The side texture
     */
    public static InheritingModelGen cubeColumnHoriz(String end, String side) {
        return new InheritingModelGen("block/cube_column_horizontal")
                   .texture("end", end)
                   .texture("side", side);
    }

    /**
     * Generates a full block with a texture on top (top), one on bottom (bottom), and a texture on other sides (side).
     * <pre>
     * {
     *     "parent": "block/cube_bottom_top",
     *     "textures": {
     *         "bottom": "...",
     *         "top": "...",
     *         "side": "..."
     *     }
     * }
     * </pre>
     *
     * @param bottom The bottom texture
     * @param top    The top texture
     * @param side   The side texture
     */
    public static InheritingModelGen cubeBottomTop(String bottom, String top, String side) {
        return new InheritingModelGen("block/cube_bottom_top")
                   .texture("bottom", bottom)
                   .texture("top", top)
                   .texture("side", side);
    }

    /**
     * Generates a full block with a separate texture on each side.
     * <pre>
     * {
     *     "parent": "block/cube",
     *     "textures": {
     *         "north": "...",
     *         "east": "...",
     *         "south": "...",
     *         "west": "...",
     *         "up": "...",
     *         "down": "..."
     *     }
     * }
     * </pre>
     *
     * @param north The north texture
     * @param east  The east texture
     * @param south The south texture
     * @param west  The west texture
     * @param up    The up texture
     * @param down  The down texture
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
     * Generates a full block with a separate texture on each side, mirroring the textures.
     * <pre>
     * {
     *     "parent": "block/cube_mirrored",
     *     "textures": {
     *         "north": "...",
     *         "east": "...",
     *         "south": "...",
     *         "west": "...",
     *         "up": "...",
     *         "down": "..."
     *     }
     * }
     * </pre>
     *
     * @param north The north texture
     * @param east  The east texture
     * @param south The south texture
     * @param west  The west texture
     * @param up    The up texture
     * @param down  The down texture
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
     * Generates a full block with a texture on the north side, texture on horizontal sides, and separate textures on
     * top and bottom.
     * <pre>
     * {
     *     "parent": "block/cube",
     *     "textures": {
     *         "north": "{front}",
     *         "east": "{side}",
     *         "south": "{side}",
     *         "west": "{side}",
     *         "up": "{top}",
     *         "down": "{bottom}"
     *     }
     * }
     * </pre>
     *
     * @param front  The front (north) texture
     * @param side   The side (east, south, west) texture
     * @param top    The top (up) texture
     * @param bottom The bottom (down) texture
     */
    public static InheritingModelGen cubeFrontSided(String front, String side, String top, String bottom) {
        return new InheritingModelGen("block/cube")
                   .texture("particle", side)
                   .texture("north", front)
                   .texture("east", side)
                   .texture("south", side)
                   .texture("west", side)
                   .texture("up", top)
                   .texture("down", bottom);
    }

    /**
     * Generates a full block with a texture on the north side, a texture on the south side, a shared texture on east
     * and west side, and separate textures on top and bottom.
     * <pre>
     * {
     *     "parent": "block/cube",
     *     "textures": {
     *         "north": "{front}",
     *         "east": "{side}",
     *         "south": "{back}",
     *         "west": "{side}",
     *         "up": "{top}",
     *         "down": "{bottom}"
     *     }
     * }
     * </pre>
     *
     * @param front  The front (north) texture
     * @param back   The back (south) texture
     * @param side   The side (east, west) texture
     * @param top    The top (up) texture
     * @param bottom The bottom (down) texture
     */
    public static InheritingModelGen cubeFrontBackSided(String front, String back, String side, String top, String bottom) {
        return new InheritingModelGen("block/cube")
                   .texture("particle", side)
                   .texture("north", front)
                   .texture("east", side)
                   .texture("south", back)
                   .texture("west", side)
                   .texture("up", top)
                   .texture("down", bottom);
    }

    public static InheritingModelGen grassBlock(String top, String side, String bottom, String overlay) {
        return new InheritingModelGen("midnight:block/grass_block")
                   .texture("top", top)
                   .texture("bottom", bottom)
                   .texture("side", side)
                   .texture("overlay", overlay);
    }

    public static InheritingModelGen hangingLeavesRoot(String inner, String outer) {
        return new InheritingModelGen("midnight:block/hanging_leaves")
                   .texture("inner", inner)
                   .texture("outer", outer);
    }

    public static InheritingModelGen hangingLeavesEnd(String inner, String end) {
        return new InheritingModelGen("midnight:block/hanging_leaves_end")
                   .texture("inner", inner)
                   .texture("end", end);
    }

    public static InheritingModelGen generated(String... layers) {
        InheritingModelGen gen = new InheritingModelGen("item/generated");
        for (int i = 0, l = layers.length; i < l; i++) {
            gen.texture("layer" + i, layers[i]);
        }
        return gen;
    }

    public static InheritingModelGen cross(String texture) {
        return new InheritingModelGen("block/cross")
                   .texture("cross", texture);
    }

    public static InheritingModelGen tintedCross(String texture) {
        return new InheritingModelGen("block/tinted_cross")
                   .texture("cross", texture);
    }

    public static InheritingModelGen fan(String texture) {
        return new InheritingModelGen("midnight:block/fan")
                   .texture("fan", texture);
    }

    public static InheritingModelGen shelf(String texture) {
        return new InheritingModelGen("midnight:block/shelf")
                   .texture("shelf", texture);
    }

    public static InheritingModelGen layeredPlant(String layer1) {
        return new InheritingModelGen("midnight:block/layered_plant_1")
                   .texture("layer1", layer1);
    }

    public static InheritingModelGen layeredPlant(String layer1, String layer2) {
        return new InheritingModelGen("midnight:block/layered_plant_2")
                   .texture("layer1", layer1)
                   .texture("layer2", layer2);
    }

    public static InheritingModelGen layeredPlant(String layer1, String layer2, String layer3) {
        return new InheritingModelGen("midnight:block/layered_plant_3")
                   .texture("layer1", layer1)
                   .texture("layer2", layer2)
                   .texture("layer3", layer3);
    }

    public static InheritingModelGen crystalotus(String leaf, String core) {
        return new InheritingModelGen("midnight:block/crystalotus_base")
                   .texture("leaf", leaf)
                   .texture("core", core);
    }

    public static InheritingModelGen flatPlant(String plant) {
        return new InheritingModelGen("midnight:block/flat_plant")
                   .texture("plant", plant);
    }

    public static InheritingModelGen moss(String texture) {
        return new InheritingModelGen("midnight:block/moss")
                   .texture("texture", texture);
    }
}
