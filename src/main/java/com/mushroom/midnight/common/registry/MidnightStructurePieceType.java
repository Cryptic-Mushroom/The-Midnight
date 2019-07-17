package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.common.world.feature.structure.WellPieces;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

public interface MidnightStructurePieceType {
    IStructurePieceType WELL = IStructurePieceType.register(WellPieces.Piece::new, "MNWell");
}
