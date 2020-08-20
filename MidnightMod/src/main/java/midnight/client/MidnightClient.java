package midnight.client;

import midnight.client.biome.BiomeColorCache;
import midnight.client.proxy.ClientBlockItemProxy;
import midnight.client.render.DeceitfulSnapperRenderer;
import midnight.common.Midnight;
import midnight.common.entity.MnEntitys;
import midnight.common.proxy.BlockItemProxy;
import midnight.core.util.MnUtil;
import midnight.data.MidnightData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

/**
 * The client-only main class of the Midnight, to handle certain client-only initialization and processing.
 */
public class MidnightClient extends Midnight {
    private final BiomeColorCache darkWaterColorCache = new BiomeColorCache();
    private final BiomeColorCache nightGrassColorCache = new BiomeColorCache();

    @Override
    public void init() {
        getBlockItemProxy().init();

        //TODO This is Temporary place,Free replace this
        RenderingRegistry.registerEntityRenderingHandler(MnEntitys.DECEITFUL_SNAPPER, DeceitfulSnapperRenderer::new);
    }

    @Override
    public Dist getRuntimeDist() {
        return Dist.CLIENT;
    }

    @Override
    protected BlockItemProxy makeBlockItemProxy() {
        return new ClientBlockItemProxy();
    }

    public BiomeColorCache getDarkWaterColorCache() {
        return darkWaterColorCache;
    }

    public BiomeColorCache getNightGrassColorCache() {
        return nightGrassColorCache;
    }

    /**
     * Returns the direct instance of {@link MidnightClient}, or throws a {@link ClassCastException} when not on the
     * client (that would already have caused a class loading failure in most cases).
     */
    public static MidnightClient get() {
        return (MidnightClient) Midnight.get();
    }

    /**
     * Creates the proper instance of {@link MidnightClient}, using {@link MidnightData} when on data generation mode.
     */
    public static MidnightClient dataOrClient() {
        return MnUtil.callForDatagen(() -> MidnightData::new, () -> MidnightClient::new);
    }
}
