package com.mushroom.midnight;

import com.google.common.reflect.Reflection;
import com.mushroom.midnight.common.CommonProxy;
import com.mushroom.midnight.common.capability.RiftCooldownCapability;
import com.mushroom.midnight.common.capability.RifterCapturedCapability;
import com.mushroom.midnight.common.capability.VoidStorage;
import com.mushroom.midnight.common.loot.InBiomeLootCondition;
import com.mushroom.midnight.common.network.MessageBridgeCreate;
import com.mushroom.midnight.common.network.MessageBridgeRemoval;
import com.mushroom.midnight.common.network.MessageBridgeState;
import com.mushroom.midnight.common.network.MessageCaptureEntity;
import com.mushroom.midnight.common.network.MessageHunterAttack;
import com.mushroom.midnight.common.registry.ModBiomes;
import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.registry.ModCriterion;
import com.mushroom.midnight.common.registry.ModDimensions;
import com.mushroom.midnight.common.registry.ModFluids;
import com.mushroom.midnight.common.registry.ModItems;
import com.mushroom.midnight.common.registry.ModRecipes;
import com.mushroom.midnight.common.util.EntityUtil;
import com.mushroom.midnight.common.world.generator.MidnightOreGenerator;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Midnight.MODID, name = Midnight.NAME, version = Midnight.VERSION, dependencies = "required:forge@[14.23.5.2768,)")
@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class Midnight {

    public static final String MODID = "midnight";
    public static final String NAME = "Midnight";
    public static final String VERSION = "@VERSION@";
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    public static final SimpleNetworkWrapper NETWORK = new SimpleNetworkWrapper(MODID);

    public static final CreativeTabs BUILDING_TAB = new CreativeTabs("midnight_building") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.MIDNIGHT_GRASS);
        }
    };
    public static final CreativeTabs DECORATION_TAB = new CreativeTabs("midnight_decoration") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.ROUXE);
        }
    };
    public static final CreativeTabs MIDNIGHT_ITEMS = new CreativeTabs("midnight_items") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.DARK_STICK);
        }
    };
    public static final CreativeTabs MIDNIGHT_TOOLS = new CreativeTabs("midnight_tools") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.SHADOWROOT_PICKAXE);
        }
    };
    public static final CreativeTabs MIDNIGHT_COMBAT = new CreativeTabs("midnight_combat") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.SHADOWROOT_SWORD);
        }
    };

    @SidedProxy(serverSide = "com.mushroom.midnight.common.CommonProxy", clientSide = "com.mushroom.midnight.client.ClientProxy")
    public static CommonProxy proxy;

    @Mod.Instance(MODID)
    public static Midnight instance;

    @CapabilityInject(RiftCooldownCapability.class)
    public static Capability<RiftCooldownCapability> riftCooldownCap;

    @CapabilityInject(RifterCapturedCapability.class)
    public static Capability<RifterCapturedCapability> rifterCapturedCap;

    static {
        FluidRegistry.enableUniversalBucket();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CapabilityManager.INSTANCE.register(RiftCooldownCapability.class, new VoidStorage<>(), RiftCooldownCapability::new);
        CapabilityManager.INSTANCE.register(RifterCapturedCapability.class, new VoidStorage<>(), RifterCapturedCapability::new);

        NETWORK.registerMessage(MessageCaptureEntity.Handler.class, MessageCaptureEntity.class, 0, Side.CLIENT);
        NETWORK.registerMessage(MessageBridgeCreate.Handler.class, MessageBridgeCreate.class, 1, Side.CLIENT);
        NETWORK.registerMessage(MessageBridgeState.Handler.class, MessageBridgeState.class, 2, Side.CLIENT);
        NETWORK.registerMessage(MessageBridgeRemoval.Handler.class, MessageBridgeRemoval.class, 3, Side.CLIENT);
        NETWORK.registerMessage(MessageHunterAttack.Handler.class, MessageHunterAttack.class, 4, Side.CLIENT);

        Reflection.initialize(ModCriterion.class);

        EntityUtil.onPreInit();
        ModFluids.register();
        ModDimensions.register();

        LootConditionManager.registerCondition(new InBiomeLootCondition.Serialiser());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        GameRegistry.registerWorldGenerator(new MidnightOreGenerator(), Integer.MAX_VALUE);

        ModBiomes.onInit();
        ModItems.onInit();
        ModBlocks.onInit();
        ModRecipes.onInit();

        proxy.onInit();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
