package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.util.MidnightUtil;
import com.mushroom.midnight.common.registry.MidnightEntities;
import com.mushroom.midnight.common.registry.MidnightSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;

@SuppressWarnings({ "WeakerAccess", "deprecation" })
public abstract class PileOfEggsBlock extends Block {
    public static final SoundType PILE_OF_EGGS = new SoundType(1f, 1f, MidnightSounds.EGG_CRACKED, SoundEvents.BLOCK_STONE_STEP, SoundEvents.BLOCK_STONE_PLACE, SoundEvents.BLOCK_STONE_HIT, SoundEvents.BLOCK_STONE_FALL);
    protected static final VoxelShape BOUND_ONE_EGG = makeCuboidShape(3d, 0d, 3d, 13d, 7d, 13d);
    protected static final VoxelShape BOUND_SEVERAL_EGGS = makeCuboidShape(1d, 0d, 1d, 15d, 7d, 15d);
    public static final IntegerProperty EGGS = BlockStateProperties.EGGS_1_4;

    protected PileOfEggsBlock() {
        super(Properties.create(Material.ROCK).sound(PILE_OF_EGGS).hardnessAndResistance(-1f, 0f));
        setDefaultState(this.stateContainer.getBaseState().with(EGGS, 1));
    }

    protected abstract MobEntity createEntityForEgg(World world, BlockPos pos, BlockState state);

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (player == null) {
            return false;
        }
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() == Item.getItemFromBlock(this)) {
            if (state.get(EGGS) < 4 && !player.getCooldownTracker().hasCooldown(stack.getItem())) {
                player.getCooldownTracker().setCooldown(stack.getItem(), 10);
                if (!player.world.isRemote) {
                    if (!player.isCreative()) {
                        stack.shrink(1);
                    }
                    world.setBlockState(pos, state.with(EGGS, state.get(EGGS) + 1));
                    world.playSound(null, pos, this.soundType.getPlaceSound(), SoundCategory.BLOCKS, (this.soundType.getVolume() + 1f) / 2f, this.soundType.getPitch() * 0.8f);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void onEntityWalk(World world, BlockPos pos, Entity entity) {
        if (canTrample(world, entity)) {
            onTrample(world, pos, 0.05f);
        }
    }

    @Override
    public void onFallenUpon(World world, BlockPos pos, Entity entity, float fallDistance) {
        super.onFallenUpon(world, pos, entity, fallDistance);
        if (canTrample(world, entity)) {
            onTrample(world, pos, 1f);
        }
    }

    protected boolean canTrample(World world, Entity trampler) {
        return trampler instanceof PlayerEntity || (trampler instanceof LivingEntity && !trampler.getType().equals(MidnightEntities.STINGER) && ForgeEventFactory.getMobGriefingEvent(world, trampler));
    }

    protected void onTrample(World world, BlockPos pos, float chance) {
        if (!world.isRemote && (chance >= 1f || world.rand.nextFloat() <= chance)) {
            breakEggs(world, pos, world.getBlockState(pos));
        }
    }

    protected void breakEggs(World world, BlockPos pos, BlockState state) {
        if (world.isRemote) {
            return;
        }
        world.playSound(null, pos, PILE_OF_EGGS.getBreakSound(), SoundCategory.BLOCKS, 0.7f, 0.9f + world.rand.nextFloat() * 0.2f);
        int eggs = state.get(EGGS);
        if (eggs <= 1) {
            world.destroyBlock(pos, false);
        } else {
            world.setBlockState(pos, state.with(EGGS, --eggs), 2);
            world.playEvent(2001, pos, getStateId(state));
        }

        MobEntity creature;
        try {
            creature = createEntityForEgg(world, pos, state);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        creature.setLocationAndAngles(pos.getX() - 0.5d + world.rand.nextFloat(), pos.getY() + 0.45f, pos.getZ() - 0.5d + world.rand.nextFloat(), world.rand.nextFloat() * 360f, 0f);
        world.addEntity(creature);
    }

    @Override
    public void harvestBlock(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity tile, ItemStack stack) {
        player.addStat(Stats.BLOCK_MINED.get(this));
        player.addExhaustion(0.005f);

        float chance = (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0 ? 0.6f : 0.2f) + 0.1f * EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, player.getHeldItemMainhand());
        if (world.rand.nextFloat() <= chance) {
            MidnightUtil.spawnItemStack(world, pos, this);
        } else {
            breakEggs(world, pos, state);
        }
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        return false;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return (state.get(EGGS) > 1 ? BOUND_SEVERAL_EGGS : BOUND_ONE_EGG);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(EGGS);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }
}
