package midnight.common.block;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Supplier;

public class MidnightStairsBlock extends StairsBlock {
    private final Supplier<BlockState> stateSupplier;

    public MidnightStairsBlock(Supplier<BlockState> stateSupplier, Properties properties) {
        super(Blocks.AIR.getDefaultState(), properties);
        this.stateSupplier = stateSupplier;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.getShape(worldIn, pos);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        this.stateSupplier.get().getBlock().animateTick(stateIn, worldIn, pos, rand);
    }

    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        this.stateSupplier.get().onBlockClicked(worldIn, pos, player);
    }

    @Override
    public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
        this.stateSupplier.get().getBlock().onPlayerDestroy(worldIn, pos, state);
    }

    @Override
    public float getExplosionResistance() {
        return this.stateSupplier.get().getBlock().getExplosionResistance();
    }

    /*@Override
    public BlockRenderLayer getRenderLayer() {
        return this.stateSupplier.get().getBlock().getRenderLayer();
    }
*/

    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (state.getBlock() != oldState.getBlock()) { // super test is strange
            BlockState currentState = this.stateSupplier.get();
            currentState.neighborChanged(worldIn, pos, Blocks.AIR, pos, false);
            currentState.getBlock().onBlockAdded(currentState, worldIn, pos, oldState, false);
        }
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            this.stateSupplier.get().onReplaced(worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        this.stateSupplier.get().getBlock().onEntityWalk(worldIn, pos, entityIn);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        this.stateSupplier.get().getBlock().tick(state, worldIn, pos, random);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        return this.stateSupplier.get().onBlockActivated(worldIn, player, handIn, hit);
    }

    @Override
    public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
        this.stateSupplier.get().getBlock().onExplosionDestroy(worldIn, pos, explosionIn);
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        return this.stateSupplier.get().getHarvestLevel();
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(BlockState state) {
        return this.stateSupplier.get().getHarvestTool();
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return this.stateSupplier.get().getBlock().getFireSpreadSpeed(state, world, pos, face);
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return this.stateSupplier.get().getBlock().getFlammability(state, world, pos, face);
    }
}
