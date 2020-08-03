package midnight.common.tileentity;


import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.tileentity.TileEntityType;
import midnight.MidnightInfo;
import midnight.common.block.MnBlocks;
import midnight.common.registry.RegistryManager;

@ObjectHolder(MidnightInfo.MODID)
public class MnTileEntities {
    public static final TileEntityType<MidnightChestTileEntity> MIDNIGHT_CHEST = register("midnight_chest", TileEntityType.Builder.create(MidnightChestTileEntity::new,
            MnBlocks.SHADOWROOT_CHEST, MnBlocks.DEAD_WOOD_CHEST, MnBlocks.DARK_WILLOW_CHEST,
            MnBlocks.NIGHTSHROOM_CHEST, MnBlocks.DEWSHROOM_CHEST, MnBlocks.VIRIDSHROOM_CHEST, MnBlocks.BOGSHROOM_CHEST).build(null));
    public static final TileEntityType<?> MIDNIGHT_FURNACE = register("midnight_furnace", TileEntityType.Builder.create(MidnightFurnaceTileEntity::new, MnBlocks.NIGHTSTONE_FURNACE).build(null));
    public static final TileEntityType<?> CACHE = register("cache", TileEntityType.Builder.create(CacheTileEntity::new, MnBlocks.VIRIDSHROOM_STEM_CACHE).build(null));

    //public static final TileEntityType<RiftPortalTileEntity> RIFT_PORTAL = RegUtil.injected();

    /*@SubscribeEvent
    public static void registerTileEntity(final RegistryEvent.Register<TileEntityType<?>> event) {
        RegUtil.generic(event.getRegistry())
                .add("midnight_chest", TileEntityType.Builder.create(MidnightChestTileEntity::new,
                        MnBlocks.SHADOWROOT_CHEST, MnBlocks.DEAD_WOOD_CHEST, MnBlocks.DARK_WILLOW_CHEST,
                        MnBlocks.NIGHTSHROOM_CHEST, MnBlocks.DEWSHROOM_CHEST, MnBlocks.VIRIDSHROOM_CHEST, MnBlocks.BOGSHROOM_CHEST
                ).build(null))
                .add("midnight_furnace", TileEntityType.Builder.create(MidnightFurnaceTileEntity::new, MnBlocks.NIGHTSTONE_FURNACE).build(null))
                .add("cache", TileEntityType.Builder.create(CacheTileEntity::new, MnBlocks.VIRIDSHROOM_STEM_CACHE).build(null))
                .add("rift_portal", TileEntityType.Builder.create(RiftPortalTileEntity::new, MnBlocks.RIFT_PORTAL).build(null));
    }*/

    private static <E extends TileEntityType<?>> E register(String id, E entitytype) {
        RegistryManager.TILE_ENTITIES.register(id, entitytype);
        return entitytype;
    }
}
