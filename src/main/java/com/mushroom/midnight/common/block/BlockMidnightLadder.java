package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.SoundType;

public class BlockMidnightLadder extends BlockLadder implements IModelProvider {

    public BlockMidnightLadder() {
        super();
        setHardness(0.4F);
        blockSoundType = SoundType.LADDER;
        setCreativeTab(Midnight.DECORATION_TAB);
    }
}
