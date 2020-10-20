/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 20
 */

package midnight.data.models.modelgen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;

/**
 * Generates an extruded square as model, with a side, bottom and top texture. The generated model has one element in
 * the bottom center of the cube space, which has a square base and extrudes upwards.
 * <pre>
 * * - - - - - - - - - - *
 * ^                     |
 * y      CUBE SPACE
 * |                     |
 *     *-------------*
 * |   |             |   |
 *     |             |
 * |   |     CUBE    |   |
 *     |             |
 * |   |             |   |
 * * - *-------------* x>*
 * </pre>
 */
public class ExtrudedSquareModelGen implements IModelGen {
    private final ResourceLocation top;
    private final ResourceLocation side;
    private final ResourceLocation bottom;

    private boolean ao = true;
    private boolean cull = true;
    private double size = 16;
    private double height = 16;
    private int tint = -1;

    private double upU = 0;
    private double upV = 0;
    private double downU = 0;
    private double downV = 0;
    private double sideU = 0;
    private double sideV = 0;

    private ExtrudedSquareModelGen(ResourceLocation top, ResourceLocation side, ResourceLocation bottom) {
        this.top = top;
        this.side = side;
        this.bottom = bottom;
    }

    public ExtrudedSquareModelGen topUVOffset(double u, double v) {
        upU = u;
        upV = v;
        return this;
    }

    public ExtrudedSquareModelGen bottomUVOffset(double u, double v) {
        downU = u;
        downV = v;
        return this;
    }

    public ExtrudedSquareModelGen sideUVOffset(double u, double v) {
        sideU = u;
        sideV = v;
        return this;
    }

    public ExtrudedSquareModelGen noAO() {
        ao = false;
        return this;
    }

    public ExtrudedSquareModelGen noCull() {
        cull = false;
        return this;
    }

    public ExtrudedSquareModelGen tintindex(int tint) {
        this.tint = tint;
        return this;
    }

    public ExtrudedSquareModelGen size(double size, double height) {
        this.size = size;
        this.height = height;
        return this;
    }

    @Override
    public JsonElement makeJson(ResourceLocation name) {
        JsonObject root = new JsonObject();

        // Orient correctly in GUI, use block/block as parent
        root.addProperty("parent", "block/block");

        // Ambient occlusion, only set if we want no ambient occlusion
        if (!ao) root.addProperty("ambientocclusion", false);

        // Add textures
        JsonObject textures = new JsonObject();
        textures.addProperty("top", top.toString());
        textures.addProperty("side", side.toString());
        textures.addProperty("bottom", bottom.toString());
        textures.addProperty("particle", side.toString());
        root.add("textures", textures);

        // Compute base coordinates
        double rad = size / 2;
        double c0 = 8 - rad;
        double c1 = 8 + rad;

        JsonObject cube = new JsonObject();

        // Cube size and coordinates
        cube.add("from", array(c0, 0, c0));
        cube.add("to", array(c1, height, c1));

        // Add faces and UVs
        JsonObject faces = new JsonObject();
        // Intellij removes extra spaces during reformatting, disable formatter to keep this arranged
        // @formatter:off
        //                           --- u1 ---   --- v1 ---   --- u2 ---   --- v2 ---
        faces.add("down", face(array(c0 + downU,  c0 + downV,  c1 + downU,  c1 + downV),  "#bottom", Direction.DOWN, true));
        faces.add("up"  , face(array(c0 + upU  ,  c0 + upV  ,  c1 + upU  ,  c1 + upV  ),  "#top"   , Direction.UP  , height == 16));
        //                            --- u1 ---   ------- v1 --------   --- u2 ---   --- v2 ---
        faces.add("north", face(array(c0 + sideU,  16 - height + sideV,  c1 + sideU,  16 + sideV),  "#side", Direction.NORTH, size == 16));
        faces.add("east" , face(array(c0 + sideU,  16 - height + sideV,  c1 + sideU,  16 + sideV),  "#side", Direction.EAST , size == 16));
        faces.add("south", face(array(c0 + sideU,  16 - height + sideV,  c1 + sideU,  16 + sideV),  "#side", Direction.SOUTH, size == 16));
        faces.add("west" , face(array(c0 + sideU,  16 - height + sideV,  c1 + sideU,  16 + sideV),  "#side", Direction.WEST , size == 16));
        // @formatter:on
        cube.add("faces", faces);

        JsonArray elements = new JsonArray();
        elements.add(cube);
        root.add("elements", elements);

        return root;
    }

    private JsonObject face(JsonArray uv, String texture, Direction cullface, boolean wantCull) {
        JsonObject face = new JsonObject();
        face.add("uv", uv);
        face.addProperty("texture", texture);

        // Add face culling only if needed (wantCull is true when the face hits the exact side of the full block)
        if (wantCull && cull && cullface != null) {
            face.addProperty("cullface", cullface.getName2());
        }

        // Add tintindex if wanted
        if (tint > -1) {
            face.addProperty("tintindex", tint);
        }
        return face;
    }

    private static JsonArray array(double... elems) {
        JsonArray array = new JsonArray();
        for (double el : elems)
            array.add(el);
        return array;
    }

    public static ExtrudedSquareModelGen extruded(ResourceLocation allSides) {
        return new ExtrudedSquareModelGen(allSides, allSides, allSides);
    }

    public static ExtrudedSquareModelGen extruded(ResourceLocation side, ResourceLocation end) {
        return new ExtrudedSquareModelGen(end, side, end);
    }

    public static ExtrudedSquareModelGen extruded(ResourceLocation side, ResourceLocation top, ResourceLocation bottom) {
        return new ExtrudedSquareModelGen(top, side, bottom);
    }

    public static ExtrudedSquareModelGen extruded(String allSides) {
        return new ExtrudedSquareModelGen(
            new ResourceLocation(allSides),
            new ResourceLocation(allSides),
            new ResourceLocation(allSides)
        );
    }

    public static ExtrudedSquareModelGen extruded(String side, String end) {
        return new ExtrudedSquareModelGen(
            new ResourceLocation(end),
            new ResourceLocation(side),
            new ResourceLocation(end)
        );
    }

    public static ExtrudedSquareModelGen extruded(String side, String top, String bottom) {
        return new ExtrudedSquareModelGen(
            new ResourceLocation(top),
            new ResourceLocation(side),
            new ResourceLocation(bottom)
        );
    }
}
