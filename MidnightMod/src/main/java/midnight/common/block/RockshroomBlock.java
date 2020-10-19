/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 19
 */

package midnight.common.block;

import midnight.common.misc.MnDamageSources;
import midnight.common.misc.MnParticleTypes;
import midnight.common.net.MnNetwork;
import midnight.common.net.RockshroomAttackPacket;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Random;

public class RockshroomBlock extends Block {
    private static final int SPORE_COUNT = 32;
    private static final double SPORE_SPEED = 0.3;

    private static final double DAMAGE_RANGE = 4;
    private static final double DAMAGE_RANGE_SQ = DAMAGE_RANGE * DAMAGE_RANGE;

    public RockshroomBlock(Properties props) {
        super(props);
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {
        if (super.removedByPlayer(state, world, pos, player, willHarvest, fluid)) {
            handleSporeDamage(world, pos, player);
            return true;
        }
        return false;
    }

    private void handleSporeDamage(World world, BlockPos pos, PlayerEntity player) {
        ItemStack heldItem = player.getHeldItemMainhand();
        if (player.isCreative() || EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, heldItem) > 0) {
            return;
        }

        if (!world.isRemote) {
            // Send particle effect to all players watching the broken block
            RockshroomAttackPacket message = new RockshroomAttackPacket(pos);
            MnNetwork.CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunkAt(pos)), message);

            damagePlayer(world, pos, player);
        }
    }

    private void damagePlayer(World world, BlockPos pos, PlayerEntity player) {
        Vector3d origin = new Vector3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        Vector3d target = player.getEyePosition(1);

        if (target.subtract(origin).lengthSquared() > DAMAGE_RANGE_SQ)
            return;


        RayTraceResult rtr = world.rayTraceBlocks(new RayTraceContext(
            origin, target,
            RayTraceContext.BlockMode.COLLIDER,
            RayTraceContext.FluidMode.NONE,
            player
        ));
        if (rtr.getType() == RayTraceResult.Type.MISS) {
            player.attackEntityFrom(MnDamageSources.ROCKSHROOM, world.rand.nextFloat() * 3.5f + 0.5f);
        }
    }

    public static void spawnSpores(World world, BlockPos pos) {
        if (world == null)
            return;

        Random rng = world.rand;
        for (int i = 0; i < SPORE_COUNT; i++) {
            Vector3d direction = new Vector3d(
                rng.nextFloat() * 2 - 1,
                rng.nextFloat() * 2 - 1,
                rng.nextFloat() * 2 - 1
            ).normalize();

            double x = pos.getX() + 0.5 + direction.x * 0.4;
            double y = pos.getY() + 0.5 + direction.y * 0.4;
            double z = pos.getZ() + 0.5 + direction.z * 0.4;

            Vector3d vel = direction.scale(SPORE_SPEED);
            world.addParticle(MnParticleTypes.SPORE, x, y, z, vel.x, vel.y + 0.05, vel.z);
        }
    }
}
