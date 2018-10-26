package com.mushroom.midnight.common.block;

import javax.annotation.Nullable;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;

import net.minecraft.block.BlockButton;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMidnightButton extends BlockButton implements IModelProvider {

    private final SoundEvent clickSound;
    private final SoundEvent releaseSound;

    /**
     * Assumes nothing. You must provide a click sound, release sound, sound type,
     * and wooden specification for this one to work.
     */
    public BlockMidnightButton(SoundEvent clickSoundIn, SoundEvent releaseSoundIn, SoundType soundTypeIn, boolean wooden) {

        super(wooden);
        this.setCreativeTab(Midnight.MIDNIGHT_TAB);
        this.setSoundType(soundTypeIn);
        this.clickSound = clickSoundIn;
        this.releaseSound = releaseSoundIn;

    }

    /**
     * If you want to be simple, use this. Assumes you want either a wooden or stone
     * button.
     */
    public BlockMidnightButton(boolean wooden) {

        this(wooden ? SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON : SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, wooden ? SoundEvents.BLOCK_WOOD_BUTTON_CLICK_OFF : SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, wooden ? SoundType.WOOD : SoundType.STONE, wooden);

    }

    @Override
    protected void playClickSound(@Nullable EntityPlayer player, World worldIn, BlockPos pos) {

        worldIn.playSound(player, pos, this.clickSound, SoundCategory.BLOCKS, 0.3F, 0.6F);

    }

    @Override
    protected void playReleaseSound(World worldIn, BlockPos pos) {

        worldIn.playSound((EntityPlayer) null, pos, this.releaseSound, SoundCategory.BLOCKS, 0.3F, 0.5F);

    }

}
