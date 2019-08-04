package com.mushroom.midnight.common.world;

import com.mushroom.midnight.common.biome.ConfigurableBiome;
import com.mushroom.midnight.common.config.MidnightConfig;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mushroom.midnight.Midnight.MIDNIGHT_MOB;

public final class MidnightEntitySpawner<T extends ConfigurableBiome> {
    private static final int MOB_COUNT_DIV = (int) Math.pow(17d, 2d);
    private static final int CHUNK_RANGE = 8;

    private static final long ANIMAL_SPAWN_INTERVAL = 400;

    private final Function<BlockPos, T> biomeFunction;
    private final PlacementLevel placementLevel;

    private final Set<ChunkPos> eligibleSpawnChunks = new HashSet<>();

    public MidnightEntitySpawner(Function<BlockPos, T> biomeFunction, PlacementLevel placementLevel) {
        this.biomeFunction = biomeFunction;
        this.placementLevel = placementLevel;
    }

    public void spawnAroundPlayers(ServerWorld world) {
        Set<ChunkPos> spawnChunks = this.computeEligibleSpawnChunks(world);

        Collection<EntityClassification> validClassifications = Arrays.stream(EntityClassification.values())
                .filter(c -> this.shouldSpawnCreatureType(world, c))
                .collect(Collectors.toList());

        if (validClassifications.isEmpty()) {
            return;
        }

        EntityClassificationCount entityCount = EntityClassificationCount.count(world, validClassifications);
        for (EntityClassification classification : validClassifications) {
            int actualCreatureCount = entityCount.getCount(classification);
            int maxCreatureCount = classification.getMaxNumberOfCreature() * spawnChunks.size() / MOB_COUNT_DIV;
            if (actualCreatureCount <= maxCreatureCount) {
                //this.spawnEntitiesOfClassification(world, spawnChunks, classification);
            }
        }
    }

    public void populateChunk(World world, int chunkX, int chunkZ, Random random) {
        int globalX = chunkX << 4;
        int globalZ = chunkZ << 4;

        int originX = globalX + 8;
        int originZ = globalZ + 8;

        BlockPos centerPos = new BlockPos(globalX + 16, 0, globalZ + 16);
        T biome = this.biomeFunction.apply(centerPos);

        // TODO Spawner
        /*SpawnerConfig spawnerConfig = biome.getSpawnerConfig();
        if (spawnerConfig.isEmpty()) {
            return;
        }

        while (random.nextFloat() < spawnerConfig.getSpawnChance()) {
            WeightedPool<Biome.SpawnListEntry> pool = spawnerConfig.getPool(EntityClassification.CREATURE);
            Biome.SpawnListEntry entry = pool.pick(world.rand);
            if (entry == null) {
                continue;
            }

            this.spawnGroupInChunk(world, originX, originZ, random, entry);
        }*/
    }

    private void spawnEntitiesOfClassification(ServerWorld world, Set<ChunkPos> spawnChunks, EntityClassification classification) {
        List<ChunkPos> shuffledChunks = new ArrayList<>(spawnChunks);
        Collections.shuffle(shuffledChunks);

        for (ChunkPos chunkPos : shuffledChunks) {
            BlockPos pos = this.getRandomPositionInChunk(world, chunkPos.x, chunkPos.z);
            BlockState state = world.getBlockState(pos);
            if (state.isNormalCube(world, pos)) {
                continue;
            }

            this.spawnEntitiesAround(world, pos, classification);
            if (classification == MIDNIGHT_MOB) { break; } // spawn slowly monsters
        }
    }

