/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - $today.date
 */

package midnight.client.util;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.level.ColorResolver;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.IntSupplier;

public class BiomeColorCache {
    private final Object2ObjectLinkedOpenHashMap<SectionPos, int[]> cache = new Object2ObjectLinkedOpenHashMap<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public int getColor(BlockPos pos, ColorResolver resolver) {
        if(pos == null) pos = BlockPos.ZERO;
        if(Minecraft.getInstance().world == null) return 0xFFFFFF;
        BlockPos finalPos = pos;
        return getColor(pos, () -> Minecraft.getInstance().world.calculateColor(finalPos, resolver));
    }

    public int getColor(BlockPos pos, IntSupplier supplier) {
        if(pos == null) pos = BlockPos.ZERO;

        SectionPos sectPos = SectionPos.from(pos);

        lock.readLock().lock();
        int[] cacheBuf = cache.get(sectPos);
        lock.readLock().unlock();

        boolean forcePut = false;
        if(cacheBuf == null) {
            cacheBuf = new int[4096];
            Arrays.fill(cacheBuf, -1);
            if(cache.size() > Runtime.getRuntime().availableProcessors() * 16) {
                lock.writeLock().lock();
                cache.removeLast();
                lock.writeLock().unlock();
            }
            forcePut = true;
        }

        lock.writeLock().lock();
        try {
            cache.putAndMoveToFirst(sectPos, cacheBuf);
        } catch(ArrayIndexOutOfBoundsException exc) {
            if(forcePut) // Get rid of a bug in FastUtil
                cache.put(sectPos, cacheBuf);
        }
        lock.writeLock().unlock();

        int x = pos.getX() & 15;
        int y = pos.getY() & 15;
        int z = pos.getZ() & 15;

        int i = (x * 16 + z) * 16 + y;

        lock.readLock().lock();
        int cached = cacheBuf[i];
        lock.readLock().unlock();

        if(cached < 0) {
            cached = supplier.getAsInt();

            lock.writeLock().lock();
            cacheBuf[i] = cached;
            lock.writeLock().unlock();
        }

        return cached;
    }

    public void reset() {
        cache.clear();
    }

    public void chunkLoad(ChunkPos pos) {
        for(int x = -1; x <= 1; x++) {
            for(int z = -1; z <= 1; z++) {
                invalidateAll(new ChunkPos(pos.x + x, pos.z + z));
            }
        }
    }

    private void invalidateAll(ChunkPos pos) {
        for(int i = 0; i < 16; i++) {
            SectionPos sp = SectionPos.from(pos, i);

            lock.writeLock().lock();
            cache.remove(sp);
            lock.writeLock().unlock();
        }
    }
}
