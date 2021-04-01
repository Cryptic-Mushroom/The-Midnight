/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.block;

import midnight.common.item.MnItems;
import net.minecraft.block.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nullable;
import java.util.Random;

import net.minecraft.block.AbstractBlock.Properties;

@SuppressWarnings("deprecation")
public class SuavisBlock extends Block implements IGrowable {
    public static final IntegerProperty STAGE = MnBlockStateProperties.STAGE_0_3;

    private static final VoxelShape[] BOUNDS = {
        box(0, 0, 0, 16, 4, 16),
        box(0, 0, 0, 16, 7, 16),
        box(0, 0, 0, 16, 13, 16),
        box(0, 0, 0, 16, 16, 16),
    };

    private static final double[] HEIGHTS = {
        4, 7, 13, 16
    };

    public SuavisBlock(Properties props) {
        super(props);

        registerDefaultState(getStateDefinition().any().setValue(STAGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }


    // HARVESTING AND NAUSEA CLOUDS

    @Override
    public void fallOn(World world, BlockPos pos, Entity entity, float fallDistance) {
        super.fallOn(world, pos, entity, fallDistance);

        // Handle entity trampling: an entity falling upon suavis will make it pop off one slice
        if (!world.isClientSide && fallDistance > 0.6 && entity.canTrample(world.getBlockState(pos), pos, fallDistance)) {
            BlockState state = world.getBlockState(pos);

            boolean isFirstStage = state.getValue(STAGE) == 0;
            if (!isFirstStage && world.random.nextInt(100) == 0) {
                world.destroyBlock(pos, true); // Random chance of breaking the full block instead of just one slice
            } else {
                // Play effects
                SoundType type = getSoundType(state, world, pos, entity);
                world.playSound(null, pos, type.getBreakSound(), SoundCategory.BLOCKS, 0.7f, 0.9f + world.random.nextFloat() * 0.2f);
                world.levelEvent(2001, pos, getId(state)); // 2001 is block break event - it spawns particles

                // Update state and spawn item
                popResource(world, pos, new ItemStack(MnItems.RAW_SUAVIS));
                world.setBlock(pos, isFirstStage ? Blocks.AIR.defaultBlockState() : state.setValue(STAGE, state.getValue(STAGE) - 1), 3);

                // Create nausea cloud, except if entity is a creative player
                if (!(entity instanceof PlayerEntity) || !((PlayerEntity) entity).isCreative()) {
                    createNauseaCloud(world, pos, 0, isFirstStage ? 0 : heightOf(state.getValue(STAGE) - 1));
                }
            }
        }
    }

    @Override
    public void playerDestroy(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.playerDestroy(world, player, pos, state, te, stack);
        if (!world.isClientSide()) { // Create a nausea cloud for harvesting player
            if (!player.isCreative() && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0) {
                createNauseaCloud(world, pos, state.getValue(STAGE), 0);
            }
        }
    }



    private static void createNauseaCloud(World world, BlockPos pos, int intensity, double height) {
        AreaEffectCloudEntity nausea = new AreaEffectCloudEntity(world, pos.getX() + 0.5, pos.getY() + 0.3 + height, pos.getZ() + 0.5);
        nausea.addEffect(new EffectInstance(Effects.CONFUSION, 20 * (intensity + 1) * 6, 0, false, true));
        nausea.setRadius(1.5f + 0.5f * intensity);
        nausea.setRadiusOnUse(-0.3f);
        nausea.setWaitTime(10);
        nausea.setRadiusPerTick(-nausea.getRadius() / nausea.getDuration());
        nausea.setFixedColor(0x355796);
        world.addFreshEntity(nausea);
    }

    private static double heightOf(int stage) {
        return HEIGHTS[stage];
    }



    // GROWING

    @Override
    public boolean isValidBonemealTarget(IBlockReader world, BlockPos pos, BlockState state, boolean client) {
        return state.getValue(STAGE) < 3;
    }

    @Override
    public boolean isBonemealSuccess(World world, Random rng, BlockPos pos, BlockState state) {
        return state.getValue(STAGE) < 3;
    }

    @Override
    public void performBonemeal(ServerWorld world, Random rng, BlockPos pos, BlockState state) {
        BlockState newState = state.setValue(STAGE, state.getValue(STAGE) + 1);
        world.setBlock(pos, newState, 2);
        pushEntitiesUp(state, newState, world, pos); // Push entities up as we grow
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rng) {
        if (state.getValue(STAGE) < 3 && ForgeHooks.onCropsGrowPre(world, pos, state, rng.nextInt(5) == 0)) {
            performBonemeal(world, rng, pos, state);
            ForgeHooks.onCropsGrowPost(world, pos, state);
        }
    }


    // PLACEMENT LOGIC

    @Override
    public BlockState updateShape(BlockState state, Direction dir, BlockState adjState, IWorld world, BlockPos pos, BlockPos adjPos) {
        if (dir == Direction.DOWN) {
            // Check support, if we are unstable we should break
            if (!canRemain(world, pos)) {
                // Destroy block without drops on death - player needs to harvest and get nausea, or no item
                world.destroyBlock(pos, false);
                return Blocks.AIR.defaultBlockState();
            }
        }
        return super.updateShape(state, dir, adjState, world, pos, adjPos);
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
        return canRemain(world, pos); // We must be stable in order to be placed
    }

    private boolean canRemain(IWorldReader world, BlockPos pos) {
        BlockState down = world.getBlockState(pos.below());
        return down.getBlock() != this // Even though we are solid, suavis must not be placed on other suavis
                   && down.isFaceSturdy(world, pos.below(), Direction.UP)
                   || down.getBlock() == MnBlocks.DECEITFUL_MUD; // Mud has no solid collision hitbox
    }


    // OTHER LOGIC

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return state.getValue(STAGE) < 3;
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        return state.getValue(STAGE) == 3
               ? new ItemStack(this) // Get a suavis slice if the block isn't fully grown - get ourselves otherwise
               : new ItemStack(MnItems.RAW_SUAVIS);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {
        return defaultBlockState().setValue(STAGE, 3);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return BOUNDS[state.getValue(STAGE)];
    }

    @Override
    public boolean isPathfindable(BlockState state, IBlockReader world, BlockPos pos, PathType type) {
        return state.getValue(STAGE) < 2;
    }
}
