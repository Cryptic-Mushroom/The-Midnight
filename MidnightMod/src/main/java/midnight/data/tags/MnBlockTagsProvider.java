package midnight.data.tags;

import midnight.common.block.MnBlockTags;
import midnight.common.block.MnBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.Tags;

import java.nio.file.Path;

public class MnBlockTagsProvider extends TagsProvider<Block> {
    @SuppressWarnings("deprecation") // We need Registry.BLOCK. Sorry Forge...
    public MnBlockTagsProvider(DataGenerator gen) {
        super(gen, Registry.BLOCK);
    }

    @Override
    protected void registerTags() {
        getOrCreateTagBuilder(Tags.Blocks.DIRT).replace(false).add(
            MnBlocks.NIGHT_DIRT,
            MnBlocks.COARSE_NIGHT_DIRT,
            MnBlocks.DECEITFUL_MUD,
            MnBlocks.DECEITFUL_PEAT,
            MnBlocks.NIGHT_GRASS_BLOCK
        );
        getOrCreateTagBuilder(BlockTags.ENDERMAN_HOLDABLE).replace(false).add(
            MnBlocks.NIGHT_DIRT,
            MnBlocks.NIGHT_GRASS_BLOCK,
            MnBlocks.NIGHT_STONE,
            MnBlocks.DECEITFUL_PEAT,
            MnBlocks.DECEITFUL_MUD,
            MnBlocks.TRENCHSTONE,
            MnBlocks.STRANGE_SAND,
            MnBlocks.COARSE_NIGHT_DIRT,
            MnBlocks.NIGHTSHROOM,
            MnBlocks.NIGHTSHROOM_SHELF,
            MnBlocks.NIGHTSHROOM_ROOTS,
            MnBlocks.FLOWERING_NIGHTSHROOM_ROOTS,
            MnBlocks.DEWSHROOM,
            MnBlocks.DEWSHROOM_SHELF,
            MnBlocks.DEWSHROOM_ROOTS,
            MnBlocks.FLOWERING_DEWSHROOM_ROOTS,
            MnBlocks.GHOST_PLANT
        );
        getOrCreateTagBuilder(MnBlockTags.DARK_WILLOW_LOGS).replace(false).add(
            MnBlocks.DARK_WILLOW_LOG,
            MnBlocks.DARK_WILLOW_WOOD,
            MnBlocks.STRIPPED_DARK_WILLOW_LOG,
            MnBlocks.STRIPPED_DARK_WILLOW_WOOD
        );
        getOrCreateTagBuilder(MnBlockTags.SHADOWROOT_LOGS).replace(false).add(
            MnBlocks.SHADOWROOT_LOG,
            MnBlocks.SHADOWROOT_WOOD,
            MnBlocks.STRIPPED_SHADOWROOT_LOG,
            MnBlocks.STRIPPED_SHADOWROOT_WOOD
        );
        getOrCreateTagBuilder(MnBlockTags.DEAD_WOOD_LOGS).replace(false).add(
            MnBlocks.DEAD_WOOD_LOG,
            MnBlocks.DEAD_WOOD,
            MnBlocks.STRIPPED_DEAD_WOOD_LOG,
            MnBlocks.STRIPPED_DEAD_WOOD
        );
        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN)
            .replace(false)
            .addTag(MnBlockTags.DARK_WILLOW_LOGS)
            .addTag(MnBlockTags.SHADOWROOT_LOGS)
            .addTag(MnBlockTags.DEAD_WOOD_LOGS);
        getOrCreateTagBuilder(BlockTags.LEAVES).replace(false).add(
            MnBlocks.DARK_WILLOW_LEAVES,
            MnBlocks.SHADOWROOT_LEAVES
        );
        getOrCreateTagBuilder(BlockTags.PLANKS).replace(false).add(
            MnBlocks.DEAD_WOOD_PLANKS,
            MnBlocks.SHADOWROOT_PLANKS,
            MnBlocks.DARK_WILLOW_PLANKS,
            MnBlocks.NIGHTSHROOM_PLANKS,
            MnBlocks.DEWSHROOM_PLANKS
        );
    }

    protected ITag.Builder getBuilder(ITag.INamedTag<Block> namedTag) {
        return tagToBuilder.computeIfAbsent(namedTag.getId(), id -> new ITag.Builder());
    }

    @Override
    protected Path makePath(ResourceLocation id) {
        return generator.getOutputFolder().resolve("data/" + id.getNamespace() + "/tags/blocks/" + id.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "Midnight - Block tags";
    }
}
