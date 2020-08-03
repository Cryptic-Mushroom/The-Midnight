package midnight.common.registry;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.*;

public class MidnightTags {
    public static class Blocks {
        public static final ITag.INamedTag<Block> CAN_HOLD_ORES = BlockTags.makeWrapperTag("can_hold_ores");
        public static final ITag.INamedTag<Block> FUNGI_STEMS = BlockTags.makeWrapperTag("fungi_stems");
        public static final ITag.INamedTag<Block> FUNGI_HATS = BlockTags.makeWrapperTag("fungi_hats");
        public static final ITag.INamedTag<Block> LOGS = BlockTags.makeWrapperTag("logs");
        public static final ITag.INamedTag<Block> BONEMEAL_GROUNDS = BlockTags.makeWrapperTag("bonemeal_grounds");
        public static final ITag.INamedTag<Block> PLANTABLE_GROUNDS = BlockTags.makeWrapperTag("plantable_grounds");
        public static final ITag.INamedTag<Block> BOGSHROOM = BlockTags.makeWrapperTag("bogshroom");
        public static final ITag.INamedTag<Block> DEWSHROOM = BlockTags.makeWrapperTag("dewshroom");
        public static final ITag.INamedTag<Block> NIGHTSHROOM = BlockTags.makeWrapperTag("nightshroom");
        public static final ITag.INamedTag<Block> VIRIDSHROOM = BlockTags.makeWrapperTag("viridshroom");
        public static final ITag.INamedTag<Block> PLANKS = BlockTags.makeWrapperTag("planks");

    }

    public static class Items {
        public static final ITag.INamedTag<Item> SPORE_BOMBS = ItemTags.makeWrapperTag("spore_bombs");
        public static final ITag.INamedTag<Item> UNSTABLE_FRUITS = ItemTags.makeWrapperTag("unstable_fruits");
        public static final ITag.INamedTag<Item> FUNGI_STEMS = ItemTags.makeWrapperTag("fungi_stems");
        public static final ITag.INamedTag<Item> LOGS = ItemTags.makeWrapperTag("logs");
        public static final ITag.INamedTag<Item> STICKS = ItemTags.makeWrapperTag("sticks");
        public static final ITag.INamedTag<Item> BOGSHROOM = ItemTags.makeWrapperTag("bogshroom");
        public static final ITag.INamedTag<Item> DEWSHROOM = ItemTags.makeWrapperTag("dewshroom");
        public static final ITag.INamedTag<Item> NIGHTSHROOM = ItemTags.makeWrapperTag("nightshroom");
        public static final ITag.INamedTag<Item> VIRIDSHROOM = ItemTags.makeWrapperTag("viridshroom");
        public static final ITag.INamedTag<Item> PLANKS = ItemTags.makeWrapperTag("planks");
    }

    public static class Fluids {
        public static final ITag.INamedTag<Fluid> MIASMA = FluidTags.makeWrapperTag("miasma");
        public static final ITag.INamedTag<Fluid> DARK_WATER = FluidTags.makeWrapperTag("dark_water");

    }

    public static class EntityTypes {
        public static final ITag.INamedTag<EntityType<?>> IGNORE_MUD = EntityTypeTags.func_232896_a_("ignore_mud");

    }
}
