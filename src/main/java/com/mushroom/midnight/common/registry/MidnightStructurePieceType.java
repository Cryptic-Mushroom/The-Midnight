package com.mushroom.midnight.common.registry;


import com.mushroom.midnight.common.world.feature.structure.ShadowrootGuardTowerPieces;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

public interface MidnightStructurePieceType {
    //IStructurePieceType WELL = IStructurePieceType.register(WellPieces.Piece::new, "midnight:Well");
    IStructurePieceType SHADOWROOT_GUARDTOWER = IStructurePieceType.register(ShadowrootGuardTowerPieces.Piece::new, "ShadowrootGuardTower");

}
