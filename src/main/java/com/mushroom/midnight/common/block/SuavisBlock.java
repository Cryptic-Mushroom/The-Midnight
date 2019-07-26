package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.util.MidnightUtil;
import com.mushroom.midnight.common.registry.MidnightCriterion;
import com.mushroom.midnight.common.registry.MidnightItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potions;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nullable;
import java.util.Random;

@SuppressWarnings("deprecation")
public class SuavisBlock extends Block implements IGrowable {
    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 3);
    protected static final VoxelShape[] bounds = new VoxelShape[] {
            makeCuboidShape(0.0, 0.0, 0.0, 16.0, 3.4, 16.0),
            makeCuboidShape(0.0, 0.0, 0.0, 16.0, 7.0, 16.0),
            makeCuboidShape(0.0, 0.0, 0.0, 16.0, 13.0, 16.0),
            makeCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),
    };

    public SuavisBlock() {
        super(Properties.create(Material.GOURD, MaterialColor.LIGHT_BLUE).lightValue(12).hardnessAndResistance(1f, 0f).sound(SoundType.SLIME).tickRandomly());
        setDefaultState(getStateContainer().getBaseState().with(STAGE, 3));
    }

    @Override
    public BlockState getStateForPlacement(BlockState state, Direction facing, BlockState state2, IWorld world, BlockPos pos1, BlockPos pos2, Hand hand) {
        return getDefaultState();
    }

    @Override
    public void onFallenUpon(World world, BlockPos pos, Entity entity, float fallDistance) {
        super.onFallenUpon(world, pos, entity, fallDistance);
        if (!world.isRemote && fallDistance > 0.8f && entity instanceof LivingEntity) {
            BlockState state = world.getBlockState(pos);
            world.playSound(null, pos, SoundEvents.BLOCK_SLIME_BLOCK_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + world.rand.nextFloat() * 0.2F);
            boolean isFirstStage = state.get(STAGE) == 0;
            if (!isFirstStage && world.rand.nextInt(100) == 0) {
                world.destroyBlock(pos, true);
            } else {
                spawnAsEntity(world, pos, new ItemStack(MidnightItems.RAW_SUAVIS));
                world.setBlockState(pos, isFirstStage ? Blocks.AIR.getDefaultState() : state.with(STAGE, state.get(STAGE) - 1), 2);
                world.playEvent(2001, pos, getStateId(state));
                if (!(entity instanceof PlayerEntity) || !((PlayerEntity) entity).isCreative()) {
                    createNauseaCloud(world, pos, 0);
                }
            }
        }
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        return new ItemStack(MidnightItems.RAW_SUAVIS);
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        return false;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return state.get(STAGE) < 3;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }

    @Override
    public void harvestBlock(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        player.addStat(Stats.BLOCK_MINED.get(this));
        player.addExhaustion(0.005F);
        //harvesters.set(player);
        MidnightUtil.spawnItemStack(world, pos, state.getBlock()); //dropBlockAsItem(world, pos, state, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack));

        //harvesters.set(null);
        if (!world.isRemote && !player.isCreative() && player instanceof ServerPlayerEntity) {
            MidnightCriterion.HARVESTED_SUAVIS.trigger((ServerPlayerEntity) player);
        }
    }

    private static void createNauseaCloud(World world, BlockPos pos, int intensity) {
        AreaEffectCloudEntity entity = new AreaEffectCloudEntity(world, pos.getX(), pos.getY(), pos.getZ());
        entity.setRadius(1.5f + 0.5f * intensity);
        entity.setRadiusOnUse(-0.3f);
        entity.setWaitTime(10);
        entity.setRadiusPerTick(-entity.getRadius() / (float) entity.getDuration());
        entity.setPotion(Potions.EMPTY);
        entity.setColor(0x355796);
        entity.addEffect(new EffectInstance(Effects.NAUSEA, 20 * (intensity + 1) * 6, 0, false, true));
        world.addEntity(entity);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getPackedLightmapCoords(BlockState state, IEnviromentBlockReader worldIn, BlockPos pos) {
        int skyLight = 15;
        int blockLight = 15;
        return skyLight << 20 | blockLight << 4;
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(STAGE) < 3;
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(STAGE, state.get(STAGE) + 1), 2);
    }

    @Override
    public void tick(BlockState state, World world, BlockPos pos, Random rand) {
        // TODO check isSideSolid
        /*if (!isSideSolid(world.getBlockState(pos.down()), world, pos, Direction.UP)) {
            world.destroyBlock(pos, true);
        } else {*/
            if (state.get(STAGE) < 3 && ForgeHooks.onCropsGrowPre(world, pos, state, rand.nextInt(5) == 0)) {
                grow(world, rand, pos, state);
                ForgeHooks.onCropsGrowPost(world, pos, state);
            }
        //}
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return bounds[state.get(STAGE)];
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader world, BlockPos pos, PathType type) {
        return world.getBlockState(pos).get(STAGE) < 2;
    }

    /*@Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return MidnightItems.RAW_SUAVIS;
    }

    @Override
    public boolean canSilkHarvest() { // json loot tables
        return true;
    }

    @Override
    public void dropBlockAsItemWithChance(World world, BlockPos pos, BlockState state, float chance, int fortune) {
        super.dropBlockAsItemWithChance(world, pos, state, chance, fortune);
        if (!world.isRemote) {
            PlayerEntity player = harvesters.get();
            if (player == null || (!player.isCreative() && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, player.getHeldItemMainhand()) == 0)) {
                createNauseaCloud(world, pos, state.get(STAGE));
            }
        }
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, BlockState state, int fortune) {
        boolean isSilkTouch = harvesters.get() != null && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, harvesters.get().getHeldItemMainhand()) > 0;
        int stage = state.get(STAGE);
        if (isSilkTouch && stage == 3) {
            drops.add(new ItemStack(this));
            return;
        }
        Random rand = world instanceof World ? ((World) world).rand : RANDOM;
        int count = isSilkTouch ? stage + 1 : quantityDropped(state, fortune, rand);
        for (int i = 0; i < count; i++) {
            Item item = getItemDropped(state, rand, fortune);
            if (item != Items.AIR) {
                drops.add(new ItemStack(item, 1, damageDropped(state)));
            }
        }
    }

    private int quantityDropped(BlockState state, int fortune, Random random) { // json drop or getDrops()
        int maxSlice = state.get(STAGE) + 1;
        int minSlice = Math.min(1 + fortune, maxSlice);
        return random.nextInt(maxSlice - minSlice + 1) + minSlice;
    }*/
}
