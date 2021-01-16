/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2021 - 1 - 16
 */

package midnight.common.block;


import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;

public abstract class MnMaterials {
    public static final Material CRYSTAL_ROCK = new Builder(MaterialColor.PINK)
                                                    .build();
    public static final Material CRYSTAL = new Builder(MaterialColor.PINK)
                                               .notSolid()
                                               .notOpaque()
                                               .allowMovement()
                                               .pushDestroys()
                                               .build();
    public static final Material VIRILUX = new Builder(MaterialColor.LIME)
                                               .build();

    public static class Builder {
        private final MaterialColor color;
        private PushReaction pushReaction = PushReaction.NORMAL;
        private boolean blocksMovement = true;
        private boolean flammable = false;
        private boolean liquid = false;
        private boolean replaceable = false;
        private boolean solid = true;
        private boolean opaque = true;

        public Builder(MaterialColor color) {
            this.color = color;
        }

        public Builder liquid() {
            liquid = true;
            return this;
        }

        public Builder notSolid() {
            solid = false;
            return this;
        }

        public Builder allowMovement() {
            blocksMovement = false;
            return this;
        }

        public Builder notOpaque() {
            opaque = false;
            return this;
        }

        protected Builder flammable() {
            flammable = true;
            return this;
        }

        public Builder replaceable() {
            replaceable = true;
            return this;
        }

        public Builder pushDestroys() {
            pushReaction = PushReaction.DESTROY;
            return this;
        }

        public Builder pushBlocks() {
            pushReaction = PushReaction.BLOCK;
            return this;
        }

        public Material build() {
            return new Material(color, liquid, solid, blocksMovement, opaque, flammable, replaceable, pushReaction);
        }
    }
}
