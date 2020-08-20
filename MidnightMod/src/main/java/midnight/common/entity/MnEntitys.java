package midnight.common.entity;

import midnight.MidnightInfo;
import midnight.common.Midnight;
import midnight.common.registry.RegistryManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;

public class MnEntitys {
    // TODO not sure why there're 2 strings param here (they can be replaced with normal entityClassification and directly set their number max in the mob spawner)
    public static final EntityClassification MIDNIGHT_MOB = EntityClassification.create("midnight_mob", "midnight_mob", 10, false, false);
    public static final EntityClassification MIDNIGHT_AMBIENT = EntityClassification.create("midnight_ambient", "midnight_ambient", 30, true, false);


    public static final EntityType<DeceitfulSnapperEntity> DECEITFUL_SNAPPER = register("deceitful_snapper", EntityType.Builder.create(DeceitfulSnapperEntity::new, EntityClassification.WATER_CREATURE)
                                                                                                                               .setTrackingRange(80)
                                                                                                                               .setUpdateInterval(3)
                                                                                                                               .setShouldReceiveVelocityUpdates(true)
                                                                                                                               .size(0.4f, 0.4f)
                                                                                                                               .build(MidnightInfo.MODID + ":deceitful_snapper"));
    public static final EntityType<SkulkEntity> SKULK = EntityType.Builder.create(SkulkEntity::new, EntityClassification.CREATURE)
                                                                          .setTrackingRange(80)
                                                                          .setUpdateInterval(3)
                                                                          .setShouldReceiveVelocityUpdates(true)
                                                                          .size(0.6f, 0.6f)
                                                                          .build(MidnightInfo.MODID + ":skulk");

    private static <T extends Entity> EntityType<T> register(String type, EntityType<T> entityType) {
        ResourceLocation id = Midnight.resLoc(type);
        RegistryManager.ENTITY_TYPES.register(entityType.setRegistryName(id));
        return entityType;
    }
}
