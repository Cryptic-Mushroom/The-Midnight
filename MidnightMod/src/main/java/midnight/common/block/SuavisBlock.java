/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 20
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

@SuppressWarnings("deprecation")
public class SuavisBlock extends Block implements IGrowable {
    public static final IntegerProperty STAGE = MnBlockStateProperties.STAGE_0_3;

    private static final VoxelShape[] BOUNDS = {
        makeCuboidShape(0, 0, 0, 16, 4, 16),
        makeCuboidShape(0, 0, 0, 16, 7, 16),
        makeCuboidShape(0, 0, 0, 16, 13, 16),
        makeCuboidShape(0, 0, 0, 16, 16, 16),
    };

    private static final double[] HEIGHTS = {
        4, 7, 13, 16
    };

    public SuavisBlock(Properties props) {
        super(props);

        setDefaultState(getStateContainer().getBaseState().with(STAGE, 0));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }


    // HARVESTING AND NAUSEA CLOUDS

    @Override
    public void onFallenUpon(World world, BlockPos pos, Entity entity, float fallDistance) {
        super.onFallenUpon(world, pos, entity, fallDistance);

        // Handle entity trampling: an entity falling upon suavis will make it pop off one slice
        if (!world.isRemote && fallDistance > 0.6 && entity.canTrample(world.getBlockState(pos), pos, fallDistance)) {
            BlockState state = world.getBlockState(pos);

            boolean isFirstStage = state.get(STAGE) == 0;
            if (!isFirstStage && world.rand.nextInt(100) == 0) {
                world.destroyBlock(pos, true); // Random chance of breaking the full block instead of just one slice
            } else {
                // Play effects
                SoundType type = getSoundType(state, world, pos, entity);
                world.playSound(null, pos, type.getBreakSound(), SoundCategory.BLOCKS, 0.7f, 0.9f + world.rand.nextFloat() * 0.2f);
                world.playEvent(2001, pos, getStateId(state)); // 2001 is block break event - it spawns particles

                // Update state and spawn item
                spawnAsEntity(world, pos, new ItemStack(MnItems.RAW_SUAVIS));
                world.setBlockState(pos, isFirstStage ? Blocks.AIR.getDefaultState() : state.with(STAGE, state.get(STAGE) - 1), 3);

                // Create nausea cloud, except if entity is a creative player
                if (!(entity instanceof PlayerEntity) || !((PlayerEntity) entity).isCreative()) {
                    createNauseaCloud(world, pos, 0, isFirstStage ? 0 : heightOf(state.get(STAGE) - 1));
                }
            }
        }
    }

    @Override
    public void harvestBlock(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(world, player, pos, state, te, stack);
        if (!world.isRemote()) { // Create a nausea cloud for harvesting player
            if (!player.isCreative() && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0) {
                createNauseaCloud(world, pos, state.get(STAGE), 0);
            }
        }
    }



    private static void createNauseaCloud(World world, BlockPos pos, int intensity, double height) {
        AreaEffectCloudEntity nausea = new AreaEffectCloudEntity(world, pos.getX() + 0.5, pos.getY() + 0.3 + height, pos.getZ() + 0.5);
        nausea.addEffect(new EffectInstance(Effects.NAUSEA, 20 * (intensity + 1) * 6, 0, false, true));
        nausea.setRadius(1.5f + 0.5f * intensity);
        nausea.setRadiusOnUse(-0.3f);
        nausea.setWaitTime(10);
        nausea.setRadiusPerTick(-nausea.getRadius() / nausea.getDuration());
        nausea.setColor(0x355796);
        world.addEntity(nausea);
    }

    private static double heightOf(int stage) {
        return HEIGHTS[stage];
    }



    // GROWING

    @Override
    public boolean canGrow(IBlockReader world, BlockPos pos, BlockState state, boolean client) {
        return state.get(STAGE) < 3;
    }

    @Override
    public boolean canUseBonemeal(World world, Random rng, BlockPos pos, BlockState state) {
        return state.get(STAGE) < 3;
    }

    @Override
    public void grow(ServerWorld world, Random rng, BlockPos pos, BlockState state) {
        BlockState newState = state.with(STAGE, state.get(STAGE) + 1);
        world.setBlockState(pos, newState, 2);
        nudgeEntitiesWithNewState(state, newState, world, pos); // Push entities up as we grow
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random rng) {
        if (state.get(STAGE) < 3 && ForgeHooks.onCropsGrowPre(world, pos, state, rng.nextInt(5) == 0)) {
            grow(world, rng, pos, state);
            ForgeHooks.onCropsGrowPost(world, pos, state);
        }
    }


    // PLACEMENT LOGIC

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction dir, BlockState adjState, IWorld world, BlockPos pos, BlockPos adjPos) {
        if (dir == Direction.DOWN) {
            // Check support, if we are unstable we should break
            if (!canRemain(world, pos)) {
                // Destroy block without drops on death - player needs to harvest and get nausea, or no item
                world.destroyBlock(pos, false);
                return Blocks.AIR.getDefaultState();
            }
        }
        return super.updatePostPlacement(state, dir, adjState, world, pos, adjPos);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        return canRemain(world, pos); // We must be stable in order to be placed
    }

    private boolean canRemain(IWorldReader world, BlockPos pos) {
        BlockState down = world.getBlockState(pos.down());
        return down.getBlock() != this // Even though we are solid, suavis must not be placed on other suavis
                   && down.isSideSolidFullSquare(world, pos.down(), Direction.UP)
                   || down.getBlock() == MnBlocks.DECEITFUL_MUD; // Mud has no solid collision hitbox
    }


    // OTHER LOGIC

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return state.get(STAGE) < 3;
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        return state.get(STAGE) == 3
               ? new ItemStack(this) // Get a suavis slice if the block isn't fully grown - get ourselves otherwise
               : new ItemStack(MnItems.RAW_SUAVIS);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {
        return getDefaultState().with(STAGE, 3);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return BOUNDS[state.get(STAGE)];
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader world, BlockPos pos, PathType type) {
        return state.get(STAGE) < 2;
    }
}
