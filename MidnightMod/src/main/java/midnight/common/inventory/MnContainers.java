package midnight.common.inventory;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import midnight.MidnightInfo;
import midnight.client.gui.CacheScreen;
import midnight.client.gui.MidnightFurnaceScreen;

@ObjectHolder(MidnightInfo.MODID)
@Mod.EventBusSubscriber(modid = MidnightInfo.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MnContainers {
    public static ContainerType<CacheContainer> CACHE = new ContainerType<>(CacheContainer::new);
    public static ContainerType<MidnightFurnaceContainer> FURNACE = new ContainerType<>(MidnightFurnaceContainer::new);

    @SubscribeEvent
    public static void registerContainer(RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().registerAll(
                CACHE.setRegistryName(MidnightInfo.MODID, "cache"),
                FURNACE.setRegistryName(MidnightInfo.MODID, "furnace")
        );

        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            ScreenManager.registerFactory(MnContainers.CACHE, CacheScreen::new);
            ScreenManager.registerFactory(MnContainers.FURNACE, MidnightFurnaceScreen::new);
        });
    }
}
