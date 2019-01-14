package com.mushroom.midnight.common.entity.creature;

import com.mushroom.midnight.common.entity.navigation.CustomPathNavigateGround;
import com.mushroom.midnight.common.registry.ModLootTables;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityTreeHopper extends EntityMob {
    public EntityTreeHopper(World world) {
        super(world);
        setSize(0.5f, 0.5f);
    }

    @Override
    protected PathNavigate createNavigator(World world) {
        return new CustomPathNavigateGround(this, world);
    }

    @Override
    public boolean getCanSpawnHere() {
        return false;
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return ModLootTables.LOOT_TABLE_TREE_HOPPER;
    }
}
