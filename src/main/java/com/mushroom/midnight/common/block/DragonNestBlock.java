package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.particle.ParticleDataOneInt;
import com.mushroom.midnight.common.registry.MidnightEffects;
import com.mushroom.midnight.common.registry.MidnightParticleTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class DragonNestBlock extends HangablePlantBlock {
    public DragonNestBlock(Properties properties) {
        super(properties, true);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity && !entity.world.isRemote && entity.ticksExisted % 10 == 0) {
            ((LivingEntity) entity).addPotionEffect(new EffectInstance(MidnightEffects.DRAGON_GUARD, 100, 0, false, true));
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
        super.animateTick(state, world, pos, rand);
        if (rand.nextFloat() < 0.5f) {
            Vec3d offset = getOffset(state, world, pos);
            double distX = rand.nextFloat() * 0.6 - 0.3d;
            double posX = pos.getX() + 0.5d + offset.x + distX;
            double posY = pos.getY() + offset.y + Math.abs(distX);
            double posZ = pos.getZ() + 0.5d + offset.z + (rand.nextBoolean() ? distX : -distX);
            world.addParticle(new ParticleDataOneInt(MidnightParticleTypes.DRAGONS_NEST_DRIP, 0xbf4652), posX, posY, posZ, 0d, 0d, 0d);
        }
    }
}
