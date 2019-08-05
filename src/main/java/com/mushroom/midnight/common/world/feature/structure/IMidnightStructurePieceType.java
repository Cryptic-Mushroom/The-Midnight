package com.mushroom.midnight.common.world.feature.structure;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

import java.util.Locale;

public interface IMidnightStructurePieceType {
    IStructurePieceType SHADOWROOT_GUARDTOWER = register(ShadowrootGuardTowerPieces.Piece::new, "ShadowrootGuardTower");

    static IStructurePieceType register(IStructurePieceType p_214750_0_, String key) {
        return Registry.register(Registry.STRUCTURE_PIECE, key.toLowerCase(Locale.ROOT), p_214750_0_);
    }
}
