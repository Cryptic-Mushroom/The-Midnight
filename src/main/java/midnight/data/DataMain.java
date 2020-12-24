package midnight.data;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import midnight.data.loottables.MnLootTablesProvider;
import midnight.data.models.MnStateModelProvider;
import midnight.data.recipes.MnRecipeProvider;
import midnight.data.tags.MnBlockTagsProvider;
import midnight.data.tags.MnFluidTagsProvider;
import midnight.data.tags.MnItemTagsProvider;
import net.minecraft.data.DataGenerator;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;

public class DataMain {
    public static void main(String[] strings) throws IOException {
        OptionParser opts = new OptionParser();

        OptionSpec<Void> help = opts.accepts("help", "Show the help menu").forHelp();
        OptionSpec<Void> server = opts.accepts("server", "Include server generators");
        OptionSpec<Void> client = opts.accepts("client", "Include client generators");
        OptionSpec<Void> dev = opts.accepts("dev", "Include development tools");
        OptionSpec<Void> reports = opts.accepts("reports", "Include data reports");
        OptionSpec<Void> validate = opts.accepts("validate", "Validate inputs");
        OptionSpec<Void> all = opts.accepts("all", "Include all generators");

        OptionSpec<String> output = opts.accepts("output", "Output folder").withRequiredArg().defaultsTo("generated");
        OptionSpec<String> input = opts.accepts("input", "Input folder").withRequiredArg();

        OptionSet optionSet = opts.parse(strings);
        if (!optionSet.has(help) && optionSet.hasOptions()) {
            Path path = Paths.get(output.value(optionSet));
            boolean genAll = optionSet.has(all);
            boolean genClient = genAll || optionSet.has(client);
            boolean genServer = genAll || optionSet.has(server);
            boolean genDev = genAll || optionSet.has(dev);
            boolean genReports = genAll || optionSet.has(reports);
            boolean genValidate = genAll || optionSet.has(validate);
            DataGenerator gen = create(
                path,
                optionSet.valuesOf(input)
                         .stream()
                         .map(Paths::get)
                         .collect(Collectors.toList()),
                genClient,
                genServer,
                genDev,
                genReports,
                genValidate
            );
            gen.run();
        } else {
            opts.printHelpOn(System.out);
        }
    }

    public static DataGenerator create(Path output, Collection<Path> inputs, boolean includeClient, boolean includeServer, boolean includeDev, boolean includeReports, boolean validate) {
        DataGenerator gen = new DataGenerator(output, inputs);
        if (includeClient) {
            gen.install(new MnStateModelProvider(gen));
        }

        if (includeServer) {
            gen.install(new MnFluidTagsProvider(gen));
            MnBlockTagsProvider blockTags = new MnBlockTagsProvider(gen);
            gen.install(blockTags);
            gen.install(new MnItemTagsProvider(gen, blockTags));
            gen.install(new MnRecipeProvider(gen));
            gen.install(new MnLootTablesProvider(gen));
        }

        return gen;
    }
}
