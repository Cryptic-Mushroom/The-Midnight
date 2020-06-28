package midnight;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import midnight.client.MidnightClient;
import midnight.common.Midnight;
import midnight.core.plugin.PluginManager;

import java.util.stream.Collectors;

@Mod(MidnightInfo.MODID)
public class MidnightMod {
    // TODO: Make a better logger later in development.
    private static final Logger LOGGER = LogManager.getLogger("MidnightMod");

    public static final Midnight MIDNIGHT = DistExecutor.safeRunForDist(() -> Midnight::new, () -> MidnightClient::new);

    public MidnightMod() {
        printVersion();
        PluginManager.INSTANCE.loadPlugins();

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void printVersion() {
        LOGGER.info("Initializing The Midnight");
        LOGGER.info(" - Version: " + MidnightInfo.VERSION);
        LOGGER.info(" - Build Date: " + MidnightInfo.BUILD_DATE);
        if (MidnightInfo.IDE) {
            LOGGER.info(" - Running in IDE");
        }
    }

    private void setup(FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        LOGGER.info("Pre-Initialization of The Midnight successful");
    }

    private void doClientStuff(FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    private void enqueueIMC(InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("midnight", "helloworld", () -> {
            LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
    }

    private void processIMC(InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class RegistryEvents {
        private RegistryEvents() {
        }

        @SubscribeEvent
        public static void onBlocksRegistry(RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
}