/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.block;

import midnight.common.net.MnNetwork;
import midnight.common.net.VioleafHealPacket;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.Random;

public class VioleafBlock extends PlantBlock implements IGrowable {
    public static final BooleanProperty GROWN = MnBlockStateProperties.GROWN;
    private static final int PARTICLE_COUNT = 32;

    protected VioleafBlock(Properties props) {
        super(props);
        registerDefaultState(getStateDefinition().any().setValue(GROWN, true));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {
        return defaultBlockState().setValue(GROWN, false);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(GROWN);
    }

    @Override
    public boolean isValidBonemealTarget(IBlockReader world, BlockPos pos, BlockState state, boolean client) {
        return !state.getValue(GROWN);
    }

    @Override
    public boolean isBonemealSuccess(World world, Random rng, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerWorld world, Random rng, BlockPos pos, BlockState state) {
        world.setBlock(pos, state.setValue(GROWN, true), 3);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        if (isValidBonemealTarget(world, pos, state, world.isClientSide) && ForgeHooks.onCropsGrowPre(world, pos, state, rand.nextInt(5) == 0)) {
            performBonemeal(world, rand, pos, state);
            ForgeHooks.onCropsGrowPost(world, pos, state);
        }
    }

    @Override
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClientSide && state.getValue(GROWN) && entity instanceof LivingEntity && entity.tickCount % 20 == 0) {
            LivingEntity livingEntity = (LivingEntity) entity;
            if (livingEntity.hasEffect(Effects.CONFUSION)) {
                livingEntity.removeEffect(Effects.CONFUSION);

                world.setBlock(pos, state.setValue(GROWN, false), 2);
                world.playSound(null, pos, SoundEvents.CHORUS_FLOWER_DEATH, SoundCategory.BLOCKS, 1, world.random.nextFloat() * 0.4f + 0.8f);

                Vector3d offset = state.getOffset(world, pos);
                double posX = pos.getX() + 0.5 + offset.x;
                double posY = pos.getY() + 0.3;
                double posZ = pos.getZ() + 0.5 + offset.z;
                VioleafHealPacket pkt = new VioleafHealPacket(new Vector3d(posX, posY, posZ));
                MnNetwork.CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunkAt(pos)), pkt);
            }
        }
    }

    public static void spawnParticles(World world, Vector3d pos) {
        if (world == null) // Null check here for the ease of passing Minecraft.world as argument
            return;

        for (int i = 0; i < PARTICLE_COUNT; i++) {
            Vector3d vel = new Vector3d( // Velocity
                                         world.random.nextDouble() * 2 - 1,
                                         world.random.nextDouble() * 2 - 1,
                                         world.random.nextDouble() * 2 - 1
            ).normalize().scale(world.random.nextDouble() * 0.02 + 0.02);

            Vector3d loc = new Vector3d( // Location
                                         world.random.nextDouble() * 2 - 1,
                                         world.random.nextDouble() * 2 - 1,
                                         world.random.nextDouble() * 2 - 1
            ).scale(0.2)
             .add(pos);

            // Extra vertical upwards velocity
            double extraYV = world.random.nextDouble() * 0.1;
            vel = vel.add(0, extraYV, 0);

            world.addParticle(
                ParticleTypes.DRAGON_BREATH,
                loc.x, loc.y, loc.z,
                vel.x, vel.y, vel.z
            );
        }
    }
}