    private void spawnEntitiesAround(ServerWorld world, BlockPos pos, EntityClassification classification) {
        /*BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        int spawnedEntities = 0;

        for (int i = 0; i < 3; i++) {
            int range = 6;
            Biome.SpawnListEntry selectedEntry = null;
            ILivingEntityData livingData = null;

            mutablePos.setPos(pos);

            int spawnGroupSize = MathHelper.ceil(Math.random() * 4.0);
            for (int groupIndex = 0; groupIndex < spawnGroupSize; groupIndex++) {
                mutablePos.setPos(
                        mutablePos.getX() + world.rand.nextInt(range) - world.rand.nextInt(range),
                        mutablePos.getY() + world.rand.nextInt(1) - world.rand.nextInt(1),
                        mutablePos.getZ() + world.rand.nextInt(range) - world.rand.nextInt(range)
                );

                if (world.isPlayerWithin(mutablePos.getX() + 0.5, mutablePos.getY(), mutablePos.getZ() + 0.5, 24)) {
                    continue;
                }

                if (selectedEntry == null) {
                    T biome = this.biomeFunction.apply(pos);
                    WeightedPool<Biome.SpawnListEntry> pool = biome.getSpawnerConfig().getPool(classification);

                    selectedEntry = pool.pick(world.rand);
                    if (selectedEntry == null) {
                        break;
                    }
                }

                if (this.canSpawnAt(world, classification, mutablePos, selectedEntry)) {
                    MobEntity creature = this.createEntity(world, mutablePos, selectedEntry);

                    float spawnX = mutablePos.getX() + 0.5f;
                    float spawnY = mutablePos.getY();
                    float spawnZ = mutablePos.getZ() + 0.5f;

                    Event.Result spawnResult = ForgeEventFactory.canEntitySpawn(creature, world, spawnX, spawnY, spawnZ, null);
                    if (spawnResult != Event.Result.ALLOW && spawnResult != Event.Result.DEFAULT) {
                        continue;
                    }

                    if (creature.canSpawn(world, SpawnReason.NATURAL) && creature.isNotColliding(world)) {
                        if (!ForgeEventFactory.doSpecialSpawn(creature, world, spawnX, spawnY, spawnZ, null)) {
                            livingData = creature.onInitialSpawn(world, world.getDifficultyForLocation(new BlockPos(creature)), SpawnReason.NATURAL, livingData, null);
                        }

                        if (creature.isNotColliding(world)) {
                            spawnedEntities++;
                            world.addEntity(creature);
                        } else {
                            creature.remove();
                        }

                        if (spawnedEntities >= ForgeEventFactory.getMaxSpawnPackSize(creature)) {
                            return;
                        }
                    }
                }
            }
        }*/
    }

