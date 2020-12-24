package midnight.data.tags;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.server.AbstractTagProvider;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.nio.file.Path;

public class MnBlockTagsProvider extends AbstractTagProvider<Block> {
    public MnBlockTagsProvider(DataGenerator gen) {
        super(gen, Registry.BLOCK);
    }

    @Override
    protected void configure() {
        //
        //
        //  REGISTER BLOCK TAGS HERE
        //
        //
    }

    protected Tag.Builder getTagBuilder(Tag.Identified<Block> identified) {
        return super.method_27169(identified);
    }

    @Override
    protected Path getOutput(Identifier id) {
        return root.getOutput().resolve("data/" + id.getNamespace() + "/tags/blocks/" + id.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "Midnight/BlockTags";
    }
}
