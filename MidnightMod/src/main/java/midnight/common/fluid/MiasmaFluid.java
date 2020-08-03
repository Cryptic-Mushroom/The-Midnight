package midnight.common.fluid;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidAttributes;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.item.Item;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import midnight.MidnightInfo;
import midnight.common.block.MnBlocks;
import midnight.common.item.MnItems;
import midnight.common.registry.MidnightTags;
import midnight.common.registry.MnSounds;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class MiasmaFluid extends LavaFluid implements NeighborReactiveFluid {
    private static final ResourceLocation STILL_TEXTURE = new ResourceLocation(MidnightInfo.MODID, "blocks/miasma_still");
    private static final ResourceLocation FLOW_TEXTURE = new ResourceLocation(MidnightInfo.MODID, "blocks/miasma_flow");

    @Override
    public Fluid getFlowingFluid() {
        return MnFluids.FLOWING_MIASMA;
    }

    @Override
    public Fluid getStillFluid() {
        return MnFluids.MIASMA;
    }

    @Override
    public Item getFilledBucket() {
        return MnItems.MIASMA_BUCKET;
    }

    @Override
    public BlockState getBlockState(FluidState state) {
        return MnBlocks.MIASMA.getDefaultState().with(FlowingFluidBlock.LEVEL, getLevelFromState(state));
    }

    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("deprecation")
    public void animateTick(World world, BlockPos pos, FluidState state, Random random) {
        BlockPos abovePos = pos.up();
        if (world.getBlockState(abovePos).isAir() && !world.getBlockState(abovePos).isOpaqueCube(world, abovePos)) {
            if (random.nextInt(200) == 0) {
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), MnSounds.MIASMA_AMBIENT, SoundCategory.BLOCKS, 0.2f + random.nextFloat() * 0.2f, 0.9f + random.nextFloat() * 0.15f, false);
            }
        }
    }

    @Override
    public boolean isEquivalentTo(Fluid fluid) {
        return fluid.isIn(MidnightTags.Fluids.MIASMA);
    }

    @Override
    public int getTickRate(IWorldReader world) {
        return 20;
    }

    @Override
    protected void flowInto(IWorld world, BlockPos intoPos, BlockState intoBlock, Direction direction, FluidState fluidState) {
        if (direction == Direction.DOWN) {
            FluidState intoFluid = world.getFluidState(intoPos);
            if (intoFluid.isTagged(MidnightTags.Fluids.DARK_WATER) || intoFluid.isTagged(FluidTags.WATER)) {
                this.mixInto(world, intoPos, MnBlocks.NIGHTSTONE.getDefaultState());
                return;
            } else if (intoFluid.isTagged(FluidTags.LAVA)) {
                this.mixInto(world, intoPos, MnBlocks.MIASMA_SURFACE.getDefaultState());
                return;
            }
        }
        if (intoBlock.getBlock() instanceof ILiquidContainer) {
            ((ILiquidContainer) intoBlock.getBlock()).receiveFluid(world, intoPos, intoBlock, fluidState);
        } else {
            if (!intoBlock.isAir()) {
                this.beforeReplacingBlock(world, intoPos, intoBlock);
            }
            world.setBlockState(intoPos, fluidState.getBlockState(), 3);
        }
    }

    @Override
    public boolean reactWithNeighbors(World world, BlockPos pos, BlockState state) {
        boolean nearWater = false;
        for (Direction direction : Direction.values()) {
            if (direction == Direction.DOWN) continue;
            FluidState fluid = world.getFluidState(pos.offset(direction));
            if (fluid.isTagged(FluidTags.WATER)) {
                nearWater = true;
                break;
            }
        }

        if (nearWater) {
            FluidState fluid = world.getFluidState(pos);
            if (fluid.isSource()) {
                this.mixInto(world, pos, MnBlocks.TRENCHSTONE.getDefaultState());
                return false;
            }

            if (fluid.getActualHeight(world, pos) >= 0.45) {
                this.mixInto(world, pos, MnBlocks.NIGHTSTONE.getDefaultState());
                return false;
            }
        }

        return true;
    }

    private void mixInto(IWorld world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state, Constants.BlockFlags.NOTIFY_NEIGHBORS | Constants.BlockFlags.BLOCK_UPDATE);
        world.playEvent(1501, pos, 0);
    }

    @Override
    protected FluidAttributes createAttributes() {
        return FluidAttributes.builder(STILL_TEXTURE, FLOW_TEXTURE)
                              .density(3000)
                              .viscosity(3000)
                              .luminosity(15)
                              .temperature(400)
                              .sound(SoundEvents.ITEM_BUCKET_FILL_LAVA, SoundEvents.ITEM_BUCKET_EMPTY_LAVA)
                              .build(this);
    }

    public static class Flowing extends MiasmaFluid {
        public Flowing() {
        }

        @Override
        protected void fillStateContainer(StateContainer.Builder<Fluid, FluidState> container) {
            super.fillStateContainer(container);
            container.add(LEVEL_1_8);
        }

        @Override
        public int getLevel(FluidState state) {
            return state.get(LEVEL_1_8);
        }

        @Override
        public boolean isSource(FluidState state) {
            return false;
        }
    }

    public static class Source extends MiasmaFluid {
        public Source() {
        }

        @Override
        public int getLevel(FluidState state) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState state) {
            return true;
        }
    }
}