    private void spawnGroupInChunk(World world, int originX, int originZ, Random random, Biome.SpawnListEntry spawnEntry) {
        int groupSize = spawnEntry.minGroupCount + random.nextInt(spawnEntry.maxGroupCount - spawnEntry.minGroupCount + 1);
        int centerX = originX + random.nextInt(16);
        int centerZ = originZ + random.nextInt(16);

        int x = centerX;
        int z = centerZ;

        ILivingEntityData livingData = null;

        for (int i = 0; i < groupSize; i++) {
            for (int attempt = 0; attempt < 4; attempt++) {
                boolean spawnedEntity = false;

                BlockPos pos = this.placementLevel.getSurfacePos(world, Heightmap.Type.MOTION_BLOCKING, new BlockPos(x, 0, z));
                EntityType<?> entitytype = spawnEntry.entityType;
                if (canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.getPlacementType(entitytype), world, pos, entitytype)) {
                    MobEntity creature = this.createEntity(world, pos, spawnEntry);
                    if (ForgeEventFactory.canEntitySpawn(creature, world, x + 0.5F, pos.getY(), z + 0.5F, null) == Event.Result.DENY) {
                        creature.remove();
                        continue;
                    }

                    world.addEntity(creature);
                    livingData = creature.onInitialSpawn(world, world.getDifficultyForLocation(new BlockPos(creature)), SpawnReason.NATURAL, livingData, null);
                    spawnedEntity = true;
                }

                x += random.nextInt(5) - random.nextInt(5);
                z += random.nextInt(5) - random.nextInt(5);

                while (x < originX || x >= originX + 16 || z < originZ || z >= originZ + 16) {
                    x = centerX + random.nextInt(5) - random.nextInt(5);
                    z = centerZ + random.nextInt(5) - random.nextInt(5);
                }

                if (spawnedEntity) {
                    break;
                }
            }
        }
    }

    private MobEntity createEntity(World world, BlockPos pos, Biome.SpawnListEntry selectedEntry) {
        try {
            MobEntity creature = (MobEntity) selectedEntry.entityType.create(world);
            float yaw = world.rand.nextFloat() * 360.0F;
            creature.setLocationAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, yaw, 0.0F);
            return creature;
        } catch (Exception e) {
            throw new RuntimeException("Failed to spawn entry " + selectedEntry, e);
        }
    }

    private Set<ChunkPos> computeEligibleSpawnChunks(ServerWorld world) {
        this.eligibleSpawnChunks.clear();
        /*getPlayerStream(world).forEach(player -> {
            int playerChunkX = MathHelper.floor(player.posX) >> 4;
            int playerChunkZ = MathHelper.floor(player.posZ) >> 4;
            for (int z = -CHUNK_RANGE; z <= CHUNK_RANGE; z++) {
                for (int x = -CHUNK_RANGE; x <= CHUNK_RANGE; x++) {
                    boolean isLimit = x == -CHUNK_RANGE || x == CHUNK_RANGE || z == -CHUNK_RANGE || z == CHUNK_RANGE;
                    ChunkPos chunkPos = new ChunkPos(playerChunkX + x, playerChunkZ + z);
                    if (!isLimit && world.getWorldBorder().contains(chunkPos)) {
                        PlayerChunkMapEntry trackedChunk = world.getPlayerChunkMap().getEntry(chunkPos.x, chunkPos.z);
                        if (trackedChunk != null && trackedChunk.isSentToPlayers()) {
                            this.eligibleSpawnChunks.add(chunkPos);
                        }
                    }
                }
            }
        });*/

        return this.eligibleSpawnChunks;
    }

    private Stream<? extends PlayerEntity> getPlayerStream(World world) {
        return world.getPlayers().stream().filter(player -> !player.isSpectator());
    }

    private boolean shouldSpawnCreatureType(World world, EntityClassification classification) {
        if (!classification.getPeacefulCreature() && world.getDifficulty() == Difficulty.PEACEFUL) {
            return false;
        }
        if (classification == MIDNIGHT_MOB) {
            return world.getGameTime() % MidnightConfig.general.monsterSpawnRate.get() == 0;
        }
        return !classification.getAnimal() || world.getGameTime() % ANIMAL_SPAWN_INTERVAL == 0;
    }

    private BlockPos getRandomPositionInChunk(World world, int chunkX, int chunkZ) {
        Chunk chunk = world.getChunk(chunkX, chunkZ);
        int x = (chunkX << 4) + world.rand.nextInt(16);
        int z = (chunkZ << 4) + world.rand.nextInt(16);

        int surfaceY = MathHelper.roundUp(this.placementLevel.getSurfacePos(world, Heightmap.Type.MOTION_BLOCKING, new BlockPos(x, 0, z)).getY() + 1, 16);
        int y = world.rand.nextInt(surfaceY > 0 ? surfaceY : chunk.getTopFilledSegment() + 16 - 1);

        return new BlockPos(x, y, z);
    }

    private static boolean canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.PlacementType placeType, IWorldReader worldIn, BlockPos pos, @Nullable EntityType<?> entityTypeIn) {
        if (placeType == EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS) {
            return true; // ?world.getWorldBorder().contains(pos)?
        } else if (entityTypeIn != null && worldIn.getWorldBorder().contains(pos)) {
            return placeType.canSpawnAt(worldIn, pos, entityTypeIn);
        }
        return false;
    }
}
