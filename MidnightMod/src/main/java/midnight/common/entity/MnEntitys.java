package midnight.common.entity;

import midnight.MidnightInfo;
import midnight.common.Midnight;
import midnight.common.registry.RegistryManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;

public class MnEntitys {
    public static final EntityType<DeceitfulSnapperEntity> DECEITFUL_SNAPPER = register("deceitful_snapper", EntityType.Builder.create(DeceitfulSnapperEntity::new, EntityClassification.WATER_CREATURE)
                                                                                                                               .setTrackingRange(80)
                                                                                                                               .setUpdateInterval(3)
                                                                                                                               .setShouldReceiveVelocityUpdates(true)
                                                                                                                               .size(0.4f, 0.4f)
                                                                                                                               .build(MidnightInfo.MODID + ":deceitful_snapper"));

    private static <T extends Entity> EntityType<T> register(String type, EntityType<T> entityType) {
        ResourceLocation id = Midnight.resLoc(type);
        RegistryManager.ENTITY_TYPES.register(entityType.setRegistryName(id));
        return entityType;
    }
}
