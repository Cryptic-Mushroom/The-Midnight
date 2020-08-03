package midnight.common.item;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import midnight.common.entity.projectile.ThrownGeodeEntity;

public class GeodeItem extends Item {
    public GeodeItem(Item.Properties properties) {
        super(properties);
        DispenserBlock.registerDispenseBehavior(this, new GeodeItem.DispenserBehavior());
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        if (!player.abilities.isCreativeMode) {
            heldItem.shrink(1);
        }

        world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_EGG_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));

        if (!world.isRemote) {
            ThrownGeodeEntity geode = new ThrownGeodeEntity(world, player);
            geode.func_234612_a_(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
            world.addEntity(geode);
        }

        return new ActionResult<>(ActionResultType.SUCCESS, heldItem);
    }

    private static class DispenserBehavior extends ProjectileDispenseBehavior {
        @Override
        protected ProjectileEntity getProjectileEntity(World world, IPosition pos, ItemStack stack) {
            return new ThrownGeodeEntity(world, pos.getX(), pos.getY(), pos.getZ());
        }
    }
}
