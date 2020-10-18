/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 18
 */

package midnight.common.block;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BouncyShroomCapBlock extends Block {
    public BouncyShroomCapBlock(Properties props) {
        super(props);
    }

    @Override
    public void onFallenUpon(World world, BlockPos pos, Entity entity, float fallDistance) {
        if (entity.bypassesLandingEffects()) {
            super.onFallenUpon(world, pos, entity, fallDistance);
        } else {
            entity.handleFallDamage(fallDistance, 0);
        }

    }

    @Override
    public void onLanded(IBlockReader world, Entity entity) {
        if (entity.bypassesLandingEffects()) {
            super.onLanded(world, entity);
        } else {
            bounce(entity);
        }
    }

    private void bounce(Entity entity) {
        Vector3d motion = entity.getMotion();
        if (motion.y < 0) {
            double bounceFactor = entity instanceof LivingEntity ? 1 : 0.8;
            entity.setMotion(motion.x, -motion.y * bounceFactor, motion.z);
        }
    }

    @Override
    public void onEntityWalk(World world, BlockPos pos, Entity entity) {
        double vspeed = Math.abs(entity.getMotion().y);
        if (vspeed < 0.1 && !entity.bypassesSteppingEffects()) {
            double hspeedMul = 0.4 + vspeed * 0.2;
            entity.setMotion(entity.getMotion().mul(hspeedMul, 1, hspeedMul));
        }

        super.onEntityWalk(world, pos, entity);
    }
}
