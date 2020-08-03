package midnight.common.registry;

import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import net.minecraftforge.common.ToolType;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import midnight.common.Midnight;
import midnight.core.util.BlockLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.*;

/**
 * Block builder class to wrap around {@link AbstractBlock.Properties} for clarity and to configure and create items
 * with the configured block.
 */
public class BlockItemBuilder<B extends Block> extends AbstractItemBuilder<BlockItemBuilder<B>, BlockItem> {
    private final Function<? super AbstractBlock.Properties, ? extends B> factory;
    private BiFunction<? super B, ? super Item.Properties, ? extends BlockItem> itemFactory = BlockItem::new;
    private Material material = Material.ROCK;
    private Function<BlockState, MaterialColor> color = state -> state.getMaterial().getColor();
    private boolean blocksMovement = true;
    private SoundType sound = SoundType.STONE;
    private ToIntFunction<BlockState> lightLevel = state -> 0;
    private double resistance = 0;
    private double hardness = 0;
    private boolean requiresTool = false;
    private boolean ticksRandomly = false;
    private double slipperiness = 0.6;
    private double speedFactor = 1;
    private double jumpFactor = 1;
    private boolean drops = true;
    private boolean solid = true;
    private boolean air = false;
    private int harvestLevel = -1;
    private ToolType harvestTool = null;
    private Block lootFrom;
    private AbstractBlock.IExtendedPositionPredicate<EntityType<?>> canEntitySpawn // func_235827_a_
            = (state, world, pos, type) -> state.isSolidSide(world, pos, Direction.UP) && state.getLightValue() < 14;
    private AbstractBlock.IPositionPredicate normalCube // func_235828_a_
            = (state, world, pos) -> state.getMaterial().isOpaque() && state.func_235785_r_(world, pos);
    private AbstractBlock.IPositionPredicate suffocation // func_235842_b_
            = (state, world, pos) -> state.getMaterial().blocksMovement() && state.func_235785_r_(world, pos);
    private AbstractBlock.IPositionPredicate blockOverlay // func_235847_c_
            = suffocation;
    private AbstractBlock.IPositionPredicate postProcessing // func_235852_d_
            = (state, world, pos) -> false;
    private AbstractBlock.IPositionPredicate emissiveRendering // func_235856_e_
            = (state, world, pos) -> false;
    private boolean variableOpacity;

    private final List<Consumer<? super B>> blockProcess = new ArrayList<>();

    private BlockItemBuilder(Function<? super AbstractBlock.Properties, ? extends B> factory) {
        this.factory = factory;
    }

