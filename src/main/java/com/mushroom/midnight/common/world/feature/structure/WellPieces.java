package com.mushroom.midnight.common.world.feature.structure;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.MidnightStructurePieceType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;
import java.util.Random;

public class WellPieces {
    private static final BlockPos STRUCTURE_OFFSET = new BlockPos(5, 0, 5);
    private static final ResourceLocation well_resource = new ResourceLocation(Midnight.MODID, "well");

    public static void generateWell(TemplateManager p_204760_0_, BlockPos p_204760_1_, Rotation p_204760_2_, List<StructurePiece> p_204760_3_, Random p_204760_4_) {
        p_204760_3_.add(new WellPieces.Piece(p_204760_0_, p_204760_1_, p_204760_2_));
    }

    public static class Piece extends TemplateStructurePiece {
        private final Rotation rotation;

        public Piece(TemplateManager p_i48904_1_, BlockPos p_i48904_3_, Rotation p_i48904_4_) {
            super(MidnightStructurePieceType.WELL, 0);
            this.templatePosition = p_i48904_3_;
            this.rotation = p_i48904_4_;
            this.func_204754_a(p_i48904_1_);
        }

        public Piece(TemplateManager p_i50445_1_, CompoundNBT p_i50445_2_) {
            super(MidnightStructurePieceType.WELL, p_i50445_2_);
            this.rotation = Rotation.valueOf(p_i50445_2_.getString("Rot"));
            this.func_204754_a(p_i50445_1_);
        }

        /**
         * (abstract) Helper method to read subclass data from NBT
         */
        protected void readAdditional(CompoundNBT tagCompound) {
            super.readAdditional(tagCompound);
            tagCompound.putString("Rot", this.rotation.name());
        }

        private void func_204754_a(TemplateManager p_204754_1_) {
            Template template = p_204754_1_.getTemplateDefaulted(well_resource);
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE).setCenterOffset(WellPieces.STRUCTURE_OFFSET);
            this.setup(template, this.templatePosition, placementsettings);
        }


        /**
         * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
         * the end, it adds Fences...
         */
        public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_) {
            int j = 0;
            BlockPos blockpos = this.templatePosition.add(this.template.getSize().getX() - 1, 0, this.template.getSize().getZ() - 1);

            for (BlockPos blockpos1 : BlockPos.getAllInBoxMutable(this.templatePosition, blockpos)) {
                int k = worldIn.getHeight(Heightmap.Type.WORLD_SURFACE_WG, blockpos1.getX(), blockpos1.getZ());
                j += k;
            }

            this.templatePosition = new BlockPos(this.templatePosition.getX(), j - 10, this.templatePosition.getZ());
            return super.addComponentParts(worldIn, randomIn, structureBoundingBoxIn, p_74875_4_);
        }

        @Override
        protected void handleDataMarker(String function, BlockPos pos, IWorld worldIn, Random rand, MutableBoundingBox sbb) {

        }
    }
}