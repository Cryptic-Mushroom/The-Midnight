package midnight.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class UnstableFruitItem extends Item {
    public enum Color {
        BLUE(0.7f, 0.14f),
        LIME(0.4f, 0.2f),
        GREEN(0.1f, 0.33f);

        private final float poisonChance, levitationChance;

        Color(float poisonChance, float levitationChance) {
            this.poisonChance = poisonChance;
            this.levitationChance = levitationChance;
        }
    }

    public final Color color;

    public UnstableFruitItem(Color color, Item.Properties properties) {
        super(properties);
        this.color = color;
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        Vector3d motion = entity.getMotion();
        entity.setMotion(motion.x, 0.06, motion.z);
        return false;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entity) {
        if (!world.isRemote) {
            if (world.rand.nextFloat() < this.color.poisonChance) {
                entity.addPotionEffect(new EffectInstance(Effects.POISON, 200, this.color.ordinal(), false, true));
            }
            if (world.rand.nextFloat() < this.color.levitationChance) {
                entity.addPotionEffect(new EffectInstance(Effects.LEVITATION, 200, this.color.ordinal(), false, true));
                //entity.addPotionEffect(new EffectInstance(MidnightEffects.UNSTABLE_FALL, 400, 0, false, true));
            }
        }
        return super.onItemUseFinish(stack, world, entity);
    }
}