    /**
     * Set the {@link Material} of the block. Default is {@link Material#ROCK}.
     */
    public BlockItemBuilder<B> material(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Set the {@link MaterialColor} of the block. Default depends on material.
     */
    public BlockItemBuilder<B> color(MaterialColor color) {
        this.color = state -> color;
        return this;
    }

    /**
     * Set the {@link MaterialColor} of the block by taking it from the specified {@link DyeColor}.
     */
    public BlockItemBuilder<B> color(DyeColor color) {
        this.color = state -> color.getMapColor();
        return this;
    }

    /**
     * Sets the {@link MaterialColor} for each separate state of the block, by using the specified mapping function.
     */
    public BlockItemBuilder<B> color(Function<BlockState, MaterialColor> color) {
        this.color = color;
        return this;
    }

    /**
     * Sets whether the block disables entities from moving through it. Default is true.
     */
    public BlockItemBuilder<B> blocksMovement(boolean blocksMovement) {
        this.blocksMovement = blocksMovement;
        return this;
    }

    /**
     * Sets the sound type of this block. Default is {@link SoundType#STONE}.
     */
    public BlockItemBuilder<B> sound(SoundType sound) {
        this.sound = sound;
        return this;
    }

    /**
     * Sets how much light the block emits. Default is 0.
     */
    public BlockItemBuilder<B> emission(int light) {
        this.lightLevel = state -> light;
        return this;
    }

    /**
     * Sets how much light each separate state of the block emits, by using the specified mapping function.
     */
    public BlockItemBuilder<B> emission(ToIntFunction<BlockState> light) {
        this.lightLevel = light;
        return this;
    }

    /**
     * Sets the mining hardness of the block. Default is 0.
     */
    public BlockItemBuilder<B> hardness(double hardness) {
        this.hardness = hardness;
        return this;
    }

    /**
     * Sets the explosion resistance of the block. Default is 0.
     */
    public BlockItemBuilder<B> resistance(double resistance) {
        this.resistance = resistance;
        return this;
    }

    /**
     * Sets the mining hardness and the explosion resistance (together: the strength) of the block to two separate
     * values.
     */
    public BlockItemBuilder<B> strength(double hardness, double resistance) {
        this.hardness = hardness;
        this.resistance = resistance;
        return this;
    }

    /**
     * Sets the mining hardness and the explosion resistance (together: the strength) of the block to the same value.
     */
    public BlockItemBuilder<B> strength(double hardness) {
        this.hardness = hardness;
        this.resistance = hardness;
        return this;
    }

    /**
     * Makes the block insta-mineable.
     */
    public BlockItemBuilder<B> noStrength() {
        this.hardness = 0;
        this.resistance = 0;
        return this;
    }

    /**
     * Makes the block immortal, unbreakable, like bedrock.
     */
    public BlockItemBuilder<B> unbreakable() {
        this.hardness = -1;
        this.resistance = 36000000;
        return this;
    }

    /**
     * Sets whether this block requires a tool to properly harvest its loot. Default is false.
     */
    public BlockItemBuilder<B> requiresTool(boolean require) {
        this.requiresTool = require;
        return this;
    }

    /**
     * Sets whether this block must receive random ticks. Default is false.
     */
    public BlockItemBuilder<B> ticksRandomly(boolean tick) {
        this.ticksRandomly = tick;
        return this;
    }

    /**
     * Sets the slipperiness of the block. Default is 0.6.
     */
    public BlockItemBuilder<B> slipperiness(double slipperiness) {
        this.slipperiness = slipperiness;
        return this;
    }

    /**
     * Sets the speed multiplier of the block. Default is 1.
     */
    public BlockItemBuilder<B> speedFactor(double speedFactor) {
        this.speedFactor = speedFactor;
        return this;
    }

    /**
     * Sets the jump height multiplier of the block. Default is 1.
     */
    public BlockItemBuilder<B> jumpFactor(double jumpFactor) {
        this.jumpFactor = jumpFactor;
        return this;
    }

    /**
     * Sets whether this block drops any items. Default is true.
     */
    public BlockItemBuilder<B> drops(boolean drops) {
        this.drops = drops;
        return this;
    }

    /**
     * Sets whether this block is a solid and fully opaque block. Default is true.
     */
    public BlockItemBuilder<B> solid(boolean solid) {
        this.solid = solid;
        return this;
    }

    /**
     * Sets whether this block is air-like. Default is false.
     */
    public BlockItemBuilder<B> air(boolean air) {
        this.air = air;
        return this;
    }

    /**
     * Sets the required harvest tool of this block. Default is null.
     */
    public BlockItemBuilder<B> harvestTool(ToolType tool) {
        this.harvestTool = tool;
        return this;
    }

    /**
     * Sets the required harvest tool tier of this block. Default is -1.
     */
    public BlockItemBuilder<B> harvestLevel(int level) {
        this.harvestLevel = level;
        return this;
    }

    /**
     * Sets the required harvest tool and its required tier of this block.
     */
    public BlockItemBuilder<B> harvestTool(ToolType tool, int level) {
        this.harvestTool = tool;
        this.harvestLevel = level;
        return this;
    }

    /**
     * Sets the drops of this block to be equal that of another block.
     */
    public BlockItemBuilder<B> lootFrom(Block block) {
        this.lootFrom = block;
        return this;
    }

    /**
     * Sets a predicate that checks whether an entity can spawn in a certain block state in the world.
     */
    public BlockItemBuilder<B> canEntitySpawn(AbstractBlock.IExtendedPositionPredicate<EntityType<?>> pred) {
        this.canEntitySpawn = pred;
        return this;
    }

    /**
     * Sets wheter an entity can spawn in this block.
     */
    public BlockItemBuilder<B> canEntitySpawn(boolean canSpawn) {
        this.canEntitySpawn = (s, w, p, t) -> canSpawn;
        return this;
    }

    /**
     * Sets a predicate that checks whether a certain state of this block in the world is a normal cube.
     */
    public BlockItemBuilder<B> normalCube(AbstractBlock.IPositionPredicate pred) {
        this.normalCube = pred;
        return this;
    }

    /**
     * Sets whether this block is a normal cube.
     */
    public BlockItemBuilder<B> normalCube(boolean normal) {
        this.normalCube = (s, w, p) -> normal;
        return this;
    }

    /**
     * Sets a predicate that checks whether a certain state of this block causes suffocation.
     */
    public BlockItemBuilder<B> suffocation(AbstractBlock.IPositionPredicate pred) {
        this.suffocation = pred;
        return this;
    }

    /**
     * Sets whether this block causes suffocation.
     */
    public BlockItemBuilder<B> suffocation(boolean suffocation) {
        this.suffocation = (s, w, p) -> suffocation;
        return this;
    }

    /**
     * Sets a predicate that checks whether a certain state of this block causes a block overlay to render when the
     * camera is in the block.
     */
    public BlockItemBuilder<B> blockOverlay(AbstractBlock.IPositionPredicate pred) {
        this.blockOverlay = pred;
        return this;
    }

    /**
     * Sets whether this block causes a block overlay to render when the camera is in the block.
     */
    public BlockItemBuilder<B> blockOverlay(boolean overlay) {
        this.blockOverlay = (s, w, p) -> overlay;
        return this;
    }

    /**
     * Sets a predicate that checks whether a certain state of this block requires post-processing after being generated
     * in the world.
     */
    public BlockItemBuilder<B> postProcess(AbstractBlock.IPositionPredicate pred) {
        this.postProcessing = pred;
        return this;
    }

    /**
     * Sets whether this block requires post-processing after being generated in the world.
     */
    public BlockItemBuilder<B> postProcess(boolean process) {
        this.postProcessing = (s, w, p) -> process;
        return this;
    }

    /**
     * Sets a predicate that checks whether a certain state of this block renders always with the same brightness.
     */
    public BlockItemBuilder<B> emissive(AbstractBlock.IPositionPredicate pred) {
        this.emissiveRendering = pred;
        return this;
    }

    /**
     * Sets whether this block renders always with the same brightness.
     */
    public BlockItemBuilder<B> emissive(boolean emissive) {
        this.emissiveRendering = (s, w, p) -> emissive;
        return this;
    }

    /**
     * Sets whether this block has variable opacity. Default is false.
     */
    public BlockItemBuilder<B> variableOpacity(boolean variableOpac) {
        this.variableOpacity = variableOpac;
        return this;
    }

    /**
     * Sets an item factory for this block.
     */
    public BlockItemBuilder<B> itemFactory(BiFunction<? super B, ? super Item.Properties, ? extends BlockItem> factory) {
        this.itemFactory = factory;
        return this;
    }

    /**
     * Adds a post-processor to process the block right before creation.
     */
    public BlockItemBuilder<B> processBlock(Consumer<? super B> processor) {
        this.blockProcess.add(processor);
        return this;
    }

    /**
     * Sets the render type of this block. Delegates to the client via a proxy.
     */
    public BlockItemBuilder<B> renderLayer(BlockLayer renderType) {
        processBlock(block -> Midnight.get().getBlockItemProxy().registerRenderLayer(block, renderType));
        return this;
    }

    /**
     * Sets the render type predicate of this block. Delegates to the client via a proxy.
     */
    public BlockItemBuilder<B> renderLayer(Predicate<BlockLayer> renderType) {
        processBlock(block -> Midnight.get().getBlockItemProxy().registerRenderLayer(block, renderType));
        return this;
    }

    /**
     * Sets the render types of this block. Delegates to the client via a proxy.
     */
    public BlockItemBuilder<B> renderLayer(BlockLayer... renderTypes) {
        Set<BlockLayer> layers = Sets.newHashSet(renderTypes);
        processBlock(block -> Midnight.get().getBlockItemProxy().registerRenderLayer(block, layers::contains));
        return this;
    }

    /**
     * Makes the {@link AbstractBlock.Properties} instance using the configuration in this builder.
     */
    protected AbstractBlock.Properties makeBlockProps() {
        AbstractBlock.Properties props = AbstractBlock.Properties.create(material, color);
        if (!blocksMovement) props.doesNotBlockMovement();
        props.sound(sound);
        props.setLightLevel(lightLevel);
        props.hardnessAndResistance((float) hardness, (float) resistance);
        if (requiresTool) props.setRequiresTool();
        if (ticksRandomly) props.tickRandomly();
        props.slipperiness((float) slipperiness);
        props.speedFactor((float) speedFactor);
        props.jumpFactor((float) jumpFactor);
        if (!drops) props.noDrops();
        if (!solid) props.notSolid();
        if (air) props.setAir();
        props.harvestLevel(harvestLevel);
        props.harvestTool(harvestTool);
        if (drops && lootFrom != null) props.lootFrom(lootFrom);
        //IDK How to fix this //props.func_235827_a_(canEntitySpawn);
        props.setOpaque(normalCube);
        props.setSuffocates(suffocation);
        props.setBlocksVision(blockOverlay);
        props.setNeedsPostProcessing(postProcessing);
        props.setEmmisiveRendering(emissiveRendering);
        if (variableOpacity) props.variableOpacity();
        return props;
    }

    /**
     * Makes and post-processes a configured block.
     */
    public B makeBlock() {
        B block = factory.apply(makeBlockProps());
        for (Consumer<? super B> processor : blockProcess) {
            processor.accept(block);
        }
        return block;
    }

    /**
     * Makes and post-processes an item for the configured block.
     */
    public BlockItem makeItem(B block) {
        BlockItem item = itemFactory.apply(block, makeItemProps());
        for (Consumer<? super BlockItem> processor : itemProcess) {
            processor.accept(item);
        }
        return item;
    }

    /**
     * Makes a pair of a configured block and its respective item.
     */
    public Pair<B, BlockItem> makeBlockAndItem() {
        B block = makeBlock();
        BlockItem item = makeItem(block);
        return Pair.of(block, item);
    }

    /**
     * Creates a builder for the given block factory. The block factory may not be null.
     */
    public static <B extends Block> BlockItemBuilder<B> builder(Function<? super AbstractBlock.Properties, ? extends B> factory) {
        return new BlockItemBuilder<>(factory);
    }
}
