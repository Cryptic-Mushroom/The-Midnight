/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 20
 */

package midnight.common.block;

import midnight.common.misc.MnParticleTypes;
import midnight.common.net.MnNetwork;
import midnight.common.net.TendrilweedGrowPacket;
import midnight.core.util.MnMath;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Random;

@SuppressWarnings("deprecation")
public class TendrilweedBlock extends MnPlantBlock {
    protected TendrilweedBlock(Properties props) {
        super(props);
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        return state.getBlock() == MnBlocks.NIGHTSTONE;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        BlockPos down = pos.down();
        return isValidGround(world.getBlockState(down), world, down);
    }


    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rng) {
        for (int i = 0; i < 3; i++) {

            BlockPos nearby = pos.add(rng.nextInt(5) - 2, rng.nextInt(3) - 1, rng.nextInt(5) - 2);
            if (isValidPosition(world.getBlockState(nearby), world, nearby) && world.isAirBlock(nearby)) {

                // Find neaby tendrilweeds, don't grow too much tendrilweeds in one place
                int nearTendrilweeds = 0;
                for (BlockPos lookPos : BlockPos.getAllInBoxMutable(pos.add(-2, -1, -2), pos.add(2, 1, 2))) {
                    if (world.getBlockState(lookPos).isIn(this)) {
                        nearTendrilweeds++;
                    }
                }

                if (nearTendrilweeds < 6) {
                    world.setBlockState(nearby, getDefaultState(), 3);

                    // Notify clients to create a pollen cloud
                    TendrilweedGrowPacket pkt = new TendrilweedGrowPacket(nearby);
                    MnNetwork.CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunkAt(nearby)), pkt);
                }
            }
        }
    }

    @Override // Spawn particles as entities move through the block
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (world.isRemote) { // Only compute this on the client
            Vector3d velo = entity.getMotion();
            double speed = velo.length();

            // Do not spawn a particle when entity is not moving
            if (speed < 0.01) return;

            // Limit chance to 0.2 - do not spam particles at high speed
            double particleChance = MnMath.clamp(0, 0.2, speed);

            Random rng = world.getRandom();
            if (rng.nextDouble() < particleChance) {
                Vector3d motion;
                // Prevent motion overscaling (do not give particles too much motion)
                if (speed > 0.2)
                    motion = velo.normalize().scale(0.2);
                else
                    motion = velo;

                spawnPollen(world, pos, state, rng, motion.scale(0.4));
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random rng) {
        if (rng.nextInt(26) == 0) {
            spawnPollen(world, pos, state, rng, Vector3d.ZERO);
        }
    }

    private static void spawnPollen(World world, BlockPos pos, BlockState state, Random rng, Vector3d motion) {
        Vector3d off = state.getOffset(world, pos);

        double x = pos.getX() + off.x + (rng.nextDouble() * 2 - 1) / 4 + 0.5;
        double y = pos.getY() + off.y + rng.nextDouble() * 0.92;
        double z = pos.getZ() + off.z + (rng.nextDouble() * 2 - 1) / 4 + 0.5;

        world.addParticle(MnParticleTypes.TENDRIL_POLLEN, x, y, z, motion.x, motion.y, motion.z);
    }

    public static void spawnPollenCloud(World world, BlockPos pos) {
        if (world == null) // Null check here for the ease of giving Minecraft.world as argument
            return;

        BlockState state = world.getBlockState(pos);
        Random rng = world.rand;
        for (int i = 0; i < 10; i++) {
            spawnPollen(world, pos, state, rng, new Vector3d(
                rng.nextDouble() * 2 - 1,
                rng.nextDouble() * 2 - 1,
                rng.nextDouble() * 2 - 1
            ).scale(0.004));
        }
    }
}
