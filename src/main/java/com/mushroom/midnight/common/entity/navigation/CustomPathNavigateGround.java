package com.mushroom.midnight.common.entity.navigation;

import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.world.World;

public class CustomPathNavigateGround extends PathNavigateGround {
    public CustomPathNavigateGround(EntityLiving owner, World world) {
        super(owner, world);
    }

    @Override
    protected PathFinder getPathFinder() {
        this.nodeProcessor = new CustomWalkNodeProcessor();
        this.nodeProcessor.setCanEnterDoors(true);
        return new PathFinder(this.nodeProcessor);
    }
}
