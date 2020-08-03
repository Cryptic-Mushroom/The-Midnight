package midnight.common.entity.projectile;

import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import midnight.MidnightInfo;
import midnight.common.entity.MnEntities;
import midnight.common.item.MnItems;
import midnight.common.registry.MnSounds;

@Mod.EventBusSubscriber(modid = MidnightInfo.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BladeshroomCapEntity extends AbstractArrowEntity {
    private static final ThreadLocal<Boolean> HIT_ACTIVE = ThreadLocal.withInitial(() -> false);

    private static final float DAMAGE = 1.0F;
    private static final double GRAVITY = 0.015;

    public float spin;
    public float prevSpin;

    public BladeshroomCapEntity(EntityType<BladeshroomCapEntity> entityType, World world) {
        super(entityType, world);
        this.setDamage(DAMAGE);
    }

    public BladeshroomCapEntity(World world, LivingEntity thrower) {
        super(MnEntities.BLADESHROOM_CAP, thrower, world);
        this.setDamage(DAMAGE);
    }

    public BladeshroomCapEntity(World world, double x, double y, double z) {
        super(MnEntities.BLADESHROOM_CAP, x, y, z, world);
        this.setDamage(DAMAGE);
    }

    public BladeshroomCapEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(MnEntities.BLADESHROOM_CAP, world);
    }

    @Override
    public void tick() {
        super.tick();

        this.prevSpin = this.spin;

        if (!this.inGround) {
            this.spin += 25.0F;

            if (!this.hasNoGravity()) {
                this.setMotion(this.getMotion().add(0.0, 0.05 - GRAVITY, 0.0));
            }
        }
    }

    @Override
    protected void onImpact(RayTraceResult result) {

        try {
            HIT_ACTIVE.set(true);
            super.onImpact(result);
        } finally {
            HIT_ACTIVE.set(false);
        }
    }


    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(MnItems.BLADESHROOM_CAP);
    }

    @SubscribeEvent
    public static void onPlaySound(PlaySoundAtEntityEvent event) {
        if (!HIT_ACTIVE.get()) {
            return;
        }

        if (event.getSound() == SoundEvents.ENTITY_ARROW_HIT) {
            event.setSound(MnSounds.BLADESHROOM_CAP_HIT);
        }
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
