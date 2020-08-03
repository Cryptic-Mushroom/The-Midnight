package midnight.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.biome.BiomeColors;
import midnight.client.proxy.ClientBlockItemProxy;
import midnight.client.render.entity.BladeshroomCapRenderer;
import midnight.client.render.entity.CrystalBugRenderer;
import midnight.common.Midnight;
import midnight.common.block.MnBlocks;
import midnight.common.entity.MnEntities;
import midnight.common.proxy.BlockItemProxy;
import midnight.core.util.MnUtil;
import midnight.data.MidnightData;

/**
 * The client-only main class of the Midnight, to handle certain client-only initialization and processing.
 */
public class MidnightClient extends Midnight {
    private static final Minecraft MC = Minecraft.getInstance();
    private static final int DEFAULT_GRASS_COLOR = 0xBF8ECC;
    private static final int DEFAULT_FOLIAGE_COLOR = 0x8F6DBC;

    @Override
    public Dist getRuntimeDist() {
        return Dist.CLIENT;
    }

    /**
     * Returns the direct instance of {@link MidnightClient}, or throws a {@link ClassCastException} when not on the
     * client (that would already have caused a class loading failure in most cases).
     */
    public static MidnightClient get() {
        return (MidnightClient) Midnight.get();
    }

    @Override
    protected BlockItemProxy makeBlockItemProxy() {
        return new ClientBlockItemProxy();
    }

    /**
     * Creates the proper instance of {@link MidnightClient}, using {@link MidnightData} when on data generation mode.
     */
    public static MidnightClient dataOrClient() {
        return MnUtil.callForDatagen(() -> MidnightData::new, () -> MidnightClient::new);
    }

    @Override
    public void init() {
        super.init();
        registerModels();
    }

    public static void registerModels() {
        //RenderingRegistry.registerEntityRenderingHandler(MnEntities.RIFTER, RifterRenderer::new);
        //RenderingRegistry.registerEntityRenderingHandler(MnEntities.HUNTER, HunterRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MnEntities.BLADESHROOM_CAP, BladeshroomCapRenderer::new);
        //RenderingRegistry.registerEntityRenderingHandler(MnEntities.NOVA, NovaRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MnEntities.CRYSTAL_BUG, CrystalBugRenderer::new);
       /* RenderingRegistry.registerEntityRenderingHandler(MnEntities.PENUMBRIAN, PenumbrianRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MnEntities.TREE_HOPPER, TreeHopperRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MnEntities.STINGER, StingerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MnEntities.NIGHTSTAG, NightStagRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MnEntities.DECEITFUL_SNAPPER, DeceitfulSnapperRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MnEntities.BULB_ANGLER, BulbAnglerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MnEntities.SKULK, SkulkRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MnEntities.SHADE_SQUIRREL, ShadeSquirrelRenderer::new);
        */
        RenderingRegistry.registerEntityRenderingHandler(MnEntities.THROWN_GEODE, manager -> new SpriteRenderer(MC.getRenderManager(), MC.getItemRenderer()));
        /*RenderingRegistry.registerEntityRenderingHandler(MnEntities.SPORE_BOMB, manager -> new SpriteRenderer(MC.getRenderManager(), MC.getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(MnEntities.CLOUD, CloudRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MnEntities.NOVA_SPIKE, NovaSpikeRenderer::new);
*/
       /* ClientRegistry.bindTileEntityRenderer(MidnightTileEntities.MIDNIGHT_CHEST, MidnightChestBlockRenderer::new);
        ClientRegistry.bindTileEntityRenderer(MidnightTileEntities.RIFT_PORTAL, RiftPortalBlockRenderer::new);
        */
        //ClientRegistry.bindTileEntityRenderer(CacheTileEntity.class, new CacheBlockRenderer());
        //ClientRegistry.bindTileEntityRenderer(RiftPortalTileEntity.class, new RiftPortalBlockRenderer());

        BlockColors blockColors = MC.getBlockColors();
        ItemColors itemColors = MC.getItemColors();

        blockColors.register(MidnightClient::computeGrassColor, MnBlocks.GRASS_BLOCK);
        itemColors.register(MidnightClient::defaultGrassColor, MnBlocks.GRASS_BLOCK);

        /*blockColors.register(MidnightClient::computeFoliageColor, MnBlocks.SHADOWROOT_LEAVES);
        itemColors.register(MidnightClient::defaultFoliageColor, MnBlocks.SHADOWROOT_LEAVES);*/

        blockColors.register(MidnightClient::computeGrassColor, MnBlocks.GRASS, MnBlocks.TALL_GRASS);
        itemColors.register(MidnightClient::defaultGrassColor, MnBlocks.GRASS, MnBlocks.TALL_GRASS);
    }

    private static int computeGrassColor(BlockState state, IBlockDisplayReader world, BlockPos pos, int tintIndex) {
        if (world == null || pos == null) {
            return DEFAULT_FOLIAGE_COLOR;
        }
        return BiomeColors.getGrassColor(world, pos);
    }

    private static int defaultGrassColor(ItemStack stack, int tintIndex) {
        return DEFAULT_GRASS_COLOR;
    }

    private static int computeFoliageColor(BlockState state, IBlockDisplayReader world, BlockPos pos, int tintIndex) {
        if (world == null || pos == null) {
            return DEFAULT_FOLIAGE_COLOR;
        }
        return BiomeColors.getFoliageColor(world, pos);
    }

    private static int defaultFoliageColor(ItemStack stack, int tintIndex) {
        return DEFAULT_FOLIAGE_COLOR;
    }

}
